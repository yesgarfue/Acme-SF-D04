
package acme.features.sponsor.invoices;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.StringHelper;
import acme.client.services.AbstractService;
import acme.components.ExchangeRate;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.forms.MoneyExchange;
import acme.roles.Sponsor;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int shipId;
		Sponsorship sponsorship;

		shipId = super.getRequest().getData("shipId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(shipId);
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int shipId;
		Sponsorship sponsorship;

		shipId = super.getRequest().getData("shipId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(shipId);

		object = new Invoice();
		object.setSponsorship(sponsorship);
		object.setPublished(false);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null, "code", "Code-duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("registrationTime"))
			super.state(object.getRegistrationTime() != null, "registrationTime", "RegistrationTime-cannot-be-empty");

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			super.state(MomentHelper.isAfter(object.getDueDate(), object.getRegistrationTime()), "dueDate", "must-be-date-after-registrationTime ");
			super.state(MomentHelper.isLongEnough(object.getRegistrationTime(), object.getDueDate(), 1, ChronoUnit.MONTHS), "dueDate", "must-be-at-least-one-month-away");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			Double isAmount = object.getQuantity().getAmount();
			super.state(isAmount != null && isAmount > 0, "quantity", "quantity-cannot-be-negative-or-zero ");

			String isCurrency = object.getQuantity().getCurrency();
			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean currencyOk = Stream.of(sc.get(0).aceptedCurrencies.split(",")).anyMatch(c -> c.equals(isCurrency));
			super.state(currencyOk, "quantity", "Currency-not-supported ");
		}

		if (!super.getBuffer().getErrors().hasErrors("tax"))
			super.state(object.getTax() >= 0, "tax", "tax-must-be-positive-or-zero ");
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "isPublished");
		dataset.put("shipId", super.getRequest().getData("shipId", int.class));

		super.getResponse().addData(dataset);
	}

	// ********************************************************************************

	public MoneyExchange computeMoneyExchange(final Money amountInvoice, final String currencySponsorship) {
		assert amountInvoice != null;
		assert !StringHelper.isBlank(currencySponsorship);

		MoneyExchange result;
		RestTemplate api;
		ExchangeRate api_response;
		String inputCurrency;
		Double inputAmount, targetAmount, rate;
		Money target;
		Date moment;

		try {
			api = new RestTemplate();

			inputCurrency = amountInvoice.getCurrency();
			inputAmount = amountInvoice.getAmount();

			api_response = api.getForObject( //				
				"https://api.currencyapi.com/v3/latest?apikey={0}&base_currency={1}&currencies={2}", //
				ExchangeRate.class, //
				"cur_live_9CS0QA54yYzg4W3iJ1QQMMktAfPY2DpLVpUjpPKP", //
				inputCurrency, //
				currencySponsorship);

			assert api_response != null && api_response.getRates().containsKey(currencySponsorship);
			rate = Double.valueOf(api_response.getRates().get(currencySponsorship));
			//rate = Double.valueOf(api_response.getData().get(currencySponsorship).get("value"));
			assert rate != null;
			targetAmount = rate * inputAmount;

			target = new Money();
			target.setAmount(targetAmount);
			target.setCurrency(currencySponsorship);

			moment = api_response.getDate();

			result = new MoneyExchange();
			result.setSource(amountInvoice);
			result.setTargetCurrency(currencySponsorship);
			result.setDate(moment);
			result.setTarget(target);

			MomentHelper.sleep(1000); // HINT: need to pause the requests to the API a bit down to prevent DOS attacks
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}
}

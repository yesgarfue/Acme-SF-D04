
package acme.features.sponsor.invoices;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;
import acme.systemConfiguration.SystemConfiguration;
import acme.systemConfiguration.moneyExchange.MoneyExchangePerform;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository	repository;

	@Autowired
	private MoneyExchangePerform		currencyExchange;

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
		object.setRegistrationTime(MomentHelper.getCurrentMoment());
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
			String dateString = "2201/01/01 00:00";
			Date limitDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");

			super.state(MomentHelper.isAfter(object.getDueDate(), object.getRegistrationTime()), "dueDate", "must-be-date-after-registrationTime ");
			super.state(MomentHelper.isLongEnough(object.getRegistrationTime(), object.getDueDate(), 1, ChronoUnit.MONTHS), "dueDate", "must-be-at-least-one-month-away");
			super.state(MomentHelper.isBefore(object.getDueDate(), limitDate), "dueDate", "sponsor.invoice.form.error.dateOutOfBounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			Double isAmount = object.getQuantity().getAmount();
			super.state(isAmount != null && isAmount == 0, "quantity", "quantity-cannot-be-negative-or-zero ");
			super.state(0 < isAmount && isAmount <= 1000000.00, "quantity", "quantity-must-be-between-0-and-1000000.00 ");

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

		int shipId;

		shipId = super.getRequest().getData("shipId", int.class);
		Dataset dataset;
		double balance;
		Money f = new Money();

		Collection<Invoice> invoicesBySponsorship = this.repository.findManyPublishedInvoicesBySponsorshipId(shipId);

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "isPublished");
		dataset.put("shipId", super.getRequest().getData("shipId", int.class));
		balance = 5.23;
		dataset.put("balance", balance);

		//amountAprox = Double.parseDouble(String.format("%.2f", amountAprox));

		if (invoicesBySponsorship.isEmpty()) {
			double amountAprox = object.getSponsorship().getAmount().getAmount();
			String currencyAprox = object.getSponsorship().getAmount().getCurrency();
			//amountAprox = Double.parseDouble(String.format("%.2f", amountAprox));
			//f = object.getSponsorship().getAmount();
			//f.setAmount(amountAprox);
			dataset.put("amountAprox", amountAprox);
			dataset.put("currencyAprox", currencyAprox);
		}

		super.getResponse().addData(dataset);
	}
}

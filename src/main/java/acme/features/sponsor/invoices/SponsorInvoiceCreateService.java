
package acme.features.sponsor.invoices;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		super.bind(object, "code", "dueDate", "quantity", "tax", "link");
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
			super.state(MomentHelper.isBefore(object.getDueDate(), limitDate), "dueDate", "sponsor.invoice.form.error.date-out-of-bounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			super.state(object.getQuantity() != null && object.getQuantity().getAmount() <= 1000000.00 && object.getQuantity().getAmount() >= 0.00, "quantity", "amount-must-be-between-limits-0-and-1000000.00 ");

			String isCurrency = object.getQuantity().getCurrency();
			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean currencyOk = Stream.of(sc.get(0).aceptedCurrencies.split(",")).anyMatch(c -> c.equals(isCurrency));
			super.state(currencyOk, "quantity", "Currency-not-supported ");
		}

		if (!super.getBuffer().getErrors().hasErrors("tax")) {
			super.state(object.getTax() >= 0, "tax", "tax-must-be-positive-or-zero ");

			super.state(object.getTax() < 1000000.00, "tax", "tax-out-of-bounds ");
		}
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
		Dataset dataset;
		double accumulatedAmountInvoices = 0.00;

		String sponsorshipCurrency = object.getSponsorship().getAmount().getCurrency();
		shipId = super.getRequest().getData("shipId", int.class);
		Collection<Invoice> invoicesPublishedBySponsorship = this.repository.findManyPublishedInvoicesBySponsorshipId(shipId);

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "isPublished", "sponsorship");
		dataset.put("shipId", super.getRequest().getData("shipId", int.class));
		dataset.put("sponsorshipAmount", object.getSponsorship().getAmount().getAmount());
		dataset.put("sponsorshipCurrency", sponsorshipCurrency);

		if (invoicesPublishedBySponsorship.isEmpty())
			dataset.put("accumulatedAmountInvoices", accumulatedAmountInvoices);
		else {

			for (Invoice i : invoicesPublishedBySponsorship)
				if (i.getQuantity().getCurrency().equals(sponsorshipCurrency))
					accumulatedAmountInvoices += i.totalAmount();
				else {
					double rate = this.currencyExchange.computeMoneyExchange(i.getQuantity().getCurrency(), sponsorshipCurrency);
					accumulatedAmountInvoices += rate * i.totalAmount();
				}
			dataset.put("accumulatedAmountInvoices", accumulatedAmountInvoices);
		}

		super.getResponse().addData(dataset);
	}
}

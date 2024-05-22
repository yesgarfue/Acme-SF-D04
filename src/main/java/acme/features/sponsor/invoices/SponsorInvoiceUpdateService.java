
package acme.features.sponsor.invoices;

import java.time.temporal.ChronoUnit;
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

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Invoice inv;
		Sponsorship sponsorship;

		invoiceId = super.getRequest().getData("id", int.class);
		inv = this.repository.findOneInvoiceById(invoiceId);
		sponsorship = inv == null ? null : inv.getSponsorship();
		status = sponsorship != null && !inv.isPublished() && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);

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
			super.state(existing == null || existing.equals(object), "code", "Code-duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("registrationTime"))
			super.state(object.getRegistrationTime() != null, "registrationTime", "RegistrationTime-cannot-be-empty");

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			String dateString = "2200/12/31 23:59";
			Date limitDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");

			super.state(MomentHelper.isAfter(object.getDueDate(), object.getRegistrationTime()), "dueDate", "must-be-date-after-registrationTime ");
			super.state(MomentHelper.isLongEnough(object.getRegistrationTime(), object.getDueDate(), 1, ChronoUnit.MONTHS), "dueDate", "must-be-at-least-one-month-away");
			super.state(MomentHelper.isBefore(object.getDueDate(), limitDate), "dueDate", "sponsor.invoice.form.error.date-out-of-bounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			super.state(object.getQuantity().getAmount() >= 0, "quantity", "amount-error-negative");
			super.state(object.getQuantity().getAmount() <= 1_000_000.00, "quantity", "amount-error-too-high-salary");

			String isCurrency = object.getQuantity().getCurrency();
			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean currencyOk = Stream.of(sc.get(0).aceptedCurrencies.split(",")).anyMatch(c -> c.equals(isCurrency));
			super.state(currencyOk, "quantity", "Currency-not-supported ");
		}

		if (!super.getBuffer().getErrors().hasErrors("tax")) {
			super.state(object.getTax() >= 0, "tax", "tax-must-be-positive-or-zero ");
			super.state(object.getTax() <= 1_000_000.00, "tax", "tax-out-of-bounds ");
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

		Dataset dataset;
		int id;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "isPublished");
		dataset.put("shipId", object.getSponsorship().getId());
		dataset.put("quantityTax", object.totalAmount());

		super.getResponse().addData(dataset);
	}
}

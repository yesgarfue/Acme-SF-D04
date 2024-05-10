
package acme.features.sponsor.invoices;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class SponsorInvoicePublishService extends AbstractService<Sponsor, Invoice> {

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
		sponsorship = this.repository.findOneSponsorshipByInvoiceId(invoiceId);
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(Sponsor.class);

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

		object.setPublished(true);
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
}

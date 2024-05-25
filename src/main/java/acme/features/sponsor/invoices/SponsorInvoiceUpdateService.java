
package acme.features.sponsor.invoices;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
	private SponsorInvoiceRepository	repository;

	@Autowired
	private SponsorInvoiceCreateService	createService;


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

		if (!super.getBuffer().getErrors().hasErrors("registrationTime")) {
			String dateString = "2000/01/01 01:00";
			Date minimunDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");
			super.state(object.getRegistrationTime() != null, "registrationTime", "RegistrationTime-cannot-be-empty");
			super.state(MomentHelper.isAfter(object.getRegistrationTime(), minimunDate), "registrationTime", "registrationTime-out-of-bounds ");
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

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			String dateString = "2200/12/31 23:59";
			Date limitDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");
			super.state(MomentHelper.isAfter(object.getDueDate(), object.getRegistrationTime()), "dueDate", "must-be-date-after-registrationTime ");
			super.state(MomentHelper.isBeforeOrEqual(object.getDueDate(), limitDate), "dueDate", "sponsor.invoice.form.error.date-out-of-bounds");
		}

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {

			LocalDateTime registrationDateLocal = object.getRegistrationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			int yearValue = registrationDateLocal.getYear();
			int monthValue = registrationDateLocal.getMonthValue();
			int dayValue = registrationDateLocal.getDayOfMonth();

			Date date30 = this.createService.sumarDias(registrationDateLocal, 30);
			Date date31 = this.createService.sumarDias(registrationDateLocal, 31);
			Date date28 = this.createService.sumarDias(registrationDateLocal, 28);
			Date date29 = this.createService.sumarDias(registrationDateLocal, 29);

			boolean esBisiesto = yearValue % 4 == 0 && (yearValue % 100 != 0 || yearValue % 400 == 0);

			switch (monthValue) {
			case 1:
			case 2:
				if (esBisiesto) {
					if (monthValue == 1)
						switch (dayValue) {
						case 30:
						case 31:
							super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date30), "dueDate", "must-be-at-least-one-month-away");
							break;
						default:
							super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date31), "dueDate", "must-be-at-least-one-month-away");
							break;
						}
					else
						super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date29), "dueDate", "must-be-at-least-one-month-away");
				} else if (monthValue == 1) {
					if (dayValue == 29)
						super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date30), "dueDate", "must-be-at-least-one-month-away");
					else {
						if (dayValue == 30 || dayValue == 31)
							super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date28), "dueDate", "must-be-at-least-one-month-away");
						super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date31), "dueDate", "must-be-at-least-one-month-away");
					}
				} else
					super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date28), "dueDate", "must-be-at-least-one-month-away");
				break;
			case 12:
			case 10:
			case 8:
			case 7:
			case 5:
			case 3:
				if (dayValue == 31)
					super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date30), "dueDate", "must-be-at-least-one-month-away");
				super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date31), "dueDate", "must-be-at-least-one-month-away");
				break;
			default:
				super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date30), "dueDate", "must-be-at-least-one-month-away");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("sponsorship")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getSponsorship().getCode());
			super.state(existing != null, "sponsorship", "Invalid-Sponsorship-code");
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

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "isPublished");
		dataset.put("shipId", object.getSponsorship().getId());
		dataset.put("quantityTax", object.totalAmount());

		super.getResponse().addData(dataset);
	}
}

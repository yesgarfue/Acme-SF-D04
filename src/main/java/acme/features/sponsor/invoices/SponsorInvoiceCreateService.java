
package acme.features.sponsor.invoices;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
	private MoneyExchangePerform		moneyExchange;


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

		if (!super.getBuffer().getErrors().hasErrors("registrationTime")) {
			String dateString = "2000/01/01 01:00";
			Date minimunDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");
			super.state(object.getRegistrationTime() != null, "registrationTime", "RegistrationTime-cannot-be-empty");
			super.state(MomentHelper.isAfter(object.getRegistrationTime(), minimunDate), "registrationTime", "registrationTime-out-of-bounds ");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			super.state(object.getQuantity().getAmount() > 0, "quantity", "quantity-error-not-validated-or-negative");
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

			Date date30 = this.sumarDias(registrationDateLocal, 30);
			Date date31 = this.sumarDias(registrationDateLocal, 31);
			Date date28 = this.sumarDias(registrationDateLocal, 28);
			Date date29 = this.sumarDias(registrationDateLocal, 29);

			boolean esBisiesto = yearValue % 4 == 0 && (yearValue % 100 != 0 || yearValue % 400 == 0);

			switch (monthValue) {
			case 1:
			case 2:
				if (esBisiesto) {
					if (monthValue == 1) {
						if (dayValue == 30 || dayValue == 31)
							super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date30), "dueDate", "must-be-at-least-one-month-away");
						super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), date31), "dueDate", "must-be-at-least-one-month-away");
					} else
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

		int shipId;
		Dataset dataset;

		String sponsorshipCurrency = object.getSponsorship().getAmount().getCurrency();
		shipId = super.getRequest().getData("shipId", int.class);
		Collection<Invoice> invoicesPublishedBySponsorship = this.repository.findManyPublishedInvoicesBySponsorshipId(shipId);

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "isPublished", "sponsorship");
		dataset.put("shipId", super.getRequest().getData("shipId", int.class));
		dataset.put("sponsorshipAmount", object.getSponsorship().getAmount().getAmount());
		dataset.put("sponsorshipCurrency", sponsorshipCurrency);
		dataset.put("accumulatedAmountInvoices", this.moneyExchange.totalMoneyExchangeInvoices(invoicesPublishedBySponsorship, sponsorshipCurrency));

		super.getResponse().addData(dataset);
	}

	public Date sumarDias(final LocalDateTime registrationDateLocal1, final int dias) {
		LocalDateTime dateTemp1;
		ZoneId zoneId1 = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime1;
		Date dateVar1;

		dateTemp1 = registrationDateLocal1.plusDays(dias);
		zonedDateTime1 = dateTemp1.atZone(zoneId1);
		dateVar1 = Date.from(zonedDateTime1.toInstant());

		return dateVar1;
	}
}


package acme.features.sponsor.invoices;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;
import acme.systemConfiguration.moneyExchange.MoneyExchangePerform;

@Service
public class SponsorInvoiceListService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository	repository;

	@Autowired
	private MoneyExchangePerform		moneyExchange;


	@Override
	public void authorise() {
		boolean status;
		int shipId;
		Sponsorship sponsorship;

		int sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		shipId = super.getRequest().getData("shipId", int.class);
		sponsorship = this.repository.findOneSponsorshipByIdAndSponsorId(shipId, sponsorId);
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> objects;
		int shipId;

		shipId = super.getRequest().getData("shipId", int.class);
		objects = this.repository.findManyInvoicesByShipId(shipId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;
		double totalAmount;
		String payload;
		int shipId = object.getSponsorship().getId();

		totalAmount = object.totalAmount();
		Boolean temp = object.isPublished();

		String sponsorshipCurrency = object.getSponsorship().getAmount().getCurrency();
		Collection<Invoice> invoicesPublishedBySponsorship = this.repository.findManyPublishedInvoicesBySponsorshipId(shipId);
		double accumulatedAmountInvoices = this.moneyExchange.totalMoneyExchangeInvoices(invoicesPublishedBySponsorship, sponsorshipCurrency);

		dataset = super.unbind(object, "code", "registrationTime", "sponsorship.code", "isPublished");

		if (temp.equals(true)) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("state", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("state", "No");

		dataset.put("totalAmount", totalAmount);

		payload = String.format(//
			"%s; %s; %s; %s;%s; %s; %s; %s;", //
			accumulatedAmountInvoices,//
			object.getDueDate(), //
			object.getQuantity(),//
			object.getTax(),//
			object.getLink(),//
			sponsorshipCurrency,//
			object.getSponsorship().getAmount().getAmount(),//
			shipId);

		dataset.put("payload", payload);

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<Invoice> objects) {
		assert objects != null;

		int shipId;
		Sponsorship sponsorship;
		final boolean showCreate;

		shipId = super.getRequest().getData("shipId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(shipId);
		showCreate = super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().addGlobal("shipId", shipId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}
}

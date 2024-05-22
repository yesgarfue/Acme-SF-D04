
package acme.features.sponsor.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	@Autowired
	private SponsorDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		String currencyDefault = this.repository.findCurrencyDefault();
		SponsorDashboard dashboard = this.dash(currencyDefault, sponsorId);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		final int sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		Dataset dataset;

		SponsorDashboard usd, gbp;
		usd = this.dash("USD", sponsorId);
		gbp = this.dash("GBP", sponsorId);

		dataset = super.unbind(object, "minimumSponsorshipsAmount", "maximunSponsorshipsAmount", "averageSponsorshipsAmount", "deviationSponsorshipsAmount", "minimumInvoicesQuantity", "maximunInvoicesQuantity", "averageInvoicesQuantity",
			"deviationInvoicesQuantity", "totalNumInvoicesWithTaxLessOrEqualTo21", "totalNumInvoicesWithLink");

		dataset.put("usdMinimumSponsorshipsAmount", usd.getMinimumSponsorshipsAmount());
		dataset.put("usdMaximunSponsorshipsAmount", usd.getMaximunSponsorshipsAmount());
		dataset.put("usdAverageSponsorshipsAmount", usd.getAverageSponsorshipsAmount());
		dataset.put("usdDeviationSponsorshipsAmount", usd.getDeviationSponsorshipsAmount());

		dataset.put("usdMinimumInvoicesQuantity", usd.getMinimumInvoicesQuantity());
		dataset.put("usdMaximunInvoicesQuantity", usd.getMaximunInvoicesQuantity());
		dataset.put("usdAverageInvoicesQuantityt", usd.getAverageInvoicesQuantity());
		dataset.put("usdDeviationInvoicesQuantity", usd.getDeviationInvoicesQuantity());

		dataset.put("gbpMinimumSponsorshipsAmount", gbp.getMinimumSponsorshipsAmount());
		dataset.put("gbpMaximunSponsorshipsAmount", gbp.getMaximunSponsorshipsAmount());
		dataset.put("gbpAverageSponsorshipsAmount", gbp.getAverageSponsorshipsAmount());
		dataset.put("gbpDeviationSponsorshipsAmount", gbp.getDeviationSponsorshipsAmount());

		dataset.put("gbpMinimumInvoicesQuantity", gbp.getMinimumInvoicesQuantity());
		dataset.put("gbpMaximunInvoicesQuantity", gbp.getMaximunInvoicesQuantity());
		dataset.put("gbpAverageInvoicesQuantityt", gbp.getAverageInvoicesQuantity());
		dataset.put("gbpDeviationInvoicesQuantity", gbp.getDeviationInvoicesQuantity());

		super.getResponse().addData(dataset);
	}

	private SponsorDashboard dash(final String currencyTarget, final int id) {

		SponsorDashboard dashboardNew, temp;

		Collection<Sponsorship> allSponsorships = this.repository.findSponsorshipsBySponsorId(id);
		Map<String, Collection<Sponsorship>> collectionSPcurrencies = new HashMap<>();
		for (Sponsorship sp : allSponsorships) {
			String currency = sp.getAmount().getCurrency();
			collectionSPcurrencies.computeIfAbsent(currency, k -> new ArrayList<>()).add(sp);
		}

		Collection<Invoice> allInvoices = this.repository.totalNumberOfInvoicesOfSponsor(id);
		Map<String, Collection<Invoice>> collectionINVcurrencies = new HashMap<>();
		for (Invoice inv : allInvoices) {
			String currency = inv.getQuantity().getCurrency();
			collectionINVcurrencies.computeIfAbsent(currency, k -> new ArrayList<>()).add(inv);
		}

		double tempEUR = 0;
		int invoicesTax = 0;

		Double minimum = null;
		Double maximum = null;
		Double average = null;
		Double deviation = null;

		Double minimumQua = null;
		Double maximumQua = null;
		Double averageQua = null;
		Double deviationQua = null;

		Collection<Invoice> invoices = this.repository.totalNumberOfInvoicesOfSponsor(id);
		for (Invoice inv : invoices)
			if (inv.getTax() >= 21.00)
				invoicesTax++;

		int totalSponsorshipLink = this.repository.totalNumberSponsorshipWithLink(id);

		temp = new SponsorDashboard();
		temp = this.initializeDash(temp);

		Collection<Sponsorship> sponsorshipCurrency = collectionSPcurrencies.get(currencyTarget);
		if (sponsorshipCurrency == null) {
			minimum = temp.getMinimumSponsorshipsAmount();
			maximum = temp.getMaximunSponsorshipsAmount();
			average = temp.getAverageSponsorshipsAmount();
			deviation = temp.getDeviationSponsorshipsAmount();
		} else {
			minimum = this.repository.minimumSponsorship(id, currencyTarget);
			maximum = this.repository.maximumSponsorship(id, currencyTarget);
			average = this.repository.averageSponsorship(id, currencyTarget);

			double numSponsorship = sponsorshipCurrency.size();
			for (Sponsorship sp : sponsorshipCurrency)
				tempEUR += Math.pow(sp.getAmount().getAmount() - average, 2);
			deviation = Math.sqrt(tempEUR / numSponsorship);
		}

		Collection<Invoice> invoiceCurrency = collectionINVcurrencies.get(currencyTarget);
		if (invoiceCurrency == null) {
			minimumQua = temp.getMinimumInvoicesQuantity();
			maximumQua = temp.getMaximunInvoicesQuantity();
			averageQua = temp.getAverageInvoicesQuantity();
			deviationQua = temp.getDeviationInvoicesQuantity();
		} else {
			minimumQua = this.repository.minimumInvoices(id);
			maximumQua = this.repository.maximumInvoices(id);
			averageQua = this.repository.averageInvoices(id);

			double numInvoices = invoiceCurrency.size();
			tempEUR = 0;
			for (Invoice inv : invoiceCurrency)
				tempEUR += Math.pow(inv.getQuantity().getAmount() - averageQua, 2);
			deviationQua = Math.sqrt(tempEUR / numInvoices);
		}

		dashboardNew = new SponsorDashboard();

		dashboardNew.setTotalNumInvoicesWithTaxLessOrEqualTo21(invoicesTax);
		dashboardNew.setTotalNumInvoicesWithLink(totalSponsorshipLink);

		dashboardNew.setMinimumSponsorshipsAmount(minimum);
		dashboardNew.setMaximunSponsorshipsAmount(maximum);
		dashboardNew.setAverageSponsorshipsAmount(average);
		dashboardNew.setDeviationSponsorshipsAmount(deviation);

		dashboardNew.setMinimumInvoicesQuantity(minimumQua);
		dashboardNew.setMaximunInvoicesQuantity(maximumQua);
		dashboardNew.setAverageInvoicesQuantity(averageQua);
		dashboardNew.setDeviationInvoicesQuantity(deviationQua);

		return dashboardNew;
	}

	private SponsorDashboard initializeDash(final SponsorDashboard dash) {

		dash.setTotalNumInvoicesWithTaxLessOrEqualTo21(0);
		dash.setTotalNumInvoicesWithLink(0);

		dash.setMinimumSponsorshipsAmount(0);
		dash.setMaximunSponsorshipsAmount(0);
		dash.setAverageSponsorshipsAmount(0);
		dash.setDeviationSponsorshipsAmount(0);

		dash.setMinimumInvoicesQuantity(0);
		dash.setMaximunInvoicesQuantity(0);
		dash.setAverageInvoicesQuantity(0);
		dash.setDeviationInvoicesQuantity(0);

		return dash;
	}
}

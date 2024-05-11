
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
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

		int invoicesTax = 0;

		Double minimumAmount = null;
		Double maximumAmount = null;
		Double averageAmount = null;
		int totalSponsorship = 0;
		double deviationSponsorship = 0;
		Double deviationAmount = null;

		Double minimumQuanty = null;
		Double maximumQuanty = null;
		Double averageQuanty = null;
		int totalInvoices = 0;
		double deviationInvoice = 0;
		Double deviationQuanty = null;

		//Calculation of total invoices with Tax<=21%
		Collection<Invoice> invoices = this.repository.totalNumberOfInvoicesOfSponsor(sponsorId);
		for (Invoice inv : invoices)
			if (inv.getTax() >= 21.00)
				invoicesTax++;

		int totalSponsorshipLink = this.repository.totalNumberSponsorshipWithLink(sponsorId);

		//SPONSORSHIP
		minimumAmount = this.repository.minimumEstimatedCostOfSponsorshipOfSponsor(sponsorId);
		maximumAmount = this.repository.maximumEstimatedCostOfSponsorshipOfSponsor(sponsorId);
		averageAmount = this.repository.averageEstimatedCostOfSponsorshipOfSponsor(sponsorId);

		//Calculation of the standard deviation of the amount of all sponsorships
		Collection<Sponsorship> sponsorships = this.repository.findSponsorshipsBySponsorId(sponsorId);
		totalSponsorship = this.repository.totalNumberSponsorshipBySponsor(sponsorId);
		for (Sponsorship sp : sponsorships)
			deviationSponsorship += Math.pow(sp.getAmount().getAmount() - averageAmount, 2);

		deviationAmount = Math.sqrt(deviationSponsorship / totalSponsorship);

		//INVOICES
		minimumQuanty = this.repository.minimumQuantityForInvoicesBySponsorId(sponsorId);
		maximumQuanty = this.repository.maximumQuantityForInvoicesBySponsorId(sponsorId);
		averageQuanty = this.repository.averageQuantityForInvoicesBySponsorId(sponsorId);

		//Calculation of the standard deviation of the quantity of all invoices
		totalInvoices = invoices.size();
		for (Invoice inv : invoices)
			deviationInvoice += Math.pow(inv.getQuantity().getAmount() - averageQuanty, 2);

		deviationQuanty = Math.sqrt(deviationInvoice / totalInvoices);

		SponsorDashboard dashboard = new SponsorDashboard();

		dashboard.setTotalNumInvoicesWithTaxLessOrEqualTo21(invoicesTax);
		dashboard.setTotalNumInvoicesWithLink(totalSponsorshipLink);

		dashboard.setMinimumSponsorshipsAmount(minimumAmount);
		dashboard.setMaximunSponsorshipsAmount(maximumAmount);
		dashboard.setAverageSponsorshipsAmount(averageAmount);
		dashboard.setDeviationSponsorshipsAmount(deviationAmount);

		dashboard.setMinimumInvoicesQuantity(minimumQuanty);
		dashboard.setMaximunInvoicesQuantity(maximumQuanty);
		dashboard.setAverageInvoicesQuantity(averageQuanty);
		dashboard.setDeviationInvoicesQuantity(deviationQuanty);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "minimumSponsorshipsAmount", "maximunSponsorshipsAmount", "averageSponsorshipsAmount", "deviationSponsorshipsAmount", "minimumInvoicesQuantity", "maximunInvoicesQuantity", "averageInvoicesQuantity",
			"deviationInvoicesQuantity", "totalNumInvoicesWithTaxLessOrEqualTo21", "totalNumInvoicesWithLink");

		super.getResponse().addData(dataset);
	}
}


package acme.features.sponsor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	@Autowired
	private SponsorDashboardRepository repository;


	@Override
	public void authorise() {
		//final boolean status = super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int sponsorId;
		int limit = 21;

		Double averageAmount = null;
		Double deviationAmount = null;
		Double minimumAmount = null;
		Double maximumAmount = null;

		Double averageQuanty = null;
		Double deviationQuanty = null;
		Double minimumQuanty = null;
		Double maximumQuanty = null;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();

		int totalInvoicesTax = this.repository.totalNumberOfInvoicesTaxLessThanOrEqualTo(sponsorId);
		int totalSponsorshipLink = this.repository.totalNumberSponsorshipWithLink(sponsorId);
		int totalSponsorship = this.repository.totalNumberSponsorshipBySponsor(sponsorId);

		if (totalSponsorship > 0) {
			averageAmount = this.repository.averageEstimatedCostOfSponsorshipOfSponsor(sponsorId);
			deviationAmount = this.repository.deviationEstimatedCostOfSponsorshipOfSponsor(sponsorId);
			minimumAmount = this.repository.minimumEstimatedCostOfSponsorshipOfSponsor(sponsorId);
			maximumAmount = this.repository.maximumEstimatedCostOfSponsorshipOfSponsor(sponsorId);
		}

		if (this.repository.totalNumberOfInvoicesOfSponsor(sponsorId) > 0) {
			averageQuanty = this.repository.averageCostOfProjectsOfManager(sponsorId);
			deviationQuanty = this.repository.deviationCostOfProjectsOfManager(sponsorId);
			minimumQuanty = this.repository.minimumCostOfProjectsOfManager(sponsorId);
			maximumQuanty = this.repository.maximumCostOfProjectsOfManager(sponsorId);
		}

		SponsorDashboard dashboard = new SponsorDashboard();

		dashboard.setTotalNumInvoicesWithLink(totalSponsorshipLink);
		dashboard.setTotalNumInvoicesWithTaxLessOrEqualTo21(totalInvoicesTax);

		dashboard.setAverageSponsorshipsAmount(averageAmount);
		dashboard.setDeviationSponsorshipsAmount(deviationAmount);
		dashboard.setMinimumSponsorshipsAmount(minimumAmount);
		dashboard.setMaximunSponsorshipsAmount(maximumAmount);

		dashboard.setAverageInvoicesQuantity(averageQuanty);
		dashboard.setDeviationInvoicesQuantity(deviationQuanty);
		dashboard.setMinimumInvoicesQuantity(minimumQuanty);
		dashboard.setMaximunInvoicesQuantity(maximumQuanty);

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

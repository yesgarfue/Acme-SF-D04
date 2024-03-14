
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.enumerate.Priority;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		final boolean status = super.getRequest().getPrincipal().hasRole(Manager.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ManagerDashboard dashboard;
		Integer numberMust;
		Integer numberShould;
		Integer numberCould;
		Integer numberWont;

		Double averageEstimate = null;
		Double deviationEstimate = null;
		Double minimumEstimate = null;
		Double maximumEstimate = null;
		Double averageCost = null;
		Double deviationCost = null;
		Double minimumCost = null;
		Double maximumCost = null;
		final int managerId;
		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		numberMust = this.repository.totalNumberOfMustOfUserStoryOfManager(managerId, Priority.MUST);
		numberShould = this.repository.totalNumberOfShouldOfUserStoryOfManager(managerId, Priority.SHOULD);
		numberCould = this.repository.totalNumberOfCouldOfUserStoryOfManager(managerId, Priority.COULD);
		numberWont = this.repository.totalNumberOfWontOfUserStoryOfManager(managerId, Priority.WONT);

		double totalNumberOfUserStories = numberMust + numberShould + numberCould + numberWont;

		if (totalNumberOfUserStories > 0) {
			averageEstimate = this.repository.averageEstimatedCostOfUserStoriesOfManager(managerId);
			deviationEstimate = this.repository.deviationEstimatedCostOfUserStoriesOfManager(managerId);
			minimumEstimate = this.repository.minimumEstimatedCostOfUserStoriesOfManager(managerId);
			maximumEstimate = this.repository.maximumEstimatedCostOfUserStoriesOfManager(managerId);
		}
		if (this.repository.totalNumberOfProjectsOfManager(managerId) > 0) {
			averageCost = this.repository.averageCostOfProjectsOfManager(managerId);
			deviationCost = this.repository.deviationCostOfProjectsOfManager(managerId);
			minimumCost = this.repository.minimumCostOfProjectsOfManager(managerId);
			maximumCost = this.repository.maximumCostOfProjectsOfManager(managerId);

		}

		dashboard = new ManagerDashboard();
		dashboard.setNumberMust(numberMust);
		dashboard.setNumberShould(numberShould);
		dashboard.setNumberCould(numberCould);
		dashboard.setNumberWont(numberWont);
		dashboard.setAverageEstimate(averageEstimate);
		dashboard.setDeviationEstimate(deviationEstimate);
		dashboard.setMinimumEstimate(minimumEstimate);
		dashboard.setMaximumEstimate(maximumEstimate);
		dashboard.setAverageCost(averageCost);
		dashboard.setDeviationCost(deviationCost);
		dashboard.setMinimumCost(minimumCost);
		dashboard.setMaximumCost(maximumCost);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "numberMust", "numberShould", "numberCould", "numberWont", "averageEstimate", "deviationEstimate", "minimumEstimate", "maximumEstimate", "averageCost", "deviationCost", "minimumCost", "maximumCost");
		dataset.put("totalNumberOfProjectsOfManager", this.repository.totalNumberOfProjectsOfManager(super.getRequest().getPrincipal().getActiveRoleId()));
		super.getResponse().addData(dataset);
	}

}

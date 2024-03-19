
package acme.features.developer.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		final boolean status = super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		DeveloperDashboard dashboard;

		Integer totalNumberOfTrainingModules = null;
		Integer totalNumberOfTrainingSessions = null;

		Double averageTimeTrainingModule = null;
		Double deviationTimeTrainingModule = null;
		Double maxTimeTrainingModule = null;
		Double minTimeTrainingModule = null;

		final int developerId;
		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		totalNumberOfTrainingModules = this.repository.totalNumberOfTrainingModules(developerId);
		totalNumberOfTrainingSessions = this.repository.totalNumberOfTrainingSessions(developerId);

		if (totalNumberOfTrainingModules > 0) {
			averageTimeTrainingModule = this.repository.averageTimeTrainingModule(developerId);
			deviationTimeTrainingModule = this.repository.deviationTimeTrainingModule(developerId);
			maxTimeTrainingModule = this.repository.maxTimeTrainingModule(developerId);
			minTimeTrainingModule = this.repository.minTimeTrainingModule(developerId);
		}

		dashboard = new DeveloperDashboard();
		dashboard.setAverageTimeTrainingModule(averageTimeTrainingModule);
		dashboard.setDeviationTimeTrainingModule(deviationTimeTrainingModule);
		dashboard.setMaxTimeTrainingModule(maxTimeTrainingModule);
		dashboard.setMinTimeTrainingModule(minTimeTrainingModule);
		dashboard.setTotalTrainingModules(totalNumberOfTrainingModules);
		dashboard.setTotalTrainingSessions(totalNumberOfTrainingSessions);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalTrainingModules", "totalTrainingSessions", "averageTimeTrainingModule", "deviationTimeTrainingModule", "maxTimeTrainingModule", "minTimeTrainingModule");
		super.getResponse().addData(dataset);
	}

}


package acme.features.developer.TrainingSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionShowService extends AbstractService<Developer, TrainingSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingSession trainingSession;
		final TrainingModule trainingModule;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		trainingSession = this.repository.findTrainingSessionById(masterId);
		trainingModule = trainingSession == null ? null : trainingSession.getTrainingModule();
		developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingSession != null && //
			trainingModule != null && //
			super.getRequest().getPrincipal().hasRole(developer) && //
			trainingModule.getDeveloper().getId() == super.getRequest().getPrincipal().getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingSessionById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;
		TrainingModule trainingModule;
		boolean draftMode;

		trainingModule = object.getTrainingModule();
		draftMode = trainingModule.getDraftMode();

		dataset = super.unbind(object, "code", "startDate", "endDate", "location", "instructor", "contactEmail", "optionalLink", "draftMode");
		dataset.put("masterId", trainingModule.getId());
		dataset.put("draftMode", draftMode);
		dataset.put("trainingModule", trainingModule);

		super.getResponse().addData(dataset);
	}

}

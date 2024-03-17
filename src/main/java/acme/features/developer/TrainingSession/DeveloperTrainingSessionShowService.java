
package acme.features.developer.TrainingSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
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

		SelectChoices choices;
		Dataset dataset;
		int developerId;
		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		final Collection<TrainingModule> trainingModules = this.repository.findTrainingModulesByDeveloperId(developerId);

		choices = SelectChoices.from(trainingModules, "code", object.getTrainingModule());
		dataset = super.unbind(object, "code", "startDate", "endDate", "location", "instructor", "contactEmail", "optionalLink", "draftMode");
		dataset.put("trainingModule", choices.getSelected().getKey());
		dataset.put("trainingModules", choices);

		super.getResponse().addData(dataset);
	}

}

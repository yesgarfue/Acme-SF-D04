
package acme.features.developer.TrainingSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionDeleteService extends AbstractService<Developer, TrainingSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		TrainingSession trainingSession;

		trainingSession = this.repository.findTrainingSessionById(super.getRequest().getData("id", int.class));

		status = trainingSession != null && trainingSession.getDraftMode() && super.getRequest().getPrincipal().hasRole(trainingSession.getTrainingModule().getDeveloper());

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
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startDate", "endDate", "location", "instructor", "contactEmail", "optionalLink", "draftMode", "trainingModule");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;
		int developerId;
		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		final Collection<TrainingModule> trainingModules = this.repository.findTrainingModulesByDeveloperId(developerId);

		choices = SelectChoices.from(trainingModules, "code", object.getTrainingModule());
		dataset = super.unbind(object, "code", "startDate", "endDate", "location", "instructor", "contactEmail", "optionalLink");
		dataset.put("trainingModule", choices.getSelected().getKey());
		dataset.put("trainingModules", choices);
		dataset.put("draftMode", object.getDraftMode());
		super.getResponse().addData(dataset);

	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}

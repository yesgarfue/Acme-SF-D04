
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.training.TrainingModule;
import acme.enumerate.Difficulty;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		TrainingModule trainingModule;

		trainingModule = this.repository.findTrainingModuleById(super.getRequest().getData("id", int.class));

		status = trainingModule != null && super.getRequest().getPrincipal().hasRole(trainingModule.getDeveloper());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = SerializationUtils.clone(this.repository.findTrainingModuleById(id));

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updatedMoment", "optionalLink", "totalTime", "draftMode", "project");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;

			existing = this.repository.findTrainingModuleByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "developer.trainingModel.error.code.duplicated");
		}
		super.state(!this.repository.findTrainingSessionByTrainingModuleId(object.getId()).isEmpty(), "*", "assistant.tutorial.form.error.sessions");
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;
		SelectChoices choices2;
		final Collection<Project> projects = this.repository.findAllProject();

		choices = SelectChoices.from(projects, "code", object.getProject());
		choices2 = SelectChoices.from(Difficulty.class, object.getDifficultyLevel());
		dataset = super.unbind(object, "code", "creationMoment", "details", "updatedMoment", "optionalLink", "totalTime", "draftMode");
		dataset.put("difficultyLevels", choices2);
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		super.getResponse().addData(dataset);
	}

}

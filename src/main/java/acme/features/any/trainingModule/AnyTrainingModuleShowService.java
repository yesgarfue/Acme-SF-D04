
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.training.TrainingModule;
import acme.enumerate.Difficulty;

@Service
public class AnyTrainingModuleShowService extends AbstractService<Any, TrainingModule> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		final TrainingModule trainingModule;

		id = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findTrainingModuleById(id);
		status = trainingModule != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTrainingModuleById(id);

		super.getBuffer().addData(object);
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

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updatedMoment", "optionalLink", "totalTime", "draftMode");
		dataset.put("authenticated", super.getRequest().getPrincipal().hasRole(Authenticated.class));
		dataset.put("difficultyLevels", choices2);
		dataset.put("project", choices.getSelected().getKey());
		super.getResponse().addData(dataset);
	}
}

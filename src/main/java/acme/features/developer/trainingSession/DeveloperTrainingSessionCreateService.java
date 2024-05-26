
package acme.features.developer.trainingSession;

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
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Developer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingSession object;

		object = new TrainingSession();
		object.setDraftMode(true);

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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingSession existing;

			existing = this.repository.findTrainingSessionByCode(object.getCode());
			super.state(existing == null, "code", "developer.trainingSession.error.code.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate") && !super.getBuffer().getErrors().hasErrors("endDate")) {
			super.state(object.getStartDate().before(object.getEndDate()), "endDate", "developer.trainingSession.error.endDate.afterStartDate");
			super.state(object.getEndDate().after(object.getStartDate()), "startDate", "developer.trainingSession.error.startDate.afterEndDate");

			long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 1 semana en milisegundos
			long diffM_SF = object.getEndDate().getTime() - object.getStartDate().getTime();
			long diffM_CS = object.getStartDate().getTime() - object.getTrainingModule().getCreationMoment().getTime();

			super.state(diffM_SF >= oneWeekInMillis, "startDate", "developer.trainingSession.error.duration.lessThanOneWeek");
			super.state(diffM_CS >= oneWeekInMillis, "startDate", "developer.trainingSession.error.startDate.lessThanOneWeek");
		}

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
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

}

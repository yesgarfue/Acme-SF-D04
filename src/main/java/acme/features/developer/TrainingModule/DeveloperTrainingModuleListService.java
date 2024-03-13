
package acme.features.developer.TrainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleListService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		final boolean status;
		status = super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TrainingModule> objects;
		int developerId;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyByDeveloperId(developerId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "creationMoment", "details", "updatedMoment", "optionalLink", "totalTime", "draftMode", "difficultyLevel");
		dataset.put("trainingModuleId", object.getId());
		super.getResponse().addData(dataset);
	}
}

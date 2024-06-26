
package acme.features.manager.userStory;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.UserStory;
import acme.enumerate.Priority;
import acme.roles.Manager;

@Service
public class ManagerUserStoryPublishService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		UserStory userStory;

		userStory = this.repository.findUserStoryById(super.getRequest().getData("id", int.class));

		status = userStory != null && super.getRequest().getPrincipal().hasRole(userStory.getManager());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = SerializationUtils.clone(this.repository.findUserStoryById(id));

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draftMode");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;

	}
	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draftMode");
		dataset.put("priorities", SelectChoices.from(Priority.class, object.getPriority()));

		super.getResponse().addData(dataset);
	}
	@Override
	public void perform(final UserStory object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

}

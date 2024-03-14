
package acme.features.manager.userStory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;
import acme.enumerate.Priority;
import acme.roles.Manager;

@Service
public class ManagerUserStoryCreateService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);
		if (super.getRequest().hasData("projectId", int.class)) {
			final Project project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));
			status = super.getRequest().getPrincipal().hasRole(project.getManager());
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int userAccountId;
		Manager manager;

		userAccountId = super.getRequest().getPrincipal().getActiveRoleId();
		manager = this.repository.findManagerById(userAccountId);

		object = new UserStory();
		object.setManager(manager);
		object.setDraftMode(true);
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

		if (!super.getBuffer().getErrors().hasErrors("estimatedCost")) {
			final List<String> aceptedCurrencies = Arrays.asList(this.repository.getAvailableCurrencies().split(",")).stream().map(Object::toString).collect(Collectors.toList());
			super.state(object.getEstimatedCost().getAmount() >= 0, "estimatedCost", "manager.userStory.error.estimatedCost.negative");
			super.state(aceptedCurrencies.contains(object.getEstimatedCost().getCurrency()), "estimatedCost", this.repository.getAvailableCurrencies());
		}
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.save(object);
		if (super.getRequest().hasData("projectId", int.class)) {
			final ProjectUserStory pu = new ProjectUserStory();
			pu.setProject(this.repository.findProjectById(super.getRequest().getData("projectId", int.class)));
			pu.setUserStory(object);
			this.repository.save(pu);
		}

	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draftMode");

		if (super.getRequest().hasData("projectId", int.class)) {
			final int projectId = super.getRequest().getData("projectId", int.class);
			final Project project = this.repository.findProjectById(projectId);
			dataset.put("projectId", projectId);
			dataset.put("projectCode", project.getCode());
		}

		dataset.put("priorities", SelectChoices.from(Priority.class, object.getPriority()));

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}

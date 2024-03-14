
package acme.features.manager.projectUserStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryAddService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		final Project project = this.repository.findProjectById(super.getRequest().getData("projectId", int.class));
		status = super.getRequest().getPrincipal().hasRole(project.getManager());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStory object;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findProjectById(projectId);

		object = new ProjectUserStory();
		object.setProject(project);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;
		int userStoryId;
		UserStory userStory;
		int projectId;
		Project project;

		userStoryId = super.getRequest().getData("userStory", int.class);
		userStory = this.repository.findUserStoryById(userStoryId);
		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findProjectById(projectId);
		object.setUserStory(userStory);
		object.setProject(project);
	}

	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;

	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset;
		int projectId;
		int managerId;
		Project project;
		SelectChoices choices;

		projectId = super.getRequest().getData("projectId", int.class);
		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		project = this.repository.findProjectById(projectId);
		choices = SelectChoices.from(this.repository.findUserStoriesAvailableForAProject(projectId, managerId), "title", object.getUserStory());

		dataset = super.unbind(object, "Project", "UserStory");
		dataset.put("userStories", choices);
		dataset.put("userStory", choices.getSelected().getKey());
		dataset.put("projectId", projectId);
		dataset.put("projectCode", project.getCode());
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}

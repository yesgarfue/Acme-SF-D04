
package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListService extends AbstractService<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Project project;
		if (super.getRequest().hasData("projectId", int.class)) {
			masterId = super.getRequest().getData("projectId", int.class);
			project = this.repository.findProjectById(masterId);
			status = project != null && super.getRequest().getPrincipal().hasRole(project.getManager());

		} else
			status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<UserStory> objects;
		final int projectId;
		if (super.getRequest().hasData("projectId", int.class)) {
			projectId = super.getRequest().getData("projectId", int.class);
			objects = this.repository.findManyUserStoriesByProjectId(projectId);
		} else {
			projectId = super.getRequest().getPrincipal().getActiveRoleId();
			objects = this.repository.findManyUserStoriesByManagerId(projectId);
		}
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draftMode");
		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<UserStory> objects) {
		assert objects != null;
		if (super.getRequest().hasData("projectId", int.class)) {
			final int projectId;
			final Project project;
			projectId = super.getRequest().getData("projectId", int.class);
			project = this.repository.findProjectById(projectId);
			super.getResponse().addGlobal("projectId", projectId);
			super.getResponse().addGlobal("draftMode", project.isDraftMode());

		}

	}

}


package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service

public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Project project;

		project = this.repository.findProjectById(super.getRequest().getData("id", int.class));

		status = project != null && project.isDraftMode() && super.getRequest().getPrincipal().hasRole(project.getManager());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "abstracts", "indication", "cost", "link", "draftMode");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		this.repository.deleteAllEntitiesAssociatedToAProject(object);
		this.repository.delete(object);
	}

}

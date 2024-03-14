
package acme.features.any.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;

@Service
public class AnyProjectListService extends AbstractService<Any, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		final boolean status = true;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Project> objects;

		objects = this.repository.findAllPublishedProjects();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "abstracts", "indication", "cost", "link", "draftMode");
		super.getResponse().addData(dataset);
	}
}

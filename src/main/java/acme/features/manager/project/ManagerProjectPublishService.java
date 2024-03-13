
package acme.features.manager.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Project project;

		project = this.repository.findProjectById(super.getRequest().getData("id", int.class));

		status = project != null && super.getRequest().getPrincipal().hasRole(project.getManager());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = SerializationUtils.clone(this.repository.findProjectById(id));

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
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project existing;

			existing = this.repository.findProjectByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "Manager.Project.error.code.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			final List<String> aceptedCurrencies = Arrays.asList(this.repository.getAvailableCurrencies().split(",")).stream().map(Object::toString).collect(Collectors.toList());
			super.state(object.getCost().getAmount() >= 0, "cost", "manager.project.error.cost.negative");
			super.state(aceptedCurrencies.contains(object.getCost().getCurrency()), "cost", "manager.project.error.cost.currencyNotPermitted");
		}

		super.state(!this.repository.findUserStoriesWithoutPublishByProject(object.getId()), "*", "manager.project.error.userStoryNotPublished");
		super.state(!this.repository.hasAProjectAnyFatalError(object.getId()), "*", "manager.project.error.fatalError");

	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "abstracts", "indication", "cost", "link", "draftMode");
		dataset.put("projectId", object.getId());
		super.getResponse().addData(dataset);
	}
}

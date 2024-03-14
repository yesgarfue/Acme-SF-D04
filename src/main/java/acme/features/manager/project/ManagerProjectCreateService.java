
package acme.features.manager.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.roles.Manager;

@Service

public class ManagerProjectCreateService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int userAccountId;
		Manager manager;

		userAccountId = super.getRequest().getPrincipal().getActiveRoleId();
		manager = this.repository.findManagerById(userAccountId);

		object = new Project();
		object.setManager(manager);
		object.setDraftMode(true);
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
			super.state(existing == null, "code", "manager.project.error.code.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("cost")) {
			final List<String> aceptedCurrencies = Arrays.asList(this.repository.getAvailableCurrencies().split(",")).stream().map(Object::toString).collect(Collectors.toList());
			super.state(object.getCost().getAmount() >= 0, "cost", "manager.project.error.cost.negative");
			super.state(aceptedCurrencies.contains(object.getCost().getCurrency()), "cost", "manager.project.error.cost.currencyNotPermitted");
		}
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

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

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}

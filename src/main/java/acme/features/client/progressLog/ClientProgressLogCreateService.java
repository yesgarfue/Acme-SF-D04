
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;
import acme.roles.Client;

@Service
public class ClientProgressLogCreateService extends AbstractService<Client, ProgressLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientProgressLogRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Client.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProgressLog object;

		object = new ProgressLog();
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProgressLog object) {
		assert object != null;
		super.bind(object, "recordId", "completenessPercentage", "progressComment", "registrationMoment", "responsiblePerson", "draftMode", "contract");

	}

	@Override
	public void validate(final ProgressLog object) {

		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			ProgressLog existing;

			existing = this.repository.findProgressLogByRecordId(object.getRecordId());
			super.state(existing == null, "recordId", "client.progress-log.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("registrationMoment"))
			super.state(object.getRegistrationMoment().after(object.getContract().getInstantiationMoment()), "registrationMoment", "client.progress-log.form.error.registration-moment-must-be-later");
	}

	@Override
	public void perform(final ProgressLog object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final ProgressLog object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;
		int clientId;
		clientId = super.getRequest().getPrincipal().getActiveRoleId();
		final Collection<Contract> contracts = this.repository.findContractsByClientId(clientId);

		choices = SelectChoices.from(contracts, "code", object.getContract());
		dataset = super.unbind(object, "recordId", "completenessPercentage", "progressComment", "registrationMoment", "responsiblePerson");
		dataset.put("contract", choices.getSelected().getKey());
		dataset.put("contracts", choices);
		dataset.put("draftMode", object.getDraftMode());

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}

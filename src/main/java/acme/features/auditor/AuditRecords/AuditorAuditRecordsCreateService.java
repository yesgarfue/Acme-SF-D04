
package acme.features.auditor.AuditRecords;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditRecords;
import acme.entities.audits.CodeAudit;
import acme.enumerate.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordsCreateService extends AbstractService<Auditor, AuditRecords> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordsRepository repository;


	// AbstractService interface ----------------------------------------------
	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecords object;

		object = new AuditRecords();
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecords object) {
		assert object != null;
		super.bind(object, "code", "startTime", "finishTime", "mark", "optionalLink", "draftMode", "codeAudit");

	}

	@Override
	public void validate(final AuditRecords object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecords existing;

			existing = this.repository.findAuditRecordByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "auditor.auditRecords.error.code.duplicated");
		}

	}

	@Override
	public void perform(final AuditRecords object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecords object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;
		final Collection<CodeAudit> codeAudits = this.repository.findAllCodeAudits();

		dataset = super.unbind(object, "code", "startTime", "finishTime", "mark", "optionalLink", "draftMode");
		dataset.put("AuditRecordId", object.getId());
		dataset.put("marks", SelectChoices.from(Mark.class, object.getMark()));
		choices = SelectChoices.from(codeAudits, "code", object.getCodeAudit());
		dataset.put("codeAudit", choices.getSelected().getKey());
		dataset.put("codeAudits", choices);
		super.getResponse().addData(dataset);

	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}

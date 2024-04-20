
package acme.features.auditor.CodeAudit;

import java.util.Collection;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditRecords;
import acme.entities.audits.CodeAudit;
import acme.entities.projects.Project;
import acme.enumerate.Mark;
import acme.enumerate.Type;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		CodeAudit codeAudit;

		codeAudit = this.repository.findAuditById(super.getRequest().getData("id", int.class));

		status = codeAudit != null && super.getRequest().getPrincipal().hasRole(codeAudit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = SerializationUtils.clone(this.repository.findAuditById(id));

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "draftMode", "project");
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.repository.findCodeAuditByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "auditor.code-audit.error.code.duplicated");
		}
		if (object != null) {
			Collection<AuditRecords> records = this.repository.findAuditRecordsByid(object.getId());
			for (AuditRecords record : records)
				super.state(record.getMark() != Mark.F && record.getMark() != Mark.F_Minus, "*", "auditor.code-audit.error.mark.minimum");
		}
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choicesProjects;
		final Collection<Project> projects = this.repository.findAllProject();

		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "draftMode");
		dataset.put("CodeAuditId", object.getId());
		dataset.put("types", SelectChoices.from(Type.class, object.getType()));
		choicesProjects = SelectChoices.from(projects, "code", object.getProject());
		dataset.put("project", choicesProjects.getSelected().getKey());
		dataset.put("projects", choicesProjects);
		super.getResponse().addData(dataset);
	}

}

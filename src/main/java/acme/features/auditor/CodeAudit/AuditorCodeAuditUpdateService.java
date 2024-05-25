
package acme.features.auditor.CodeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.CodeAudit;
import acme.entities.projects.Project;
import acme.enumerate.Type;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditUpdateService extends AbstractService<Auditor, CodeAudit> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;
		Auditor auditor;

		masterId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findAuditById(masterId);
		auditor = codeAudit.getAuditor();
		status = super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findProjectById(projectId);

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "draftMode");
		object.setProject(project);
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.repository.findCodeAuditByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "auditor.code-audit.error.code.duplicated");
		}
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

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

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("PUT"))
			PrincipalHelper.handleUpdate();
	}
}

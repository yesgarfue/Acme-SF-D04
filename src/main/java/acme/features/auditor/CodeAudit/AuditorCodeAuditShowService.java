
package acme.features.auditor.CodeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.CodeAudit;
import acme.entities.projects.Project;
import acme.enumerate.Type;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {
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
		object = this.repository.findAuditById(id);

		super.getBuffer().addData(object);

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

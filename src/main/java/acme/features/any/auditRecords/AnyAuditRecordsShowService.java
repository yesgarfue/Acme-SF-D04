
package acme.features.any.auditRecords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditRecords;
import acme.entities.audits.CodeAudit;
import acme.enumerate.Mark;

@Service
public class AnyAuditRecordsShowService extends AbstractService<Any, AuditRecords> {

	@Autowired
	private AnyAuditRecordsRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int id;
		CodeAudit codeAudit;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findCodeAuditByAuditRecordsId(id);

		status = codeAudit != null && !codeAudit.isDraftMode();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {

		AuditRecords object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordsById(id);

		super.getBuffer().addData(object);

	}

	@Override
	public void unbind(final AuditRecords object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startTime", "finishTime", "mark", "optionalLink", "draftMode");
		dataset.put("auditRecordId", object.getId());
		dataset.put("marks", SelectChoices.from(Mark.class, object.getMark()));
		super.getResponse().addData(dataset);
	}

}

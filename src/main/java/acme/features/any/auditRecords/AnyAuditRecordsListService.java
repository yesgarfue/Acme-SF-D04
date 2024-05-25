
package acme.features.any.auditRecords;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditRecords;
import acme.entities.audits.CodeAudit;

@Service
public class AnyAuditRecordsListService extends AbstractService<Any, AuditRecords> {

	@Autowired
	private AnyAuditRecordsRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int masterId;
		CodeAudit codeAudit;

		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findCodeAuditById(masterId);

		status = codeAudit != null && !codeAudit.isDraftMode();

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Collection<AuditRecords> auditRecords;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		auditRecords = this.repository.findPublishedAuditRecordsByMasterId(masterId);

		super.getBuffer().addData(auditRecords);
	}

	@Override
	public void unbind(final AuditRecords object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "startTime", "finishTime", "mark", "optionalLink", "draftMode", "codeAudit");
		dataset.put("auditRecordId", object.getId());
		super.getResponse().addData(dataset);
	}

}

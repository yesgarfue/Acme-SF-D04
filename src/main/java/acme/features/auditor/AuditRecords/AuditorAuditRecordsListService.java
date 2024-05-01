
package acme.features.auditor.AuditRecords;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditRecords;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordsListService extends AbstractService<Auditor, AuditRecords> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordsRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		final boolean status;
		status = super.getRequest().getPrincipal().hasRole(Auditor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditRecords> objects;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findAuditRecordsByAuditorId(auditorId);

		super.getBuffer().addData(objects);
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

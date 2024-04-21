
package acme.features.auditor.AuditRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecords;

public interface AuditorAuditRecordsRepository extends AbstractRepository {

	@Query("SELECT ar FROM AuditRecords ar WHERE ar.codeAudit.auditor.id = :id")
	Collection<AuditRecords> findAuditRecordsByAuditorId(int id);

}

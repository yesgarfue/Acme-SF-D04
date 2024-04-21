
package acme.features.auditor.AuditRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecords;
import acme.entities.audits.CodeAudit;

public interface AuditorAuditRecordsRepository extends AbstractRepository {

	@Query("SELECT ar FROM AuditRecords ar WHERE ar.codeAudit.auditor.id = :id")
	Collection<AuditRecords> findAuditRecordsByAuditorId(int id);

	@Query("SELECT ar FROM AuditRecords ar WHERE ar.id = :id")
	AuditRecords findAuditRecordById(int id);

	@Query("SELECT ar FROM AuditRecords ar WHERE ar.code = :code")
	AuditRecords findAuditRecordByCode(String code);

	@Query("SELECT ca FROM CodeAudit ca")
	Collection<CodeAudit> findAllCodeAudits();
}

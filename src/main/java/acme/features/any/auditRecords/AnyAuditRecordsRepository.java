
package acme.features.any.auditRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecords;
import acme.entities.audits.CodeAudit;

public interface AnyAuditRecordsRepository extends AbstractRepository {

	@Query("SELECT c FROM CodeAudit c WHERE c.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("SELECT ar FROM AuditRecords ar WHERE ar.id = :id")
	AuditRecords findAuditRecordsById(int id);

	@Query("SELECT ar.codeAudit FROM AuditRecords ar WHERE ar.id = :id")
	CodeAudit findCodeAuditByAuditRecordsId(int id);

	@Query("SELECT ar FROM AuditRecords ar WHERE ar.codeAudit.id = :masterId AND ar.draftMode = false")
	Collection<AuditRecords> findPublishedAuditRecordsByMasterId(int masterId);
}

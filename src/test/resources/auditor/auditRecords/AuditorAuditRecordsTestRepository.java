
package acme.testing.auditor.auditRecords;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecords;

public interface AuditorAuditRecordsTestRepository extends AbstractRepository {

	@Query("SELECT au FROM AuditRecords au WHERE au.code = :code")
	AuditRecords findAuditRecordByCode(String code);

	@Query("SELECT au FROM AuditRecords au WHERE au.codeAudit.auditor.userAccount.username = :username")
	Collection<AuditRecords> findAuditRecordsByAuditor(String username);

	@Query("SELECT au FROM AuditRecords au WHERE au.codeAudit.auditor.userAccount.username = :username AND au.draftMode = false")
	Collection<AuditRecords> findAuditRecordsNotInDraftModeByManager(String username);
}

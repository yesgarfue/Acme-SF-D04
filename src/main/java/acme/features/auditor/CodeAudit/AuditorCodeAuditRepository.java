
package acme.features.auditor.CodeAudit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.CodeAudit;

public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("SELECT a FROM CodeAudit a WHERE a.auditor.id = :auditorId")
	List<CodeAudit> findAllAuditsByAuditorId(int auditorId);

	@Query("SELECT a FROM CodeAudit a WHERE a.id = :id")
	CodeAudit findAuditById(int id);
}

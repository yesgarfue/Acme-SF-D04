
package acme.features.any.auditor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.CodeAudit;

public interface AnyCodeAuditRepository extends AbstractRepository {

	@Query("SELECT a FROM CodeAudit a WHERE a.draftMode = false")
	Collection<CodeAudit> findAllPublishedCodeAudits();

	@Query("SELECT a FROM CodeAudit a WHERE a.auditor.id = :auditorId")
	Collection<CodeAudit> findManyCodeAuditsByAudtitorId(int auditorId);

	@Query("SELECT a FROM CodeAudit a WHERE a.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("SELECT a FROM CodeAudit a WHERE a.code = :code")
	CodeAudit findCodeAuditByCode(String code);
}

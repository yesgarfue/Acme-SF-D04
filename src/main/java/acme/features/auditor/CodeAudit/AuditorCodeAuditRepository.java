
package acme.features.auditor.CodeAudit;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.CodeAudit;
import acme.entities.projects.Project;
import acme.roles.Auditor;

public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProject();

	@Query("SELECT c FROM CodeAudit c WHERE c.auditor.id = :auditorId")
	List<CodeAudit> findAllAuditsByAuditorId(int auditorId);

	@Query("SELECT c FROM CodeAudit c WHERE c.id = :id")
	CodeAudit findAuditById(int id);

	@Query("SELECT a FROM Auditor a WHERE a.id = :id")
	Auditor findAuditorById(int id);

	@Query("SELECT c FROM CodeAudit c WHERE c.code = :code")
	CodeAudit findCodeAuditByCode(String code);

	@Query("SELECT p FROM Project p WHERE p.id = ?1")
	Project findProjectById(int id);

	//VALIDATIONS:
	/*
	 * @Query("SELECT a FROM AuditRecords c WHERE a.code_audit_id = :code_audit_id")
	 * CodeAudit findCodeAuditByCode(String code);
	 */
}

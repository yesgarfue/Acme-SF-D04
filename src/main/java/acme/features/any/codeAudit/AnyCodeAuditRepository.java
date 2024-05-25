
package acme.features.any.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.CodeAudit;
import acme.entities.projects.Project;

@Repository
public interface AnyCodeAuditRepository extends AbstractRepository {

	@Query("SELECT c FROM CodeAudit c WHERE c.draftMode = false")
	Collection<CodeAudit> findAllPublishedCodeAudit();

	@Query("SELECT c FROM CodeAudit c WHERE c.id = :id")
	CodeAudit findCodeAuditById(int id);

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProjects();
}

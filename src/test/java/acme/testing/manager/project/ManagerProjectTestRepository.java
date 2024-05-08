
package acme.testing.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

public interface ManagerProjectTestRepository extends AbstractRepository {

	@Query("SELECT c FROM Project c WHERE c.code = :code")
	Project findProjectByCode(String code);

	@Query("SELECT c FROM Project c WHERE c.manager.userAccount.username = :username")
	Collection<Project> findProjectsByManager(String username);

	@Query("SELECT c FROM Project c WHERE c.manager.userAccount.username = :username AND c.draftMode = false")
	Collection<Project> findProjectsNotInDraftModeByManager(String username);

	@Query("SELECT c FROM Project c WHERE c.manager.userAccount.username = :username AND c.draftMode = true")
	Collection<Project> findProjectsInDraftModeByManager(String username);
}

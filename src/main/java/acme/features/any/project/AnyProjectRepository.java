
package acme.features.any.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

public interface AnyProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	Collection<Project> findAllPublishedProjects();

	@Query("SELECT p FROM Project p WHERE p.manager.id = :managerId")
	Collection<Project> findManyProjectsByManagerId(int managerId);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT p FROM Project p WHERE p.code = :code")
	Project findProjectByCode(String code);
}

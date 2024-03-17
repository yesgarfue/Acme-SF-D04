
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;

public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("SELECT us FROM UserStory us WHERE us.id = :userStoryId")
	UserStory findUserStoryById(int userStoryId);

	@Query("SELECT p FROM Project p WHERE p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("SELECT DISTINCT(us) FROM UserStory us WHERE us NOT IN (SELECT us FROM UserStory us LEFT JOIN ProjectUserStory pus ON us.id = pus.userStory.id WHERE pus.project.id = :projectId) AND us.manager.id = :managerId")
	Collection<UserStory> findUserStoriesAvailableForAProject(int projectId, int managerId);
}

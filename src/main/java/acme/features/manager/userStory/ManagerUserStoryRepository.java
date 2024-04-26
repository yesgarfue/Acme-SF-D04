
package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;
import acme.roles.Manager;

public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findManagerById(int id);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT pus.userStory FROM ProjectUserStory pus WHERE pus.project.id = :projectId")
	Collection<UserStory> findManyUserStoriesByProjectId(int projectId);

	@Query("SELECT us FROM UserStory us WHERE us.manager.id = :managerId")
	Collection<UserStory> findManyUserStoriesByManagerId(int managerId);

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findUserStoryById(Integer id);

	@Modifying
	@Query("DELETE FROM ProjectUserStory pus WHERE pus.userStory.id= :id")
	void deleteAllReferencesToUserStoryByUserStoryId(Integer id);

}

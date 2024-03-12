
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.enumerate.Priority;
import acme.roles.Manager;

public interface ManagerProjectRepository extends AbstractRepository {

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findManagerById(int id);

	@Query("SELECT p FROM Project p WHERE p.manager.id = :managerId")
	Collection<Project> findManyProjectsByManagerId(int managerId);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT p FROM Project p WHERE p.code = :code")
	Project findProjectByCode(String code);

	@Query("SELECT pus FROM ProjectUserStory pus WHERE pus.project.id = :id")
	Collection<ProjectUserStory> findProjectUserStoriesByProject(int id);

	@Query("SELECT count(pus) FROM ProjectUserStory pus WHERE pus.userStory.priority = :priority AND pus.project.id = :id")
	Integer numOfUserStoriesOfOnePriorityByProject(int id, Priority priority);
	/*
	 * @Query("SELECT COUNT(pus) > 0 FROM ProjectUserStory pus WHERE pus.userStory.draftMode = true  AND pus.project.id = :projectId")
	 * boolean findUserStoriesWithoutPublishErrorByProject(int projectId);
	 * 
	 * @Query("SELECT count(cl) > 0 FROM ProjectLecture cl WHERE cl.lecture.type = acme.entities.enumerates.Type.HANDS_ON AND cl.Project.id = :ProjectId")
	 * boolean hasAProjectAnyFatalError(int ProjectId);
	 */

	@Query("SELECT sc.aceptedCurrencies FROM SystemConfiguration sc")
	String getAvailableCurrencies();

	default void deleteAllEntitiesAssociatedToAProject(final Project object) {

		this.deleteAll(this.findProjectUserStoriesByProject(object.getId()));

		/*
		 * final Collection<Audit> audits = this.findAuditsByProject(object.getId());
		 * for (final Audit a : audits)
		 * this.deleteAll(this.findAuditingRecordByAudit(a.getId()));
		 * this.deleteAll(audits);
		 */
	}

}

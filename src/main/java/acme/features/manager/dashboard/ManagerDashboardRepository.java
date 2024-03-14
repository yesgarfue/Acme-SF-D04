
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.enumerate.Priority;

public interface ManagerDashboardRepository extends AbstractRepository {

	Priority MUST = Priority.MUST;


	//NUMBERS OF PRIORITIES OF USER STORIES
	@Query("SELECT count(us) FROM UserStory us WHERE us.manager.id = :managerId AND us.priority = :priority AND us.draftMode = false")
	Integer totalNumberOfMustOfUserStoryOfManager(int managerId, Priority priority);

	@Query("SELECT count(us) FROM UserStory us WHERE us.manager.id = :managerId AND us.priority = :priority AND us.draftMode = false")
	Integer totalNumberOfShouldOfUserStoryOfManager(int managerId, Priority priority);

	@Query("SELECT count(us) FROM UserStory us WHERE us.manager.id = :managerId AND us.priority = :priority AND us.draftMode = false")
	Integer totalNumberOfCouldOfUserStoryOfManager(int managerId, Priority priority);

	@Query("SELECT count(us) FROM UserStory us WHERE us.manager.id = :managerId AND us.priority = :priority AND us.draftMode = false")
	Integer totalNumberOfWontOfUserStoryOfManager(int managerId, Priority priority);

	//AVG AND DEV OF ESTIMATED COST OF USER STORIES
	@Query("SELECT AVG(us.estimatedCost.amount) FROM UserStory us WHERE us.manager.id = :managerId  AND us.draftMode = false")
	Double averageEstimatedCostOfUserStoriesOfManager(int managerId);

	@Query("SELECT us.estimatedCost.amount FROM UserStory us WHERE us.manager.id = :managerId  AND us.draftMode = false")
	Collection<Double> estimatedCostOfUserStoriesOfManager(int managerId);

	default Double deviationEstimatedCostOfUserStoriesOfManager(final int managerId) {

		final Double avg = this.averageEstimatedCostOfUserStoriesOfManager(managerId);
		final Collection<Double> userStories = this.estimatedCostOfUserStoriesOfManager(managerId);
		final double numerator = userStories.stream().mapToDouble(x -> (x - avg) * (x - avg)).sum();
		return Math.sqrt(numerator / userStories.size());

	}
	// MIN MAX OF ESTIMATED COST OF USER STORIES
	@Query("SELECT MIN(us.estimatedCost.amount) FROM UserStory us WHERE us.manager.id = :managerId  AND us.draftMode = false")
	Double minimumEstimatedCostOfUserStoriesOfManager(int managerId);

	@Query("SELECT MAX(us.estimatedCost.amount) FROM UserStory us WHERE us.manager.id = :managerId  AND us.draftMode = false")
	Double maximumEstimatedCostOfUserStoriesOfManager(int managerId);

	//TOTAL NUMBER OF PROJECTS
	@Query("SELECT count(p) FROM Project p WHERE p.manager.id = :managerId  AND p.draftMode = false")
	Double totalNumberOfProjectsOfManager(int managerId);

	//AVG AND DEV OF COST OF PROJECTS 

	@Query("SELECT AVG(p.cost.amount) FROM Project p WHERE p.manager.id = :managerId AND p.draftMode = false")
	Double averageCostOfProjectsOfManager(int managerId);

	@Query("SELECT p.cost.amount FROM Project p WHERE p.manager.id = :managerId  AND p.draftMode = false")
	Collection<Double> costOfProjectsOfManager(int managerId);

	default Double deviationCostOfProjectsOfManager(final int managerId) {

		final Double avg = this.averageCostOfProjectsOfManager(managerId);
		final Collection<Double> projects = this.costOfProjectsOfManager(managerId);
		final double numerator = projects.stream().mapToDouble(x -> (x - avg) * (x - avg)).sum();

		return Math.sqrt(numerator / projects.size());

	}

	//MIN MAX OF COST OF PROJECTS
	@Query("SELECT MIN(p.cost.amount) FROM Project p WHERE p.manager.id = :managerId  AND p.draftMode = false")
	Double minimumCostOfProjectsOfManager(int managerId);

	@Query("SELECT MAX(p.cost.amount) FROM Project p WHERE p.manager.id = :managerId  AND p.draftMode = false")
	Double maximumCostOfProjectsOfManager(int managerId);

}

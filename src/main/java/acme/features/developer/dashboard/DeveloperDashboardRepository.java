
package acme.features.developer.dashboard;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface DeveloperDashboardRepository extends AbstractRepository {

	//Total Number of Training Modules
	@Query("SELECT count(tm) FROM TrainingModule tm WHERE tm.updatedMoment IS NOT NULL AND tm.developer.id = ?1")
	Integer totalNumberOfTrainingModules(int developerId);

	//Total Number of Training Sessions
	@Query("SELECT count(ts) FROM TrainingSession ts WHERE ts.optionalLink IS NOT NULL AND ts.trainingModule.developer.id = ?1")
	Integer totalNumberOfTrainingSessions(int developerId);

	//Average Time of the Training Modules
	@Query("SELECT AVG(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = ?1")
	Double averageTimeTrainingModule(int developerId);

	//Deviation Time of the Training Modules
	@Query("SELECT STDDEV(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = ?1")
	Double deviationTimeTrainingModule(int developerId);

	//Minimum Time of the Training Modules
	@Query("SELECT MIN(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = ?1")
	Double minTimeTrainingModule(int developerId);

	//Maximum Time of the Training Modules
	@Query("SELECT MAX(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = ?1")
	Double maxTimeTrainingModule(int developerId);
}

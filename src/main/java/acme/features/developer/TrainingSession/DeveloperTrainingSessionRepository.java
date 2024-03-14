
package acme.features.developer.TrainingSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;

public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select ts from TrainingSession ts where ts.trainingModule.developer.id = ?1")
	Collection<TrainingSession> findTrainingSessionByDeveloperId(int developerId);

	@Query("select ts from TrainingSession ts where ts.id = ?1")
	TrainingSession findTrainingSessionById(int id);

	//nose si es correcto en create service
	@Query("select tm from TrainingModule tm where tm.id = ?1")
	TrainingModule findTrainingModuleById(int id);

	@Query("select ts from TrainingSession ts where ts.code = ?1")
	TrainingSession findTrainingSessionByCode(String code);

	@Query("select tm from TrainingModule tm")
	Collection<TrainingModule> findAllTrainingModules();
}

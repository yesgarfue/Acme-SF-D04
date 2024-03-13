
package acme.features.developer.TrainingSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.training.TrainingSession;

public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select t from TrainingSession t where t.trainingModule.developer.id = ?1")
	Collection<TrainingSession> findTrainingSessionByDeveloperId(int developerId);

}

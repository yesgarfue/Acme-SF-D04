
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.training.TrainingModule;

public interface AnyTrainingModuleRepository extends AbstractRepository {

	@Query("SELECT tm FROM TrainingModule tm WHERE tm.draftMode = false")
	Collection<TrainingModule> findAllPublishedTrainingModules();

	@Query("SELECT tm FROM TrainingModule tm WHERE tm.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProject();
}

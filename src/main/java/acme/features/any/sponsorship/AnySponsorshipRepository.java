
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.sponsor.Sponsorship;

@Repository
public interface AnySponsorshipRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsorship s WHERE s.draftMode = true")
	Collection<Sponsorship> findAllPublishedSponsorships();

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProjects();

}

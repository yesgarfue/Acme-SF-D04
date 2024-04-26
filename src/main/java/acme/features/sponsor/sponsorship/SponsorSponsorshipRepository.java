
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("SELECT sp FROM Sponsor sp WHERE sp.id= :id")
	Sponsor findOneSponsorbyId(int id);

	@Query("SELECT p FROM Project p WHERE p.id= :projectId")
	Project findOneProjectById(int projectId);

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProjects();

	@Query("SELECT s FROM Sponsorship s WHERE s.id= :id")
	Sponsorship findSponsorshipById(int id);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id= :id")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int id);

	@Query("SELECT s FROM Sponsorship s")
	Collection<Sponsorship> findAllSponsorship();

	@Query("SELECT s FROM Sponsorship s WHERE s.sponsor.id= :activeRoleId")
	Collection<Sponsorship> findMySponsorshipById(int activeRoleId);

	//------------------------------

	@Query("SELECT s FROM Sponsorship s WHERE s.code= :code")
	Sponsorship findOneSponsorshipByCode(String code);

}
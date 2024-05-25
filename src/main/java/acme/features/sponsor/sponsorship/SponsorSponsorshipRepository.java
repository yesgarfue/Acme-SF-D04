
package acme.features.sponsor.sponsorship;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;
import acme.systemConfiguration.SystemConfiguration;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsorship s WHERE s.id= :id")
	Sponsorship findSponsorshipById(int id);

	@Query("SELECT p FROM Project p WHERE p.id= :projectId")
	Project findOneProjectById(int projectId);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id= :id")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int id);

	@Query("SELECT sp FROM Sponsor sp WHERE sp.id= :id")
	Sponsor findOneSponsorbyId(int id);

	@Query("SELECT s FROM Sponsorship s WHERE s.code= :code")
	Sponsorship findOneSponsorshipByCode(String code);

	@Query("SELECT sc FROM SystemConfiguration sc")
	List<SystemConfiguration> findSystemConfiguration();

	@Query("SELECT p FROM Project p WHERE p.code= :code")
	Project findOneProjectByCode(String code);

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProjects();

	@Query("SELECT s FROM Sponsorship s WHERE s.sponsor.id= :activeRoleId")
	Collection<Sponsorship> findMySponsorshipById(int activeRoleId);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id= :id AND i.isPublished= true")
	Collection<Invoice> findPublishedInvoicesBySponsorshipId(int id);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id= :id AND i.isPublished= false")
	Collection<Invoice> findNotPublishedInvoicesBySponsorshipId(int id);
}


package acme.features.sponsor.invoices;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;
import acme.systemConfiguration.SystemConfiguration;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("SELECT i FROM Invoice i WHERE i.id = :id")
	Invoice findOneInvoiceById(int id);

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :shipId")
	Sponsorship findOneSponsorshipById(int shipId);

	@Query("SELECT i FROM Invoice i WHERE i.code = :code")
	Invoice findOneInvoiceByCode(String code);

	@Query("SELECT sc FROM SystemConfiguration sc")
	List<SystemConfiguration> findSystemConfiguration();

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id= :shipId AND i.isPublished= true")
	Collection<Invoice> findManyPublishedInvoicesBySponsorshipId(int shipId);

	@Query("SELECT sp FROM Sponsor sp WHERE sp.id= :id")
	Sponsor findOneSponsorbyId(int id);

	@Query("SELECT i FROM Invoice i JOIN Sponsorship sp ON i.sponsorship.id = sp.id JOIN Sponsor s ON sp.sponsor.id = s.id WHERE s.id = :activeRoleId")
	Collection<Invoice> findMyInvoicesBySponsorId(int activeRoleId);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :shipId")
	Collection<Invoice> findManyInvoicesByShipId(int shipId);

	@Query("SELECT sp FROM Sponsorship sp WHERE sp.sponsor.id = :activeRoleId")
	Collection<Sponsorship> findAllSponsorshipBySponsorId(int activeRoleId);

	@Query("SELECT sp FROM Sponsorship sp WHERE sp.code= :code")
	Sponsorship findOneSponsorshipByCode(String code);
}

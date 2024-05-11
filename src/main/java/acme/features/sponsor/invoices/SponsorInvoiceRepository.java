
package acme.features.sponsor.invoices;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.systemConfiguration.SystemConfiguration;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :shipId")
	Sponsorship findOneSponsorshipById(int shipId);

	@Query("SELECT s FROM Sponsorship s JOIN Invoice i ON s.id = i.sponsorship.id WHERE i.id = :invoiceId")
	Sponsorship findOneSponsorshipByInvoiceId(int invoiceId);

	@Query("SELECT i FROM Invoice i WHERE i.id = :id")
	Invoice findOneInvoiceById(int id);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :shipId")
	Collection<Invoice> findManyInvoicesByShipId(int shipId);

	@Query("SELECT i FROM Invoice i JOIN Sponsorship sp ON i.sponsorship.id = sp.id JOIN Sponsor s ON sp.sponsor.id = s.id WHERE s.id = :activeRoleId")
	Collection<Invoice> findMyInvoicesBySponsorId(int activeRoleId);

	@Query("SELECT sc FROM SystemConfiguration sc")
	List<SystemConfiguration> findSystemConfiguration();

	@Query("SELECT i FROM Invoice i WHERE i.code = :code")
	Invoice findOneInvoiceByCode(String code);

	//--------------------------------

	//@Query("SELECT i FROM Invoice i")
	//Collection<Invoice> findAllInvoices();

	//@Query("select i from Invoice i join Sponsorship s on i.sponsorship.id = s.id join Sponsor s2  on s.sponsor.id = s2.id where s2.id = :sponsorId")
	//Collection<Invoice> findAllInvoicesBySponsorId(int sponsorId);

}

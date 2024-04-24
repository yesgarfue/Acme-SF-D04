
package acme.features.sponsor.invoices;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	//@Query("SELECT i FROM Invoice i")
	//Collection<Invoice> findAllInvoices();

	//@Query("select i from Invoice i join Sponsorship s on i.sponsorship.id = s.id join Sponsor s2  on s.sponsor.id = s2.id where s2.id = :sponsorId")
	//Collection<Invoice> findAllInvoicesBySponsorId(int sponsorId);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :shipId")
	Collection<Invoice> findManyInvoicesByShipId(int shipId);

	@Query("SELECT i FROM Invoice i WHERE i.id = :id")
	Invoice findOneInvoiceById(int id);

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :shipId")
	Sponsorship findOneSponsorshipById(int shipId);
}

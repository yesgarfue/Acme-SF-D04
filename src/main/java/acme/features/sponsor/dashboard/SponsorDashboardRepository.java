
package acme.features.sponsor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT i FROM Invoice i JOIN Sponsorship s ON i.sponsorship.id = s.id JOIN Sponsor sp ON s.sponsor.id = sp.id WHERE sp.id = :sponsorId")
	Collection<Invoice> totalNumberOfInvoicesOfSponsor(int sponsorId);

	@Query("SELECT COUNT(s.id) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId AND s.link IS NOT NULL")
	Integer totalNumberSponsorshipWithLink(int sponsorId);

	@Query("SELECT MIN(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Double minimumEstimatedCostOfSponsorshipOfSponsor(int sponsorId);

	@Query("SELECT MAX(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Double maximumEstimatedCostOfSponsorshipOfSponsor(int sponsorId);

	@Query("SELECT AVG(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Double averageEstimatedCostOfSponsorshipOfSponsor(int sponsorId);

	@Query("SELECT s FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Collection<Sponsorship> findSponsorshipsBySponsorId(int sponsorId);

	@Query("SELECT COUNT(s.id) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Integer totalNumberSponsorshipBySponsor(int sponsorId);

	@Query("SELECT MIN(i.quantity.amount) FROM Invoice i JOIN Sponsorship s ON i.sponsorship.id = s.id JOIN Sponsor sp ON s.sponsor.id = sp.id WHERE sp.id = :sponsorId")
	Double minimumInvoices(int sponsorId);

	@Query("SELECT MAX(i.quantity.amount) FROM Invoice i JOIN Sponsorship s ON i.sponsorship.id = s.id JOIN Sponsor sp ON s.sponsor.id = sp.id WHERE sp.id = :sponsorId")
	Double maximumInvoices(int sponsorId);

	@Query("SELECT AVG(i.quantity.amount) FROM Invoice i JOIN Sponsorship s ON i.sponsorship.id = s.id JOIN Sponsor sp ON s.sponsor.id = sp.id WHERE sp.id = :sponsorId")
	Double averageInvoices(int sponsorId);

	@Query("SELECT MIN(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId AND s.amount.currency = :currency")
	Double minimumSponsorship(int sponsorId, String currency);

	@Query("SELECT MAX(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId AND s.amount.currency = :currency")
	Double maximumSponsorship(int sponsorId, String currency);

	@Query("SELECT AVG(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId AND s.amount.currency = :currency")
	Double averageSponsorship(int sponsorId, String currency);

	@Query("SELECT systemCurrency FROM SystemConfiguration")
	String findCurrencyDefault();
}

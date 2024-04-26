
package acme.features.sponsor.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(i) FROM Invoice i JOIN Sponsorship s ON i.sponsorship.id = s.id JOIN Sponsor sp ON s.sponsor.id = sp.id WHERE sp.id = :sponsorId AND i.tax <= 21.00")
	Integer totalNumberOfInvoicesTaxLessThanOrEqualTo(int sponsorId);

	@Query("SELECT COUNT(s.id) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId AND s.link IS NOT NULL")
	Integer totalNumberSponsorshipWithLink(int sponsorId);

	@Query("SELECT COUNT(s.id) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Integer totalNumberSponsorshipBySponsor(int sponsorId);

	@Query("SELECT AVG(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Double averageEstimatedCostOfSponsorshipOfSponsor(int sponsorId);

	//BORRAR
	@Query("SELECT AVG(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Double deviationEstimatedCostOfSponsorshipOfSponsor(int sponsorId);

	@Query("SELECT MIN(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Double minimumEstimatedCostOfSponsorshipOfSponsor(int sponsorId);

	@Query("SELECT MAX(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Double maximumEstimatedCostOfSponsorshipOfSponsor(int sponsorId);

	@Query("SELECT COUNT(i) FROM Invoice i JOIN Sponsorship s ON i.sponsorship.id = s.id JOIN Sponsor sp ON s.sponsor.id = sp.id WHERE sp.id = :sponsorId")
	int totalNumberOfInvoicesOfSponsor(int sponsorId);

	@Query("SELECT AVG(i.quantity.amount) FROM Invoice i JOIN Sponsorship s ON i.sponsorship.id = s.id JOIN Sponsor sp ON s.sponsor.id = sp.id WHERE sp.id = :sponsorId")
	Double averageCostOfProjectsOfManager(int sponsorId);

	//BORRAR
	@Query("SELECT AVG(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Double deviationCostOfProjectsOfManager(int sponsorId);

	@Query("SELECT MIN(i.quantity.amount) FROM Invoice i JOIN Sponsorship s ON i.sponsorship.id = s.id JOIN Sponsor sp ON s.sponsor.id = sp.id WHERE sp.id = :sponsorId")
	Double minimumCostOfProjectsOfManager(int sponsorId);

	@Query("SELECT MAX(i.quantity.amount) FROM Invoice i JOIN Sponsorship s ON i.sponsorship.id = s.id JOIN Sponsor sp ON s.sponsor.id = sp.id WHERE sp.id = :sponsorId")
	Double maximumCostOfProjectsOfManager(int sponsorId);
}

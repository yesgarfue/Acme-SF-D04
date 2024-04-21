
package acme.features.auditor.dashboard;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface AuditorDashboardRepository extends AbstractRepository {

	//Total Number of Static Code Audits
	@Query("SELECT count(ca) FROM CodeAudit ca WHERE ca.type = 0 AND ca.auditor.id = :auditorId")
	Integer totalNumberOfStaticCodeAudits(int auditorId);

	//Total Number of Dynamic Code Audits
	@Query("SELECT count(ca) FROM CodeAudit ca WHERE ca.type = 1 AND ca.auditor.id = :auditorId")
	Integer totalNumberOfDynamicCodeAudits(int auditorId);
}

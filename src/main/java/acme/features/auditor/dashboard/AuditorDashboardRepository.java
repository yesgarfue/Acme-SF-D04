
package acme.features.auditor.dashboard;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecords;

public interface AuditorDashboardRepository extends AbstractRepository {

	//Total Number of Static Code Audits
	@Query("SELECT count(ca) FROM CodeAudit ca WHERE ca.type = 0 AND ca.auditor.id = :auditorId")
	Integer totalNumberOfStaticCodeAudits(int auditorId);

	//Total Number of Dynamic Code Audits
	@Query("SELECT count(ca) FROM CodeAudit ca WHERE ca.type = 1 AND ca.auditor.id = :auditorId")
	Integer totalNumberOfDynamicCodeAudits(int auditorId);

	@Query("SELECT AVG(SELECT COUNT(ar) FROM AuditRecords ar WHERE ar.codeAudit.id = a.id) FROM CodeAudit a WHERE a.auditor.id = :id")
	Double averageNumberOfAuditRecords(int id);

	@Query("SELECT COUNT(ar) FROM AuditRecords ar WHERE ar.codeAudit.auditor.id = :id GROUP BY ar.codeAudit ")
	List<Integer> numberOfRecordsByCodeAudit(int id);

	@Query("SELECT MIN(SELECT COUNT(ar) FROM AuditRecords ar WHERE ar.codeAudit.id = a.id) FROM CodeAudit a WHERE a.auditor.id = :id")
	Double minimumNumberOfAuditRecords(int id);

	@Query("SELECT MAX(SELECT COUNT(ar) FROM AuditRecords ar WHERE ar.codeAudit.id = a.id) FROM CodeAudit a WHERE a.auditor.id = :id")
	Double maximumNumberOfAuditRecords(int id);

	@Query("SELECT ar FROM AuditRecords ar WHERE ar.codeAudit.auditor.id = :id")
	List<AuditRecords> findAllAuditRecordsByAuditorId(int id);

	default Double deviationOfAuditRecords(final int id) {
		final List<Integer> numberOfRecords = this.numberOfRecordsByCodeAudit(id);
		if (numberOfRecords.isEmpty())
			return null;
		final Double average = numberOfRecords.stream().mapToInt(Integer::intValue).average().orElse(0);
		final List<Double> squaredDistancesToMean = numberOfRecords.stream().map(n -> Math.pow(n - average, 2.)).collect(Collectors.toList());
		final Double averageSquaredDistancesToMean = squaredDistancesToMean.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		return Math.sqrt(averageSquaredDistancesToMean);
	}

	default Double averageTimeOfAuditRecords(final int id) {
		final List<AuditRecords> records = this.findAllAuditRecordsByAuditorId(id);
		return records.isEmpty() ? null : records.stream().mapToDouble(AuditRecords::getHoursFromPeriod).average().orElse(0);
	}

	default Double timeDeviationOfAuditRecords(final int id) {
		final List<AuditRecords> records = this.findAllAuditRecordsByAuditorId(id);
		if (records.size() < 2)
			return null;
		final List<Double> hours = records.stream().map(AuditRecords::getHoursFromPeriod).collect(Collectors.toList());
		final Double average = hours.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		final List<Double> squaredDistancesToMean = hours.stream().map(h -> Math.pow(h - average, 2)).collect(Collectors.toList());
		final Double averageSquaredDistancesToMean = squaredDistancesToMean.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		return Math.sqrt(averageSquaredDistancesToMean);
	}

	default Double minimumTimeOfAuditRecords(final int id) {
		final List<AuditRecords> records = this.findAllAuditRecordsByAuditorId(id);
		return records.isEmpty() ? null : records.stream().mapToDouble(AuditRecords::getHoursFromPeriod).min().getAsDouble();
	}

	default Double maximumTimeOfAuditRecords(final int id) {
		final List<AuditRecords> records = this.findAllAuditRecordsByAuditorId(id);
		return records.isEmpty() ? null : records.stream().mapToDouble(AuditRecords::getHoursFromPeriod).max().getAsDouble();
	}

}

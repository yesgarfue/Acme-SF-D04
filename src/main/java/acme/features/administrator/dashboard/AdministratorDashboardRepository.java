
package acme.features.administrator.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface AdministratorDashboardRepository extends AbstractRepository {

	//	@Query("SELECT r.role, COUNT(r) FROM Principal r GROUP BY r.role")
	//	Collection<String> principalsByRole(String role);
	//
	//	default Map<String, Integer> numberOfPrincipalsByRol() {
	//		return null;
	//	}

	@Query("SELECT COUNT(n) FROM Notice n WHERE n.email IS NOT NULL AND n.link IS NOT NULL")
	Integer noticeWithEmailAndLinkRatio();

	@Query("SELECT COUNT(o) FROM Objective o WHERE o.criticalStatus = true")
	Integer criticalObjectiveRatio();

	@Query("SELECT COUNT(o) FROM Objective o WHERE o.criticalStatus = false")
	Integer nonCriticalObjectiveRatio();

	@Query("SELECT r.impact * r.probability FROM Risk r")
	Collection<Double> valuesOfRisks();

	default Double averageValueRisk() {
		final Collection<Double> values = this.valuesOfRisks();
		return values.stream().mapToDouble(x -> x).average().orElse(0.0);
	}

	default Double minimumValueRisk() {
		final Collection<Double> values = this.valuesOfRisks();
		return values.stream().mapToDouble(x -> x).min().orElse(0.0);

	}

	default Double maximumValueRisk() {
		final Collection<Double> values = this.valuesOfRisks();
		return values.stream().mapToDouble(x -> x).max().orElse(0.0);

	}

	default Double deviationValueRisk() {
		final Double avg = this.averageValueRisk();
		final Collection<Double> values = this.valuesOfRisks();
		final double numerator = values.stream().mapToDouble(x -> (x - avg) * (x - avg)).sum();
		return Math.sqrt(numerator / values.size());
	}

	@Query("SELECT COUNT(c) FROM Claim c WHERE c.instantiation >= CURRENT_DATE - 70")
	Integer numberOfClaimsOverLastTenWeeks();

	@Query("SELECT COUNT(c) FROM Claim c ")
	Integer numberOfClaims();

	default Double averageNumberOfClaimsOverLastTenWeeks() {
		final Integer values = this.numberOfClaimsOverLastTenWeeks();
		final Integer total = this.numberOfClaims();
		double result = values / total;
		return result;
	}

	default Integer minimumNumberOfClaimsOverLastTenWeeks() {
		return this.numberOfClaimsOverLastTenWeeks();
	}

	default Integer maximumNumberOfClaimsOverLastTenWeeks() {
		return this.numberOfClaimsOverLastTenWeeks();
	}

	default Double deviationNumberOfClaimsOverLastTenWeeks() {
		return 0.0;
	}

}

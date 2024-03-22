
package acme.features.administrator.dashboard;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;

import acme.client.data.AbstractRole;
import acme.client.repositories.AbstractRepository;

public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("SELECT r FROM AbstractRole r")
	Collection<AbstractRole> findAllRoles();

	default Map<String, Integer> numberOfPrincipalsByRol() {
		AbstractRole role = this.findAllRoles().stream().findFirst().orElse(null);
		role.getAuthorityName();
		return this.findAllRoles().stream().collect(Collectors.groupingBy(AbstractRole::getAuthorityName, Collectors.summingInt(x -> 1)));
	}

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

	@Query("SELECT DATE(c.instantiation), COUNT(c) FROM Claim c GROUP BY DATE(c.instantiation)")
	Collection<Object[]> numberOfClaimsPostedForDay();

	default Collection<Integer> numberOfClaimsPostedForDayOverLastTenWeeks() {
		LocalDate systemDate = LocalDate.of(2022, 7, 30);
		Date seventyDaysAgo = Date.from(systemDate.minusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Collection<Object[]> results = this.numberOfClaimsPostedForDay();

		Collection<Integer> counts = results.stream().filter(result -> ((Date) result[0]).after(seventyDaysAgo)).map(result -> ((Long) result[1]).intValue()).collect(Collectors.toList());
		return counts;
	}

	default Double averageNumberOfClaimsOverLastTenWeeks() {
		final Collection<Integer> values = this.numberOfClaimsPostedForDayOverLastTenWeeks();
		return values.stream().mapToDouble(x -> x).average().orElse(0.0);
	}

	default Integer minimumNumberOfClaimsOverLastTenWeeks() {
		return this.numberOfClaimsPostedForDayOverLastTenWeeks().stream().mapToInt(x -> x).min().orElse(0);
	}

	default Integer maximumNumberOfClaimsOverLastTenWeeks() {
		return this.numberOfClaimsPostedForDayOverLastTenWeeks().stream().mapToInt(x -> x).max().orElse(0);
	}

	default Double deviationNumberOfClaimsOverLastTenWeeks() {
		final Double avg = this.averageNumberOfClaimsOverLastTenWeeks();
		final Collection<Integer> values = this.numberOfClaimsPostedForDayOverLastTenWeeks();
		final double numerator = values.stream().mapToDouble(x -> (x - avg) * (x - avg)).sum();
		return values.isEmpty() ? 0.0 : Math.sqrt(numerator / values.size());
	}

}

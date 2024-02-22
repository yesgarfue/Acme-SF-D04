
package acme.roles;

import javax.persistence.Entity;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Manager extends AbstractRole {

	/*
	 * The system must handle manager dashboards with the following data: total number of “must”,
	 * “should”, “could”, and “won’t” user stories; average, deviation, minimum, and maximum estimated
	 * cost of the user stories; average, deviation, minimum, and maximum cost of the projects.
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				numberMust;
	private Integer				numberShould;
	private Integer				numberCould;
	private Integer				numberWont;
	private Double				averageEstimate;
	private Double				deviationEstimate;
	private Double				minimumEstimate;
	private Double				maximumEstimate;

	private Double				averageCost;
	private Double				deviationCost;
	private Double				minimumCost;
	private Double				maximumCost;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}

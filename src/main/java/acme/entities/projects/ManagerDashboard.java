
package acme.entities.projects;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.PositiveOrZero;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ManagerDashboard extends AbstractEntity {

	/*
	 * The system must handle manager dashboards with the following data: total number of “must”,
	 * “should”, “could”, and “won’t” user stories; average, deviation, minimum, and maximum estimated
	 * cost of the user stories; average, deviation, minimum, and maximum cost of the projects.
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@PositiveOrZero
	private Integer				numberMust;
	@PositiveOrZero
	private Integer				numberShould;
	@PositiveOrZero
	private Integer				numberCould;
	@PositiveOrZero
	private Integer				numberWont;
	@PositiveOrZero
	private Double				averageEstimate;
	@PositiveOrZero
	private Double				deviationEstimate;
	@PositiveOrZero
	private Double				minimumEstimate;
	@PositiveOrZero
	private Double				maximumEstimate;
	@PositiveOrZero
	private Double				averageCost;
	@PositiveOrZero
	private Double				deviationCost;
	@PositiveOrZero
	private Double				minimumCost;
	@PositiveOrZero
	private Double				maximumCost;

	// Relationships ----------------------------------------------------------
	@OneToOne
	private Manager				manager;
}


package acme.entities.projects;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
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
	@NotNull
	private Integer				numberMust;
	@PositiveOrZero
	@NotNull
	private Integer				numberShould;
	@PositiveOrZero
	@NotNull
	private Integer				numberCould;
	@PositiveOrZero
	@NotNull
	private Integer				numberWont;
	@PositiveOrZero
	@NotNull
	private Double				averageEstimate;
	@PositiveOrZero
	@NotNull
	private Double				deviationEstimate;
	@PositiveOrZero
	@NotNull
	private Double				minimumEstimate;
	@PositiveOrZero
	@NotNull
	private Double				maximumEstimate;
	@PositiveOrZero
	@NotNull
	private Double				averageCost;
	@PositiveOrZero
	@NotNull
	private Double				deviationCost;
	@PositiveOrZero
	@NotNull
	private Double				minimumCost;
	@PositiveOrZero
	private Double				maximumCost;

	// Relationships ----------------------------------------------------------
	@OneToOne
	@NotNull
	private Manager				manager;
}

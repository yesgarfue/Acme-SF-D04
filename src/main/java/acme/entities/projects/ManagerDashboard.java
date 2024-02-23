
package acme.entities.projects;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

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

	// Relationships ----------------------------------------------------------
	@OneToOne
	private Manager				manager;
}

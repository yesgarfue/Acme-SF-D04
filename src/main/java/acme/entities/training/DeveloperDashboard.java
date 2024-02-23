
package acme.entities.training;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import acme.client.data.AbstractEntity;
import acme.roles.Developer;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DeveloperDashboard extends AbstractEntity {

	/*
	 * The system must handle developer dashboards
	 * with the following data:
	 * total number of training modules with an update moment;
	 * total number of training sessions with a link;
	 * average, deviation, minimum,
	 * and maximum time of the training modules.
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				totalTrainingModules;

	private Integer				totalTrainingSessions;

	private Double				averageTimeTrainingModule;

	private Double				deviationTimeTrainingModule;

	private Double				maxTimeTrainingModule;

	private Double				minTimeTrainingModule;

	// Relationships ----------------------------------------------------------

	@OneToOne
	private Developer			developer;
}

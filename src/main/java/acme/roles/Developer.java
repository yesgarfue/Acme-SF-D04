
package acme.roles;

import javax.persistence.Entity;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Developer extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Integer				totalTrainingModules;

	private Integer				totalTrainingSessions;

	private Double				averageTimeTrainingModule;

	private Double				deviationTimeTrainingModule;

	private Double				maxTimeTrainingModule;

	private Double				minTimeTrainingModule;

}

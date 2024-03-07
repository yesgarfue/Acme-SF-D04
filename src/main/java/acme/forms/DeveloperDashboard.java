
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

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

	Integer						totalTrainingModules;

	Integer						totalTrainingSessions;

	Double						averageTimeTrainingModule;

	Double						deviationTimeTrainingModule;

	Double						maxTimeTrainingModule;

	Double						minTimeTrainingModule;

}

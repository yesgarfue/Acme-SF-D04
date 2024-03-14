
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	/*
	 * The system must handle manager dashboards with the following data: total number of “must”,
	 * “should”, “could”, and “won’t” user stories; average, deviation, minimum, and maximum estimated
	 * cost of the user stories; average, deviation, minimum, and maximum cost of the projects.
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						numberMust;
	Integer						numberShould;
	Integer						numberCould;
	Integer						numberWont;

	Double						averageEstimate;
	Double						deviationEstimate;
	Double						minimumEstimate;
	Double						maximumEstimate;

	Double						averageCost;
	Double						deviationCost;
	Double						minimumCost;
	Double						maximumCost;

}

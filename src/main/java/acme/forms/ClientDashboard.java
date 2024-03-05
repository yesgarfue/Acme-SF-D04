
package acme.forms;

import javax.validation.constraints.NotNull;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	/*
	 * The system must handle client dashboards with the following data:
	 * total number of progress logs with a completeness rate below 25%,
	 * between 25% and 50%, between 50% and 75%, and above 75%; average,
	 * deviation, minimum, and maximum budget of the contracts.
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	public Integer				progressLogsBelow25;

	@NotNull
	public Integer				progressLogsBetween25And50;

	@NotNull
	public Integer				progressLogsBetween50And75;

	@NotNull
	public Integer				progressLogsAbove75;

	@NotNull
	public Double				averageBudget;

	@NotNull
	public Double				deviationBudget;

	@NotNull
	public Double				minimumBudget;

	@NotNull
	public Double				maximumBudget;
}

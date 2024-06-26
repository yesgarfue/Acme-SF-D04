
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	/*
	 * The system must handle administrator dashboards with the following indicators: total number of principals with each role;
	 * ratio of notices with both an email address and a link;(Ratio que contenga tanto un email como un link)
	 * ratios of critical and non-critical objectives;
	 * average, minimum,maximum, and standard deviation of the value in the risks;
	 * average, minimum, maximum, and standard deviation of the number of claims posted over the last 10 weeks.
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	//Strings represent every roles
	Map<String, Integer>		numberOfPrincipalsByRol;

	Integer						noticeWithEmailAndLinkRatio;
	Integer						criticalObjectiveRatio;
	Integer						nonCriticalObjectiveRatio;

	Double						averageValueRisk;
	Double						minimunValueRisk;
	Double						maximunValueRisk;
	Double						desviationValueRisk;

	Double						averageNumberOfClaimsOverLastTenWeeks;
	Integer						minimunNumberOfClaimsOverLastTenWeeks;
	Integer						maximunNumberOfClaimsOverLastTenWeeks;
	Double						desviationNumberOfClaimsOverLastTenWeeks;

}


package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	/*
	 * 4) The system must handle auditor dashboards with the following data:
	 * total number of code audits for “Static” and “Dynamic” types; average, deviation,
	 * minimum, and maximum number of audit records in their audits; average,
	 * deviation, minimum, and maximum time of the period lengths in their audit
	 * records.
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	private Double				totalNumberOfAudits;

	private Double				averageNumberOfAuditingRecords;

	private Double				deviationOfAuditingRecords;

	private Double				minimumNumberOfAuditingRecords;

	private Double				maximumNumberOfAuditingRecords;

	private Double				averageTimeOfAuditingRecords;

	private Double				timeDeviationOfAuditingRecords;

	private Double				minimumTimeOfAuditingRecords;

	private Double				maximumTimeOfAuditingRecords;

}

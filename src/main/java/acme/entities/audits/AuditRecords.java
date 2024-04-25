
package acme.entities.audits;

import java.beans.Transient;
import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.helpers.MomentHelper;
import acme.enumerate.Mark;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
public class AuditRecords extends AbstractEntity {

	/*
	 * The result of each code audit is based on the analysis of their
	 * audit records. The system must store the following data about them:
	 * a code (pattern “AU-[0-9]{4}-[0-9]{3}”, not blank, unique),
	 * the period during which the subject was audited (in the past, at least one hour long),
	 * a mark (“A+”, “A”, “B”, “C”, “F”, or “F-”), and an optional link with further
	 * information
	 */
	// Serialisation identifier -----------------------------------------------
	public static final long	serialVersionUID	= 1L;

	//Atributes----------------------------------------------------------------
	@NotNull
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}")
	private String				code;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date				finishTime;

	@NotNull
	private Mark				mark;

	@URL
	private String				optionalLink;

	@NotNull
	private boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double getHoursFromPeriod() {
		final Duration duration = MomentHelper.computeDuration(this.startTime, this.finishTime);
		return duration.getSeconds() / 3600.0;
	}


	// Relationships ----------------------------------------------------------
	@ManyToOne
	@NotNull
	@Valid
	private CodeAudit codeAudit;
}

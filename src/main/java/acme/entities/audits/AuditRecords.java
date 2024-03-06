
package acme.entities.audits;

import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.time.DurationMin;

import acme.client.data.AbstractEntity;
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
	protected String			code;

	@NotNull
	@Past
	@DurationMin(hours = 1)
	protected Duration			period;

	@NotNull
	protected Mark				mark;

	@URL
	protected String			optionalLink;

	// Relationships ----------------------------------------------------------
	@ManyToOne
	@NotNull
	@Valid
	private CodeAudits			codeAudit;
}

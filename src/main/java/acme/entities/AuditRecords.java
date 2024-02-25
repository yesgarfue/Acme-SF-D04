
package acme.entities;

import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.time.DurationMin;

import acme.client.data.AbstractEntity;
import acme.enumerate.Nota;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
public class AuditRecords extends AbstractEntity {

	/*
	 * Code audits are essential pieces to ensure the quality
	 * of a project. The system must store the following data
	 * about them: a code (pattern “[A-Z]{1,3}-[0-9]{3}”, not blank, unique),
	 * an execution date (in the past), a type (“Static”, “Dynamic”),
	 * a list of proposed corrective actions (not blank, shorter
	 * than 101 characters), a mark (computed as the mode of the
	 * marks in the corresponding auditing records; ties must be
	 * broken arbitrarily if necessary), and an optional link with
	 * further information.
	 */
	// Serialisation identifier -----------------------------------------------
	public static final long	serialVersionUID	= 1L;

	//Atributes----------------------------------------------------------------
	@NotNull
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^AU-[0-9]{4}-[0-9]{3}$")
	private String				code;

	@NotNull
	@Past
	@DurationMin(hours = 1)
	private Duration			period;

	@NotNull
	private Nota				mark;

	@URL
	private String				optionalLink;
}

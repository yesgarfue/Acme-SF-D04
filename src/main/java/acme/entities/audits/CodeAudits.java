
package acme.entities.audits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.enumerate.Type;

public class CodeAudits extends AbstractEntity {

	/*
	 * Code audits are essential pieces to ensure the
	 * quality of a project. The system must store the
	 * following data about them: a code (pattern “[A-Z]{1,3}-[0-9]{3}”
	 * , not blank, unique), an execution date
	 * (in the past), a type (“Static”, “Dynamic”),
	 * a list of proposed corrective actions (not blank
	 * , shorter than 101 characters), a mark (computed as the mode
	 * of the marks in the corresponding auditing records; ties must be
	 * broken arbitrarily if necessary), and an optional link with further information.
	 */
	// Serialisation identifier -----------------------------------------------	
	public static final long	serialVersionUID	= 1L;

	//Atributes----------------------------------------------------------------
	@NotNull
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				executionDate;

	@NotNull
	private Type				type;

	@NotNull
	@NotBlank
	@Length(max = 101)
	private String				correctiveActions;

}

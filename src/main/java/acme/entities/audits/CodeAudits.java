
package acme.entities.audits;

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

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.entities.projects.Project;
import acme.enumerate.Type;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	//@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				executionDate;

	@NotNull
	private Type				type;

	@NotNull
	@NotBlank
	@Length(max = 101)
	private String				correctiveActions;
	// Relationships ----------------------------------------------------------
	@ManyToOne
	@NotNull
	@Valid
	private Project				project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Auditor			auditor;

}

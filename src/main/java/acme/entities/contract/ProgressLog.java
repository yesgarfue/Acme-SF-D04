
package acme.entities.contract;

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
import org.hibernate.validator.constraints.Range;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProgressLog extends AbstractEntity {

	/*
	 * Every contract has an evolution that is composed of progress logs.
	 * The system must store the following data about them:
	 * a record id (pattern “PG-[A-Z]{1,2}-[0-9]{4}”, not blank, unique),
	 * a percentage of completeness (positive),
	 * a comment on the progress (not blank, shorter than 101 characters),
	 * a registration moment (in the past),
	 * and a responsible person for the registration (not blank, shorter than 76 characters).
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^PG-[A-Z]{1,2}-[0-9]{4}$", message = "La referencia debe seguir el patrón PG-AA-XXXX")
	private String				recordId;

	@NotNull
	@Range(min = 0, max = 100)
	private Double				completenessPercentage;

	@NotBlank
	@Length(max = 100)
	private String				progressComment;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationMoment;

	@NotBlank
	@Length(max = 75)
	private String				responsiblePerson;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Contract			contract;
}

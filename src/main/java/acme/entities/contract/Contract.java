
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

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contract extends AbstractEntity {

	/*
	 * A contract is one or several agreements between the stakeholders
	 * involved in the development of a project. The system must store the following data about them:
	 * a code (pattern “[A-Z]{1,3}-[0-9]{3}”, not blank, unique), an instantiation moment (in the past),
	 * a provider name (not blank, shorter than 76 characters),
	 * a customer name (not blank, shorter than 76 characters),
	 * some goals (not blank, shorter than 101 characters),
	 * and a budget (less than or equal to the corresponding project cost).
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}", message = "La referencia debe seguir el patrón AAA-XXX")
	@Column(unique = true)
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				instantiationMoment;

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String				providerName;

	@NotNull
	@NotBlank
	@Length(max = 75)
	private String				customerName;

	@NotNull
	@NotBlank
	@Length(max = 100)
	private String				goals;

	@NotNull
	private Money				budget;

	// Relationships -------------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;
}

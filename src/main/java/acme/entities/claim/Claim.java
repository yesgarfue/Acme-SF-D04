
package acme.entities.claim;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Claim extends AbstractEntity {
	/*
	 * A claim is an opposition or contradiction posted by anyone
	 * that is made to something considered to be unjust.
	 * The system must store the following data about them:
	 * a code (pattern “C-[0-9]{4}”), not blank, unique),
	 * an instantiation moment (in the past),
	 * a heading (not blank, shorter than 76 characters),
	 * a description (not blank, shorter than 101 characters),
	 * the department to which it is addressed (not blank, shorter than 101 characters),
	 * an optional email address,
	 * and an optional link.
	 */
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotBlank
	@Pattern(regexp = "C-[0-9]{4}", message = "La referencia debe seguir el patrón C-XXXX")
	@Column(unique = true)
	private String				code;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	private Date				instantiation;

	@Length(max = 76)
	@NotBlank
	private String				heading;

	@NotBlank
	@Length(max = 101)
	private String				description;

	@NotBlank
	@Length(max = 101)
	private String				departament;

	@Email
	private String				email;

	@URL
	private String				link;

}

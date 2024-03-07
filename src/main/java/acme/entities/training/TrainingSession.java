
package acme.entities.training;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingSession extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "TS-[A-Z]{1,3}-[0-9]{3}", message = "The reference must follow the pattern TS-XXX-XXX.")
	@Column(unique = true)
	private String				code;

	private Date				period; // poner restricciones en servicio

	@NotNull
	@NotBlank
	@Size(max = 76, message = "The location must be less than 76 characters.")
	private String				location;

	@NotNull
	@NotBlank
	@Size(max = 76, message = "The instructor must be less than 76 characters.")
	private String				instructor;

	@NotNull
	@NotEmpty
	private String				contactEmail;

	@URL
	private String				optionalLink;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private TrainingModule		trainingModule;
}

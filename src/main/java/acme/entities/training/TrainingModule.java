
package acme.entities.training;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.enumerate.Difficulty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingModule extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}", message = "The code must follow the pattern XXX-XXX.")
	private String				code;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				creationMoment;

	@NotNull
	@NotBlank
	@Size(max = 101, message = "The details must be less than 101 characters.")
	private String				details;

	@NotNull
	private Difficulty			difficultyLevel;

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	//que sea despues de la creacion hacer en servicio
	private Date				updatedMoment;

	@URL
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------

	private Date				totalTime;


	private Date getTotaltime() {
		return new Date(this.updatedMoment.getTime() - this.creationMoment.getTime());
	}

	// relacion con project

}

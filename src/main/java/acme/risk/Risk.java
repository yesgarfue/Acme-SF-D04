
package acme.risk;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "R-[0-9]{3}", message = "The reference must follow the pattern R-XXX.")
	@Column(unique = true)
	@NotNull
	private String				reference;

	@Past
	@NotNull
	private LocalDateTime		identificationDate;

	@Positive
	@Max(100)
	private Double				impact;

	@Positive
	private Double				probability;

	@NotBlank
	@Size(max = 101, message = "The description must be less than 101 characters.")
	private String				description;

	@URL
	private String				additionalInfoLink;

	// Derived attributes -----------------------------------------------------

	private Double				value				= this.impact * this.probability;

}

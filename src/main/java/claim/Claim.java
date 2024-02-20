
package claim;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.checkerframework.common.aliasing.qual.Unique;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Claim {
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
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int					id;

	@NotBlank
	@Pattern(regexp = "C-[0-9]{4}", message = "La referencia debe seguir el patrón R-XXX")
	@Unique
	private String				code;

	@Past
	@NotNull
	private LocalDateTime		instantiation;

	@Size(max = 76, message = "Heading must be shorter than 76 characters.")
	@NotBlank
	private String				heading;

	@NotBlank
	@Size(max = 101, message = "Description must be shorter than 101 characters.")
	private String				description;
	@NotBlank
	@Size(max = 101, message = "Departament must be shorter than 101 characters.")
	private String				departament;

	private String				emailOptional;
	private String				linkInfoOpcional;

}

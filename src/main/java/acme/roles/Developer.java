
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Developer extends AbstractRole {

	/*
	 * 13) There is a new project-specific role called developer,
	 * which has the following profile data:
	 * degree (not blank, shorter than 76 characters),
	 * a specialisation (not blank, shorter than 101 characters),
	 * list of skills (not blank, shorter than 101 characters),
	 * an email, and an optional link with further information.
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Size(max = 76, message = "Degree must be shorter than 76 characters.")
	private String				degree;

	@NotBlank
	@Size(max = 101, message = "Specialisation must be shorter than 101 characters.")
	private String				specialisation;

	@NotBlank
	@Size(max = 101, message = "Skills must be shorter than 101 characters.")
	private String				skills;

	@NotNull
	private String				email;

	@URL
	private String				optionalLink;

}

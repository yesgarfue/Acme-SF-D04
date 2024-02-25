
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Manager extends AbstractRole {

	/*
	 * There is a new project-specific role called manager, which has the following profile data:
	 * degree (not blank, shorter than 76 characters), an overview (not blank, shorter than 101 characters),
	 * list of certifications (not blank, shorter than 101 characters),
	 * and an optional link with further information.
	 */
	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Size(max = 76, message = "Degree must be shorter than 76 characters.")
	private String				degree;

	@NotBlank
	@Size(max = 101, message = "Overview must be shorter than 101 characters.")
	private String				overview;

	@NotBlank
	@Size(max = 101, message = "Certifications must be shorter than 101 characters.")
	private String				certifications;

	@URL
	private String				link;

}

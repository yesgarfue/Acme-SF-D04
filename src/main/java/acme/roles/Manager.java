
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
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
	@Length(max = 75)
	private String				degree;

	@NotBlank
	@Length(max = 100)
	private String				overview;

	@NotBlank
	@Length(max = 100)
	private String				certifications;

	@URL
	private String				link;

}


package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import acme.enumerate.ClientType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client extends AbstractRole {

	/*
	 * There is a new project-specific role called clients, which has the following profile data:
	 * identification (pattern “CLI-[0-9]{4}”, not blank, unique),
	 * a company name (not blank, shorter than 76 characters),
	 * type (not blank, either “company” or “individual”),
	 * an email (not blank), and an optional link with further information.
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^CLI-[0-9]{4}$", message = "La referencia debe seguir el patrón CLI-XXXX")
	private String				identification;

	@NotBlank
	@Length(max = 75)
	private String				companyName;

	@NotNull
	private ClientType			clientType;

	@NotNull
	@NotBlank
	@Email
	private String				email;

	@URL
	private String				link;
}

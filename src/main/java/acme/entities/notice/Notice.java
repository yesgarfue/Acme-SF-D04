
package acme.entities.notice;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice extends AbstractEntity {
	/*
	 * A notice is a message posted by an authenticated principal.
	 * The system must store the following data about them: an
	 * instantiation moment (in the past), a title (not blank,
	 * shorter than 76 characters), an author (not blank, shorter
	 * than 76 characters), a message (not blank, shorter than 101
	 * characters), an optional email address, and an optional link.
	 * The author must be computed as follows: “〈username〉 - 〈surname,
	 * name〉”, where “〈username〉” denotes the username of the principal
	 * who has posted
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	@Size(max = 76, message = "A title (not blank, shorter than 76 characters)")
	@NotBlank
	private String				title;

	@Size(max = 76, message = "A author (not blank, shorter than 76 characters)")
	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9]+-[A-Za-z]+,[A-Za-z]+")
	private String				author;

	@NotBlank
	@Size(max = 101, message = "Message must be shorter than 101 characters.")
	private String				message;

	@Email
	private String				email;

	@URL
	private String				link;
}

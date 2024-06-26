
package acme.entities.projects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Project extends AbstractEntity {

	/*
	 * A project aggregates several user stories elicited by the same manager. The system must store the
	 * following data about them: a code (pattern “[A-Z]{3}-[0-9]{4}”, not blank, unique), a title (not blank,
	 * shorter than 76 characters), an abstract (not blank, shorter than 101 characters), an indication on
	 * whether it has fatal errors, e.g., panics, a cost (positive or nought), and an optional link with further
	 * information. Projects containing fatal errors must be rejected by the system.
	 */

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{4}$", message = "La referencia debe seguir el patrón AAA-XXXX")
	@Column(unique = true)
	private String				code;

	@Length(max = 75)
	@NotBlank
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				abstracts;

	@NotNull
	private boolean				indication;

	@NotNull
	@Valid
	private Money				cost;

	@URL
	private String				link;

	@NotNull
	private boolean				draftMode;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Manager				manager;
}

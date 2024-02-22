
package acme.entities.projects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserStory extends AbstractEntity {

	/*
	 * A user story is a document that a manager uses to represent the smallest unit of work in a project.
	 * The system must store the following data about them: a title (not blank, shorter than 76
	 * characters), a description (not blank, shorter than 101 characters), an estimated cost (in hours,
	 * positive, not nought), the acceptance criteria (not blank, shorter than 101 characters), a priority
	 * (“Must”, “Should”, “Could”, or “Won’t”), and an optional link with further information.
	 */

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;
	// Attributes -------------------------------------------------------------

	@NotBlank
	@Size(max = 76, message = "Title must be shorter than 76 characters.")
	private String				title;

	@NotBlank
	@Size(max = 101, message = "Description must be shorter than 101 characters.")
	private String				description;

	@Positive
	private Double				estimatedCost;

	@NotBlank
	@Size(max = 101, message = "Acceptance criteria must be shorter than 101 characters.")
	private String				acceptanceCriteria;

	@NotBlank
	private Priorities			priority;

	@URL
	private String				link;

	// Relationships ----------------------------------------------------------
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Manager				manager;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project				project;

}

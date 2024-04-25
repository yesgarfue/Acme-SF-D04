
package acme.entities.projects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.enumerate.Priority;
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
	@Length(max = 75)
	private String				title;

	@NotBlank
	@Length(max = 100)
	private String				description;

	@NotNull
	@Min(1)
	private Integer				estimatedCost;

	@NotBlank
	@Length(max = 100)
	private String				acceptanceCriteria;

	@NotNull
	private Priority			priority;

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

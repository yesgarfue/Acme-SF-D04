
package acme.entities.training;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.helpers.MomentHelper;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TrainingSession extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "TS-[A-Z]{1,3}-[0-9]{3}", message = "The reference must follow the pattern TS-XXX-XXX.")
	@Column(unique = true)
	private String				code;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endDate;

	@NotBlank
	@Length(max = 76)
	private String				location;

	@NotBlank
	@Length(max = 76)
	private String				instructor;

	@NotBlank
	private String				contactEmail;

	@URL
	private String				optionalLink;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double getPeriodInHours() {
		final Duration duration = MomentHelper.computeDuration(this.getStartDate(), this.getEndDate());
		final Long seconds = duration.getSeconds();
		return seconds.doubleValue() / 3600.;
	}

	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private TrainingModule trainingModule;
}

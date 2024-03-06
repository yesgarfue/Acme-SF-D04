
package acme.entities.banner;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Banner extends AbstractEntity {

	/*
	 * A banner allows administrators to advertise products,
	 * services, or organisations. The system must store the following data
	 * about them: an instantiation/update moment (in the past), a
	 * display period (must start at any moment after the
	 * instantiation/update moment and must last for at least one week), a link
	 * to a picture that must be stored somewhere else, a slogan (not blank, shorter
	 * than 76 characters), and a link to a target web document.
	 */
	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	@NotNull
	@Past
	private Date				instationUpdateMoment;

	//////////////////////////
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				finishTime;
	////////////////////////// 

	@NotNull
	@URL
	private String				linkPicture;

	@NotBlank
	@Length(max = 76)
	private String				slogan;

	@NotNull
	@URL
	private String				linkDocument;

	// Derived attributes -----------------------------------------------------


	private Date period() {
		Long difference = this.finishTime.getTime() - this.startTime.getTime();
		return new Date(difference);
	}
}

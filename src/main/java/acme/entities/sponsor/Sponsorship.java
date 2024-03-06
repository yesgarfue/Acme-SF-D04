
package acme.entities.sponsor;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.projects.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//------------------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				moment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date				endDate;

	@PositiveOrZero
	private Money				amount;

	private SponsorshipType		SponsorshipType;

	@Email
	private String				contactEmail;

	@URL
	private String				link;

	//Relationships -------------------------------------------------------------

	//Muchos sponsorchips pueden estar relacionados a u proyecto por eso la relación de muchos a uno
	@ManyToOne(optional = false)
	private Project				project;

	//Aqui le iba a poner de UNO A UNO, pero dijo que no lo usaramos entonces es de UNO A MUCHOS (QUE PUEDE SER UNO)
	//o asi pienso que puede ser
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private Invoice				invoice;

}

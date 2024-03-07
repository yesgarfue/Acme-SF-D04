
package acme.entities.sponsor;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invoice extends AbstractEntity {

	//Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//------------------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	private String				code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				recordTime;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				expirationDate;

	@PositiveOrZero
	private int					amount;

	@PositiveOrZero
	private double				tax;

	@URL
	private String				link;


	public double totalAmount() {
		return this.amount + this.tax / 100 * this.amount;
	}
}

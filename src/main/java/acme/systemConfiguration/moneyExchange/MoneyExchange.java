
package acme.systemConfiguration.moneyExchange;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MoneyExchange extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Query attributes -------------------------------------------------------
	@NotNull
	@Valid
	private Money				source;

	@NotBlank
	@Pattern(regexp = "[A-Z]{3}")
	private String				targetCurrency;

	// Response attributes ----------------------------------------------------

	@Valid
	private Money				target;

	@NotNull
	private Date				date;
}

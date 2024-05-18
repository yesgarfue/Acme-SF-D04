
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Atributes --------------------------------------------------------------
	int							totalNumInvoicesWithTaxLessOrEqualTo21;
	int							totalNumInvoicesWithLink;
	double						averageSponsorshipsAmount;
	double						deviationSponsorshipsAmount;
	double						minimumSponsorshipsAmount;
	double						maximunSponsorshipsAmount;
	double						averageInvoicesQuantity;
	double						deviationInvoicesQuantity;
	double						minimumInvoicesQuantity;
	double						maximunInvoicesQuantity;
}

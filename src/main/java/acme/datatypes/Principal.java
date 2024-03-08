
package acme.datatypes;

import javax.validation.constraints.NotBlank;

import acme.client.data.accounts.DefaultUserIdentity;

public class Principal extends DefaultUserIdentity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	private String				username;

	// Derived attributes -----------------------------------------------------


	@Override
	public String getFullName() {
		StringBuilder result;

		result = new StringBuilder();
		result.append(this.username);
		result.append("-");
		result.append(this.getSurname());
		result.append(",");
		result.append(this.getName());
		return result.toString();
	}
}

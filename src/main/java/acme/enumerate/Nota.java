
package acme.enumerate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Nota {

	A_Plus("A+"), A("A"), B("B"), C("C"), F("F"), F_Minus("F-");


	private final String nota;


	public String getNota() {
		return this.nota;
	}

}

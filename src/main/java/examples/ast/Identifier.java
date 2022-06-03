/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

public class Identifier extends Expresion {

	private final String name;
	
	public Identifier(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}
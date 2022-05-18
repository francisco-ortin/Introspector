/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.ast;

public abstract class Expresion extends Statement {
	
	public Type type;
	
	public Expresion() {
		super();
	}

	public boolean lvalue;
}

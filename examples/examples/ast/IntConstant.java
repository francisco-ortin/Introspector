/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

public class IntConstant extends Expresion {

	public int value;
	
	public IntConstant(int value) {
		super();
		this.value=value;
	}


}

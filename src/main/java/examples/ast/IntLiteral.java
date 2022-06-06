/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

/**
 * Integer literals of the language.
 */
public class IntLiteral extends Expression {

	public int value;
	
	public IntLiteral(int line, int column, int value) {
		super(line, column);
		this.value=value;
	}

	@Override
	public String toString() {
		return "" + value;
	}

}

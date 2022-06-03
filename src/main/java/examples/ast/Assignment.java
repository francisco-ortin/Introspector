/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package examples.ast;


public class Assignment extends BinaryExpression {

	public Assignment(Expresion operand1, Expresion operand2) {
		super("=", operand1, operand2);
	}

	
}

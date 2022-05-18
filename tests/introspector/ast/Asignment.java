/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.ast;


public class Asignment extends BinaryExpression {

	public Asignment(Expresion operand1,Expresion operand2) {
		super("=", operand1, operand2);
	}

	
}

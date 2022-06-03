/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package examples.ast;


public class BinaryExpression extends Expresion {

	public String operator;

	public Expresion operand1;

	public Expresion operand2;

	public BinaryExpression(String operator, Expresion operand1, Expresion operand2) {
		super();
		this.operator=operator;
		this.operand1=operand1;
		this.operand2=operand2;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", this.operand1, this.operator, this.operand2);
	}
}

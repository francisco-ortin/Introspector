/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package examples.ast;


/**
 * Expressions with two operands and one operator.
 */
public class BinaryExpression extends Expression {

	public String operator;

	public Expression operand1;

	public Expression operand2;

	public BinaryExpression(int line, int column, String operator, Expression operand1, Expression operand2) {
		super(line, column);
		this.operator=operator;
		this.operand1=operand1;
		this.operand2=operand2;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s", this.operand1, this.operator, this.operand2);
	}
}

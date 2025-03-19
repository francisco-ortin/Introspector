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

	/**
	 * The operator of the expression.
	 */
	public String operator;

	/**
	 * The first operand of the expression.
	 */
	public Expression operand1;

	/**
	 * The second operand of the expression.
	 */
	public Expression operand2;

	/**
	 * Constructor of a binary expression.
	 * @param line Line of the expression.
	 * @param column Column of the expression.
	 * @param operator Operator of the expression.
	 * @param operand1 First operand of the expression.
	 * @param operand2 Second operand of the expression.
	 */
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

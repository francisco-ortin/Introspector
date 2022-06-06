/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package examples.ast;


/**
 * An expression with one operand and one operator.
 */
public class UnaryExpression extends Expression {

	public String operator;

	public Expression operand;

	public UnaryExpression(int line, int column, String operator, Expression operand) {
		super(line, column);
		this.operator=operator;
		this.operand=operand;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.operator, this.operand);
	}


}

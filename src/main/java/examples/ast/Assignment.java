/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package examples.ast;


/**
 * Assignments as statements.
 */
public class Assignment extends Statement {

	final Expression lhs, rhs;
	public Assignment(int line, int column, Expression lhs, Expression rhs) {
		super(line, column);
		this.lhs = lhs;
		this.rhs = rhs;
	}

	@Override
	public String toString() {
		return this.lhs + " = " + this.rhs;
	}
}

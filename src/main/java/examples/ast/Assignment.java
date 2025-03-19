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

	/**
	 * The left- and right-hand sides of the assignment.
	 */
	final Expression lhs, rhs;

	/**
	 * Constructor.
	 * @param line The line where the assignment appears.
	 * @param column The column where the assignment appears.
	 * @param lhs The left-hand side of the assignment.
	 * @param rhs The right-hand side of the assignment.
	 */
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

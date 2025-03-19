/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package examples.ast;

/**
 * Expressions of the language.
 */
public abstract class Expression extends ASTNode {

	/**
	 * The type of the expression.
	 */
	public Type type;

	/**
	 * Constructor
	 * @param line
	 * @param column
	 */
	public Expression(int line, int column) {
		super(line, column);
	}

	/**
	 * Whether the expression could be assigned to.
	 */
	public boolean lvalue;
}

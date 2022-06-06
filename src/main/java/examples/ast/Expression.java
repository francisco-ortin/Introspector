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
	
	public Type type;
	
	public Expression(int line, int column) {
		super(line, column);
	}

	public boolean lvalue;
}

/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

/**
 * A generalization of any statement
 */
public class Statement extends ASTNode {

	public Statement(int line, int column) {
		super(line, column);
	}

}

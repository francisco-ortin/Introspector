/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

import java.util.List;
import java.util.ArrayList;

/**
 * The root node of the AST representing a program.
 */
public class Program extends ASTNode {

	public List<Statement> statements = new ArrayList<>();

	public Program(int line, int column, List<Statement> statements) {
		super(line, column);
		this.statements.addAll(statements);
	}

	@Override
	public String toString() {
		return String.format("Program with %d statements", this.statements.size());
	}
}

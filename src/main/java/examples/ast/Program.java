/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

import java.util.List;
import java.util.ArrayList;


public class Program extends ASTNode {

	public List<ASTNode> statements = new ArrayList<>();

	public Program(List<ASTNode> statements) {
		super();
		this.statements.addAll(statements);
	}

	@Override
	public String toString() {
		return String.format("Program with %d statements", this.statements.size());
	}
}

/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.ast;

import java.util.List;
import java.util.ArrayList;


public class Program extends ASTNode {

	public List<ASTNode> statements = new ArrayList<ASTNode>();

	public Program(List<ASTNode> statements) {
		super();
		this.statements.addAll(statements);
	}

}

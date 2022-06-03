/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

import java.util.List;
import java.util.ArrayList;


public class Write extends Statement {

	public List<ASTNode> expressions = new ArrayList<>();

	public Write(List<ASTNode> expressions) {
		super();
		this.expressions.addAll(expressions);
	}

	@Override
	public String toString() {
		return "write " + this.expressions.toString();
	}

}

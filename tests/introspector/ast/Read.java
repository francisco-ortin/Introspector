/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.ast;

import java.util.List;
import java.util.ArrayList;

public class Read extends Statement {

	public List<ASTNode> expressions=new ArrayList<ASTNode>();
	
	public Read(List<ASTNode> expressions) {
		this.expressions.addAll(expressions);
	}


}

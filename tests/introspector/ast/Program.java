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

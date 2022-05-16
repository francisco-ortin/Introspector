package introspector.ast;

import java.util.List;
import java.util.ArrayList;


public class Write extends Statement {

	public List<ASTNode> expressions=new ArrayList<ASTNode>();
	
	public Write(List<ASTNode> expressions) {
		super();
		this.expressions.addAll(expressions);
	}


}

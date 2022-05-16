package introspector.ast;

import java.util.List;
import java.util.ArrayList;

public class Read extends Statement {

	public List<ASTNode> expressions=new ArrayList<ASTNode>();
	
	public Read(List<ASTNode> expressions) {
		this.expressions.addAll(expressions);
	}


}

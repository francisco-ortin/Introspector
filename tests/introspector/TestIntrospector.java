package introspector;

import java.util.*;
import introspector.model.IntrospectorModel;
import introspector.ast.*;
import introspector.view.IntrospectorTree;

import javax.swing.tree.TreeModel;

public class TestIntrospector {

	/**
	 * Input program;
	 * read a,b;
	 * a = (-b+3)*c;
	 * write a,c*2;
	 */
	public static ASTNode createTree() {
		Statement s1,s2,s3;
		// * First line
		List<ASTNode> list=new ArrayList<ASTNode>();
		list.add(new Identifier("a"));
		list.add(new Identifier("b"));
		s1=new Read(list);
		// * Second line
		s2=new Asignment(new Identifier("a"),
				new BinaryExpression("*",
						new BinaryExpression("+",
								new UnaryExpression("-",
										new Identifier("b")),
								new IntConstant(3)),
						new Identifier("c")));
		// * Third line 
		list=new ArrayList<ASTNode>();
		list.add(new Identifier("a"));
		list.add(new BinaryExpression("*",
				new Identifier("c"),
				new IntConstant(2)));
		s3=new Write(list);
		// * Builds and returns the AST
		list=new ArrayList<ASTNode>();
		list.add(s1);
		list.add(s2);
		list.add(s3);
		return new Program(list);
	}

	
	public static void main(String[] args) {
		for (int i=14;i<15;i++) {
			Object tree = SimpleReflectionTest.createTrees(i+1);
			TreeModel modelo=new IntrospectorModel("AST",tree);
			new IntrospectorTree("Introspector", modelo);
		}
	}

}

/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples;

import java.util.*;

import examples.ast.*;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;

import javax.swing.tree.TreeModel;

/**
 * Shows how to use Introspector to represent an Abstract Syntax Tree (AST) used in most compilers.
 */
public class ASTExample {

	/**
	 * This method creates the AST for the following program: <br/>
	 * read a,b;
	 * a = (-b+3)*c;
	 * write a,c*2;
	 */
	public static ASTNode createTree() {
		Statement s1, s2, s3;
		// * First line
		List<ASTNode> list = new ArrayList<>();
		list.add(new Identifier("a"));
		list.add(new Identifier("b"));
		s1 = new Read(list);
		// * Second line
		s2 = new Assignment(new Identifier("a"),
				new BinaryExpression("*",
						new BinaryExpression("+",
								new UnaryExpression("-",
										new Identifier("b")),
								new IntConstant(3)),
						new Identifier("c")));
		// * Third line 
		list = new ArrayList<>();
		list.add(new Identifier("a"));
		list.add(new BinaryExpression("*",
				new Identifier("c"),
				new IntConstant(2)));
		s3 = new Write(list);
		// * Builds and returns the AST
		list = new ArrayList<>();
		list.add(s1);
		list.add(s2);
		list.add(s3);
		return new Program(list);
	}


	/**
	 * Example main program
	 * @param args no params are required
	 */
	public static void main(String[] args) {
		ASTNode tree = createTree();
		TreeModel model = new IntrospectorModel("AST", tree);
		new IntrospectorView("Introspector", model);
	}

}

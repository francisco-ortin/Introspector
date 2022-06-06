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
	 * <p>This method creates the AST for the following program:</p>
	 * <pre>
	 * read a,b;
	 * a = (-b+3)*c;
	 * write a,c*2;
	 * </pre>
	 */
	public static ASTNode createTree() {
		Statement s1, s2, s3, s4, s5;
		// * First line
		s1 = new Read(1, 1, new Variable(1, 6, "a"));
		s2 = new Read(1, 1, new Variable(1, 8, "b"));
		// * Second line
		s3 = new Assignment(2, 1, new Variable(2, 1, "a"),
				new BinaryExpression(2, 10, "*",
						new BinaryExpression(2, 8, "+",
								new UnaryExpression(2, 6, "-",
										new Variable(2, 7, "b")),
								new IntLiteral(2, 9, 3)),
						new Variable(2, 11, "c")));
		// * Third line 
		s4 = new Write(3, 1, new Variable(3, 7, "a"));
		s5 = new Write(3, 1,
				new BinaryExpression(3, 10, "*",
						new Variable(3, 9, "c"),
						new IntLiteral(3, 11, 2)));
		// * Builds and returns the AST
		List<Statement> list = new ArrayList<>(Arrays.asList(s1, s2, s3, s4, s5));
		return new Program(1,1, list);
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

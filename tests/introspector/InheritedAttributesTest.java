/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector;


import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;

import javax.swing.tree.TreeModel;

public class InheritedAttributesTest {

	static class A {
		String field1 = "field1";
		String type = "A";
	}

	static class B extends A {
		String field2 = "field2";
		String type = "B";
	}

	public static void main(String[] args) {
			Object tree = new B();
			TreeModel modelo=new IntrospectorModel("AST",tree);
			new IntrospectorTree("Introspector", modelo);
	}

}



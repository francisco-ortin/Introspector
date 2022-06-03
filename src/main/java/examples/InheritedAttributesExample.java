/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples;



import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;

import javax.swing.tree.TreeModel;

/**
 * Example use of introspector when two attributes exist with the same name (one is inherited).
 */
public class InheritedAttributesExample {

	static class A {
		String field1 = "field1";
		// the field "type" is repeated
		String type = "A";
	}

	static class B extends A {
		String field2 = "field2";
		// the field "type" is repeated
		String type = "B";
	}

	public static void main(String[] args) {
		Object tree = new B();
		TreeModel model = new IntrospectorModel("AST", tree);
		new IntrospectorView("Introspector", model);
	}

}



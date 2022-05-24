 /**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples;

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;
import java.util.List;
import java.util.ArrayList;

 /**
  * Example use of enumerations, lists, objects and integer fields.
  */
 public class EnumExample {

	enum Color {
		red, blue, green
	}

	static class RootNode {
		private final Color color = Color.red;
		private final Node childNode = new Node("Child1");
		private final List<String> stringChildren = new ArrayList<>();
		private final int integerChild;

		//private Color color = Color.red;

		RootNode() {
			int i;
			for(i=2; i<=10; i++)
				this.stringChildren.add("StrChild"+i);
			this.integerChild = i;
		}

		@Override
		public String toString() { return "Root node"; }
	}

	static class Node {
		private final String name;

		Node(String name) {
			this.name = name;
		}

		@Override
		public String toString() { return this.name; }
	}

	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", new RootNode());
		new IntrospectorTree("Tree", model);
	}
	
}


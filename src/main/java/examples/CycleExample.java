/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples;

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;
import introspector.Introspector;

import java.util.*;

/**
 * Example use of Introspector with graphs where cycles exist (not as a tree).
 * The cycles are represented as repeated nodes, so they could be expanded infinitive.
 * When visualized, click on the "expand all" button to see how it controls the cycles (i.e., it does not expand the same node infinitely).
 * When reporting the tree, the cycles are reported with <revisited node> tags (all the information parameter must be set to true).
 */
public class CycleExample {
	
	public static void main(String... args) {
		// We visualize the tree (click no expand all the nodes to see how cycles are controlled)
		IntrospectorModel model = new IntrospectorModel("Root", new Root());
		new IntrospectorView("Tree", model);
		// We write the tree in different formats (with allInfo=true to see the cycle information with <revisited node> tags)
		Introspector.writeTreeAsTxt(model, "Tree", "out/output.txt", true);
		Introspector.writeTreeAsHtml(model, "Tree", "out/output.html", true);
	}

	/**
	 * Example class.
	 */
	static class NodeA {
		public  NodeB b;
	}

	/**
	 * Example class.
	 */
	static class NodeB {
		public  NodeC c;
	}

	/**
	 * Example class that may have cycles.
	 */
	static class NodeC {
		// cycle
		public  NodeA a;
	}


	/**
	 * Example class whose objects will be used as root nodes of a tree to be visualized.
	 */
	static class Root {
		private final NodeA a;

		Root() {
			this.a = new NodeA();
			this.a.b = new NodeB();
			this.a.b.c = new NodeC();
			this.a.b.c.a = this.a;  // cyclic reference (graph)
		}



	}


}


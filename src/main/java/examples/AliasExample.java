/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples;

import introspector.Introspector;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;

/**
 * Example use of Introspector with graphs alias references (references to a tree repeated in another part of the graph) exist.
 * The alias references are represented as repeated nodes, so they should not be expanded to avoid repetition.
 * When visualized, click on the "expand all" button to see how it controls the aliases (i.e., it does not expand the same tree twice).
 * When reporting the tree, aliases are reported with <revisited node> tags (all the information parameter must be set to true).
 */
public class AliasExample {
	
	public static void main(String... args) {
		// We visualize the tree (click no expand all the nodes to see how alias references are controlled)
		IntrospectorModel model = new IntrospectorModel("Root", new Root());
		new IntrospectorView("Tree", model);
		// We write the tree in different formats (with allInfo=true to see the alias nodes tagged with <revisited node>)
		Introspector.writeTreeAsTxt(model, "Tree", "out/output.txt", true);
		Introspector.writeTreeAsHtml(model, "Tree", "out/output.html", true);
	}

	/**
	 * Example class with alias references.
	 */
	static class NodeA {
		public NodeB b1;
		public NodeB b2;  // alias; this NodeB will be the same as b1
	}

	/**
	 * Example class.
	 */
	static class NodeB {
		public  NodeC c;
	}

	/**
	 * Example class
	 */
	static class NodeC {
		public String value = "NodeC";
	}


	/**
	 * Example class whose objects will be used as root nodes of a tree to be visualized.
	 */
	static class Root {
		private final NodeA a;

		Root() {
			this.a = new NodeA();
			NodeB alias = new NodeB();
			alias.c = new NodeC();
			this.a.b1 = this.a.b2 = alias;  // alias: we make this.a.b1 and this.a.b2 point to the same NodeB
		}



	}


}


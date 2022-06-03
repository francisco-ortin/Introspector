/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples;

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;

/**
 * Example use of Introspector with graphs where cycles exist (not as a tree).
 * The cycles are represented as repeated nodes, so they could be expanded infinitive.
 */
public class CycleExample {
	
	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", new Root());
		new IntrospectorView("Tree", model);
	}

	static class NodeA {
		public  NodeB b;
	}

	static class NodeB {
		public  NodeC c;
	}

	static class NodeC {
		// cycle
		public  NodeA a;
	}


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


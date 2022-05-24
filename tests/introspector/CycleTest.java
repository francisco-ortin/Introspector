/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector;

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;
import java.util.List;
import java.util.ArrayList;

// TODO Mover los tests de esta carpeta a una carpeta nueva que sea "examples"
// TODO Convertir los ejemplos de esta carpeta a tests con JUnit (aquellos que tenga sentido como el cycle)

public class CycleTest {
	
	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", new Root());
		new IntrospectorTree("Tree", model);
	}

	static class NodeA {
		public  NodeB b;
	}

	static class NodeB {
		public  NodeC c;
	}

	static class NodeC {
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


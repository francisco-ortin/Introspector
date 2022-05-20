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

public class CycleTest {
	
	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", new Root());
		new IntrospectorTree("Tree", model);
	}
	
}


class NodeA { 
	public NodeB b;	
}

class NodeB { 
	public NodeC c;
}

class NodeC { 
	public NodeA a;
}


class Root {
	private NodeA a;
	
	Root() {
		this.a = new NodeA();
		this.a.b = new NodeB();
		this.a.b.c = new NodeC();
		this.a.b.c.a = this.a;  // cyclic reference (graph)
	}
	
}


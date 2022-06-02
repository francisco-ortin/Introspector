package examples; /**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;
import java.util.List;
import java.util.ArrayList;

/**
 * Example and simple use of Introspector.
 * It includes enums, records, objects, lists, and built-in types.
 */
public class IntrospectorDemo {
	
	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", new RootNode());
		new IntrospectorView("Tree", model);
	}
	
}

enum Color {
	red, blue, green
}

class RootNode {
	private final Color color = Color.red;
	private final Node childNode = new Node("Child1");
	private final List<String> stringChildren = new ArrayList<>();
	private final int integerChild;
	private final Person personRecord;
	//private examples.Color color = examples.Color.red;

	RootNode() {
		int i;
		for (i = 2; i <= 10; i++)
			this.stringChildren.add("StrChild" + i);
		this.integerChild = i;
		this.personRecord = new Person(12, "Francisco", "Ortin");
	}

	@Override
	public String toString() {
		return "Root node";
	}
}

class Node {
	private final String name;
	
	Node(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() { return this.name; }
}

record Person(int id, String firstName, String lastName) {
}
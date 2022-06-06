package examples; /**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

import introspector.Introspector;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;
import java.util.List;
import java.util.ArrayList;

/**
 * Example and simple use of Introspector.
 * It includes enums, records, objects, lists, and built-in types.
 */
public class IntrospectorDemo {

	/**
	 * Shows haw to use Introspector as an application and as an API
	 * @param args
	 */
	public static void main(String... args) {
		// we use introspector as ana application to visualize an object structure as a tree
		RootClass rootObject = new RootClass();
		IntrospectorModel model = new IntrospectorModel("Root", rootObject);
		new IntrospectorView("Tree", model);
		// Introspector can also be used programmatically to dump object structures as html and txt files
		Introspector.writeTreeAsTxt(rootObject, "Root", "out/output.txt");
		Introspector.writeTreeAsHtml(rootObject, "Root", "out/output.html", true);
	}
	
}

/**
 * Example enumeration
 */
enum Color {
	red, blue, green
}

/**
 * Example class whose objects will be used as root nodes of a tree to be visualized.
 */
class RootClass {
	private final Color color = Color.red;
	private final Node childNode = new Node("Child1");
	private final List<String> stringChildren = new ArrayList<>();
	private final int integerChild;
	private final Person personRecord;
	//private examples.Color color = examples.Color.red;

	RootClass() {
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

/**
 * Example child class representing an intermediate node.
 */
class Node {
	private final String name;
	
	Node(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() { return this.name; }
}

/**
 * Example record.
 * @param id primary key of the person
 * @param firstName first name of the person
 * @param lastName family name of the person
 */
record Person(int id, String firstName, String lastName) {
}
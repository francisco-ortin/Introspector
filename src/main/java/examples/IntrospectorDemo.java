package examples; /**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

import introspector.Introspector;
import introspector.view.IntrospectorView;
import java.util.List;
import java.util.ArrayList;

/**
 * Example use of Introspector including enums, records, objects, lists, and built-in types.
 */
public class IntrospectorDemo {

	/**
	 * Shows haw to use Introspector as an application and as an API
	 * @param args
	 */
	public static void main(String... args) {
		// we use introspector as ana application to visualize an object structure as a tree
		RootClass myObject = new RootClass();
		new IntrospectorView("Introspector", "Tree", myObject);
		// Introspector can also be used programmatically to dump object structures as html and txt files
		Introspector.writeTreeAsTxt(myObject, "Root", "out/output.txt");
		Introspector.writeTreeAsHtml(myObject, "Root", "out/output.html", true);
	}

}

/**
 * Example enumeration
 */
enum Color {
	/**
	 * Different colors: red, blue, green
	 */
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
	/**
	 * Name of the node
	 */
	private final String name;

	/**
	 * Constructor
	 * @param name name of the node
	 */
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
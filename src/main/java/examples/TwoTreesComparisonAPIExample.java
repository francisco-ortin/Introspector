package examples; /**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

import introspector.Introspector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Example use of Introspector showing how to compare two trees programmatically with no visual representation.
 */
public class TwoTreesComparisonAPIExample {

	/**
	 * Main method that creates two trees and compares them.
	 */
	public static void main(String... args) {
		// last parameter is false to avoid the visualization of the window => allows to add more trees and then setVisible(true)
		RootClass tree1 = new RootClass();
		RootClass tree2 = new RootClass();
		tree2.color = Color.blue; // Change the color of the root node
		tree2.stringChildren.set(4, "new value"); // change the value of one array element
		tree2.stringChildren.add("new child"); // add a new child
		tree2.integerChild = -1; // Change the integer child
		tree2.personRecord = new Person(12, "Francisco", "Soler", Map.of("One", 1, "Two", 2)); // Change the person record

		// textual comparison of the trees: changed nodes appear between ** and **
		// complete information including toString
		Introspector.compareTreesAsTxt(tree1, tree2, "out/full-output1.txt", "out/full-output2.txt");
		// simple information (the allInfo parameter is set to false)
		Introspector.compareTreesAsTxt(tree1, tree2, "out/simple-output1.txt", "out/simple-output2.txt", false);
		// the same functionality with HTML output
		// complete information including toString
		Introspector.compareTreesAsHtml(tree1, tree2, "out/full-output1.html", "out/full-output2.html");
		// simple information (the allInfo parameter is set to false)
		Introspector.compareTreesAsHtml(tree1, tree2, "out/simple-output1.html", "out/simple-output2.html", false);
	}



	/**
	 * Example class whose objects will be used as root nodes of a tree to be visualized.
	 */
	static class RootClass {
		private Color color = Color.red;
		private final Node childNode = new Node("Child1");
		private final List<String> stringChildren = new ArrayList<>();
		int integerChild;
		private Person personRecord;

		RootClass() {
			int i;
			for (i = 2; i <= 10; i++)
				this.stringChildren.add("StrChild" + i);
			this.integerChild = i;
			this.personRecord = new Person(12, "Francisco", "Ortin", null);
		}

		@Override
		public String toString() {
			return "Root node";
		}
	}

	/**
	 * Example child class representing an intermediate node.
	 */
	static class Node {
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
	 * @param anything any other information
	 */
	record Person(int id, String firstName, String lastName, Object anything) {
	}

}

package examples; /**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

import introspector.Introspector;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Example and simple use of Introspector.
 * This example shows how to compare two trees.
 */
public class TreeComparisonExample {

	/**
	 * Main method that creates two trees and compares them.
	 */
	public static void main(String... args) {
		// last parameter is false to avoid the visualization of the window => allows to add more trees and then setVisible(true)
		IntrospectorView view = new IntrospectorView("Introspector", "Tree 1", new RootClass(), 1024, 768, false);

		RootClass root2 = new RootClass();
		root2.color = Color.blue; // Change the color of the root node
		root2.stringChildren.set(4, "new value"); // change the value of one array element
		root2.integerChild = -1; // Change the integer child
		root2.personRecord = new Person(12, "Francisco", "Soler", Map.of("One", 1, "Two", 2)); // Change the person record

		view.addTree(new IntrospectorModel("Tree 2", root2));
		view.setVisible(true);
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
		//private examples.Color color = examples.Color.red;

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
	 */
	record Person(int id, String firstName, String lastName, Object anything) {
	}

}

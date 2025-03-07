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

/**
 * Example and simple use of Introspector.
 * This example shows how to compare two trees.
 */
public class TreeComparisonExample {

	/**
	 * Main method that creates two trees and compares them.
	 */
	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Tree 1", new RootClass());
		// last parameter is false to avoid the visualization of the window => allows to add more trees and then setVisible(true)
		IntrospectorView view = new IntrospectorView("Introspector", model, false);

		RootClass root2 = new RootClass();
		root2.color = Color.blue; // Change the color of the root node
		root2.stringChildren.remove(0); // Remove the first child
		root2.integerChild = -1; // Change the integer child
		root2.personRecord = new examples.Person(12, "Francisco", null); // Change the person record

		view.addTree(new IntrospectorModel("Tree 2", root2));
		view.addTree(new IntrospectorModel("Tree 3", new int[]{1, 2, 3}));
		view.addTree(new IntrospectorModel("Tree 4", new int[]{1, 2, 4, 3}));

		view.setVisible(true);
		// TODO hacer un constructor que reciba un Object en lugar de un Model
		// TODO hacer un ejemplo con dos árbiles
		// TODO quitar el botón penúltimo
		// TODO cambiar el icono del último botón
		// TODO hacer un contextual "compare trees" que solo se active si hay exacamante dos árboles seleccionados
	}



	/**
	 * Example class whose objects will be used as root nodes of a tree to be visualized.
	 */
	static class RootClass {
		private Color color = Color.red;
		private final examples.Node childNode = new examples.Node("Child1");
		private final List<String> stringChildren = new ArrayList<>();
		int integerChild;
		private examples.Person personRecord;
		//private examples.Color color = examples.Color.red;

		RootClass() {
			int i;
			for (i = 2; i <= 10; i++)
				this.stringChildren.add("StrChild" + i);
			this.integerChild = i;
			this.personRecord = new examples.Person(12, "Francisco", "Ortin");
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

}

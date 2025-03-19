package examples; /**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

import introspector.view.IntrospectorView;

import java.util.HashMap;
import java.util.Map;

/**
 * Example use of Introspector showing how to show multiple trees in the same window.
 */
public class MultipleTreesExample {

	/**
	 * Main method that creates multiple trees in the same view
	 */
	public static void main(String... args) {
		// last parameter is false to avoid the visualization of the window => allows to add more trees and then setVisible(true)
		IntrospectorView view = new IntrospectorView("Introspector", "Tree 1", "String tree", 1024, 768, false);
		view.addTree("Tree 2",  1);
		view.addTree("Tree 3", new int[]{1, 2, 3, 4});
		view.addTree("Tree 4", new HashMap<>(Map.of("One", 1, "Two", 2)));
		view.addTree("Tree 5", new Person(12, "Francisco", "Ortin", new Person(13, "John", "Doe", "Anything")));
		view.setVisible(true);
	}



	/**
	 * Example class whose objects will be used as root nodes of a tree to be visualized.
	 */
	/**
	 * Example record.
	 * @param id primary key of the person
	 * @param firstName first name of the person
	 * @param lastName family name of the person
	 * @param anything any object
	 */
	record Person(int id, String firstName, String lastName, Object anything) {
	}

}

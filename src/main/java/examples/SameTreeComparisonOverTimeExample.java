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
 * Example and simple use of Introspector showing how to compare the same tree at different times.
 */
public class SameTreeComparisonOverTimeExample {

	/**
	 * Main method that creates two trees and compares them.
	 */
	public static void main(String... args) {
		List<Object> list = new ArrayList<>();
		list.add("One");
		list.add(2);
		list.add(3.0);
		String [] array = {"Four", "Five", "Six"};
		list.add(array);
		// We must tell Introspector that the tree should be cloned; otherwise, the same tree would be shown twice
		// We do so by passing true as the third parameter (deepClone)
		IntrospectorModel tree1 = new IntrospectorModel("Tree 1", list, true);
		IntrospectorView view = new IntrospectorView("Introspector", tree1, false);
		// we now modify the tree
		array[1] = "Five modified";
		// the second one does not need to be cloned (otherwise, we would have to use IntrospectorModel("Tree 2", list, true))
		view.addTree("Tree 2", list);
		view.setVisible(true);

		// we can also compare the trees programmatically, using Introspector as an API
		// textual comparison of the trees: changed nodes appear between ** and **
		// complete information
		Introspector.compareTreesAsTxt(tree1, list, "out/full-output1.txt", "out/full-output2.txt");
		// simple information
		Introspector.compareTreesAsTxt(tree1, list, "out/simple-output1.txt", "out/simple-output2.txt", false);
		// the same functionality with HTML output
		// complete information
		Introspector.compareTreesAsHtml(tree1, list, "out/full-output1.html", "out/full-output2.html");
		// simple information
		Introspector.compareTreesAsHtml(tree1, list, "out/simple-output1.html", "out/simple-output2.html", false);
	}



}

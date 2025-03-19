package examples; /**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;

/**
 * Example use of Introspector showing how to compare the same tree in two different points of execution.
 */
public class DeepCloneTreeComparisonExample {

	/**
	 * Main method that creates two trees and compares them.
	 */
	public static void main(String... args) {
		// last parameter is false to avoid the visualization of the window => allows to add more trees and then setVisible(true)
		RootClass rootObject = new RootClass();
		IntrospectorModel model = new IntrospectorModel("Tree 1", rootObject, true);
		IntrospectorView view = new IntrospectorView("Introspector", model, 1024, 768, false);

		rootObject.anything = "New value";

		view.addTree(new IntrospectorModel("Tree 2", rootObject));
		view.setVisible(true);
	}



	/**
	 * Example class whose objects will be used as root nodes of a tree to be visualized.
	 */
	static class RootClass {
		private Color color = Color.red;
		Object anything;
	}

}

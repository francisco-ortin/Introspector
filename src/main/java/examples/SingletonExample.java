/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;

/**
 * Example use of Introspector with graphs where cycles exist (not as a tree).
 * The cycles are represented as repeated nodes, so they could be expanded infinitive.
 * A direct recursive type is the Singleton design pattern.
 */
public class SingletonExample {

	/**
	 * Example Singleton class that has a direct recursive reference (cycle) via its instance field.
	 */
	static class Singleton {

		static private final Singleton instance = new Singleton();

		private final String field = "sample field";

		private Singleton() {
		}

		static public Singleton getInstance() {
			return instance;
		}
	}


	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", Singleton.getInstance());
		new IntrospectorView("Tree", model);
	}
	
}


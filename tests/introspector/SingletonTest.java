/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector;

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;

public class SingletonTest {

	static class Singleton {

		static private Singleton instance = new Singleton();

		private String field = "sample field";

		private Singleton() {
		}

		static public Singleton getInstance() {
			return instance;
		}
	}


	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", Singleton.getInstance());
		new IntrospectorTree("Tree", model);
	}
	
}


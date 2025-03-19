/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorView;

import java.util.Optional;

/**
 * Example use of Introspector with Optional fields.
 */
public class OptionalExample {

	private final String nonOptionalField = "sample non optional field";

	private final Optional<String> optionalNonNullField = Optional.of("sample optional non-null field");

	private final Optional<String> optionalNullField = null;
	private final Optional<String> optionalEmptyField = Optional.empty();

	@Override
	public String toString() {
		return "Optional example object";
	}


	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", new OptionalExample());
		new IntrospectorView("Tree", model);
	}

}


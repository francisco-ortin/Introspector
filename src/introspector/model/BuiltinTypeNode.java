/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.lang.reflect.Field;
import java.util.List;

public class BuiltinTypeNode extends Node {

	public BuiltinTypeNode(Field field, Object implicitObject) throws IllegalAccessException {
		super(field, implicitObject);
	}

	public BuiltinTypeNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	public boolean isLeaf() {
		return true;
	}

	public List<Node> getChildren() {
		return null;
	}

}

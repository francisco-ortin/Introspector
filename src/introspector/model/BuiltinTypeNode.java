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

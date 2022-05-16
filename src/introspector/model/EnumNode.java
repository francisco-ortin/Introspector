package introspector.model;

import java.util.List;

public class EnumNode extends Node {

	public EnumNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	public List<Node> getChildren() {
		return null;
	}

}


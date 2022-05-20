/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.lang.reflect.Field;
import java.util.List;

/**
 * BuiltinTypeNode provides a Node implementation to represent any value whose type is built-in.
 */
public class BuiltinTypeNode extends Node {

	/**
	 * @see Node#Node(String, Object)
	 */
	public BuiltinTypeNode(String name, Object value) {
		super(name, value);
	}

	/**
	 * @see Node#Node(String, Object, Class)
	 */
	public BuiltinTypeNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	/**
	 * A builtin node is leaf.
	 *
	 * @see introspector.model.Node#isLeaf()
	 */
	public boolean isLeaf() {
		return true;
	}

	/**
	 * A builtin node has no children.
	 *
	 * @see Node#getChildren()
	 */
	public List<Node> getChildren() {
		return null;
	}

}

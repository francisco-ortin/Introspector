/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.util.List;

/**
 * BuiltinTypeNode provides a Node implementation to represent any value whose type is built-in.
 */
public class BuiltinTypeNode extends AbstractNode implements Node {

	/**
	 * @see AbstractNode#AbstractNode(String, Object)
	 */
	public BuiltinTypeNode(String name, Object value) {
		super(name, value);
	}

	/**
	 * @see AbstractNode#AbstractNode(String, Object, Class)
	 */
	public BuiltinTypeNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	/**
	 * A builtin node is leaf.
	 *
	 * @see AbstractNode#isLeaf()
	 */
	public boolean isLeaf() {
		return true;
	}

	/**
	 * A builtin node has no children.
	 *
	 * @see AbstractNode#getChildren()
	 */
	public List<Node> getChildren() {
		return null;
	}

}

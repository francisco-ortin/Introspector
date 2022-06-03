/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.lang.reflect.Array;
import java.util.*;

/**
 * ArrayNode provides a Node implementation to represent any value whose type is an array.
 */
public class ArrayNode extends AbstractNode implements Node  {

	/**
	 * @see AbstractNode#AbstractNode(String, Object)
	 */
	public ArrayNode(String name, Object value) {
		super(name, value);
	}

	/**
	 * @see AbstractNode#AbstractNode(String, Object, Class)
	 */
	public ArrayNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	/**
	 * An array node is not leaf.
	 *
	 * @see AbstractNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		return false;
	}

	/**
	 * An array node has as many children as elements in the array.
	 * A node is created for each child.
	 *
	 * @see AbstractNode#getChildren()
	 */
	@Override
	public List<Node> getChildren() {
		List<Node> children = new ArrayList<>();
		int length = Array.getLength(getValue());
		for (int i = 0; i < length; i++) {
			Object element = Array.get(getValue(), i);
			if (element == null) {
				System.err.printf("Introspector: the array \"%s\" has a null reference in its item number %d.\n", getName(), i);
				children.add(NodeFactory.createNode(getName() + "[" + i + "]", null, null));
			} else
				children.add(NodeFactory.createNode(getName() + "[" + i + "]", element, element.getClass()));
		}
		return children;
	}

}

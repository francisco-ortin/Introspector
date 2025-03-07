/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import introspector.model.traverse.TraverseHelper;

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


	/**
	 * @see Node#compareTrees(Node, boolean, List, List)
	 */
	public List<Node> compareTrees(Node node2, boolean equalName, List<Node> modifiedNodes, List<Node> alreadyTraversed) {
		if (!TraverseHelper.shouldBeTraversed(this, alreadyTraversed))
			return modifiedNodes; // cycle detected
		if (node2 instanceof ArrayNode arrayNode2) {
			// they must have the same types
			if (!this.getType().equals(arrayNode2.getType())) {
				modifiedNodes.add(this);
				modifiedNodes.add(arrayNode2);
				return modifiedNodes;
			}
			// if they are not the root nodes, they must have the same names
			if (equalName && !this.getName().equals(arrayNode2.getName())) {
				modifiedNodes.add(this);
				modifiedNodes.add(arrayNode2);
				return modifiedNodes;
			}
			List<Node> children1 = this.getChildren();
			List<Node> children2 = arrayNode2.getChildren();
			// they must have the same number of children
			if (children1.size() != children2.size()) {
				modifiedNodes.add(this);
				modifiedNodes.add(arrayNode2);
				return modifiedNodes;
			}
			// they must have the same children
			for (int i = 0; i < children1.size(); i++)
				children1.get(i).compareTrees(children2.get(i), equalName, modifiedNodes, alreadyTraversed);
			return modifiedNodes;
		}
		// node2 is not an ArrayNode => they are different
		modifiedNodes.add(this);
		modifiedNodes.add(node2);
		return modifiedNodes;
	}

}

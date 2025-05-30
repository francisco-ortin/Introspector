/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import introspector.model.traverse.SymmetricPair;
import introspector.model.traverse.TraverseHelper;

import java.util.*;

/**
 * The abstract Node provides the default implementation of the Node interface.
 * The node interface represents the nodes in the tree that models the structure of the program
 * at runtime.
 * See the derived classes to find the appropriate one to instantiate.
 */
public abstract class AbstractNode implements Node {

	/**
	 * The object that is wrapped by the node
	 */
	private final Object value;

	/**
	 * The name of the node
	 */
	private final String name;

	/**
	 * The type of the node
	 */
	private final Class<?> type;

	/**
	 * Creates a Node that wraps a Java Object
	 * @param name The name to display the node
	 * @param value The object that will be represented as a node
	 */
	protected AbstractNode(String name, Object value) {
		this(name, value, value.getClass());
	}

	/**
	 * Creates a Node that wraps a Java Object
	 * @param name The name to display the node
	 * @param value The object that will be represented as a node
	 * @param type The type of the object
	 */
	protected AbstractNode(String name, Object value, Class<?> type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	/**
	 * @return The name used to display the object represented as a node
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @return The type of the object represented as a node
	 */
	@Override
	public Class<?> getType() {
		return type;
	}

	/**
	 * @return The object represented as a node
	 */
	@Override
	public Object getValue() {
		return value;
	}

	/**
	 * Returns the child node of the current node.
	 * Template method to implement {@link Node#getChild(int)}, {@link Node#getChildrenCount()} and
	 * {@link Node#getIndexOfChild(Object)}
	 * @return The children of this node (null if there are no children)
	 */
	protected abstract List<Node> getChildren();


	/**
	 * @see Node#getChild(int)
	 */
	@Override
	public Node getChild(int index) {
		if (index >= this.getChildrenCount())
			return null;
		return this.getChildren().get(index);
	}

	/**
	 * @see Node#getChildrenCount()
	 */
	@Override
	public int getChildrenCount() {
		if (this.getChildren() == null)
			return 0;
		return this.getChildren().size();
	}

	/**
	 * @see Node#getIndexOfChild(Object)
	 */
	@Override
	public int getIndexOfChild(Object child) {
		if (child instanceof Node childNode) {
			for (int i=0; i<this.getChildrenCount(); i++)
				if (this.getChildren().get(i).getName().equals(childNode.getName()))
					return i;
		}
		return -1; // no child was found
	}

	/**
	 * A node is represented as "name (type)".
	 * @return The node representation as string.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		sb.append(" (");
		sb.append(type.getSimpleName());
		sb.append(")");
		if (NodeFactory.isBuiltinType(type) || NodeFactory.isEnumType(type))
			sb.append(": ").append(value);
		return sb.toString();
	}

	/**
	 * Two nodes are equal when they wrap the same exact object (identity comparison)
	 * @param obj To object to be compared with
	 * @return Whether the two objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node node)
			return this.value == node.getValue();
		else
			return false;  // not the same type, different objects
	}

	/**
	 * @return The hash code of the name
	 */
	@Override
	public int hashCode() {
		return  this.name.hashCode();
	}

	/**
	 * @see Node#getNodeDescription()
	 */
	@Override
	public String getNodeDescription() {
		if (this.getValue() == null)
			return "null";
		return this.getValue().toString();
	}

	/**
	 * @see Node#getClassName()
	 */
	@Override
	public String getClassName() {
		return this.getType().getName();
	}

	/**
	 * @see Node#compareTrees(Node, boolean, Set, Set)
	 */
	@Override
	public Set<Node> compareTrees(Node node2, boolean equalName, Set<Node> modifiedNodes, Set<SymmetricPair<Node, Node>> alreadyTraversed) {
		// This is the implementation for leaf nodes
		if (!TraverseHelper.shouldBeTraversed(new SymmetricPair<>(this, node2), alreadyTraversed))
			return modifiedNodes; // cycle detected
		if (this.getValue() == null && node2.getValue() == null)
			return modifiedNodes;
		if (this.getValue() == null || node2.getValue() == null) {
			// one of them is null but not the other (first condition above)
			modifiedNodes.add(this); modifiedNodes.add(node2);
		}
		else if (!this.getValue().equals(node2.getValue())) {
			modifiedNodes.add(this); modifiedNodes.add(node2);
		}
		else if (!this.getType().equals(node2.getType())) {
			modifiedNodes.add(this); modifiedNodes.add(node2);
		}
		else if(equalName && !this.getName().equals(node2.getName())) {
			modifiedNodes.add(this); modifiedNodes.add(node2);
		}
		return modifiedNodes;
	}

}

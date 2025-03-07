/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import javax.lang.model.type.NullType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The interface Node represents the nodes in the tree that models the structure of the program at runtime.
 * See the derived classes to find the appropriate one to instantiate.
 */
public interface Node {

	/**
	 * Returns the name of the node
	 * @return The name used to display the object represented as a node
	 */
	String getName();

	/**
	 * Returns the type of the object represented as a node
	 * @return The type of the object represented as a node
	 */
	Class<?> getType();

	/**
	 * Returns the object represented as a node
	 * @return The object represented as a node
	 */
	Object getValue();

	/**
	 * To know if a node is a leaf node.
	 * @return Whether the node is a leaf node (no children) or not
	 */
	boolean isLeaf();

	/**
	 * Gets the index-th child of the current node.
	 * @param index The index of the child to be found.
	 * @return The index-th child; null if the index is out of bounds.
	 */
	Node getChild(int index);

	/**
	 * Returns the number of children of the current node.
	 * @return The number of children of the current node.
	 */
	int getChildrenCount();

	/**
	 * Searches for a child and returns its index.
	 * The child node is searched by comparing their names.
	 * @param child The object to be found (compared by structure with equals)
	 * @return The index of the child found; -1 if it no child is found.
	 */
	int getIndexOfChild(Object child);

	/**
	 * A textual description of the node.
	 * toString must be defined for the wrapped objects.
	 * @return The string representation of the node.
	 */
	String getNodeDescription();


	/**
	 * Returns the short name of the type whose object is wrapped by the node.
	 * @return A short name of the type of the object wrapped by the node.
	 */
	String getClassName();



	/** Compare two trees and return the list of modified nodes.
	 * @param node The node to compare with the other tree
	 * @param equalName Whether the node names must be the same or not (important for root nodes)
	 * @param modifiedNodes The list of modified nodes
	 * @param alreadyTraversed The list of nodes that have been visited in this traversal
	 * @return The list of modified nodes
	 */
	List<Node> compareTrees(Node node, boolean equalName, List<Node> modifiedNodes, List<Node> alreadyTraversed);


}

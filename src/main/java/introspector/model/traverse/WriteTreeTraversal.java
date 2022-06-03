/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model.traverse;


import introspector.model.Node;
import introspector.model.NodeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes trees with different formats.
 */
public class WriteTreeTraversal {


	/**
	 * Main method to traverse a tree and perform the actions delegated in the tree serializer
	 * @param node the root node to traverse
	 * @param treeSerializer the strategy that specifies what to do with each traversal step
	 * @param depth the depth of the traversed node (zero for the root node)
	 * @param alreadyTraversed the list of nodes that have been visited in this traversal
	 * @throws IOException a textual file is written
	 */
	private void traverse(Node node, TreeSerializer treeSerializer, int depth, List<Node> alreadyTraversed) throws IOException {
		boolean hasBeenVisited = this.hasBeenVisited(node, alreadyTraversed);
		if (!hasBeenVisited && this.couldBeVisitedTwice(node))
			alreadyTraversed.add(node);
		treeSerializer.beforeTraversing(node, depth, hasBeenVisited);
		treeSerializer.traversing(node, depth, hasBeenVisited);
		if (!node.isLeaf() && !hasBeenVisited) {
 			for (int i=0; i<node.getChildrenCount(); i++)
				traverse(node.getChild(i), treeSerializer, depth + 1, alreadyTraversed);
		}
		treeSerializer.afterTraversing(node, depth, hasBeenVisited);
	}

	/**
	 * Returns whether a node is a cyclic reference. That is, it has been already traversed and hence
	 * the tree is actually a graph.
	 * @param node the node traversed
	 * @param alreadyTraversed a list of previously traversed nodes
	 * @return whether the node is a cyclic reference
	 */
	private boolean hasBeenVisited(Node node, List<Node> alreadyTraversed) {
		return this.couldBeVisitedTwice(node) &&
				alreadyTraversed.stream().anyMatch(eachNode -> eachNode.getValue() == node.getValue());
	}

	/**
	 * Returns whether a node could be visited twice or more
	 * @param node the node traversed
	 * @return whether the node could be visited twice or more
	 */
	private boolean couldBeVisitedTwice(Node node) {
		return node.getValue() != null &&  // null is not considered as a repeated object
				!NodeFactory.isBuiltinType(node.getType()) && // builtin objects could be repeated
				!node.isLeaf(); // leaf nodes can only be visited once
	}

	/**
	 * Main method to traverse a tree and perform the actions delegated in the tree serializer
	 * @param node the root node to traverse
	 * @param treeSerializer the strategy that specifies what to do with each traversal step
	 * @throws IOException a textual file is written
	 */
	public void traverse(Node node, TreeSerializer treeSerializer) throws IOException {
		treeSerializer.beginTraverse();
		this.traverse(node, treeSerializer, 0, new ArrayList<>());
		treeSerializer.endTraverse();
	}

}

/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model.traverse;


import introspector.model.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private void traverse(Node node, TreeSerializer treeSerializer, int depth, Set<Node> alreadyTraversed) throws IOException {
		boolean hasBeenVisited = TraverseHelper.hasBeenVisited(node, alreadyTraversed);
		if (!hasBeenVisited && TraverseHelper.couldBeVisitedTwice(node))
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
	 * Main method to traverse a tree and perform the actions delegated in the tree serializer
	 * @param node the root node to traverse
	 * @param treeSerializer the strategy that specifies what to do with each traversal step
	 * @throws IOException a textual file is written
	 */
	public void traverse(Node node, TreeSerializer treeSerializer) throws IOException {
		treeSerializer.beginTraverse();
		this.traverse(node, treeSerializer, 0, new HashSet<>());
		treeSerializer.endTraverse();
	}

}

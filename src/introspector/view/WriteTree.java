/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.view;


import examples.ASTExample;
import introspector.model.Node;
import introspector.model.NodeFactory;

import java.io.IOException;
import java.util.*;

/**
 * Writes trees with different formats.
 */
public class WriteTree {


	/**
	 * Main method to traverse a tree and perform the actions delegated in the tree serializer
	 * @param node the root node to traverse
	 * @param treeSerializer the strategy that specifies what to do with each traversal step
	 * @param depth the depth of the traversed node (zero for the root node)
	 * @throws IOException a textual file is written
	 */
	private void traverse(Node node, TreeSerializer treeSerializer, int depth) throws IOException {
		treeSerializer.beforeTraversing(node, depth);
		treeSerializer.traversing(node, depth);
		if (!node.isLeaf()) {
 			for (int i=0; i<node.getChildrenCount(); i++)
				traverse(node.getChild(i), treeSerializer, depth + 1);
		}
		treeSerializer.afterTraversing(node, depth);
	}

	/**
	 * Main method to traverse a tree and perform the actions delegated in the tree serializer
	 * @param node the root node to traverse
	 * @param treeSerializer the strategy that specifies what to do with each traversal step
	 * @throws IOException a textual file is written
	 */
	public void traverse(Node node, TreeSerializer treeSerializer) throws IOException {
		treeSerializer.beginTraverse();
		this.traverse(node, treeSerializer, 0);
		treeSerializer.endTraverse();
	}

}

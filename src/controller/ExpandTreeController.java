/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package controller;

import introspector.model.Node;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents different ways of expanding tree views (JTrees).
 */
public class ExpandTreeController {

	/**
	 * Expands all the nodes in a tree view
	 * @param tree the JTree to be expanded
	 * @param node the node we want al the children to be expanded transitively
	 * @param treePath the path describing when the node is placed in the tree
	 * @param alreadyVisited the list of nodes visited to avoid infinite loops in graphs
	 * @return the nodes visited in the traversal
	 */
	private List<Node> expandAll(JTree tree, Node node, TreePath treePath, List<Node> alreadyVisited) {
		if (node.isLeaf())
			return alreadyVisited; // done if nothing has to be expanded
		if (alreadyVisited.contains(node))
			return alreadyVisited; // already visited; avoids infinite loops in cycles (graphs)
		alreadyVisited.add(node);
		tree.expandPath(treePath);
		for (int i=0; i<node.getChildrenCount(); i++) {
			Node childNode = node.getChild(i);
			expandAll(tree, childNode, treePath.pathByAddingChild(childNode), alreadyVisited);
		}
		return alreadyVisited;
	}

	/**
	 * Expands all the nodes in a tree view
	 * @param tree the JTree to be expanded
	 * @param node the node we want al the children to be expanded transitively
	 * @param treePath the path describing when the node is placed in the tree
	 * @return the nodes visited in the traversal
	 */
	public List<Node> expandAll(JTree tree, Node node, TreePath treePath) {
		ArrayList<Node> alreadyVisited = new ArrayList<>();
		expandAll(tree, node, treePath, alreadyVisited);
		return alreadyVisited;
	}

}

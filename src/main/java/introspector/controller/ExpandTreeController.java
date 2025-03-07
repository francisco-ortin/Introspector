/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.controller;

import introspector.model.Node;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller implements different ways of expanding tree views (JTrees).
 */
public class ExpandTreeController {

	/**
	 * Expands all the nodes that are descendants of a given node in a tree view
	 * @param tree the JTree to be expanded
	 * @param node the node we want al the children to be expanded transitively
	 * @param treePath the path describing when the node is placed in the tree
	 * @param alreadyVisited the list of nodes visited to avoid infinite loops in graphs
	 * @return the nodes visited in the traversal
	 */
	private List<Node> expandAllFromNode(JTree tree, Node node, TreePath treePath, List<Node> alreadyVisited) {
		if (node.isLeaf())
			return alreadyVisited; // done if nothing has to be expanded
		if (alreadyVisited.contains(node))
			return alreadyVisited; // already visited; avoids infinite loops in cycles (graphs)
		alreadyVisited.add(node);
		tree.expandPath(treePath);
		for (int i=0; i<node.getChildrenCount(); i++) {
			Node childNode = node.getChild(i);
			expandAllFromNode(tree, childNode, treePath.pathByAddingChild(childNode), alreadyVisited);
		}
		return alreadyVisited;
	}

	/**
	 * Expands all the nodes that are descendants of a given node in a tree view
	 * @param tree the JTree to be expanded
	 * @param node the node we want al the children to be expanded transitively
	 * @param treePath the path describing when the node is placed in the tree
	 * @return the nodes visited in the traversal
	 */
	public List<Node> expandAllFromNode(JTree tree, Node node, TreePath treePath) {
		ArrayList<Node> alreadyVisited = new ArrayList<>();
		expandAllFromNode(tree, node, treePath, alreadyVisited);
		return alreadyVisited;
	}

	/**
	 * Expands all the nodes that are descendants of the selected node in a tree view.
	 * If no node is selected, then all the tree will be expanded.
	 * @param tree the JTree to be expanded
	 * @return the nodes visited in the traversal
	 */
	public List<Node> expandAllFromSelectedNode(JTree tree) {
		TreePath[] paths = tree.getSelectionPaths();
		if (paths == null) { // with no selection, the root node is expanded
			Node rootNode = (Node)tree.getModel().getRoot();
			TreePath rootTreePath = new TreePath(new Object[]{tree.getModel().getRoot()});
			return this.expandAllFromNode(tree, rootNode, rootTreePath);
		}
		// otherwise, we expand all the selected nodes
		List<Node> alreadyVisited = new ArrayList<>();
		for (TreePath treePath: paths) {
			Node selectedNode = (Node)treePath.getLastPathComponent();
			alreadyVisited.addAll(this.expandAllFromNode(tree, selectedNode, treePath));
		}
		return alreadyVisited;
	}

	/**
	 * Expands all the nodes that are descendants of the root node in a tree view.
	 * @param trees the JTrees to be expanded
	 * @return the nodes visited in the traversal
	 */
	public List<Node> expandAllFromRootNode(List<JTree> trees) {
		List<Node> alreadyVisited = new ArrayList<>();
		for(JTree tree: trees) {
			Node rootNode = (Node) tree.getModel().getRoot();
			TreePath rootTreePath = new TreePath(new Object[]{tree.getModel().getRoot()});
			List<Node> expandedNodes = this.expandAllFromNode(tree, rootNode, rootTreePath);
			alreadyVisited.addAll(expandedNodes);
		}
		return alreadyVisited;
	}

}

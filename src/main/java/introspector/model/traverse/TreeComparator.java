/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model.traverse;


import introspector.model.Node;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.*;

/**
 * Class that compares two trees and writes the differences in lists of files
 */
public class TreeComparator {




	/**
	 * Compares two trees and returns true if they are equal, false otherwise
	 * @param path1 the root of the first tree
	 * @param path2 the root of the second tree
	 * @return true if the trees are equal, false otherwise. The three lists are updated with the differences:
	 * newNodes, deletedNodes and modifiedNodes.
	 */
	public List<Node> compareTrees(TreePath path1, TreePath path2) {
		return compareNode(path1.getLastPathComponent(), path2.getLastPathComponent(), false, new ArrayList<>(), new ArrayList<>());
	}

	/**
	 * Compares two trees and returns the list of different nodes
	 * @param object1 the root of the first tree
	 * @param object2 the root of the second tree
	 * @param equalName whether the names of the nodes should be compared
	 * @param modifiedNodes the list of modified nodes
	 * @param alreadyTraversed the list of nodes that have been visited in this traversal
	 * @return the list of modified nodes
	 */
	private List<Node> compareNode(Object object1, Object object2, boolean equalName, List<Node> modifiedNodes, List<Node> alreadyTraversed) {
		if (object1 == null && object2 != null) {
			modifiedNodes.add((Node) object2);
			return modifiedNodes;
		}
		if (object1 != null && object2 == null) {
			modifiedNodes.add((Node) object1);
			return modifiedNodes;
		}
		if (object1 instanceof Node node1 && object2 instanceof Node node2) {
			node1.compareTrees(node2, equalName, modifiedNodes, alreadyTraversed);
		}
		return modifiedNodes;
	}



}

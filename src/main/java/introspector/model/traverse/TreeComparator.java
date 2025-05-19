/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model.traverse;


import introspector.model.Node;
import introspector.model.NodeFactory;

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
	public Set<Node> compareTrees(TreePath path1, TreePath path2) {
		return compareNode(path1.getLastPathComponent(), path2.getLastPathComponent(), false, new HashSet<>(), new HashSet<>());
	}

	/**
	 * Compares two trees and returns true if they are equal, false otherwise
	 * @param tree1 the root of the first tree
	 * @param tree2 the root of the second tree
	 * @return the set of different nodes (if any)
	 */
	public Set<Node> compareTrees(Object tree1, Object tree2) {
		if (tree1 == null && tree2 == null)
			return new HashSet<>();  // both null => equal trees
		if (tree1 == null || tree2 == null) { // one null and the other not null => different trees
			Set<Node> modifiedNodes = new HashSet<>();
			modifiedNodes.add(createNodeIfNeeded(tree1));
			modifiedNodes.add(createNodeIfNeeded(tree2));
			return modifiedNodes;
		}
		return compareTrees(new TreePath(tree1), new TreePath(tree2));
	}

	/**
	 * Compares two trees and returns the list of different nodes
	 * @param object1 the root of the first tree
	 * @param object2 the root of the second tree
	 * @param equalName whether the names of the nodes should be compared
	 * @param modifiedNodes the list of modified nodes
	 * @param alreadyTraversed the list of nodes that have been visited in this traversal
	 * @return the set of different nodes
	 */
	private Set<Node> compareNode(Object object1, Object object2, boolean equalName, Set<Node> modifiedNodes, Set<SymmetricPair<Node, Node>> alreadyTraversed) {
		if (object1 == null && object2 != null) {
			modifiedNodes.add((Node) object2);
			return modifiedNodes;
		}
		if (object1 != null && object2 == null) {
			modifiedNodes.add((Node) object1);
			return modifiedNodes;
		}
		// they are not null, but they might be not Node objects; we make sure they are by creating them when needed
		Node node1 = createNodeIfNeeded(object1);
		Node node2 = createNodeIfNeeded(object2);
		return node1.compareTrees(node2, equalName, modifiedNodes, alreadyTraversed);
	}


	/**
	 * Makes sure the object is a node by creating a node wrapping the object if needed
	 */
	private static Node createNodeIfNeeded(Object object) {
		if (object == null)
			return null;
		if (object instanceof Node)
			return (Node) object;
		return NodeFactory.createNode("Tree", object);
	}

}

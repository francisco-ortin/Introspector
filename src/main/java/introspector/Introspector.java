/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector;

import introspector.model.IntrospectorModel;
import introspector.model.Node;
import introspector.model.NodeFactory;
import introspector.model.traverse.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Facade to use Introspector as an API to store Java objects as txt or html trees, and compare trees.
 */
public class Introspector {

	/**
	 * Utility class
	 */
	private Introspector() {}


	/**
	 * Writes any Java object as a tree in a textual file
	 * @param treeRoot the object to be considered as the root node of the tree
	 * @param rootName the used to display the root node
	 * @param outputFileName the output file name
	 * @param allInfo whether all info of the object is to be displayed (true) or the simplified version (false)
	 * @return whether the file has been written or not
	 */
	public static boolean writeTreeAsTxt(Object treeRoot, String rootName, String outputFileName, boolean allInfo) {
		WriteTreeTraversal walker = new WriteTreeTraversal();
		try {
			walker.traverse(NodeFactory.createNode(rootName, treeRoot), new TxtTreeSerializer(outputFileName, allInfo));
		} catch (IOException e) {
			return false; // something went wrong, the file was not written
		}
		return true; // everything was OK
	}

	/**
	 * Writes any Java object as a tree in a textual file, showing all the info of the objects
	 * @param treeRoot the object to be considered as the root node of the tree
	 * @param rootName the used to display the root node
	 * @param outputFileName the output file name
	 * @return whether the file has been written or not
	 */
	public static boolean writeTreeAsTxt(Object treeRoot, String rootName, String outputFileName) {
		return Introspector.writeTreeAsTxt(treeRoot, rootName, outputFileName, true);
	}

	/**
	 * Writes any Java object as a tree in an HTML file
	 * @param treeRoot the object to be considered as the root node of the tree
	 * @param rootName the used to display the root node
	 * @param outputFileName the output file name
	 * @param allInfo whether all info of the object is to be displayed (true) or the simplified version (false)
	 * @return whether the file has been written or not
	 */
	public static boolean writeTreeAsHtml(Object treeRoot, String rootName, String outputFileName, boolean allInfo) {
		WriteTreeTraversal walker = new WriteTreeTraversal();
		try {
			walker.traverse(NodeFactory.createNode(rootName, treeRoot), new HtmlTreeSerializer(outputFileName, allInfo));
		} catch (IOException e) {
			return false; // something went wrong, the file was not written
		}
		return true; // everything was OK
	}

	/**
	 * Writes any Java object as a tree in an HTML file, showing all the info of the objects
	 * @param treeRoot the object to be considered as the root node of the tree
	 * @param rootName the used to display the root node
	 * @param outputFileName the output file name
	 * @return whether the file has been written or not
	 */
	public static boolean writeTreeAsHtml(Object treeRoot, String rootName, String outputFileName) {
		return Introspector.writeTreeAsHtml(treeRoot, rootName, outputFileName, true);
	}


	/**
	 * Compares two trees
	 * @param treeRoot1 the root node of the first tree
	 * @param treeRoot2 the root node of the second tree
	 * @return whether the trees are equal or not
	 */
	public static boolean compareTrees(Object treeRoot1, Object treeRoot2) {
		TreeComparator treeComparator = new TreeComparator();
		Set<Node> modifiedNodes = treeComparator.compareTrees(treeRoot1, treeRoot2);
		return modifiedNodes.isEmpty();
	}

	/**
	 * Compares two trees and write both trees in txt files highlighting the differences (modified nodes are shown between ** and **)
	 * @param treeRoot1 the root node of the first tree
	 * @param treeRoot2 the root node of the second tree
	 * @param outputFileName1 the output file name for the first tree
	 * @param outputFileName2 the output file name for the second tree
	 * @param allInfo whether all info of the object is to be displayed (true) or the simplified version (false)
	 * @return whether the trees are equal or not
	 */
	public static boolean compareTreesAsTxt(Object treeRoot1, Object treeRoot2, String outputFileName1, String outputFileName2, boolean allInfo) {
		Set<Node> modifiedNodes;
		try {
			TreeComparator treeComparator = new TreeComparator();
			modifiedNodes = treeComparator.compareTrees(treeRoot1, treeRoot2);
			WriteTreeTraversal walker = new WriteTreeTraversal();
			Node node1 = treeRoot1 instanceof Node ? (Node)treeRoot1 : NodeFactory.createNode("Tree1", treeRoot1);
			Node node2 = treeRoot2 instanceof Node ? (Node)treeRoot2 : NodeFactory.createNode("Tree2", treeRoot2);
			walker.traverse(node1, new TxtTreeSerializer(outputFileName1, allInfo, modifiedNodes));
			walker.traverse(node2, new TxtTreeSerializer(outputFileName2, allInfo, modifiedNodes));
		} catch (IOException e) {
			return false; // something went wrong, the files were not written
		}
		return modifiedNodes.isEmpty();
	}

	/**
	 * Compares two trees and write both trees in txt files highlighting the differences showing all the info of the objects (modified nodes are shown between ** and **)
	 * @param treeRoot1 the root node of the first tree
	 * @param treeRoot2 the root node of the second tree
	 * @param outputFileName1 the output file name for the first tree
	 * @param outputFileName2 the output file name for the second tree
	 * @return whether the trees are equal or not
	 */
	public static boolean compareTreesAsTxt(Object treeRoot1, Object treeRoot2, String outputFileName1, String outputFileName2) {
		return compareTreesAsTxt(treeRoot1, treeRoot2, outputFileName1, outputFileName2, true);
	}

	/**
	 * Compares the same tree in two different moments of time and write both trees in txt files highlighting the differences showing all the info of the objects (modified nodes are shown between ** and **)
	 * @param treeFirstMoment the tree in the first moment that has been cloned upon construction of the IntrospectorModel
	 * @param treeSecondMoment the tree in the second moment
	 * @param outputFileName1 the output file name for the first tree
	 * @param outputFileName2 the output file name for the second tree
	 * @return whether the trees are equal or not
	 */
	public static boolean compareTreesAsTxt(IntrospectorModel treeFirstMoment, Object treeSecondMoment, String outputFileName1, String outputFileName2) {
		return compareTreesAsTxt(treeFirstMoment.getRoot(), treeSecondMoment, outputFileName1, outputFileName2, true);
	}

	/**
	 * Compares the same tree in two different moments of time and write both trees in txt files highlighting the differences showing all the info of the objects (modified nodes are shown between ** and **)
	 * @param treeFirstMoment the tree in the first moment that has been cloned upon construction of the IntrospectorModel
	 * @param treeSecondMoment the tree in the second moment
	 * @param outputFileName1 the output file name for the first tree
	 * @param outputFileName2 the output file name for the second tree
	 * @param allInfo whether all info of the object is to be displayed (true) or the simplified version (false)
	 * @return whether the trees are equal or not
	 */
	public static boolean compareTreesAsTxt(IntrospectorModel treeFirstMoment, Object treeSecondMoment, String outputFileName1, String outputFileName2, boolean allInfo) {
		return compareTreesAsTxt(treeFirstMoment.getRoot(), treeSecondMoment, outputFileName1, outputFileName2, allInfo);
	}


	/**
	 * Compares two trees and write both trees in html files highlighting the differences (modified nodes are shown in red)
	 * @param treeRoot1 the root node of the first tree
	 * @param treeRoot2 the root node of the second tree
	 * @param outputFileName1 the output file name for the first tree
	 * @param outputFileName2 the output file name for the second tree
	 * @param allInfo whether all info of the object is to be displayed (true) or the simplified version (false)
	 * @return whether the trees are equal or not
	 */
	public static boolean compareTreesAsHtml(Object treeRoot1, Object treeRoot2, String outputFileName1, String outputFileName2, boolean allInfo) {
		Set<Node> modifiedNodes;
		try {
			TreeComparator treeComparator = new TreeComparator();
			modifiedNodes = treeComparator.compareTrees(treeRoot1, treeRoot2);
			Node node1 = treeRoot1 instanceof Node ? (Node)treeRoot1 : NodeFactory.createNode("Tree1", treeRoot1);
			Node node2 = treeRoot2 instanceof Node ? (Node)treeRoot2 : NodeFactory.createNode("Tree2", treeRoot2);
			WriteTreeTraversal walker = new WriteTreeTraversal();
			walker.traverse(node1, new HtmlTreeSerializer(outputFileName1, allInfo, modifiedNodes));
			walker.traverse(node2, new HtmlTreeSerializer(outputFileName2, allInfo, modifiedNodes));
		} catch (IOException e) {
			return false; // something went wrong, the files were not written
		}
		return modifiedNodes.isEmpty();
	}

	/**
	 * Compares two trees and write both trees in txt files highlighting the differences showing all the info of the objects (modified nodes are shown in red)
	 * @param treeRoot1 the root node of the first tree
	 * @param treeRoot2 the root node of the second tree
	 * @param outputFileName1 the output file name for the first tree
	 * @param outputFileName2 the output file name for the second tree
	 * @return whether the trees are equal or not
	 */
	public static boolean compareTreesAsHtml(Object treeRoot1, Object treeRoot2, String outputFileName1, String outputFileName2) {
		return compareTreesAsHtml(treeRoot1, treeRoot2, outputFileName1, outputFileName2, true);
	}

	/**
	 * Compares the same tree in two different moments of time and write both trees in html files highlighting the differences showing all the info of the objects (modified nodes are shown between ** and **)
	 * @param treeFirstMoment the tree in the first moment that has been cloned upon construction of the IntrospectorModel
	 * @param treeSecondMoment the tree in the second moment
	 * @param outputFileName1 the output file name for the first tree
	 * @param outputFileName2 the output file name for the second tree
	 * @return whether the trees are equal or not
	 */
	public static boolean compareTreesAsHtml(IntrospectorModel treeFirstMoment, Object treeSecondMoment, String outputFileName1, String outputFileName2) {
		return compareTreesAsHtml(treeFirstMoment.getRoot(), treeSecondMoment, outputFileName1, outputFileName2, true);
	}

	/**
	 * Compares the same tree in two different moments of time and write both trees in html files highlighting the differences showing all the info of the objects (modified nodes are shown between ** and **)
	 * @param treeFirstMoment the tree in the first moment that has been cloned upon construction of the IntrospectorModel
	 * @param treeSecondMoment the tree in the second moment
	 * @param outputFileName1 the output file name for the first tree
	 * @param outputFileName2 the output file name for the second tree
	 * @param allInfo whether all info of the object is to be displayed (true) or the simplified version (false)
	 * @return whether the trees are equal or not
	 */
	public static boolean compareTreesAsHtml(IntrospectorModel treeFirstMoment, Object treeSecondMoment, String outputFileName1, String outputFileName2, boolean allInfo) {
		return compareTreesAsHtml(treeFirstMoment.getRoot(), treeSecondMoment, outputFileName1, outputFileName2, allInfo);
	}


}


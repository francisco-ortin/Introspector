/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector;

import introspector.model.Node;
import introspector.model.NodeFactory;
import introspector.model.traverse.HtmlTreeSerializer;
import introspector.model.traverse.TreeComparator;
import introspector.model.traverse.TxtTreeSerializer;
import introspector.model.traverse.WriteTreeTraversal;

import java.io.IOException;
import java.util.List;

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
	 * Writes any Java object as a tree in a textual file, showing oll the info of the objects
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
	 * Writes any Java object as a tree in an HTML file, showing oll the info of the objects
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
		List<Node> modifiedNodes = treeComparator.compareTrees(treeRoot1, treeRoot2);
		return modifiedNodes.isEmpty();
	}


}


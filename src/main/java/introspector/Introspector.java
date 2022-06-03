/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector;

import introspector.model.NodeFactory;
import introspector.model.traverse.HtmlTreeSerializer;
import introspector.model.traverse.TxtTreeSerializer;
import introspector.model.traverse.WriteTreeTraversal;

import java.io.IOException;

/**
 * Facade to use Introspector as an API to store Java objects as txt or html trees.
 */
public class Introspector {

	// TODO generar el Javadoc con un script,  copiarlo a un directorio docs
	// TODO hacer el Readme.md con cómo instalarlo, usarlo como aplicación, usarlo como API, compilarlo, compilarlo con Maven y tetearlo con Maven
	// TODO configurar GitHub para que  publique como web el directorio docs (la salida del Javadoc)

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

}


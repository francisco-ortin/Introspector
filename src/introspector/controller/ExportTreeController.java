/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.controller;

import introspector.model.Node;
import introspector.model.NodeFactory;
import introspector.model.traverse.HtmlTreeSerializer;
import introspector.model.traverse.TxtTreeSerializer;
import introspector.model.traverse.WriteTreeTraversal;
import introspector.view.ViewHelper;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import java.io.IOException;
import java.util.Optional;

/**
 * This controller defines how to export a tree into different formats.
 */
public class ExportTreeController {

	/**
	 * The tree view
	 */
	private final JTree tree;

	/**
	 * The parent frame
	 */
	private final JFrame frame;

	/**
	 * @param frame the parent frame
	 * @param tree the JTree where the tree is displayed
	 */
	public ExportTreeController(JFrame frame, JTree tree) {
		this.frame = frame;
		this.tree = tree;
	}

	/**
	 * Exports the selected node to a text file.
	 *
	 * @param filename the name of the text file.
	 * @param expandSelectedNode true means it is the selected node to be exported; false is the whole tree
	 * @return whether the export action was successful.
	 */
	public boolean exportToTxt(String filename, boolean expandSelectedNode) {
		TreePath path = this.tree.getSelectionPath();
		Node selectedNode;
		if (!expandSelectedNode || path == null) // either we don't want the selected or none node is selected
			selectedNode = (Node) this.tree.getModel().getRoot(); // root node
		else
			selectedNode = (Node) path.getLastPathComponent(); // selected node
		try {
			WriteTreeTraversal walker = new WriteTreeTraversal();
			walker.traverse(selectedNode, new TxtTreeSerializer(filename, true));
		} catch (IOException e) {
			return false; // it has not been exported (error writing file)
		}
		return true; // successfully exported
	}

	/**
	 * Exports the selected node to a text file and shows the success or failure message.
	 *
	 * @param statusLabel the label where the success or failure message is shown
	 * @param expandSelectedNode true means it is the selected node to be exported; false is the whole tree
	 * @return whether the export action was successful.
	 */
	public boolean exportToTxt(JLabel statusLabel, boolean expandSelectedNode) {
		// asks for a filename
		Optional<String> filename = ViewHelper.selectFileFromDisk(this.frame, "Text files", "txt", "text");
		if (filename.isPresent()) { // calls the controller if a file was chosen
			boolean success = this.exportToTxt(filename.get(), expandSelectedNode);
			if (success) {
				ViewHelper.showMessageInStatus(statusLabel,
						String.format("File '%s' successfully written.", filename.get()));
				return true;
			} else {
				ViewHelper.showErrorMessageInStatus(statusLabel,
						String.format("The file '%s' could not be written.", filename.get()));
				return false;
			}
		}
		return false; // no file was selected
	}


	/**
	 * Exports the selected node to a HTML file.
	 *
	 * @param filename the name of the HTML file.
	 * @param expandSelectedNode true means it is the selected node to be exported; false is the whole tree
	 * @return whether the export action was successful.
	 */
	public boolean exportToHtml(String filename, boolean expandSelectedNode) {
		TreePath path = this.tree.getSelectionPath();
		Node selectedNode;
		if (!expandSelectedNode || path == null) // either we don't want the selected or none node is selected
			selectedNode = (Node) this.tree.getModel().getRoot(); // root node
		else
			selectedNode = (Node) path.getLastPathComponent(); // selected node
		try {
			WriteTreeTraversal walker = new WriteTreeTraversal();
			walker.traverse(selectedNode, new HtmlTreeSerializer(filename, true));
		} catch (IOException e) {
			return false; // it has not been exported (error writing file)
		}
		return true; // successfully exported
	}

	/**
	 * Exports the selected node to an HTML file and shows the success or failure message.
	 *
	 * @param statusLabel the label where the success or failure message is shown
	 * @param expandSelectedNode true means it is the selected node to be exported; false is the whole tree
	 * @return whether the export action was successful.
	 */
	public boolean exportToHtml(JLabel statusLabel, boolean expandSelectedNode) {
		// asks for a filename
		Optional<String> filename = ViewHelper.selectFileFromDisk(this.frame, "HTML files", "htm", "html");
		if (filename.isPresent()) { // calls the controller if a file was chosen
			boolean success = this.exportToHtml(filename.get(), expandSelectedNode);
			if (success) {
				ViewHelper.showMessageInStatus(statusLabel,
						String.format("File '%s' successfully written.", filename.get()));
				return true;
			} else {
				ViewHelper.showErrorMessageInStatus(statusLabel,
						String.format("The file '%s' could not be written.", filename.get()));
				return false;
			}
		}
		return false; // no file was selected
	}

}

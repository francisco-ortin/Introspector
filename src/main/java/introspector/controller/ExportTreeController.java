/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.controller;

import introspector.model.Node;
import introspector.model.traverse.HtmlTreeSerializer;
import introspector.model.traverse.TxtTreeSerializer;
import introspector.model.traverse.WriteTreeTraversal;
import introspector.view.ViewHelper;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This controller defines how to export a tree into different formats.
 */
public class ExportTreeController {

	/**
	 * The tree views associated with the action
	 */
	private final List<JTree> trees;

	/**
	 * The parent frame
	 */
	private final JFrame frame;

	/**
	 * Constructor of the controller.
	 * @param frame the parent frame
	 * @param trees the JTrees where the tree is displayed
	 */
	public ExportTreeController(JFrame frame, List<JTree> trees) {
		this.frame = frame;
		this.trees = new ArrayList<>(trees);
	}

	/**
	 * Constructor of the controller.
	 * @param frame the parent frame
	 * @param tree the JTree where the tree is displayed
	 */
	public ExportTreeController(JFrame frame, JTree tree) {
		this.frame = frame;
		this.trees = new ArrayList<>();
		this.trees.add(tree);
	}

	/**
	 * Exports the selected node to a text file.
	 *
	 * @param filename the name of the text file.
	 * @param expandSelectedNode true means it is the selected node to be exported; false is the whole tree
	 * @return whether the export action was successful.
	 */
	public boolean exportToTxt(String filename, boolean expandSelectedNode) {
		for(int i = 0; i < trees.size(); i++) {
			JTree tree = trees.get(i);
			TreePath path = tree.getSelectionPath();
			Node selectedNode;
			if (!expandSelectedNode || path == null) // either we don't want the selected or none node is selected
				selectedNode = (Node) tree.getModel().getRoot(); // root node
			else
				selectedNode = (Node) path.getLastPathComponent(); // selected node
			try {
				WriteTreeTraversal walker = new WriteTreeTraversal();
				filename = removeFileExtension(filename);
				String finalFilename = trees.size()==1 ? filename + ".txt" : String.format("%s_%s.txt", filename, i+1);
				walker.traverse(selectedNode, new TxtTreeSerializer(finalFilename, true));
			} catch (IOException e) {
				return false; // it has not been exported (error writing file)
			}
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
		for(int i = 0; i < trees.size(); i++) {
			JTree tree = trees.get(i);
			TreePath path = tree.getSelectionPath();
			Node selectedNode;
			if (!expandSelectedNode || path == null) // either we don't want the selected or none node is selected
				selectedNode = (Node) tree.getModel().getRoot(); // root node
			else
				selectedNode = (Node) path.getLastPathComponent(); // selected node
			try {
				WriteTreeTraversal walker = new WriteTreeTraversal();
				filename = removeFileExtension(filename);
				String finalFilename = trees.size()==1 ? filename + ".html" : String.format("%s_%s.html", filename, i+1);
				walker.traverse(selectedNode, new HtmlTreeSerializer(finalFilename, true));
			} catch (IOException e) {
				return false; // it has not been exported (error writing file)
			}
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

	/**
	 * Removes the extension of a file name.
	 * @param fileName the name of the file
	 * @return the name of the file without the extension
	 */
	public static String removeFileExtension(String fileName) {
		int lastDot = fileName.lastIndexOf('.');
		if (lastDot == -1)
			return fileName;
		return fileName.substring(0, lastDot);
	}

}

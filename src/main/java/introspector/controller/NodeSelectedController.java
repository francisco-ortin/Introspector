/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.controller;

import introspector.model.Node;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;

/**
 * This controller defines how to act when a new node in the tree is selected.
 */
public class NodeSelectedController {


	/**
	 * Text area that describes the current node, calling its toString() method
	 */
	private final JTextArea textArea;

	/**
	 * Above the textArea, this label shows the runtime type of the object represented by the current node.
	 */
	private final JLabel labelClass;

	/**
	 * The tree view
	 */
	private final JTree tree;

	public NodeSelectedController(JTree tree, JTextArea textArea, JLabel labelClass) {
		this.tree = tree;
		this.textArea = textArea;
		this.labelClass = labelClass;
	}

	/**
	 * When the selected node is changed, its description is shown in the text area and its dynamic type in
	 * the label.
	 * @param ignoredEvent the event that characterizes the change.
	 */
	public void selectedNodeChanged(TreeSelectionEvent ignoredEvent) {
		TreePath path = tree.getSelectionPath();
		if (path != null) {
			Node node = (Node) path.getLastPathComponent();
			this.textArea.setText(node.getNodeDescription());
			this.labelClass.setText(node.getClassName());
		}
		else { // No node has been selected
			this.textArea.setText("");
			this.labelClass.setText("");
		}
	}


}

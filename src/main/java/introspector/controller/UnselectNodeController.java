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
 * This controller implements a mechanism to unselect a node in a tree view.
 */
public class UnselectNodeController {


	/**
	 * Unselects the selected node in a tree view.
	 * @param tree the JTree to be unselected
	 */
	public void unselectNode(JTree tree) {
		tree.clearSelection();
	}


}

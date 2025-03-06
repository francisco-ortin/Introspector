package introspector.controller;

import introspector.model.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This controller implements different ways of expanding tree views (JTrees).
 */
public class UpdateTreeController {

	// A set to track which rows should be colored blue
	private Set<Integer> blueNodes = new HashSet<>();

	/**
	 * Expands all the nodes that are descendants of the root node in a tree view.
	 * @param tree the JTree to be expanded
	 * @return the nodes visited in the traversal
	 */
	public void showUpdatedNodes(JTree tree) {
		int rowCount = tree.getRowCount();
		for (int row = 0; row < rowCount; row++) {
			TreePath path = tree.getPathForRow(row);
			System.out.println("Traversing node at row " + row + ": " + path);
			if (tree.isExpanded(row)) {
				System.out.println("Expanded node at row " + row + ": " + path.getLastPathComponent());
				// Mark the node for blue coloring
				blueNodes.add(row);
				tree.repaint(tree.getPathBounds(path)); // Ensure the node gets re-rendered
			} else {
				if (((Node) path.getLastPathComponent()).isLeaf()) {
					System.out.println("Leaf node at row " + row + ": " + path);
					// Mark the leaf node for blue coloring
					blueNodes.add(row);
					tree.repaint(tree.getPathBounds(path)); // Ensure the node gets re-rendered
				}
			}
		}

		// Set the custom renderer once for the entire tree
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			@Override
			public java.awt.Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				// Get the default component for the tree node
				java.awt.Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

				// If this row is in the set of nodes to be colored blue, set the font color to blue
				if (blueNodes.contains(row)) {
					c.setForeground(Color.BLUE);
				}

				return c;
			}
		});
	}
}

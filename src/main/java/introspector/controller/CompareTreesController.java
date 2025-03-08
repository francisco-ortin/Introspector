package introspector.controller;

import introspector.model.Node;
import introspector.model.traverse.TreeComparator;
import introspector.view.IntrospectorView;
import introspector.view.ViewHelper;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.List;

/**
 * This controller implements the comparison of two trees selected by the user.
 */
public class CompareTreesController {

	/**
	 * The label where the status message is written
	 */
	private final JLabel statusLabel;

	public CompareTreesController(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}

	/**
	 * Expands all the nodes that are descendants of the root node in a tree view.
	 * @param trees all the trees in the view
	 */
	public void compareTrees(List<JTree> trees) {
		if (!this.twoNodesSelected(trees)) {
			JOptionPane.showMessageDialog(null,
					"You must select exactly two nodes to compare both trees.\nThey could belong to different trees.\nUse the popup menu (right click) or Ctrl+Click to unselect nodes.");
			return;
		}
		// get the two selected nodes
		Pair<JTree, TreePath>[] selectedNodes = this.getSelectedNodes(trees);
		TreeComparator treeComparator = new TreeComparator();
		List<Node> modifiedNodes = treeComparator.compareTrees(selectedNodes[0].getSecond(), selectedNodes[1].getSecond());
		// Show as colored nodes those that are different
		showSelectedNodes(trees, modifiedNodes);
		// Show a message in the status bar
		ViewHelper.showMessageInStatus(this.statusLabel,
				String.format("Trees compared. Different nodes are shown in red."));

	}

	private static void showSelectedNodes(List<JTree> trees, List<Node> modifiedNodes) {
		for(JTree tree: trees) {
			// Set the custom renderer once for the entire tree
			tree.setCellRenderer(new DefaultTreeCellRenderer() {
				@Override
				public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
					// Get the default component for the tree node
					Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
					// If this row is in the set of nodes to be colored, set the font color to blue
					if (modifiedNodes.contains(value))
						component.setForeground(Color.RED);
					else
						component.setForeground(Color.BLACK);
					return component;
				}
			});
		}
	}


	/**
	 * Gets the two selected nodes in the trees
	 * @param trees the trees
	 * @return the two selected nodes as TreePaths
	 */
	private Pair[] getSelectedNodes(List<JTree> trees) {
		Pair[] selectedNodes = new Pair[2];
		int i = 0;
		for (JTree tree : trees) {
			TreePath[] paths = tree.getSelectionPaths();
			if (paths != null)
				for (TreePath path : paths)
					selectedNodes[i++] = new Pair(tree, path);
		}
		return selectedNodes;
	}

	private boolean twoNodesSelected(List<JTree> trees) {
		int numberOfSelectedNodes = 0;
		for (JTree tree : trees)
			numberOfSelectedNodes += tree.getSelectionCount();
		return numberOfSelectedNodes == 2;  // Exactly two nodes in total
	}

}

/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.view;

import introspector.controller.*;
import introspector.model.IntrospectorModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
import javax.swing.tree.TreeModel;
import java.util.List;

/**
 * This is the window view to visualize the program as a tree.
 * The model representing the tree should be passed to the constructor as a parameter.
 */
public class IntrospectorView extends JFrame {

	/**
	 * The tree view
	 */
	private final List<JTree> trees =new ArrayList<>();

	/**
	 * Popup menu used over the JTree
	 */
	JPopupMenu popupMenu;

	/**
	 * The label that shows the status of the application (where messages are shown)
	 */
	JLabel labelStatus;

	/**
	 * In pixels, the default size of the window.
	 */
	private static final int DEFAULT_WINDOW_WIDTH = 800, DEFAULT_WINDOW_HEIGHT = 400;


	/**
	 * Creates the toolbar
	 * @return The toolbar created
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		// button expand all
		JButton buttonExpandAll = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/expand.png"))));
		buttonExpandAll.setToolTipText("Expand all the nodes (Alt+E)");
		buttonExpandAll.setMnemonic('E');  // shortcut is alt+E
		this.trees.forEach(tree -> buttonExpandAll.addActionListener(event -> new ExpandTreeController().expandAllFromRootNode(this.trees)));  // action
		toolBar.add(buttonExpandAll);
		toolBar.addSeparator();
		// button export to html
		JButton  buttonExportHTML = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/html.png"))));
		buttonExportHTML.setToolTipText("Export to HTML  (Alt+H)");
		buttonExportHTML.setMnemonic('H');  // shortcut is alt+H
		buttonExportHTML.addActionListener(event ->  new ExportTreeController(this, this.getTrees())
				.exportToHtml(this.labelStatus, false));
		toolBar.add(buttonExportHTML);
		// button export to text
		JButton buttonExportText = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/txt.png"))));
		buttonExportText.setToolTipText("Export to text (Alt+T)");
		buttonExportText.setMnemonic('T');  // shortcut is alt+T
		buttonExportText.addActionListener(event ->  new ExportTreeController(this, this.trees)
				.exportToTxt(this.labelStatus, false));
		toolBar.add(buttonExportText);
		toolBar.addSeparator();
		// button compare trees
		JButton buttonCompareTrees = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/compare.png"))));
		buttonCompareTrees.setToolTipText("Compare trees (Alt+C)");
		buttonCompareTrees.setMnemonic('C');  // shortcut is alt+C
		buttonCompareTrees.addActionListener(event ->  new CompareTreesController(labelStatus).compareTrees(trees));
		toolBar.add(buttonCompareTrees);
		toolBar.addSeparator();
		// return the toolbar
		return toolBar;
	}

	/**
	 * Creates the popup menu
	 * @param tree the tree where the popup menu will be shown
	 * @return The popup menu created
	 */
	private JPopupMenu createPopUpMenu(JTree tree) {
		JPopupMenu popupMenu = new JPopupMenu();
		// expand all the nodes
		final JMenuItem menuItemExpand = new JMenuItem("Expand all the nodes");
		menuItemExpand.setMnemonic('T');
		menuItemExpand.getAccessibleContext().setAccessibleDescription("Expand all the nodes");
		menuItemExpand.addActionListener(event -> new ExpandTreeController().expandAllFromSelectedNode(tree));
		popupMenu.add(menuItemExpand);
		// export to Html
		final JMenuItem menuItemExportHTML = new JMenuItem("Export to HTML");
		menuItemExpand.setMnemonic('H');
		menuItemExpand.getAccessibleContext().setAccessibleDescription("Export to HTML");
		menuItemExportHTML.addActionListener(event ->  new ExportTreeController(this, tree)
				.exportToHtml(this.labelStatus, true));
		popupMenu.add(menuItemExportHTML);
		// export to txt
		final JMenuItem menuItemExportTxt = new JMenuItem("Export to text");
		menuItemExpand.setMnemonic('T');
		menuItemExpand.getAccessibleContext().setAccessibleDescription("Export to Text");
		menuItemExportTxt.addActionListener(event ->  new ExportTreeController(this, tree)
				.exportToTxt(this.labelStatus, true));
		popupMenu.add(menuItemExportTxt);
		// export to txt
		final JMenuItem menuItemUnselect = new JMenuItem("Unselect the nodes of this tree");
		menuItemUnselect.setMnemonic('U');
		menuItemUnselect.getAccessibleContext().setAccessibleDescription("Unselect the nodes of this tree");
		menuItemUnselect.addActionListener(event ->  new UnselectNodeController().unselectNode(tree));
		popupMenu.add(menuItemUnselect);
		// compare trees
		final JMenuItem menuItemCompareTrees = new JMenuItem("Compare trees");
		menuItemCompareTrees.setMnemonic('C');
		menuItemCompareTrees.getAccessibleContext().setAccessibleDescription("Compare trees");
		menuItemCompareTrees.addActionListener(event ->  new CompareTreesController(this.labelStatus).compareTrees(trees));
		popupMenu.add(menuItemCompareTrees);
		// return the menu
		return popupMenu;
	}

	/**
	 * A DTO to represent a panel and a label with the same object
	 * @param panel the panel
	 * @param label the label
	 */
	record PanelAndLabel(JPanel panel, JLabel label) {}

	/**
	 * Creates a status bar and a label inside it.
	 * @return the panel and label created as a status bar.
	 */
	private PanelAndLabel createStatusBar() {
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		JLabel statusLabel = new JLabel("  ");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		return new PanelAndLabel(statusPanel, statusLabel);
	}

	/**
	 * List of horizontal split panes in the view. Their height should be modified every time a new tree is added.
	 */
	private List<JSplitPane> horizontalSplitPanes = new ArrayList<>();

	/**
	 * Main constructor of the view
	 * @param title title of the window
	 * @param model model to be displayed as a tree
	 * @param width window width
	 * @param height window height
	 * @param show whether the window must be showed or not
	 */
	public IntrospectorView(String title, TreeModel model, int width, int height, boolean show) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);

		JTree tree = new JTree(model);
		this.trees.add(tree);

		this.add(this.createToolBar(), BorderLayout.NORTH);

		PanelAndLabel panelAndLabel = this.createStatusBar();
		this.add(panelAndLabel.panel, BorderLayout.SOUTH);
		this.labelStatus = panelAndLabel.label;
		this.popupMenu = this.createPopUpMenu(tree);

		JSplitPane verticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		verticalSplitPane.setDividerSize(2);
		verticalSplitPane.setDividerLocation(width/2);

		verticalSplitPane.add(new JScrollPane(tree));  // the tree is the left-hand side of the window

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		JLabel labelClass = new JLabel("");
		rightPanel.add("North", labelClass);
		JTextArea textArea = new JTextArea();
		rightPanel.add("Center", new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER  ));

		verticalSplitPane.add(rightPanel);  // right-hand side of the window

		Container content = this.getContentPane();
		content.add(verticalSplitPane, "Center");

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/tree.png"));

		this.setIconImage(icon);

		// controllers
		tree.addTreeSelectionListener(event -> new NodeSelectedController(tree, textArea, labelClass)
				.selectedNodeChanged(event));
		tree.addMouseListener(new TreeMouseClickController(tree, this.popupMenu));
		// controller called when the size of the window is changed
		this.addComponentListener(new ResizeWindowController(this));
		// when the window is maximized
		this.addWindowStateListener(event -> new ResizeWindowController(this).componentResized(event));
		this.setVisible(show);
	}


	/**
	 * Adds another tree to the window in a new horizontal split view.
	 * The new tree will be displayed below the existing tree structure.
	 * @param newTreeModel The TreeModel for the new tree to be added.
	 */
	public void addTree(TreeModel newTreeModel) {
		// Create the new tree
		JTree newTree = new JTree(newTreeModel);
		this.trees.add(newTree);
		this.popupMenu = this.createPopUpMenu(newTree);

		// Create a new JSplitPane for the new tree below the previous ones
		JSplitPane newVerticalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		newVerticalSplitPane.setDividerSize(2);
		newVerticalSplitPane.setDividerLocation(this.getWidth() / 2);

		// Add the left-hand side of the second window section
		newVerticalSplitPane.add(new JScrollPane(newTree));

		// Create the second right panel
		JPanel secondRightPanel = new JPanel();
		secondRightPanel.setLayout(new BorderLayout());
		JLabel labelClass = new JLabel("");
		secondRightPanel.add("North", labelClass);
		JTextArea textArea = new JTextArea();
		secondRightPanel.add("Center", new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));

		newVerticalSplitPane.add(secondRightPanel);  // right-hand side of the second window section

		// Remove the previous "center" component from the content pane
		JSplitPane previousCenterComponent = (JSplitPane) getContentPane().getComponent(2);
		getContentPane().remove(2);

		// Create the new horizontal split pane
		JSplitPane newHorizontalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.horizontalSplitPanes.add(newHorizontalSplitPane);
		newHorizontalSplitPane.setDividerSize(2);
		this.updateVerticalSplitPanes();

		// Add the previous view above the horizontal split pane
		newHorizontalSplitPane.add(previousCenterComponent);
		// Add the new view below the previous view
		newHorizontalSplitPane.add(newVerticalSplitPane);
		// Update the main center view of the window
		this.getContentPane().add(newHorizontalSplitPane, "Center");

		// Controllers for the new tree
		newTree.addTreeSelectionListener(event -> new NodeSelectedController(newTree, textArea, labelClass)
				.selectedNodeChanged(event));
		newTree.addMouseListener(new TreeMouseClickController(newTree, this.popupMenu));

		// Revalidate and repaint to update the layout
		this.revalidate();
		this.repaint();
	}

	/**
	 * Adds another tree to the window in a new horizontal split view.
	 * The new tree will be displayed below the existing tree structure.
	 * @param treeName The name of the new tree to be added.
	 * @param newTreeModel The TreeModel for the new tree to be added.
	 */
	public void addTree(String treeName, Object newTreeModel) {
		this.addTree(new IntrospectorModel(treeName, newTreeModel));
	}

	/**
	 * Updates the vertical split panes to be in position relative to the size of the window and the number of trees.
	 */
	public void updateVerticalSplitPanes() {
		int numTrees = this.trees.size();
		// Place all the horizontal split panes in the correct height
		for(int i=0; i<numTrees-1; i++) {
			int horizontalSplitPanelHeight = (int)(this.getHeight()-90.0)/numTrees*(i+1);
			this.horizontalSplitPanes.get(i).setDividerLocation(horizontalSplitPanelHeight);
		}
		revalidate();
		repaint();
	}

	/**
	 * @return the JTree in the view
	 */
	public List<JTree> getTrees() {
		return this.trees;
	}


	/**
	 * A tree window is created and displayed: default size and visible
	 * @param title the title of the window
	 * @param model the model to be shown as a tree
	 */
	public IntrospectorView(String title, TreeModel model) {
		this(title, model, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, true);
	}
	/**
	 * A tree window is created and displayed: default size and visible
	 * @param title the title of the window
	 * @param model the model to be shown as a tree
	 */
	public IntrospectorView(String title, String treeName, Object model) {
		this(title, new IntrospectorModel(treeName, model), DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, true);
	}
	/**
	 * A tree window is created: default size
	 * @param title the title of the window
	 * @param model the model to be shown as a tree
	 * @param show whether the window must be shown
	 */
	public IntrospectorView(String title, TreeModel model, boolean show) {
		this(title, model, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, show);
	}
	/**
	 * A tree window is created: default size
	 * @param title the title of the window
	 * @param model the model to be shown as a tree
	 * @param show whether the window must be shown
	 */
	public IntrospectorView(String title, String treeName, Object model, boolean show) {
		this(title, new IntrospectorModel(treeName, model), DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, show);
	}


	/**
	 * A tree window is created
	 * @param title	the title of the window
	 * @param treeName the name of the tree
	 * @param model the model to be shown as a tree
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param show whether the window must be shown
	 */
 	public IntrospectorView(String title, String treeName, Object model, int width, int height, boolean show) {
		this(title, new IntrospectorModel(treeName, model), width, height, show);
	}
}

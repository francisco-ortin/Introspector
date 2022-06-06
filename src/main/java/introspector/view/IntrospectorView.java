/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.view;

import introspector.controller.ExpandTreeController;
import introspector.controller.ExportTreeController;
import introspector.controller.NodeSelectedController;
import introspector.controller.TreeMouseClickController;

import java.awt.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.tree.TreeModel;

/**
 * This is the window view to visualize the program as a tree.
 * The model representing the tree should be passed to the constructor as a parameter.
 */
public class IntrospectorView extends JFrame {

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
	 * A tree window is created and displayed
	 * @param title the title of the window
	 * @param model the model to be shown as a tree
	 */
	public IntrospectorView(String title, TreeModel model) {
		this(title, model, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, true);
	}
	/**
	 * A tree window is created
	 * @param title the title of the window
	 * @param model the model to be shown as a tree
	 * @param show whether the window must be shown
	 */
	public IntrospectorView(String title, TreeModel model, boolean show) {
		this(title, model, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, show);
	}

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
		buttonExpandAll.addActionListener(event -> new ExpandTreeController().expandAllFromRootNode(this.tree));  // action
		toolBar.add(buttonExpandAll);
		toolBar.addSeparator();
		// button export to html
		JButton  buttonExportHTML = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/html.png"))));
		buttonExportHTML.setToolTipText("Export to HTML  (Alt+H)");
		buttonExportHTML.setMnemonic('H');  // shortcut is alt+H
		buttonExportHTML.addActionListener(event ->  new ExportTreeController(this, this.tree)
				.exportToHtml(this.labelStatus, false));
		toolBar.add(buttonExportHTML);
		// button export to text
		JButton buttonExportText = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/txt.png"))));
		buttonExportText.setToolTipText("Export to text (Alt+T)");
		buttonExportText.setMnemonic('T');  // shortcut is alt+T
		buttonExportText.addActionListener(event ->  new ExportTreeController(this, this.tree)
				.exportToTxt(this.labelStatus, false));
		toolBar.add(buttonExportText);
		// return the toolbar
		return toolBar;
	}

	/**
	 * Creates the popup menu
	 * @return The popup menu created
	 */
	private JPopupMenu createPopUpMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		// expand all the nodes
		JMenuItem menuItem = new JMenuItem("Expand all the nodes");
		menuItem.setMnemonic('E');
		menuItem.getAccessibleContext().setAccessibleDescription("Expand all the nodes");
		menuItem.addActionListener(event -> new ExpandTreeController().expandAllFromSelectedNode(this.tree));
		popupMenu.add(menuItem);
		// export to Html
		menuItem = new JMenuItem("Export to HTML");
		menuItem.setMnemonic('H');
		menuItem.getAccessibleContext().setAccessibleDescription("Export to HTML");
		menuItem.addActionListener(event ->  new ExportTreeController(this, this.tree)
				.exportToHtml(this.labelStatus, true));
		popupMenu.add(menuItem);
		// export to txt
		menuItem = new JMenuItem("Export to Text");
		menuItem.setMnemonic('T');
		menuItem.getAccessibleContext().setAccessibleDescription("Export to Text");
		menuItem.addActionListener(event ->  new ExportTreeController(this, this.tree)
				.exportToTxt(this.labelStatus, true));
		popupMenu.add(menuItem);
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

		this.add(this.createToolBar(), BorderLayout.NORTH);

		PanelAndLabel panelAndLabel = this.createStatusBar();
		this.add(panelAndLabel.panel, BorderLayout.SOUTH);
		this.labelStatus = panelAndLabel.label;
		this.popupMenu = this.createPopUpMenu();


		this.tree = new JTree(model);
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.textArea.setLineWrap(true);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setDividerSize(2);
		splitPane.setDividerLocation(width/2);

		splitPane.add(new JScrollPane(tree));  // the tree is the left-hand side of the window

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add("North", labelClass = new JLabel(""));
		rightPanel.add("Center", new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER  ));

		splitPane.add(rightPanel);  // right-hand side of the window

		Container content = this.getContentPane();
		content.add(splitPane, "Center");

		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/tree.png"));

		this.setIconImage(icon);

		// controllers
		tree.addTreeSelectionListener(event -> new NodeSelectedController(this.tree, this.textArea, this.labelClass)
				.selectedNodeChanged(event));
		tree.addMouseListener(new TreeMouseClickController(this.tree, this.popupMenu));

		this.setVisible(show);
	}


	/**
	 * @return the JTree in the view
	 */
	public JTree getTree() {
		return this.tree;
	}


}

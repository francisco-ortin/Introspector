/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.view;

import introspector.model.Node;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the window view to visualize the program as a tree.
 * The model representing the tree should be passed to the constructor as a parameter.
 */
public class IntrospectorTree extends JFrame implements TreeSelectionListener {

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
	 * In pixels, the default size of the window.
	 */
	private static final int DEFAULT_WINDOW_WIDTH = 800, DEFAULT_WINDOW_HEIGHT = 400;

	public IntrospectorTree(String title, TreeModel model) {
		this(title, model, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	}

	/**
	 * Creates the toolbar
	 * @return The toolbar created
	 */
	private JToolBar createToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		// button expand all
		JButton buttonExpandAll = new JButton(new ImageIcon("imgs/expand.png"));
		buttonExpandAll.setToolTipText("Expand all the nodes");
		buttonExpandAll.setMnemonic('E');  // shortcut is alt+E
		buttonExpandAll.addActionListener(event -> new ExpandTree().expandAll(
				this.tree,
				(Node)this.tree.getModel().getRoot(),
				new TreePath(new Object[]{this.tree.getModel().getRoot()})
		));
		toolBar.add(buttonExpandAll);
		toolBar.addSeparator();
		// button export to html
		JButton  buttonExportHTML = new JButton(new ImageIcon("imgs/html.png"));
		buttonExportHTML.setToolTipText("Export to HTML");
		buttonExportHTML.setMnemonic('H');  // shortcut is alt+H
		buttonExportHTML.addActionListener(
				event -> JOptionPane.showMessageDialog(null, ("HTML"))
		);
		toolBar.add(buttonExportHTML);
		// button export to text
		JButton buttonExportText = new JButton(new ImageIcon("imgs/txt.png"));
		buttonExportText.setToolTipText("Export to text");
		buttonExportText.setMnemonic('T');  // shortcut is alt+T
		buttonExportText.addActionListener(
				event -> JOptionPane.showMessageDialog(null, ("Text"))
		);
		toolBar.add(buttonExportText);
		// return the toolbar
		return toolBar;
	}

	public IntrospectorTree(String title, TreeModel model, int width, int height) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);

		this.add(this.createToolBar(), BorderLayout.NORTH);

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

		Image icon = Toolkit.getDefaultToolkit().getImage("imgs/tree.png");
		this.setIconImage(icon);

		tree.addTreeSelectionListener(this);

		this.setVisible(true);
	}

	/**
	 * When the selected node is changed, its description is shown in the text area and its dynamic type in
	 * the label.
	 * @param event the event that characterizes the change.
	 */
	@Override
	public void valueChanged(TreeSelectionEvent event) {
		TreePath path = tree.getSelectionPath();
		if (path != null) {
			Node node = (Node) path.getLastPathComponent();
			textArea.setText(node.getNodeDescription());
			labelClass.setText(node.getClassName());
		}
		else { // No node has been selected
			textArea.setText("");
			labelClass.setText("");
		}
	}

	/**
	 * @return the JTree in the view
	 */
	JTree getTree() {
		return this.tree;
	}

}

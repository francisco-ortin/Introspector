/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.view;

import introspector.model.NodeAdapter;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


/**
 * This is the window view to visualize the program as a tree.
 * The model representing the tree should be passed to the constructor as a parameter.
 */
public class IntrospectorTree extends JFrame implements TreeSelectionListener {

	private static final long serialVersionUID = -5808696754140959887L;

	private JTextArea textArea;

	private JLabel labelClass;

	private JTree tree;

	/**
	 * In pixels, the default size of the window.
	 */
	private static final int DEFAULT_WINDOW_WIDTH = 800, DEFAULT_WINDOW_HEIGHT = 400;

	public IntrospectorTree(String title, TreeModel model) {
		this(title, model, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
	}

	public IntrospectorTree(String title, TreeModel model, int width, int height) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);

		tree = new JTree(model);
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);

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

	public void valueChanged(TreeSelectionEvent event) {
		TreePath path = tree.getSelectionPath();
		if (path != null) {
			NodeAdapter node = (NodeAdapter) path.getLastPathComponent();
			textArea.setText(node.getNodeDescription());
			labelClass.setText(node.getClassName());
		}
		else { // No node has been selected
			textArea.setText("");
			labelClass.setText("");
		}
	}

}

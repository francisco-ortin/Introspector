package introspector.view;

import introspector.model.NodeAdapter;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


public class IntrospectorTree extends JFrame implements TreeSelectionListener {

	private static final long serialVersionUID = -5808696754140959887L;

	private JTextArea textArea;

	private JLabel labelClase;

	private JTree tree;

	public IntrospectorTree(String title, TreeModel model) {
		JFrame f = new JFrame(title);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tree = new JTree(model);
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panel.setDividerSize(2);

		panel.add(new JScrollPane(tree));

		JPanel panelDcha = new JPanel();
		panelDcha.setLayout(new BorderLayout());
		panelDcha.add("North", labelClase = new JLabel(""));
		panelDcha.add("Center", new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER  ));

		panel.add(panelDcha);

		Container content = f.getContentPane();
		content.add(panel, "Center");

		tree.addTreeSelectionListener(this);

		f.setSize(400, 200);
		f.setVisible(true);
	}

	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = tree.getSelectionPath();
		if (path != null) {
			NodeAdapter nodo = (NodeAdapter) path.getLastPathComponent();
			textArea.setText(nodo.getNodeDescription());
			labelClase.setText(nodo.getClassName());
		}
		else {
			textArea.setText("");
			labelClase.setText("");
		}
	}

}

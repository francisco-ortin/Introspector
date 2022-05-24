/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class IntrospectorModel implements TreeModel {
	
	private final Node root;
	
	  public IntrospectorModel(String name,Object root) {
		  this.root = NodeFactory.createNode(name,root,root.getClass());
		  }

	public Object getRoot() {
		return root;
	}

	public Object getChild(Object parent, int index) {
		Node node=(Node)parent;
		return node.getChild(index);
	}

	public int getChildCount(Object parent) {
		Node node=(Node)parent;
		return node.getChildrenCount();
	}

	public boolean isLeaf(Object object) {
		Node node=(Node)object;
		return node.isLeaf();
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		  // nothing to do when the value for path changes
	}

	public int getIndexOfChild(Object parent, Object child) {
		Node node=(Node)parent;
		return node.getIndexOfChild(child);
	}

	public void addTreeModelListener(TreeModelListener l) {
		// Nothing to do when a tree model listener is added
	}

	public void removeTreeModelListener(TreeModelListener l) {
		// Nothing to do when a tree model listener is removed
	}

}

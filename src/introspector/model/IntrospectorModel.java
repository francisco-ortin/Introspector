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
	
	private NodeAdapter root;
	
	  public IntrospectorModel(String name,Object root) {
		  this.root=new NodeAdapter(Node.buildNode(name,root,root.getClass()));
		  }

	public Object getRoot() {
		return root;
	}

	public Object getChild(Object parent, int index) {
		NodeAdapter nodo=(NodeAdapter)parent;
		return nodo.getChild(index);
	}

	public int getChildCount(Object parent) {
		NodeAdapter nodo=(NodeAdapter)parent;
		return nodo.getChildrenCount();
	}

	public boolean isLeaf(Object node) {
		NodeAdapter nodo=(NodeAdapter)node;
		return nodo.isLeaf();
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		}

	public int getIndexOfChild(Object parent, Object child) {
		NodeAdapter nodo=(NodeAdapter)parent;
		return nodo.getIndexOfChild(child);
	}

	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

}

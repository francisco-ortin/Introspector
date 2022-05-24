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

/**
 * Model object that represents a tree.
 * It implements the TreeModel to allow its visualization as a JTree.
 */
public class IntrospectorModel implements TreeModel {

	/**
	 * The root node of the tree.
	 */
	private final Node root;

	/**
	 * Constructs a tres given its name and the root object
	 * @param name The name used to visualize the tree.
	 * @param root The object that will be used as the root node of the tree.
	 */
	public IntrospectorModel(String name, Object root) {
		this.root = NodeFactory.createNode(name, root, root.getClass());
	}

	/**
	 * @return The root node of the tree.
	 */
	@Override
	public Object getRoot() {
		return root;
	}

	/**
	 * Returns the {@code index}-th child of the {@code parent} node.
	 * @param parent    a node in the tree, obtained from this data source
	 * @param index     index of child to be returned
	 * @return	the {@code index}-th child of the {@code parent} node.
	 */
	@Override
	public Object getChild(Object parent, int index) {
		Node node = (Node)parent;
		return node.getChild(index);
	}

	/**
	 * Returns the number of children of {@code parent} node.
	 * @param parent  a node in the tree, obtained from this data source
	 * @return	the number of children of {@code parent} node.
	 */
	@Override
	public int getChildCount(Object parent) {
		Node node = (Node) parent;
		return node.getChildrenCount();
	}

	/**
	 * Returns whether the parameter is a leaf node.
	 * @param object  a node in the tree, obtained from this data source
	 * @return  whether the parameter is a leaf node.
	 */
	@Override
	public boolean isLeaf(Object object) {
		Node node = (Node) object;
		return node.isLeaf();
	}

	/**
	 * Gets the index of the {@code child} node in {@code parent}.
	 * @param parent a node in the tree, obtained from this data source
	 * @param child the node we are interested in
	 * @return the index of the {@code child} node in {@code parent}.
	 */
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		Node node = (Node) parent;
		return node.getIndexOfChild(child);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// nothing to do when the value for path changes
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// Nothing to do when a tree model listener is added
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// Nothing to do when a tree model listener is removed
	}

}

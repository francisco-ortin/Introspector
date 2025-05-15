/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import introspector.model.traverse.SymmetricPair;
import introspector.model.traverse.TraverseHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * CollectionNode provides a Node implementation to represent any value whose type is a java.util.Collection.
 */
public class CollectionNode extends AbstractNode implements Node  {

	/**
	 * @see AbstractNode#AbstractNode(String, Object)
	 */
	public CollectionNode(String name, Object value) {
		super(name, value);
	}

	/**
	 * @see AbstractNode#AbstractNode(String, Object, Class)
	 */
	public CollectionNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	/**
	 * A collection node is not leaf.
	 *
	 * @see AbstractNode#isLeaf()
	 */
	public boolean isLeaf() {
		return false;
	}

	/**
	 * Collection node has as many children as elements in the collection.
	 * A node is created for each child.
	 *
	 * @see AbstractNode#getChildren()
	 */
	@Override
	public List<Node> getChildren() {
		List<Node> nodes = new ArrayList<>();
		if (this.getValue() == null)
			return nodes;
		Collection<?> fields = (Collection<?>) this.getValue();
		int index = 0;
		for (Object field : fields) {
			if (field == null) {
				//System.err.printf("Introspector: the collection \"%s\" has a null reference in its item number %d.\n", getName(), index);
				nodes.add(NodeFactory.createNode(getName() + "[" + index++ + "]", null, null));
			} else
				nodes.add(NodeFactory.createNode(getName() + "[" + index++ + "]", field, field.getClass()));
		}
		return nodes;
	}

	/**
	 * @see Node#compareTrees(Node, boolean, Set, Set)
	 */
	@Override
	public Set<Node> compareTrees(Node node2, boolean equalName, Set<Node> modifiedNodes, Set<SymmetricPair<Node, Node>> alreadyTraversed) {
		if (!TraverseHelper.shouldBeTraversed(new SymmetricPair<>(this, node2), alreadyTraversed))
			return modifiedNodes; // cycle detected
		if (node2 instanceof CollectionNode mapNode2) {
			// they must have the same types
			if (!this.getType().equals(mapNode2.getType())) {
				modifiedNodes.add(this);
				modifiedNodes.add(mapNode2);
				return modifiedNodes;
			}
			// if they are not the root nodes, they must have the same names
			if (equalName && !this.getName().equals(mapNode2.getName())) {
				modifiedNodes.add(this);
				modifiedNodes.add(mapNode2);
				return modifiedNodes;
			}
			List<Node> children1 = this.getChildren();
			List<Node> children2 = mapNode2.getChildren();
			// they may not have the same number of children
			if (children1.size() != children2.size()) {
				modifiedNodes.add(this);
				modifiedNodes.add(mapNode2);
			}
			int minChildrenCount = Math.min(children1.size(), children2.size());
			for (int i = 0; i < minChildrenCount; i++)
				children1.get(i).compareTrees(children2.get(i), equalName, modifiedNodes, alreadyTraversed);
			TraverseHelper.addNewChildren(children1, children2, modifiedNodes);
			return modifiedNodes;
		}
		// node2 is not a Collection => they are different
		modifiedNodes.add(this);
		modifiedNodes.add(node2);
		return modifiedNodes;
	}

}

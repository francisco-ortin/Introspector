/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import introspector.model.traverse.SymmetricPair;
import introspector.model.traverse.TraverseHelper;

import java.util.*;

/**
 * MapNode provides a Node implementation to represent any value whose type is a java.util.Map.
 */
public class MapNode extends AbstractNode  implements Node {

	/**
	 * @see AbstractNode#AbstractNode(String, Object)
	 */
	public MapNode(String name, Object value) {
		super(name, value);
	}

	/**
	 * @see AbstractNode#AbstractNode(String, Object, Class)
	 */
	public MapNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}


	/**
	 * A map node is not leaf.
	 *
	 * @see AbstractNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		return false;
	}

	/**
	 * Collection node has as many children and elements in the collection.
	 * A node is created for each child, representing the value of each map entry.
	 * For the key, its toString representation is shown, but it is not expanded.
	 *
	 * @see AbstractNode#getChildren()
	 */
	@Override
	public List<Node> getChildren() {
		List<Node> nodes = new ArrayList<>();
		if (this.getValue() == null)
			return nodes;
		// maps are not ordered, so we must define an order to show the map entries and improve its comparison
		// however, we cannot use the natural order of the keys if one is null
		Map<?, ?> sortedMap;
		// does any key hold a null value?
		if (TraverseHelper.containsNullKey((Map<?, ?>) getValue()))
			sortedMap = (Map<?, ?>)getValue();  // we cannot sort if it has null in the keys
		else try {
			sortedMap = new TreeMap<>((Map<?, ?>) getValue()); // otherwise, we sort by the natural order of the keys
		} catch (ClassCastException e) {
			sortedMap = (Map<?, ?>) getValue();  // we cannot sort if the keys are not comparable (e.g., one is Integer and another is String)
		}
		for (Map.Entry<?, ?> entry : sortedMap.entrySet()) // we traverse with in the order of the natural order of the keys
			if (entry.getKey() == null) {
				//System.err.printf("Introspector: the map \"%s\" has a null key.\n", getName());
				if (entry.getValue() != null)
					nodes.add(NodeFactory.createNode(
							getName() + "[" + null + "]", entry.getValue(), entry.getValue().getClass()));
				else
					nodes.add(NodeFactory.createNode(getName() + "[" + null + "]", null, null));
			} else if (entry.getValue() == null) {
				//System.err.printf("Introspector: the map \"%s\" has a null value for the key \"%s\".\n", getName(), entry.getKey());
				nodes.add(NodeFactory.createNode(
						getName() + "[" + entry.getKey().toString() + "]", null, null));
			} else
				nodes.add(NodeFactory.createNode(
						getName() + "[" + entry.getKey().toString() + "]",
						entry.getValue(), entry.getValue().getClass()
				));
		return nodes;
	}

	/**
	 * @see Node#compareTrees(Node, boolean, Set, Set)
	 */
	@Override
	public Set<Node> compareTrees(Node node2, boolean equalName, Set<Node> modifiedNodes, Set<SymmetricPair<Node, Node>> alreadyTraversed) {
		if (!TraverseHelper.shouldBeTraversed(new SymmetricPair<>(this, node2), alreadyTraversed))
			return modifiedNodes; // cycle detected
		if (node2 instanceof MapNode mapNode2) {
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
			// they may have not the same number of children
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
		// node2 is not a Map => they are different
		modifiedNodes.add(this);
		modifiedNodes.add(node2);
		return modifiedNodes;
	}


}

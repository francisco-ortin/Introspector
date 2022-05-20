/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * MapNode provides a Node implementation to represent any value whose type is a java.util.Map.
 */
public class MapNode extends Node {

	/**
	 * @see Node#Node(String, Object)
	 */
	public MapNode(String name, Object value) {
		super(name, value);
	}

	/**
	 * @see Node#Node(String, Object, Class)
	 */
	public MapNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	/**
	 * A map node is not leaf.
	 *
	 * @see introspector.model.Node#isLeaf()
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
	 * @see Node#getChildren()
	 */
	@Override
	public List<Node> getChildren() {
		List<Node> nodes = new ArrayList<>();
		for (Map.Entry<?, ?> entry : ((Map<?, ?>) getValue()).entrySet())
			if (entry.getKey() == null) {
				System.err.printf("Introspector: the map \"%s\" has a null key.\n", getName());
				if (entry.getValue() != null)
					nodes.add(buildNode(
							getName() + "[" + null + "]", entry.getValue(), entry.getValue().getClass()));
				else
					nodes.add(buildNode(getName() + "[" + null + "]", null, null));
			} else if (entry.getValue() == null) {
				System.err.printf("Introspector: the map \"%s\" has a null value for the key \"%s\".\n",
						getName(), entry.getKey());
				nodes.add(buildNode(
						getName() + "[" + entry.getKey().toString() + "]", null, null));
			} else
				nodes.add(buildNode(
						getName() + "[" + entry.getKey().toString() + "]",
						entry.getValue(), entry.getValue().getClass()
				));
		return nodes;
	}

}

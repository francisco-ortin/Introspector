/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
		Collection<?> fields = (Collection<?>) this.getValue();
		int index = 0;
		for (Object field : fields) {
			if (field == null) {
				System.err.printf("Introspector: the collection \"%s\" has a null reference in its item number %d.\n",
						getName(), index);
				nodes.add(NodeFactory.createNode(getName() + "[" + index++ + "]", null, null));
			} else
				nodes.add(NodeFactory.createNode(getName() + "[" + index++ + "]", field, field.getClass()));
		}
		return nodes;
	}

}

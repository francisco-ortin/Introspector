/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * ObjectNode provides a Node implementation to represent any object whose type is neither built-in
 * nor a collection. It has as many children as field references pointing to other objects.
 */
public class ObjectNode extends AbstractNode  implements Node {

	/**
	 * @see AbstractNode#AbstractNode(String, Object)
	 */
	public ObjectNode(String name, Object value){
		super(name, value);
	}

	/**
	 * @see AbstractNode#AbstractNode(String, Object, Class)
	 */
	public ObjectNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	/**
	 * Indicates if the isLeaf method has been previously called, and hence cached.
	 */
	private boolean hasBeenIsLeafCached = false;
	/**
	 * The cached value for isLeaf. Optimization to avoid computing isLeaf with reflection any time
	 * it is invoked;
	 */
	private boolean isLeafCache;

	/**
	 * An object is a leaf node when it has no fields
	 *
	 * @see AbstractNode#isLeaf()
	 */

	@Override
	public boolean isLeaf() {
		// returns the cache if it has been already computed
		if (this.hasBeenIsLeafCached)
			return this.isLeafCache;
		this.hasBeenIsLeafCached = true;
		// checks if there are fields in the given object
		Class<?> klass = this.getType();
		do {
			if (klass.getDeclaredFields().length > 0)
				return this.isLeafCache = false;
			klass = klass.getSuperclass(); // check in the superclasses
		} while (klass != null);
		return this.isLeafCache = true;
	}


	/**
	 * Indicates if the isChildren method has been previously called, and hence cached.
	 */
	private boolean hasBeenGetChildrenCached = false;
	/**
	 * The cached value for getChildren.
	 * Optimization to avoid computing getChildren with reflection any time it is invoked;
	 */
	private List<Node> getChildrenCache;


	/**
	 * An object has as many child nodes as fields.
	 *
	 * @see AbstractNode#getChildren()
	 */
	@Override
	public List<Node> getChildren() {
		// use the cache when necessary
		if (this.hasBeenGetChildrenCached)
			return this.getChildrenCache;
		this.hasBeenGetChildrenCached = true;
		// compute the children
		List<Node> nodes = new ArrayList<>();
		List<Field> fields = new ArrayList<>();
		Class<?> klass = this.getType();
		do {
			for (Field field : klass.getDeclaredFields()) {
				field.setAccessible(true);
				if (!fields.contains(field))
					fields.add(field);
			}
			klass = klass.getSuperclass();
		} while (klass != null);

		for (Field field : fields)
			try {
				String name = field.getName();
				if (nodes.stream().filter(node -> node.getName().equals(field.getName())).toArray().length >0)
					// the field name is repeated (inherited from a superclass)
					name += ":" + field.getDeclaringClass().getName();
				Object fieldValue = field.get(this.getValue());
				if (fieldValue == null)
					nodes.add(NodeFactory.createNode(name, "null", field.getType()));
				else {
					nodes.add(NodeFactory.createNode(name, fieldValue, fieldValue.getClass()));
				}
			} catch (Exception e) {
				System.err.println("Introspector: " + e);
			}
		return this.getChildrenCache = nodes;
	}

}


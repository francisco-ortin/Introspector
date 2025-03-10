/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import introspector.model.traverse.SymmetricPair;
import introspector.model.traverse.TraverseHelper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

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
			boolean hasIntelliJField = Arrays.stream(klass.getDeclaredFields())
					.anyMatch(field -> field.getName().equals(FIELD_NAME_ADDED_BY_INTELLIJ));
			int minNumberOfFields = hasIntelliJField ? 2 : 1;
			if (klass.getDeclaredFields().length >= minNumberOfFields)
				return this.isLeafCache = false;  // the object has children (without considering the one added by IntelliJ)
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

	public static final String FIELD_NAME_ADDED_BY_INTELLIJ = "__$lineHits$__";

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
		if (this.getValue() == null)
			return this.getChildrenCache = nodes; // no child when the object reference is null
		List<Field> fields = new ArrayList<>();
		Class<?> klass = this.getType();
		do {
			for (Field field : klass.getDeclaredFields()) {
				if (field.getName().equals(FIELD_NAME_ADDED_BY_INTELLIJ))
					// one field is added by IntelliJ to store the number of lines covered (should not be included)
					continue;
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
				if (this.getValue() == null)
					nodes.add(NodeFactory.createNode(name, null, field.getType()));
				else {
					Object fieldValue = field.get(this.getValue());
					nodes.add(NodeFactory.createNode(name, fieldValue, fieldValue==null ? field.getType() : fieldValue.getClass()));
				}
			} catch (Exception e) {
				System.err.println("Introspector: " + e);
				//e.printStackTrace(System.err);
			}
		return this.getChildrenCache = nodes;
	}


	/**
	 * @see Node#compareTrees(Node, boolean, Set, Set)
	 */
	@Override
	public Set<Node> compareTrees(Node node2, boolean equalName, Set<Node> modifiedNodes, Set<SymmetricPair<Node, Node>> alreadyTraversed) {
		if (!TraverseHelper.shouldBeTraversed(new SymmetricPair<>(this, node2), alreadyTraversed))
			return modifiedNodes; // cycle detected
		if (node2 instanceof ObjectNode objectNode2) {
			// they must have the same types
			if (!this.getType().equals(objectNode2.getType())) {
				modifiedNodes.add(this);
				modifiedNodes.add(objectNode2);
				return modifiedNodes;
			}
			if (this.getValue() == null && objectNode2.getValue() == null)
				return modifiedNodes;
			if (this.getValue() == null || objectNode2.getValue() == null) {
				// one of them is null but not the other (first condition above)
				modifiedNodes.add(this);
				modifiedNodes.add(objectNode2);
				return modifiedNodes;
			}
			// if they are not the root nodes, they must have the same names
			if (equalName && !this.getName().equals(objectNode2.getName())) {
				modifiedNodes.add(this);
				modifiedNodes.add(objectNode2);
				return modifiedNodes;
			}
			Map<String, Node> children1 = this.getChildren().stream().collect(Collectors.toMap(node -> node.getName(), node -> node));
			Map<String, Node> children2 = objectNode2.getChildren().stream().collect(Collectors.toMap(node -> node.getName(), node -> node));
			// children in children1 but not in children2
			Set<String> children1NotInChildren2 = new HashSet<>(children1.keySet());
			children1NotInChildren2.removeAll(children2.keySet());
			for (String childName : children1NotInChildren2)
				modifiedNodes.add(children1.get(childName));
			// children in children2 but not in children1
			Set<String> children2NotInChildren1 = new HashSet<>(children2.keySet());
			children2NotInChildren1.removeAll(children1.keySet());
			for (String childName : children2NotInChildren1)
				modifiedNodes.add(children2.get(childName));
			// children in both nodes (recursively compare them)
			for (String childName : children1.keySet())
				children1.get(childName).compareTrees(children2.get(childName), true, modifiedNodes, alreadyTraversed);
			return modifiedNodes;
		}
		// node2 is not an ObjectNode => they are different
		modifiedNodes.add(this);
		modifiedNodes.add(node2);
		return modifiedNodes;
	}

}


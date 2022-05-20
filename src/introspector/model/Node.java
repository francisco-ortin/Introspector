/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.lang.reflect.Field;
import java.util.*;

import javax.lang.model.type.NullType;

/**
 * The abstract Node class represents the nodes in the tree that models the structure of the program at runtime.
 * See the derived classes to find the appropriate one to instantiate.
 * The buildMethod is a factory to create the nodes.
 */
public abstract class Node {

	private final Object value;
	private final String name;
	private final Class<?> type;

	/**
	 * Creates a Node that wraps a Java Object
	 * @param name The name to display the node
	 * @param value The object that will be represented as a node
	 */
	protected Node(String name, Object value) {
		this(name, value, value.getClass());
	}

	/**
	 * Creates a Node that wraps a Java Object
	 * @param name The name to display the node
	 * @param value The object that will be represented as a node
	 * @param type The type of the object
	 */
	protected Node(String name, Object value, Class<?> type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	/**
	 * @return The name used to display the object represented as a node
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The type of the object represented as a node
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * @return The object represented as a node
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * To know if a node is a leaf node.
	 * @return Whether the node is a leaf node (no children) or not
	 */
	public abstract boolean isLeaf();

	/**
	 * Returns the child node of the current node.
	 * @return The children of this node (null if there are no children)
	 */
	public abstract List<Node> getChildren();

	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		sb.append(" (");
		sb.append(type.getSimpleName());
		sb.append(")");
		if (isBuiltinType(type))
			sb.append(": ").append(value);
		return sb.toString();
	}

	static <T> boolean isBuiltinType(Class<T> type) {
		return switch (type.getName()) {
			case "null", "boolean", "byte", "short", "char", "int", "long", "float", "double",
					"java.lang.Boolean", "java.lang.Byte", "java.lang.Short", "java.lang.Character",
					"java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.String",
					"javax.lang.model.type.NullType" -> true;
			// true if it is an enum, false otherwise
			default -> type.getSuperclass() != null && type.getSuperclass().getName().equals("java.lang.Enum");
		};
	}

	/**
	 * Factory to create the nodes
	 * @param name Name of the node
	 * @param value Runtime object that will be represented as a node
	 * @return The subclass of Node appropriate to represent the name object
	 */
	public static Node buildNode(String name, Object value) {
		return buildNode(name, value, value.getClass());
	}

	/**
	 * Factory to create the nodes
	 * @param name Name of the node
	 * @param value Runtime object that will be represented as a node
	 * @param type The object class that will represent the type of the object (value)
	 * @return The subclass of Node appropriate to represent the name object
	 */
	public static Node buildNode(String name, Object value, Class<?> type) {
		if (type == null)
			type = NullType.class;
		if (isBuiltinType(type))
			return new BuiltinTypeNode(name, value, type);
		// collections (lists, sets, queues and dequeues
		if (java.util.Collection.class.isAssignableFrom(type))
			return new CollectionNode(name, value, type);
		// maps
		if (java.util.Map.class.isAssignableFrom(type))
			return new MapNode(name, value, type);
		if (type.getName().charAt(0) == '[')
			return new ArrayNode(name, value, type);
		return new ObjectNode(name, value, type);
	}

	/**
	 * Two objects are equal when they have the same name
	 * @param obj To object to be compared with
	 * @return Whether the two objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node node)
			return this.name.equals(node.name);
		else
			return false;  // not the same type, different objects
	}

	/**
	 * @return The hash code of the name
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

}

/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import javax.lang.model.type.NullType;
import java.util.Collection;
import java.util.Map;

/**
 * NodeFactory provides a mechanism to create Node instances.
 */
public class NodeFactory {


	/**
	 * Method to know if a Class<T> is a built-in type.
	 * @param type The type to be checked
	 * @return Whether the type is built-in
	 */
	public static <T> boolean isBuiltinType(Class<T> type) {
		return switch (type.getName()) {
			case "null", "boolean", "byte", "short", "char", "int", "long", "float", "double",
					"java.lang.Boolean", "java.lang.Byte", "java.lang.Short", "java.lang.Character",
					"java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.String",
					"javax.lang.model.type.NullType" -> true;
			default -> false;
		};
	}

	/**
	 * Method to know if a Class<T> is an enum type.
	 * @param type The type to be checked
	 * @return Whether the type is an enum
	 */
	static <T> boolean isEnumType(Class<T> type) {
 		return type.getSuperclass() != null && type.getSuperclass().getName().equals("java.lang.Enum");
	}

	/**
	 * Factory to create the nodes
	 * @param name Name of the node
	 * @param value Runtime object that will be represented as a node
	 * @return The subclass of Node appropriate to represent the name object
	 */
	public static Node createNode(String name, Object value) {
		return createNode(name, value, value.getClass());
	}

	/**
	 * Factory to create the nodes
	 * @param name Name of the node
	 * @param value Runtime object that will be represented as a node
	 * @param type The object class that will represent the type of the object (value)
	 * @return The subclass of Node appropriate to represent the name object
	 */
	public static Node createNode(String name, Object value, Class<?> type) {
		if (type == null)
			type = NullType.class;
		if (isBuiltinType(type))
			return new BuiltinTypeNode(name, value, type);
		if (isEnumType(type))
			return new EnumNode(name, value, type);
		// collections (lists, sets, queues and dequeues
		if (Collection.class.isAssignableFrom(type))
			return new CollectionNode(name, value, type);
		// maps
		if (Map.class.isAssignableFrom(type))
			return new MapNode(name, value, type);
		if (type.getName().charAt(0) == '[')
			return new ArrayNode(name, value, type);
		return new ObjectNode(name, value, type);
	}

}

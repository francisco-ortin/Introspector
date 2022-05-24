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
public abstract class NodeFactory {


	public static <T> boolean isBuiltinType(Class<T> type) {
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

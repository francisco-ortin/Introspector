/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import javax.lang.model.type.NullType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * NodeFactory provides a mechanism to create Node instances.
 */
public class NodeFactory {


	/**
	 * Method to know if the {@code type} parameter is a built-in type.
	 * @param type The type to know whether it is built-in
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
	 * Method to know if a the {@code type} parameter is an enum type.
	 * @param type The type to know whether it is an enum
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
		// arrays
		if (type.getName().charAt(0) == '[')
			return new ArrayNode(name, value, type);
		if (type.getName().equals("java.util.Optional")) {
			if (value == null)
				return createNode(name, null, null);
			Object childValue = ((Optional<?>)value).isPresent() ? ((Optional<?>)value).get() : null;
			if (childValue == null)
				return createNode(name, null, null);
			return createNode(name, childValue, childValue.getClass());
		}
		return new ObjectNode(name, value, type);
	}


}

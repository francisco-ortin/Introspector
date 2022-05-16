package introspector.model;

import java.lang.reflect.Field;
import java.util.*;

import javax.lang.model.type.NullType;

public abstract class Node {

	private Object value;
	private String name;
	private Class<?> type;

	protected Node(Field field, Object implicitObject) throws IllegalAccessException {
		this(field.getName(), field.get(implicitObject), field.getType());
	}

	protected Node(String name, Object value, Class<?> type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public abstract boolean isLeaf();

	public abstract List<Node> getChildren();

	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		sb.append(" (");
		sb.append(type.getSimpleName());
		sb.append(")");
		if (builtinType(type))
			sb.append(": " + value);
		return sb.toString();
	}

	public static <T> boolean builtinType(Class<T> type) {
		String className = type.getName();
		if (className.equals("null")) return true;
		if (className.equals("boolean")) return true;
		if (className.equals("byte")) return true;
		if (className.equals("short")) return true;
		if (className.equals("char")) return true;
		if (className.equals("int")) return true;
		if (className.equals("long")) return true;
		if (className.equals("float")) return true;
		if (className.equals("double")) return true;
		if (className.equals("long double")) return true;
		if (className.equals("java.lang.Boolean")) return true;
		if (className.equals("java.lang.Byte")) return true;
		if (className.equals("java.lang.Short")) return true;
		if (className.equals("java.lang.Character")) return true;
		if (className.equals("java.lang.Integer")) return true;
		if (className.equals("java.lang.Long")) return true;
		if (className.equals("java.lang.Float")) return true;
		if (className.equals("java.lang.Double")) return true;
		if (className.equals("java.lang.String")) return true;
		if (className.equals("javax.lang.introspector.model.type.NullType")) return true;
		if (type.getSuperclass() != null && type.getSuperclass().getName().equals("java.lang.Enum")) return true;
		return false;
	}

	public static Node buildNode(String name, Object value, Class<?> type) {
		if (type == null) type = NullType.class;
		String className = type.getName();
		if (builtinType(type)) return new BuiltinTypeNode(name, value, type);
		if (className.equals("java.util.ArrayList")) return new CollectionNode(name, value, type);
		if (className.equals("java.util.LinkedList")) return new CollectionNode(name, value, type);
		if (className.equals("java.util.TreeSet")) return new CollectionNode(name, value, type);
		if (className.equals("java.util.HashSet")) return new CollectionNode(name, value, type);
		if (className.equals("java.util.Vector")) return new CollectionNode(name, value, type);
		if (className.equals("java.util.HashMap")) return new MapNode(name, value, type);
		if (className.equals("java.util.WeakHashMap")) return new MapNode(name, value, type);
		if (className.equals("java.util.TreeMap")) return new MapNode(name, value, type);
		if (className.equals("java.util.Hashtable")) return new MapNode(name, value, type);
		if (className.charAt(0) == '[') return new ArrayNode(name, value, type);
		return new ObjectNode(name, value, type);
	}

	@Override
	public boolean equals(Object obj) {
		Node node = (Node) obj;
		return this.name.equals(node.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}

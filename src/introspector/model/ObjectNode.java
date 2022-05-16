package introspector.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectNode extends Node {

	public ObjectNode(Field field, Object objImplicito)
			throws IllegalAccessException {
		super(field, objImplicito);
	}

	public ObjectNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public List<Node> getChildren() {
		List<Node> nodos = new ArrayList<Node>();
		List<Field> atributos = new ArrayList<Field>();
		Class<?> klass = this.getType();
		do {
			for (Field field : klass.getDeclaredFields()) {
				field.setAccessible(true);
				if (!atributos.contains(field))
					atributos.add(field);
			}
			klass = klass.getSuperclass();
		} while (klass != null);

		for (Field atributo : atributos)
			try {
				String name = atributo.getName();
				if (nodos.stream().filter(nodo -> nodo.getName().equals(atributo.getName())).toArray().length >0)
					name += ":" + atributo.getDeclaringClass().getName();
				Object objeto = atributo.get(this.getValue());
				if (objeto == null)
					nodos.add(buildNode(name, "null", atributo.getType()));
				else {
					nodos.add(buildNode(name, objeto, objeto.getClass()));
				}
			} catch (Exception e) {
				// System.err.println(e);
			}
		return nodos;
	}

}


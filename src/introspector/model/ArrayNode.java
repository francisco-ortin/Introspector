package introspector.model;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class ArrayNode extends Node {

	public ArrayNode(Field field, Object implicitObject) throws IllegalAccessException {
		super(field, implicitObject);
	}

	public ArrayNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}
	
	@Override
	public List<Node> getChildren() {
		List<Node> children=new ArrayList<Node>();
		int length=Array.getLength(getValue());
		for (int i=0;i<length;i++) {
			Object element=Array.get(getValue(),i);
			if (element == null) {
				System.err.printf("Introspector: the array \"%s\" has a null reference in its item number %d.\n", getName(), i);
				children.add(buildNode(getName()+"["+i+"]", null, null));
			}
			else
				children.add(buildNode(getName()+"["+i+"]", element, element.getClass()));
		}
		return children;
	}

}

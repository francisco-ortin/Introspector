package introspector.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionNode extends Node {

	public CollectionNode(Field field, Object implicitObject) throws IllegalAccessException {
		super(field, implicitObject);
	}

	public CollectionNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}
	
	public boolean isLeaf() {
		return false;
	}

	@Override
	public List<Node> getChildren() {
		List<Node> nodes=new ArrayList<Node>();
		Collection<?> fields = (Collection<?>)this.getValue();
		int i=0;
		for(Object field:fields) {
			if (field == null) {
				System.err.printf("Introspector: the collection \"%s\" has a null reference in its item number %d.\n", 
						getName(), i);
				nodes.add(buildNode(getName()+"["+i+++"]", null, null));
			}
			else
				nodes.add(buildNode(getName()+"["+i+++"]",field,field.getClass()));
		}
		return nodes;
	}

}

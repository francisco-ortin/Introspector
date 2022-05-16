package introspector.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class MapNode extends Node {

	public MapNode(Field field, Object implicitObject) throws IllegalAccessException {
		super(field, implicitObject);
	}

	public MapNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public List<Node> getChildren() {
		List<Node> nodes=new ArrayList<Node>();
		for (Map.Entry<?, ?> entry:((Map<?,?>)getValue()).entrySet())
			if (entry.getKey() == null) {
				System.err.printf("Introspector: the map \"%s\" has a null key.\n", getName());
				if (entry.getValue() != null) 
					nodes.add(buildNode(
						getName()+"["+ null +"]", entry.getValue(), entry.getValue().getClass()));
				else
					nodes.add(buildNode(getName()+"["+ null +"]", null, null));
			}
			else if (entry.getValue() == null) {
				System.err.printf("Introspector: the map \"%s\" has a null value for the key \"%s\".\n",
						getName(), entry.getKey());
				nodes.add(buildNode(
						getName()+"["+entry.getKey().toString()+"]", null, null));
			}
			else
				nodes.add(buildNode(
					getName()+"["+entry.getKey().toString()+"]" ,
					entry.getValue(), entry.getValue().getClass()
					));
		return nodes;
	}

}

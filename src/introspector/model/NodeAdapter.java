/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.util.ArrayList;
import java.util.List;

public class NodeAdapter {

	private Node node;
	
	private List<NodeAdapter> children;
	
	public NodeAdapter(Node node) {
		this.node=node;
		if (!node.isLeaf()) {
			List<Node> nodes=node.getChildren();
			children=new ArrayList<NodeAdapter>();
			for(Node n:nodes)  {
				if (n.getValue()==node.getValue())
					children.add(new NodeAdapter( 
							new ObjectNode(n.getName(), "<self reference>", Object.class)));
				else
					children.add(new NodeAdapter(n));
			}
		}
	}
	
	public Object getChild(int index) {
		return children.get(index);
	}

	public int getChildCount() {
		return children.size();
	}

	public boolean isLeaf() {
		return node.isLeaf();
	}

	public int getIndexOfChild(Object child) {
		return children.indexOf(child);
	}

	public String getNodeDescription() {
		if (node.getValue() == null)
			return "null";
		return node.getValue().toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		NodeAdapter nodo=(NodeAdapter)obj;
		return this.node.equals(nodo.node);
	}

	@Override
	public int hashCode() {
		return node.hashCode();
	}
	
	@Override
	public String toString() {
		return node.toString();
	}

	public String getClassName() {
		return node.getType().getName();
	}

	
}

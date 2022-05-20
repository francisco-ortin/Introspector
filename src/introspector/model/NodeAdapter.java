/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.util.ArrayList;
import java.util.List;

// TODO Mover la funcionalidad del NodeAdapter a Node para eliminar NodeAdapter (probar que funciona con CycleTest)

public class NodeAdapter {

	private Node node;

	private List<Node> children;

	public NodeAdapter(Node node) {
		this.node = node;
		if (node.getChildren() == null)
			this.children = new ArrayList<>();
		else
			this.children = new ArrayList<>(node.getChildren());
	}

	public Object getChild(int index) {
		// TODO A new NodeAdapter is created any time getChild is called (highly inefficient)
		// It must be implemented lazy, with a cache
		// It cannot be eager in the constructor, because it enters in a in an infinite loop for graphs
		// Al pasarlo a Node YA NO TIENE NINGÚN PROBLEMA PORQUE ESTE ¡YA ES LAZY! Y, ADEMÁS, CACHEA LA COLLECCIÓN DE HIJOS
		return new NodeAdapter(children.get(index));
	}

	public int getChildrenCount() {
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
		NodeAdapter nodo = (NodeAdapter) obj;
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

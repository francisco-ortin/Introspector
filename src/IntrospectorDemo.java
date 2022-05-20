/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;
import java.util.List;
import java.util.ArrayList;

// TODO Probar con records

public class IntrospectorDemo {
	
	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", new RootNode());
		new IntrospectorTree("Tree", model);
	}
	
}

enum Color {
	red, blue, green
}

class RootNode {
	private final Color color = Color.red;
	private final Node childNode = new Node("Child1");
	private List<String> stringChildren = new ArrayList<String>();
	private int integerChild;
	
	//private Color color = Color.red;
	
	RootNode() {
		int i;
		for(i=2; i<=10; i++)
				this.stringChildren.add("StrChild"+i);
		this.integerChild = i;
	}
	
	@Override
	public String toString() { return "Root node"; }
}

class Node {
	private String name;
	
	Node(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() { return this.name; }
}
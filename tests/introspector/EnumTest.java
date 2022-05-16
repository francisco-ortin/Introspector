package introspector;

import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;
import java.util.List;
import java.util.ArrayList;

public class EnumTest {

	static enum Color {
		red, blue, gree;
	}

	static class RootNode {
		private Color color = Color.red;
		private Node childNode = new Node("Child1");
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

	static class Node {
		private String name;

		Node(String name) {
			this.name = name;
		}

		@Override
		public String toString() { return this.name; }
	}

	public static void main(String... args) {
		IntrospectorModel model = new IntrospectorModel("Root", new RootNode());
		new IntrospectorTree("Tree", model);
	}
	
}


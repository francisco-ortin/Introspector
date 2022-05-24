/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector;

import introspector.model.Node;
import introspector.model.NodeFactory;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.*;

public class SimpleReflectionTest {

	public static void show(Field field, PrintStream out, Object implicitObject)
			throws Exception {
		StringBuilder sb = new StringBuilder(field.getName())
			.append(" (")
			.append(field.getType().getName())
			.append("): ")
			.append(field.get(implicitObject));
		out.println(sb);
	}

	private static String prefix(int level) {
		return "| ".repeat(Math.max(0, level));
	}

	public static void show(Node node, int n) {
		System.out.print(prefix(n));
		System.out.println(node.toString());
		if (!node.isLeaf()) {
			for (int i=0; i<node.getChildrenCount(); i++)
				show(node.getChild(i), n + 1);
		}
	}

	public static Object createTrees(int n) {
		Collection<Object> list = new ArrayList<>(), list2;
		Map<Object, Object> map;
		list.add("hi");
		list.add(334.34);
		list.add(3);
		switch (n) {
		case 1:
			return new int[] { 1, 2, 3 };
		case 2:
			return 1;
		case 3:
			return 45.67;
		case 4:
			return "hi";
		case 5:
			return new double[][] { { 1.0 }, { 1.0, 2.0 }, { 1.0, 2.0, 3.0 } };
		case 6:
			return list;
		case 7:
			return new LinkedList<>(list);
		case 8:
			list2 = new HashSet<>();
			list2.add(list);
			list2.add(3);
			return list2;
		case 9:
			list2 = new TreeSet<>();
			list2.add(34);
			list2.add(-13);
			list2.add(13);
			return list2;
		case 10:
			Vector<Object> vector = new Vector<>();
			vector.add(list);
			return vector;
		case 11:
			map = new HashMap<>();
			map.put("one", 1);
			map.put("two", list);
			map.put("three", 2.3);
			return map;
		case 12:
			map = new TreeMap<>();
			map.put("one", 1);
			map.put("-one", -11);
			map.put("zero", 0);
			return map;
		case 13:
			map = new WeakHashMap<>();
			map.put("one", 1);
			return map;
		case 14:
			map = new Hashtable<>();
			map.put("one", 1);
			map.put("list",list);
			return map;
		case 15:
			return TestIntrospector.createTree();
		default:
			assert false;
			return null;
		}
	}

	public static void main(String[] args)  throws NullPointerException {
		Object tree = createTrees(14);
		show(NodeFactory.createNode("tree", tree, tree.getClass()), 0);
		System.out.println();
	}

}

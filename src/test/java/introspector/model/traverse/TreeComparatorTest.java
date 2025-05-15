/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */
package introspector.model.traverse;
import javax.swing.tree.TreePath;

import introspector.model.NodeFactory;
import introspector.model.traverse.TreeComparator;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the TreeComparator class in the model.traverse package
 */
class TreeComparatorTest {

    private TreeComparator treeComparator = new TreeComparator();


    @Test
    void testBuiltinTypesEqual() {
        assertEmpty(treeComparator.compareTrees(createNode("root 1", 1), createNode("root 2", 1)));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", "1"), createNode("root 2", "1")));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", 1.1), createNode("root 2", 1.1)));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", '1'), createNode("root 2", '1')));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", true), createNode("root 2", true)));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", false), createNode("root 2", false)));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", null), createNode("root 2", null)));
    }

    @Test
    void testBuiltinTypesDistinct() {
        assertLength(treeComparator.compareTrees(createNode("root 1", null), createNode("root 2", 2)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", 1), createNode("root 2", null)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", 1), createNode("root 2", 2)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", 1.0), createNode("root 2", 2.0)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", 1.0), createNode("root 2", 1)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", 1), createNode("root 2", 1.0)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", "1"), createNode("root 2", "2")), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", "1"), createNode("root 2", 1)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", 1), createNode("root 2", "1")), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", '1'), createNode("root 2", '2')), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", '1'), createNode("root 2", 1)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", 1), createNode("root 2", '1')), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", true), createNode("root 2", false)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", true), createNode("root 2", 1)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", 1), createNode("root 2", false)), 2);
    }

    @Test
    void testCompareArrays() {
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new int[] {1, 2, 3}),
                createNode("root 2", new int[] {1, 2, 3})));
        assertLength(treeComparator.compareTrees(createNode("root 1", new double[] {1, 2, 3}),
                createNode("root 2", new int[] {1, 2, 3})), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new int[] {1, 2, 3, 4}),
                createNode("root 2", new int[] {1, 2, 3})), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new int[] {1, 3, 3}),
                createNode("root 2", new int[] {1, 2, 3})), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new int[] {1, 2, 3, 4}),
                createNode("root 2", "hello")), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", 1),
                createNode("root 2", new int[] {1, 2, 3, 4})), 2);
    }

    @Test
    void testCompareEnum() {
        assertEmpty(treeComparator.compareTrees(createNode("root 1", Color.BLUE), createNode("root 2", Color.BLUE)));
        assertLength(treeComparator.compareTrees(createNode("root 1", Color.BLUE), createNode("root 2", Color.RED)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", Color.BLUE), createNode("root 2", 1)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", "RED"), createNode("root 2", Color.RED)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", Color.BLUE), createNode("root 2", null)), 2);
    }

    @Test
    void testCompareOptional() {
        assertEmpty(treeComparator.compareTrees(createNode("root 1", Optional.of(1)), createNode("root 2", Optional.of(1))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", 1), createNode("root 2", Optional.of(1))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", Optional.of(1)), createNode("root 2", 1)));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", null), createNode("root 2", Optional.empty())));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", Optional.empty()), createNode("root 2", null)));
        assertLength(treeComparator.compareTrees(createNode("root 1", Optional.of(1)), createNode("root 2", Optional.empty())), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", Optional.empty()), createNode("root 2", Optional.of(1))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", Optional.of(1)), createNode("root 2", null)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", null), createNode("root 2", Optional.of(1))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", Optional.of(1)), createNode("root 2", Optional.of(1.0))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", Optional.of(1.0)), createNode("root 2", Optional.of(1))), 2);
    }

    @Test
    void testCompareCollectionRight() {
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new ArrayList<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new ArrayList<>(Arrays.asList(1, 2, 3)))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new ArrayList<>(Arrays.asList("1", "2", "3"))),
                createNode("root 2", new ArrayList<>(Arrays.asList("1", "2", "3")))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new LinkedList<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new LinkedList<>(Arrays.asList(1, 2, 3)))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new HashSet<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new HashSet<>(Arrays.asList(1, 2, 3)))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new TreeSet<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new TreeSet<>(Arrays.asList(1, 2, 3)))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new Vector<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new Vector<>(Arrays.asList(1, 2, 3)))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new PriorityQueue<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new PriorityQueue<>(Arrays.asList(1, 2, 3)))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new ArrayDeque<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new ArrayDeque<>(Arrays.asList(1, 2, 3)))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new HashSet<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new HashSet<>(Arrays.asList(3, 2, 1)))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new TreeSet<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new TreeSet<>(Arrays.asList(3, 2, 1)))));
    }

    @Test
    void testCompareCollectionWrong() {
        assertLength(treeComparator.compareTrees(createNode("root 1", new ArrayList<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new ArrayList<>(Arrays.asList(1, -1, 3)))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new ArrayList<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new ArrayList<>(Arrays.asList(1, 2)))), 3);
        assertLength(treeComparator.compareTrees(createNode("root 1", new ArrayList<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0)))), 6);
        assertLength(treeComparator.compareTrees(createNode("root 1", new ArrayList<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", new LinkedList<>(Arrays.asList(1, 2, 3)))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new ArrayList<>(Arrays.asList(1, 2, 3))),
                createNode("root 2", null)), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", null),
                createNode("root 2", new ArrayList<>(Arrays.asList(1, 3, 2)))), 2);
    }

    @Test
    void testCompareMapRight() {
        assertEmpty(treeComparator.compareTrees(createNode("root 1", Map.of("One", 1, "Two", 2, "Three", 3)),
                createNode("root 2", Map.of("One", 1, "Two", 2, "Three", 3))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", Map.of(1, "One", 2, "Two", 3, "Three")),
                createNode("root 2", Map.of(3, "Three", 2, "Two", 1, "One"))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", Map.of("One", 1, "Two", 2, "Three", 3)),
                createNode("root 2", Map.of("Three", 3, "Two", 2, "One", 1))));
        Map<String, Integer> map1 = new HashMap<>(); map1.put("One", null); map1.put("Two", 2); map1.put("Three", null);
        Map<String, Integer> map2 = new HashMap<>(); map2.put("Three", null); map2.put("Two", 2); map2.put("One", null);
        assertEmpty(treeComparator.compareTrees(createNode("root 1", map1), createNode("root 2", map2)));
    }

    @Test
    void testCompareMapWrong() {
        assertLength(treeComparator.compareTrees(createNode("root 1", Map.of("1-One", 1, "2-Two", 2, "3-Three", 3)),
                createNode("root 2", Map.of("1-One", 1, "2-Two", 2))), 3);
        assertLength(treeComparator.compareTrees(createNode("root 1", Map.of("One", 1, "Two", 2, "Three", 3)),
                createNode("root 2", Map.of(1, "One", 2, "Two", 3, "Three"))), 6);
        Map<Object, Object> map1 = new HashMap<>(); map1.put(1, "One"); map1.put("Two", "Two"); map1.put("Three", 3);
        Map<Object, Object> map2 = new HashMap<>(); map2.put("One", 1); map2.put(2, 2); map2.put(3, "Three");
        assertLength(treeComparator.compareTrees(createNode("root 1", map1), createNode("root 2", map2)), 6);
        Map<Integer, String> map3 = new HashMap<>(); map3.put(null, "One"); map3.put(2, "Two"); map3.put(null, "Three");
        Map<Integer, String> map4 = new HashMap<>(); map4.put(null, "Three"); map4.put(2, "Two"); map4.put(null, "One");
        assertLength(treeComparator.compareTrees(createNode("root 1", map3), createNode("root 2", map4)), 2);
    }

    @Test
    void testCompareObjectRight() {
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, null)),
                createNode("root 2", new Person("Alice", 20, null))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, "Hello")),
                createNode("root 2", new Person("Alice", 20, "Hello"))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, new Person("Bob", 30, null))),
                createNode("root 2", new Person("Alice", 20, new Person("Bob", 30, null)))));
        assertEmpty(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, new Person("Bob", 30, new int[] {1, 2, 3}))),
                createNode("root 2", new Person("Alice", 20, new Person("Bob", 30, new int[] {1, 2, 3})))));
    }

    @Test
    void testCompareObjectWrong() {
        assertLength(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, null)),
                createNode("root 2", new Person("Bob", 20, null))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, null)),
                createNode("root 2", new Person("Alice", 10, null))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, "Hello")),
                createNode("root 2", new Person("Alice", 20, null))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, "Hello")),
                createNode("root 2", new Person("Alice", 20, "bye"))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, "Hello")),
                createNode("root 2", new Person("Alice", 20, 33))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, new Person("Bob", 30, null))),
                createNode("root 2", new Person("Alice", 20, "Bob"))), 2);
        assertLength(treeComparator.compareTrees(createNode("root 1", new Person("Alice", 20, new Person("Bob", 30, null))),
                createNode("root 2", new Person("Alice", 20, new Person("Robert", 30, null)))), 2);
    }


    @Test
    void testCycles() {
        Person alice = new Person("Alice", 20, null);
        alice.setChild(alice);  // cycle: alice's child is alice
        assertEmpty(treeComparator.compareTrees(createNode("root 1",alice), createNode("root 2", alice)));
    }

    private TreePath createNode(String name, Object value) {
        return new TreePath(NodeFactory.createNode(name, value));
    }

    private void assertEmpty(Set<?> list) {
        assertTrue(list.isEmpty());
    }

    private void assertLength(Set<?> list, int length) {
        assertEquals(length, list.size());
    }

    enum Color {
        RED, GREEN, BLUE
    }

    class Person {
        String name;
        int age;
        Object child;
        Person(String name, int age, Object child) {
            this.name = name;
            this.age = age;
            this.child = child;
        }
        void setChild(Object child) {
            this.child = child;
        }
    }
}
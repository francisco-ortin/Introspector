/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import org.junit.jupiter.api.Test;

import javax.management.AttributeList;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the NodeFactory class in the model package
 */
class NodeFactoryTest {

    enum Color {
    }

    static class MyClass {
    }


    @Test
    void isBuiltinType() {
        assertAll(
                // builtin types
                ()-> assertTrue(NodeFactory.isBuiltinType(boolean.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(byte.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(short.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(int.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(long.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(float.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(double.class)),
                // wrapper builtin types
                ()-> assertTrue(NodeFactory.isBuiltinType(Boolean.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(Byte.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(Short.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(Character.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(Integer.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(Long.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(Float.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(Double.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(String.class)),
                ()-> assertTrue(NodeFactory.isBuiltinType(javax.lang.model.type.NullType.class)),
                // non-builtin types
                ()-> assertFalse(NodeFactory.isBuiltinType(Collection.class)),
                ()-> assertFalse(NodeFactory.isBuiltinType(List.class)),
                ()-> assertFalse(NodeFactory.isBuiltinType(Map.class)),
                ()-> assertFalse(NodeFactory.isBuiltinType(AbstractNode.class))
        );
    }
    @Test
    void isEnumType() {
        assertAll(
                ()-> assertTrue(NodeFactory.isEnumType(Color.class)),
                ()-> assertFalse(NodeFactory.isEnumType(byte.class)),
                ()-> assertFalse(NodeFactory.isEnumType(String.class))
        );
    }

    @Test
    void buildNode() {
        // overloaded method with two params
        Node node = NodeFactory.createNode("name", 3);
        assertEquals("name", node.getName());
        assertEquals(3, node.getValue());
        assertEquals(Integer.class, node.getType());
        assertEquals(BuiltinTypeNode.class, node.getClass());
        // overloaded method with three params
        node = NodeFactory.createNode("a", 33, int.class);
        assertEquals("a", node.getName());
        assertEquals(33, node.getValue());
        assertEquals(int.class, node.getType());
        assertEquals(BuiltinTypeNode.class, node.getClass());
        // collections
        Object value = new ArrayList<Integer>();
        node = NodeFactory.createNode("col", value);
        assertEquals("col", node.getName());
        assertEquals(value, node.getValue());
        assertEquals(ArrayList.class, node.getType());
        assertEquals(CollectionNode.class, node.getClass());
        // maps
        value = new TreeMap<String, Integer>();
        node = NodeFactory.createNode("map", value);
        assertEquals("map", node.getName());
        assertEquals(value, node.getValue());
        assertEquals(TreeMap.class, node.getType());
        assertEquals(MapNode.class, node.getClass());
        // arrays
        value = new double[] {1.1, 2.2, 3.3};
        node = NodeFactory.createNode("array", value);
        assertEquals("array", node.getName());
        assertEquals(value, node.getValue());
        assertEquals(value.getClass(), node.getType());
        assertEquals(ArrayNode.class, node.getClass());
        // objects
        value = new MyClass();
        node = NodeFactory.createNode("myObject", value);
        assertEquals("myObject", node.getName());
        assertEquals(value, node.getValue());
        assertEquals(MyClass.class, node.getType());
        assertEquals(ObjectNode.class, node.getClass());
    }

    @Test
    void buildNodeLists() {
        assertAll(
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new ArrayList<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new AttributeList()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new CopyOnWriteArrayList<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new LinkedList<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new Stack<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new Vector<>()).getClass())
        );
    }

    @Test
    void buildNodeSets() {
        assertAll(
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new ConcurrentSkipListSet<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new CopyOnWriteArraySet<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new LinkedHashSet<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new HashSet<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new TreeSet<>()).getClass())
        );
    }

    @Test
    void buildNodeDeque() {
        assertAll(
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new ArrayDeque<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new ConcurrentLinkedDeque<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new LinkedBlockingDeque<>()).getClass())
        );
    }

    @Test
    void buildNodeQueue() {
        assertAll(
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new ArrayBlockingQueue<>(10)).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new ConcurrentLinkedQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new LinkedBlockingQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new LinkedTransferQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new PriorityBlockingQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new PriorityQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new SynchronousQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, NodeFactory.createNode(null, new DelayQueue<>()).getClass())
        );
    }

    @Test
    void buildNodeMaps() {
        assertAll(
                () -> assertEquals(MapNode.class, NodeFactory.createNode(null, new ConcurrentHashMap<>()).getClass()),
                () -> assertEquals(MapNode.class, NodeFactory.createNode(null, new ConcurrentSkipListMap<>()).getClass()),
                () -> assertEquals(MapNode.class, NodeFactory.createNode(null, new HashMap<>()).getClass()),
                () -> assertEquals(MapNode.class, NodeFactory.createNode(null, new Hashtable<>()).getClass()),
                () -> assertEquals(MapNode.class, NodeFactory.createNode(null, new LinkedHashMap<>()).getClass()),
                () -> assertEquals(MapNode.class, NodeFactory.createNode(null, new TreeMap<>()).getClass()),
                () -> assertEquals(MapNode.class, NodeFactory.createNode(null, new WeakHashMap<>()).getClass()),
                () -> assertEquals(MapNode.class, NodeFactory.createNode(null, new IdentityHashMap<>()).getClass())
        );
    }


}
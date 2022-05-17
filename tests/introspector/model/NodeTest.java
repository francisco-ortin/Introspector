/**
 * Tests for the Node class in the model package
 * @author Francisco Ortin
 */

package introspector.model;

import org.junit.jupiter.api.Test;

import javax.management.AttributeList;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    enum Color {
    }

    static class MyClass {
    }


    @Test
    void isBuiltinType() {
        assertAll(
                // builtin types
                ()-> assertTrue(Node.isBuiltinType(boolean.class)),
                ()-> assertTrue(Node.isBuiltinType(byte.class)),
                ()-> assertTrue(Node.isBuiltinType(short.class)),
                ()-> assertTrue(Node.isBuiltinType(int.class)),
                ()-> assertTrue(Node.isBuiltinType(long.class)),
                ()-> assertTrue(Node.isBuiltinType(float.class)),
                ()-> assertTrue(Node.isBuiltinType(double.class)),
                // wrapper builtin types
                ()-> assertTrue(Node.isBuiltinType(Boolean.class)),
                ()-> assertTrue(Node.isBuiltinType(Byte.class)),
                ()-> assertTrue(Node.isBuiltinType(Short.class)),
                ()-> assertTrue(Node.isBuiltinType(Character.class)),
                ()-> assertTrue(Node.isBuiltinType(Integer.class)),
                ()-> assertTrue(Node.isBuiltinType(Long.class)),
                ()-> assertTrue(Node.isBuiltinType(Float.class)),
                ()-> assertTrue(Node.isBuiltinType(Double.class)),
                ()-> assertTrue(Node.isBuiltinType(String.class)),
                ()-> assertTrue(Node.isBuiltinType(javax.lang.model.type.NullType.class)),
                // enums
                ()-> assertTrue(Node.isBuiltinType(Color.class)),
                // non-builtin types
                ()-> assertFalse(Node.isBuiltinType(java.util.Collection.class)),
                ()-> assertFalse(Node.isBuiltinType(java.util.List.class)),
                ()-> assertFalse(Node.isBuiltinType(java.util.Map.class)),
                ()-> assertFalse(Node.isBuiltinType(Node.class))
        );
    }

    @Test
    void buildNode() {
        // overloaded method with two params
        Node node = Node.buildNode("name", 3);
        assertEquals("name", node.getName());
        assertEquals(3, node.getValue());
        assertEquals(Integer.class, node.getType());
        assertEquals(BuiltinTypeNode.class, node.getClass());
        // overloaded method with three params
        node = Node.buildNode("a", 33, int.class);
        assertEquals("a", node.getName());
        assertEquals(33, node.getValue());
        assertEquals(int.class, node.getType());
        assertEquals(BuiltinTypeNode.class, node.getClass());
        // collections
        Object value = new ArrayList<Integer>();
        node = Node.buildNode("col", value);
        assertEquals("col", node.getName());
        assertEquals(value, node.getValue());
        assertEquals(ArrayList.class, node.getType());
        assertEquals(CollectionNode.class, node.getClass());
        // maps
        value = new TreeMap<String, Integer>();
        node = Node.buildNode("map", value);
        assertEquals("map", node.getName());
        assertEquals(value, node.getValue());
        assertEquals(TreeMap.class, node.getType());
        assertEquals(MapNode.class, node.getClass());
        // arrays
        value = new double[] {1.1, 2.2, 3.3};
        node = Node.buildNode("array", value);
        assertEquals("array", node.getName());
        assertEquals(value, node.getValue());
        assertEquals(value.getClass(), node.getType());
        assertEquals(ArrayNode.class, node.getClass());
        // objects
        value = new MyClass();
        node = Node.buildNode("myObject", value);
        assertEquals("myObject", node.getName());
        assertEquals(value, node.getValue());
        assertEquals(MyClass.class, node.getType());
        assertEquals(ObjectNode.class, node.getClass());
    }

    @Test
    void buildNodeLists() {
        assertAll(
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new ArrayList<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new AttributeList()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new CopyOnWriteArrayList<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new LinkedList<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new Stack<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new Vector<>()).getClass())
        );
    }

    @Test
    void buildNodeSets() {
        assertAll(
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new ConcurrentSkipListSet<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new CopyOnWriteArraySet<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new LinkedHashSet<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new HashSet<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new TreeSet<>()).getClass())
        );
    }

    @Test
    void buildNodeDeque() {
        assertAll(
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new ArrayDeque<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new ConcurrentLinkedDeque<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new LinkedBlockingDeque<>()).getClass())
        );
    }

    @Test
    void buildNodeQueue() {
        assertAll(
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new ArrayBlockingQueue<>(10)).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new ConcurrentLinkedQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new LinkedBlockingQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new LinkedTransferQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new PriorityBlockingQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new PriorityQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new SynchronousQueue<>()).getClass()),
                () -> assertEquals(CollectionNode.class, Node.buildNode(null, new DelayQueue<>()).getClass())
        );
    }

    @Test
    void buildNodeMaps() {
        assertAll(
                () -> assertEquals(MapNode.class, Node.buildNode(null, new ConcurrentHashMap<>()).getClass()),
                () -> assertEquals(MapNode.class, Node.buildNode(null, new ConcurrentSkipListMap<>()).getClass()),
                () -> assertEquals(MapNode.class, Node.buildNode(null, new HashMap<>()).getClass()),
                () -> assertEquals(MapNode.class, Node.buildNode(null, new Hashtable<>()).getClass()),
                () -> assertEquals(MapNode.class, Node.buildNode(null, new LinkedHashMap<>()).getClass()),
                () -> assertEquals(MapNode.class, Node.buildNode(null, new TreeMap<>()).getClass()),
                () -> assertEquals(MapNode.class, Node.buildNode(null, new WeakHashMap<>()).getClass()),
                () -> assertEquals(MapNode.class, Node.buildNode(null, new IdentityHashMap<>()).getClass())
        );
    }


}
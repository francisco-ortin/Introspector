/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */
package introspector.model.traverse;

import introspector.model.Node;
import introspector.model.NodeFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the WriteTreeTraversal class in the model package
 */
class WriteTreeTraversalTest {


    @Test
    void traverseInteger() throws IOException {
        TreeSerializerMock serializer = new TreeSerializerMock();
        WriteTreeTraversal walker = new WriteTreeTraversal();
        walker.traverse(NodeFactory.createNode("basic", 33), serializer);
        assertEquals(1, serializer.beginTraverseCount);
        assertEquals(1, serializer.endTraverseCount);
        assertEquals(1, serializer.beforeTraversingList.size());
        assertEquals(1, serializer.traversingList.size());
        assertEquals(1, serializer.afterTraversingList.size());
        assertEquals(serializer.beforeTraversingList.get(0), serializer.traversingList.get(0));
        assertEquals(serializer.afterTraversingList.get(0), serializer.traversingList.get(0));
        TreeSerializerMock.TraverseDTO dto = serializer.traversingList.get(0);
        assertEquals(33, dto.node().getValue());
        assertEquals("basic", dto.node().getName());
        assertEquals(Integer.class, dto.node().getType());
        assertEquals(0, dto.depth());
        assertFalse(dto.hasBeenVisited());
    }

    @Test
    void traverseString() throws IOException {
        TreeSerializerMock serializer = new TreeSerializerMock();
        WriteTreeTraversal walker = new WriteTreeTraversal();
        walker.traverse(NodeFactory.createNode("basic", "myObject"), serializer);
        assertEquals(1, serializer.beginTraverseCount);
        assertEquals(1, serializer.endTraverseCount);
        assertEquals(1, serializer.beforeTraversingList.size());
        assertEquals(1, serializer.traversingList.size());
        assertEquals(1, serializer.afterTraversingList.size());
        assertEquals(serializer.beforeTraversingList.get(0), serializer.traversingList.get(0));
        assertEquals(serializer.afterTraversingList.get(0), serializer.traversingList.get(0));
        TreeSerializerMock.TraverseDTO dto = serializer.traversingList.get(0);
        assertEquals("myObject", dto.node().getValue());
        assertEquals("basic", dto.node().getName());
        assertEquals(String.class, dto.node().getType());
        assertEquals(0, dto.depth());
        assertFalse(dto.hasBeenVisited());
    }

    private static class DummyA {
        int intField = 33;
        String stringField = "hi";
        DummyB dummyBField = new DummyB();
    }
    private static class DummyB {
        String stringField = "bye";
        DummyA dummyAField = null;
    }

    @Test
    void traverseObject() throws IOException {
        TreeSerializerMock serializer = new TreeSerializerMock();
        WriteTreeTraversal walker = new WriteTreeTraversal();
        walker.traverse(NodeFactory.createNode("object", new DummyA()), serializer);
        assertEquals(1, serializer.beginTraverseCount);
        assertEquals(1, serializer.endTraverseCount);
        assertEquals(6, serializer.beforeTraversingList.size());
        assertEquals(6, serializer.traversingList.size());
        assertEquals(6, serializer.afterTraversingList.size());
        for (int i = 0; i < serializer.traversingList.size(); i++) {
            assertEquals(serializer.beforeTraversingList.get(i), serializer.traversingList.get(i));
        }
        assertTrue(serializer.afterTraversingList.containsAll(serializer.traversingList));
        // first
        TreeSerializerMock.TraverseDTO dto = serializer.traversingList.get(0);
        assertEquals("object", dto.node().getName());
        assertEquals(DummyA.class, dto.node().getType());
        assertEquals(0, dto.depth());
        assertFalse(dto.hasBeenVisited());
        // second
        dto = serializer.traversingList.get(1);
        assertEquals("intField", dto.node().getName());
        assertEquals(33, dto.node().getValue());
        assertEquals(Integer.class, dto.node().getType());
        assertEquals(1, dto.depth());
        assertFalse(dto.hasBeenVisited());
        // third
        dto = serializer.traversingList.get(2);
        assertEquals("stringField", dto.node().getName());
        assertEquals("hi", dto.node().getValue());
        assertEquals(String.class, dto.node().getType());
        assertEquals(1, dto.depth());
        assertFalse(dto.hasBeenVisited());
        // forth
        dto = serializer.traversingList.get(3);
        assertEquals("dummyBField", dto.node().getName());
        assertEquals(DummyB.class, dto.node().getType());
        assertEquals(1, dto.depth());
        assertFalse(dto.hasBeenVisited());
        // fifth
        dto = serializer.traversingList.get(4);
        assertEquals("stringField", dto.node().getName());
        assertEquals("bye", dto.node().getValue());
        assertEquals(String.class, dto.node().getType());
        assertEquals(2, dto.depth());
        assertFalse(dto.hasBeenVisited());
        // sixth
        dto = serializer.traversingList.get(5);
        assertEquals("dummyAField", dto.node().getName());
        assertNull(dto.node().getValue());
        assertEquals(DummyA.class, dto.node().getType());
        assertEquals(2, dto.depth());
        assertFalse(dto.hasBeenVisited());

        walker.traverse(NodeFactory.createNode("object", new DummyA()), new ConsoleTreeSerializer());
    }

    @Test
    void traverseObjectWithCycle() throws IOException {
        TreeSerializerMock serializer = new TreeSerializerMock();
        WriteTreeTraversal walker = new WriteTreeTraversal();
        DummyA dummyObject = new DummyA();
        dummyObject.dummyBField.dummyAField = dummyObject; // cycle
        walker.traverse(NodeFactory.createNode("object", dummyObject), serializer);
        assertEquals(1, serializer.beginTraverseCount);
        assertEquals(1, serializer.endTraverseCount);
        assertEquals(6, serializer.beforeTraversingList.size());
        assertEquals(6, serializer.traversingList.size());
        assertEquals(6, serializer.afterTraversingList.size());
        for (int i = 0; i < serializer.traversingList.size(); i++) {
            assertEquals(serializer.beforeTraversingList.get(i), serializer.traversingList.get(i));
        }
        assertTrue(serializer.afterTraversingList.containsAll(serializer.traversingList));
        // cycle in the last object
        TreeSerializerMock.TraverseDTO dto = serializer.traversingList.get(5);
        assertEquals("dummyAField", dto.node().getName());
        assertNotNull(dto.node().getValue());
        assertEquals(DummyA.class, dto.node().getType());
        assertEquals(2, dto.depth());
        assertTrue(dto.hasBeenVisited());

        walker.traverse(NodeFactory.createNode("object", dummyObject), new ConsoleTreeSerializer());
    }

}
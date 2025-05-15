/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */
package introspector.model.traverse;

import introspector.model.Node;
import introspector.model.NodeFactory;
import introspector.model.traverse.TxtTreeSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the TxtSerializer class in the model package
 */
class TxtSerializerTest {


    @Test
    void traversingInt() throws IOException {
        Node node = NodeFactory.createNode("integer", 33);
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer);
        serializer.traversing(node, 0, false);
        assertEquals("integer (Integer): 33.\n", writer.toString());
    }

    @Test
    void traversingString() throws IOException {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer);
        serializer.traversing(node, 0, false);
        assertEquals("string (String): hi.\n", writer.toString());
    }

    @Test
    void traversingStringVisited() throws IOException {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer);
        serializer.traversing(node, 0, true);
        assertEquals("string (String) <revisited node>: hi.\n", writer.toString());
    }

    @Test
    void traversingStringDepth() throws IOException {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer);
        serializer.traversing(node, 2, false);
        assertEquals("string (String): hi.\n", writer.toString());
    }


    private static class DummyA {
        int intField = 33;
        String stringField = "hi";
        DummyB dummyBField = null;

        @Override
        public String toString() {
            return "DummyA object " + intField + " " + stringField + " " + dummyBField;
        }
    }
    private static class DummyB {}

    @Test
    void traversingObjectAllInfo() throws IOException {
        Node node = NodeFactory.createNode("dummy object", new DummyA());
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer);
        serializer.traversing(node, 0, false);
        assertEquals("dummy object (DummyA): DummyA object 33 hi null.\n", writer.toString());
    }

    @Test
    void traversingObjectShortInfo() throws IOException {
        Node node = NodeFactory.createNode("dummy object", new DummyA());
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer, false);
        serializer.traversing(node, 0, false);
        assertEquals("dummy object (DummyA).\n", writer.toString());
    }


    @Test
    void beginTraverse() {
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer);
        serializer.beginTraverse();
        assertEquals("", writer.toString());
    }

    @Test
    void endTraverse() throws IOException {
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer);
        serializer.endTraverse();
        assertEquals("\n", writer.toString());
    }

    @Test
    void beforeTraversing() throws IOException {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer);
        // depth = 0
        serializer.beforeTraversing(node, 0, false);
        assertEquals("", writer.toString());
        // depth = 2
        writer = new StringWriter();
        serializer = new TxtTreeSerializer(writer);
        serializer.beforeTraversing(node, 2, false);
        assertEquals("|  |- ", writer.toString());
    }

    @Test
    void afterTraversing()  {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TxtTreeSerializer serializer = new TxtTreeSerializer(writer);
        // depth = 0
        serializer.afterTraversing(node, 0, false);
        assertEquals("", writer.toString());
        // depth = 2
        serializer.afterTraversing(node, 2, false);
        assertEquals("", writer.toString());
    }

}
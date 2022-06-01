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
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the HtmlSerializer class in the model package
 */
class HtmlSerializerTest {

    @Test
    void traversingInt() throws IOException {
        Node node = NodeFactory.createNode("integer", 33);
        Writer writer = new StringWriter();
        TreeSerializer serializer = new HtmlTreeSerializer(writer);
        serializer.traversing(node, 0, false);
        assertEquals("<li>integer (Integer): 33.</li>\n", writer.toString());
    }

    @Test
    void traversingString() throws IOException {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TreeSerializer serializer = new HtmlTreeSerializer(writer);
        serializer.traversing(node, 0, false);
        assertEquals("<li>string (String): hi.</li>\n", writer.toString());
    }

    @Test
    void traversingStringVisited() throws IOException {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TreeSerializer serializer = new HtmlTreeSerializer(writer);
        serializer.traversing(node, 0, true);
        assertEquals("<li>string (String) &lt;cyclic reference&gt;: hi.</li>\n", writer.toString());
    }

    @Test
    void traversingStringDepth() throws IOException {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TreeSerializer serializer = new HtmlTreeSerializer(writer);
        serializer.traversing(node, 2, false);
        assertEquals("    <li>string (String): hi.</li>\n", writer.toString());
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
        TreeSerializer serializer = new HtmlTreeSerializer(writer);
        serializer.traversing(node, 0, false);
        assertEquals("""
                <li>
                <details>
                <summary>dummy object (DummyA): DummyA object 33 hi null.</summary>
                <ul>
                """, writer.toString());
    }

    @Test
    void traversingObjectShortInfo() throws IOException {
        Node node = NodeFactory.createNode("dummy object", new DummyA());
        Writer writer = new StringWriter();
        TreeSerializer serializer = new HtmlTreeSerializer(writer, false);
        serializer.traversing(node, 0, false);
        assertEquals("""
                <li>
                <details>
                <summary>dummy object (DummyA).</summary>
                <ul>
                """, writer.toString());
    }


    @Test
    void endTraverse() throws IOException {
        Writer writer = new StringWriter();
        HtmlTreeSerializer serializer = new HtmlTreeSerializer(writer);
        serializer.endTraverse();
        assertEquals("""
                </ul>
                </body>
                </html>
                """, writer.toString());
    }

    @Test
    void beforeTraversing() throws IOException {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TreeSerializer serializer = new HtmlTreeSerializer(writer);
        // depth = 0
        serializer.beforeTraversing(node, 0, false);
        assertEquals("", writer.toString());
        // depth = 2
        serializer.beforeTraversing(node, 2, false);
        assertEquals("", writer.toString());
    }

    @Test
    void afterTraversingLeaf() throws IOException {
        Node node = NodeFactory.createNode("string", "hi");
        Writer writer = new StringWriter();
        TreeSerializer serializer = new HtmlTreeSerializer(writer);
        // depth = 0
        serializer.beforeTraversing(node, 0, false);
        assertEquals("", writer.toString());
        // depth = 2
        serializer.beforeTraversing(node, 2, false);
        assertEquals("", writer.toString());
    }

    @Test
    void afterTraversingNonLeaf() throws IOException {
        DummyA dummyA = new DummyA();
        Node node = NodeFactory.createNode("dummy", dummyA);
        Writer writer = new StringWriter();
        TreeSerializer serializer = new HtmlTreeSerializer(writer);
        // depth = 0
        serializer.afterTraversing(node, 0, false);
        assertEquals("</ul>\n</details>\n</li>\n", writer.toString());
        // depth = 2
        writer = new StringWriter();
        serializer = new HtmlTreeSerializer(writer);
        serializer.afterTraversing(node, 2, false);
        assertEquals("    </ul>\n    </details>\n    </li>\n", writer.toString());
    }

}
/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.controller;

import introspector.model.IntrospectorModel;
import introspector.model.Node;
import introspector.view.IntrospectorView;
import org.junit.jupiter.api.Test;

import javax.swing.tree.TreePath;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This class tests the expandAll method in ExpandTree
 */
class ExpandTreeControllerTest {

    /**
     * Dummy classes for testing purposes.
     */
    static class DirectCycle {
        static DirectCycle field = new DirectCycle();
        private DirectCycle()  {} // private constructor
        private final String[] strings = new String[]{"one", "two", "three"};
    }

    static class NodeA {
        NodeB b;
    }

    static class NodeB {
        NodeC c;
    }

    static class NodeC {
        // cycle
        NodeA a;
    }


    static class Root {
        final NodeA a;

        Root() {
            this.a = new NodeA();
            this.a.b = new NodeB();
            this.a.b.c = new NodeC();
            this.a.b.c.a = this.a;  // cyclic reference (graph)
        }
    }


    static class NoCycle {
        final NodeA a;

        NoCycle() {
            this.a = new NodeA();
            this.a.b = new NodeB();
            this.a.b.c = new NodeC();
            this.a.b.c.a = new NodeA();  // no cycle, since this is a new object
            this.a.b.c.a.b = new NodeB();
            this.a.b.c.a.b.c = null; // end of the tree
        }
    }

    @Test
    void directCycle() {
        IntrospectorModel model = new IntrospectorModel("Root", new DirectCycle());
        IntrospectorView view = new IntrospectorView("View", model, false);
        List<Node> visitedNodes = new ExpandTreeController().expandAllFromNode(view.getTrees(), model.getRoot(),
                new TreePath(new Object[]{model.getRoot()}));
        assertEquals(4, visitedNodes.size()); // Root + filed + strings + the repeated strings
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("field")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("strings")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("Root")));
        assertTrue(visitedNodes.stream().allMatch(node -> node.getName().equals("strings") ||
                node.getName().equals("field") || node.getName().equals("Root")));
    }

    @Test
    void testIndirectCycle() {
        IntrospectorModel model = new IntrospectorModel("Root", new Root());
        IntrospectorView view = new IntrospectorView("View", model, false);
        List<Node> visitedNodes = new ExpandTreeController().expandAllFromNode(view.getTrees(), model.getRoot(),
                new TreePath(new Object[]{model.getRoot()}));
        assertEquals(4, visitedNodes.size()); // Root + a + b + c
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("a")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("b")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("c")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("Root")));
        assertTrue(visitedNodes.stream().allMatch(node -> node.getName().equals("Root") ||
                node.getName().equals("a") || node.getName().equals("b") || node.getName().equals("c")));
    }

    @Test
    void testNoCycle() {
        IntrospectorModel model = new IntrospectorModel("Root", new NoCycle());
        IntrospectorView view = new IntrospectorView("View", model, false);
        List<Node> visitedNodes = new ExpandTreeController().expandAllFromNode(view.getTrees(), model.getRoot(),
                new TreePath(new Object[]{model.getRoot()}));
        assertEquals(7, visitedNodes.size()); // Root + a + b + c + a + b + c
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("a")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("b")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("c")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("Root")));
        assertTrue(visitedNodes.stream().allMatch(node -> node.getName().equals("Root") ||
                node.getName().equals("a") || node.getName().equals("b") || node.getName().equals("c")));
    }


    @Test
    void testExpandAllFromRootNode() {
        IntrospectorModel model = new IntrospectorModel("Root", new NoCycle());
        IntrospectorView view = new IntrospectorView("View", model, false);
        List<Node> visitedNodes = new ExpandTreeController().expandAllFromRootNode(view.getTrees());
        assertEquals(7, visitedNodes.size()); // Root + a + b + c + a + b + c
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("a")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("b")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("c")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("Root")));
        assertTrue(visitedNodes.stream().allMatch(node -> node.getName().equals("Root") ||
                node.getName().equals("a") || node.getName().equals("b") || node.getName().equals("c")));
    }

}
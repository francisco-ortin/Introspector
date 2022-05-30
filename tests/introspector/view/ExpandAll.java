/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.view;

import introspector.model.IntrospectorModel;
import introspector.model.Node;
import introspector.model.NodeFactory;
import org.junit.jupiter.api.Test;

import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This class tests the expandAll method in IntrsopectorTree
 */
class ExpandAllTest {

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


    @Test
    void directCycle() {
        IntrospectorModel model = new IntrospectorModel("Root", new DirectCycle());
        IntrospectorTree view = new IntrospectorTree("View", model);
        List<Node> visitedNodes = new ArrayList<>();
        view.expandAllAction(model.getRoot(), new TreePath(new Object[]{model.getRoot()}), visitedNodes);
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
        IntrospectorTree view = new IntrospectorTree("View", model);
        List<Node> visitedNodes = new ArrayList<>();
        view.expandAllAction(model.getRoot(), new TreePath(new Object[]{model.getRoot()}), visitedNodes);
        assertEquals(4, visitedNodes.size()); // a + b + c + a
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("a")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("b")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("c")));
        assertTrue(visitedNodes.stream().anyMatch(node -> node.getName().equals("Root")));
        assertTrue(visitedNodes.stream().allMatch(node -> node.getName().equals("Root") ||
                node.getName().equals("a") || node.getName().equals("b") || node.getName().equals("c")));
    }



}
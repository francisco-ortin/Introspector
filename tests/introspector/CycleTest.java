/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector;

import examples.CycleExample;
import introspector.model.ArrayNode;
import introspector.model.IntrospectorModel;
import introspector.model.Node;
import introspector.model.NodeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class tests the IntrospectorModel when there are cycles in the model, so it is no longer a tree but a graph.
 */
class CycleTest {

    /**
     * Dummy classes for testing purposes.
     */
    static class DirectCycle {
        static DirectCycle field = new DirectCycle();
        private DirectCycle()  {} // private constructor
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
        IntrospectorModel model = new IntrospectorModel("Root", DirectCycle.field);
        Node parentNode = NodeFactory.createNode("Root", DirectCycle.field);
        Node childNode = model.getChild(parentNode,0);
        // they must be the same object
        assertEquals(parentNode, childNode);
        // the same for the grandchild
        Node grandChild = model.getChild(childNode, 0);
        assertEquals(parentNode, grandChild);
        assertEquals(childNode, grandChild);
    }

    @Test
    void testIndirectCycle() {
        Root root = new Root();
        IntrospectorModel model = new IntrospectorModel("Root", root);
        Node nodeA = model.getChild(NodeFactory.createNode("Root", root), 0);
        Node nodeB = model.getChild(nodeA, 0);
        Node nodeC = model.getChild(nodeB, 0);
        Node nodeA2 = model.getChild(nodeC, 0);
        // they must be the same object
        assertEquals(nodeA, nodeA2);
    }



}
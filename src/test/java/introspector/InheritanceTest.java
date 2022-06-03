/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector;

import introspector.model.IntrospectorModel;
import introspector.model.Node;
import introspector.model.NodeFactory;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * This class tests the IntrospectorModel when inheritance adds fields to derived instances,
 * including the repetition of fields with the same name.
 */
class InheritanceTest {

    /**
     * Dummy classes for testing purposes.
     */
    static class A {
        private final int fieldA = 1;
    }

    static class B extends A {
        private final int fieldB = 2;
        private final int fieldA = 3; // repeated field
    }

    static class C extends B {
        private final int fieldC = 4;
        private final int fieldB = 5; // repeated field
        private final int fieldA = 6; // repeated field
    }


    @Test
    void testInheritance() {
        C root = new C();
        IntrospectorModel model = new IntrospectorModel("Root", root);
        Node rootNode = NodeFactory.createNode("c node", root);
        assertEquals(6, model.getChildCount(rootNode));

        Map<String,Integer> values = new HashMap<>() {{
          put("fieldC", 4); put("fieldB", 5); put("fieldA", 6);
          put("fieldA:introspector.InheritanceTest$B", 3); put("fieldB:introspector.InheritanceTest$B", 2);
          put("fieldA:introspector.InheritanceTest$A", 1);
        }};

        for (int i=0; i<model.getChildCount(rootNode); i++) {
            Node child = model.getChild(rootNode, i);
            assertTrue(values.containsKey(child.getName())); // the name is the expected one
            assertEquals(values.get(child.getName()), model.getChild(rootNode, i).getValue()); // so is the value
        }
    }



}
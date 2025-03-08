/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.view;

import introspector.model.IntrospectorModel;
import introspector.model.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * This class tests the IntrospectorView's addTree method
 */
class IntrospectorViewAddTreeTest {

    @Test
    void testAddTreeModel() {
        IntrospectorModel model1 = new IntrospectorModel("Tree 1", new Object());
        IntrospectorView view = new IntrospectorView("Introspector", model1, false);
        assertEquals(view.getTrees().size(), 1);
        assertEquals(view.isVisible(), false);
        assertEquals(view.getTrees().get(0).getModel(), model1);
        IntrospectorModel model2 = new IntrospectorModel("Tree 2", new Object());
        view.addTree(model2);
        assertEquals(view.getTrees().size(), 2);
        assertEquals(view.getTrees().get(1).getModel(), model2);
        IntrospectorModel model3 = new IntrospectorModel("Tree 3", new Object());
        view.addTree(model3);
        assertEquals(view.getTrees().size(), 3);
        assertEquals(view.getTrees().get(2).getModel(), model3);
    }

    @Test
    void testAddTreeObject() {
        Object model1 = new Object();
        IntrospectorView view = new IntrospectorView("Introspector", "Tree 1", model1, false);
        assertEquals(view.getTrees().size(), 1);
        assertEquals(view.isVisible(), false);
        assertEquals(((Node)view.getTrees().get(0).getModel().getRoot()).getValue(), model1);
        Object model2 = new Object();
        view.addTree("Tree 2", model2);
        assertEquals(view.getTrees().size(), 2);
        assertEquals(((Node)view.getTrees().get(1).getModel().getRoot()).getValue(), model2);
        Object model3 = new Object();
        view.addTree("Tree 3", model3);
        assertEquals(view.getTrees().size(), 3);
        assertEquals(((Node)view.getTrees().get(2).getModel().getRoot()).getValue(), model3);
    }


}
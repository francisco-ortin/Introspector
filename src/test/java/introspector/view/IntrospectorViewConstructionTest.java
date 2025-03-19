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
 * This class tests the IntrospectorView construction
 */
class IntrospectorViewConstructionTest {

    @Test
    void testConstruct1() {
        IntrospectorModel model = new IntrospectorModel("Tree", new Object());
        IntrospectorView view = new IntrospectorView("Introspector", model);
        assertEquals("Introspector", view.getTitle());
        assertEquals(view.getTrees().size(), 1);
        assertEquals(view.getTrees().get(0).getModel(), model);
    }

    @Test
    void testConstruct2() {
        Object model = new Object();
        IntrospectorView view = new IntrospectorView("Introspector", "Tree", model);
        assertEquals("Introspector", view.getTitle());
        assertEquals(view.getTrees().size(), 1);
        assertEquals(((Node)view.getTrees().get(0).getModel().getRoot()).getValue(), model);
    }

    @Test
    void testConstruct3() {
        IntrospectorModel model = new IntrospectorModel("Tree", new Object());
        IntrospectorView view = new IntrospectorView("Introspector", model,false);
        assertEquals("Introspector", view.getTitle());
        assertEquals(view.getTrees().size(), 1);
        assertEquals(view.getTrees().get(0).getModel(), model);
        assertEquals(view.isVisible(), false);
    }

    @Test
    void testConstruct4() {
        IntrospectorModel model = new IntrospectorModel("Tree", new Object());
        IntrospectorView view = new IntrospectorView("Introspector", model, 800, 400, false);
        assertEquals("Introspector", view.getTitle());
        assertEquals(view.getTrees().size(), 1);
        assertEquals(view.getTrees().get(0).getModel(), model);
        assertEquals(view.isVisible(), false);
        assertEquals(view.getWidth(), 800);
        assertEquals(view.getHeight(), 400);
    }


}
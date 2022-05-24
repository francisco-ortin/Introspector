/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for the IntrospectorModel class in the model package
 */
class IntrospectorModelTest {


    /**
     * Dummy class for testing
     */
    static class EmptyClass {}

    private int[] array;
    private Object object;
    private IntrospectorModel arrayModel, objectModel;

    private Node arrayNode, objectNode;

    @BeforeEach
    void createNodes() {
        this.arrayModel = new IntrospectorModel("Array model", this.array = new int [] {1, 2, 3});
        this.objectModel = new IntrospectorModel("Object model", this.object = new EmptyClass());
        this.arrayNode = arrayModel.getRoot();
        this.objectNode = objectModel.getRoot();
    }

    @Test
    void getChild() {
        assertAll(
                // array
                ()-> assertEquals(1, arrayModel.getChild(arrayNode, 0).getValue()),
                ()-> assertEquals(2, arrayModel.getChild(arrayNode, 1).getValue()),
                ()-> assertEquals(3, arrayModel.getChild(arrayNode, 2).getValue()),
                // object
                ()-> assertNull(objectModel.getChild(objectNode, 0))
                );
    }

    @Test
    void getChildCount() {
        assertEquals(3, this.arrayModel.getChildCount(this.arrayNode));
        assertEquals(0, this.arrayModel.getChildCount(this.objectNode));
    }

    @Test
    void getRoot() {
        assertEquals(this.array, this.arrayModel.getRoot().getValue());
        assertEquals(this.object, this.objectModel.getRoot().getValue());
    }

    @Test
    void isLeaf() {
        assertFalse(this.arrayModel.isLeaf(this.arrayNode));
        assertTrue(this.objectModel.isLeaf(this.objectNode));
    }


    @Test
    void getIndexOfChild() {
        assertEquals(0, this.arrayModel.getIndexOfChild(this.arrayNode,
                NodeFactory.createNode("", 1)));
        assertEquals(1, this.arrayModel.getIndexOfChild(this.arrayNode,
                NodeFactory.createNode("", 2)));
        assertEquals(2, this.arrayModel.getIndexOfChild(this.arrayNode,
                NodeFactory.createNode("", 3)));
        // not found child
        assertEquals(-1, this.objectModel.getIndexOfChild(this.objectNode, this.objectNode));
    }

}
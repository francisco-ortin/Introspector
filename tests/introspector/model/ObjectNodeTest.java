/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for the ObjectNode class in the model package
 */
class ObjectNodeTest {

    /**
     * Dummy class for testing purposes.
     */
    private static class EmptyClass {}

    /**
     * Dummy class for testing purposes.
     */
    private static class MyClass {
        final int intValue = 1;
        final String stringValue = "str";
        final Object objectValue = new Object();
    }

    /**
     * Dummy class for testing purposes.
     */
    private static class DerivedClass extends MyClass {
        final int intValue = 2; // repeated field
        final double doubleValue = 2.2;
    }
    private ObjectNode objectNode, myClassNode, derivedClassNode, emptyClassNode;

    @BeforeEach
    void createNodes() {
        this.objectNode = new ObjectNode("object", new Object());
        this.emptyClassNode = new ObjectNode("emptyClass", new EmptyClass());
        this.myClassNode = new ObjectNode("myClass", new MyClass());
        this.derivedClassNode = new ObjectNode("derivedClass", new DerivedClass());
    }

    @Test
    void isLeaf() {
        assertAll(
                () -> assertTrue(this.objectNode.isLeaf()),
                () -> assertTrue(this.emptyClassNode.isLeaf()),
                () -> assertFalse(this.myClassNode.isLeaf()),
                () -> assertFalse(this.derivedClassNode.isLeaf())
        );
    }

    @Test
    void getChildrenNumber() {
        assertAll(
                () -> assertEquals(0, this.objectNode.getChildren().size()),
                () -> assertEquals(0, this.emptyClassNode.getChildren().size()),
                () -> assertEquals(3, this.myClassNode.getChildren().size()),
                () -> assertEquals(5, this.derivedClassNode.getChildren().size())
        );
    }

    @Test
    void getChildrenName() {
        // strToInt
        List<Node> list = this.myClassNode.getChildren();
        assertEquals("intValue", list.get(0).getName());
        assertEquals(1, list.get(0).getValue());
        assertEquals("stringValue", list.get(1).getName());
        assertEquals("str", list.get(1).getValue());
        assertEquals("objectValue", list.get(2).getName());
        assertEquals(((MyClass)this.myClassNode.getValue()).objectValue, list.get(2).getValue());
        // myClassToMyClass
        list = this.derivedClassNode.getChildren();
        assertEquals("intValue", list.get(0).getName());
        assertEquals(2, list.get(0).getValue());
        assertEquals("doubleValue", list.get(1).getName());
        assertEquals(2.2, list.get(1).getValue());
        assertEquals("intValue:introspector.model.ObjectNodeTest$MyClass", list.get(2).getName());
        assertEquals(1, list.get(2).getValue());
        assertEquals("stringValue", list.get(3).getName());
        assertEquals("str", list.get(3).getValue());
        assertEquals("objectValue", list.get(4).getName());
        assertEquals(((DerivedClass)this.derivedClassNode.getValue()).objectValue, list.get(4).getValue());
    }


    @Test
    void toStringOverridden() {
        assertAll(
                () -> assertEquals("object (Object)", this.objectNode.toString()),
                () -> assertEquals("emptyClass (EmptyClass)", this.emptyClassNode.toString()),
                () -> assertEquals("myClass (MyClass)", this.myClassNode.toString()),
                () -> assertEquals("derivedClass (DerivedClass)", this.derivedClassNode.toString())
        );
    }


}
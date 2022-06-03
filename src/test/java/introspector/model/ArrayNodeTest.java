/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for the ArrayNode class in the model package
 */
class ArrayNodeTest {

    /**
     * Dummy class for testing purposes.
     */
    private static class MyClass {}

    private ArrayNode intArray, stringArray, myClassArray;

    private MyClass myObject;

    @BeforeEach
    void createNodes() {
        intArray = new ArrayNode("int array", new int[]{1, 2, 3});
        stringArray = new ArrayNode("string array", new String[]{"one", "two"});
        myClassArray = new ArrayNode("myClass array", new MyClass[]{this.myObject = new MyClass()});
    }

    @Test
    void isLeaf() {
        assertAll(
                ()-> assertFalse(this.intArray.isLeaf()),
                ()-> assertFalse(this.stringArray.isLeaf()),
                ()-> assertFalse(this.myClassArray.isLeaf())
        );
    }

    @Test
    void getChildrenNumber() {
        assertAll(
                ()-> assertEquals(3, this.intArray.getChildren().size()),
                ()-> assertEquals(2, this.stringArray.getChildren().size()),
                ()-> assertEquals(1, this.myClassArray.getChildren().size())
        );
    }

    @Test
    void getChildrenName() {
        // int array
        List<Node> list = this.intArray.getChildren();
        assertEquals("int array[0]", list.get(0).getName());
        assertEquals(1, list.get(0).getValue());
        assertEquals("int array[1]", list.get(1).getName());
        assertEquals(2, list.get(1).getValue());
        assertEquals("int array[2]", list.get(2).getName());
        assertEquals(3, list.get(2).getValue());
        // string array
        list = this.stringArray.getChildren();
        assertEquals("string array[0]", list.get(0).getName());
        assertEquals("one", list.get(0).getValue());
        assertEquals("string array[1]", list.get(1).getName());
        assertEquals("two", list.get(1).getValue());
        // myClass array
        list = this.myClassArray.getChildren();
        assertEquals("myClass array[0]", list.get(0).getName());
        assertEquals(this.myObject, list.get(0).getValue());
    }

    @Test
    void toStringOverridden() {
        assertAll(
                () -> assertEquals("int array (int[])", this.intArray.toString()),
                () -> assertEquals("string array (String[])", this.stringArray.toString()),
                () -> assertEquals("myClass array (MyClass[])", this.myClassArray.toString())
        );
    }


}
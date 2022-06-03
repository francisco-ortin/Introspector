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
 * Tests for the BuiltinTypeNode class in the model package
 */
class BuiltinTypeNodeTest {


    private BuiltinTypeNode intNode, charNode, doubleNode, stringNode, booleanNode, byteNode;

    @BeforeEach
    void createNodes() {
        intNode = new BuiltinTypeNode("int node", 3);
        doubleNode = new BuiltinTypeNode("double node", 3.3);
        stringNode = new BuiltinTypeNode("string node", "3");
        charNode = new BuiltinTypeNode("char node", '3');
        booleanNode = new BuiltinTypeNode("boolean node", true);
        byteNode = new BuiltinTypeNode("byte node", (byte)3);
    }

    @Test
    void isLeaf() {
        assertAll(
                ()-> assertTrue(this.intNode.isLeaf()),
                ()-> assertTrue(this.doubleNode.isLeaf()),
                ()-> assertTrue(this.stringNode.isLeaf()),
                ()-> assertTrue(this.charNode.isLeaf()),
                ()-> assertTrue(this.booleanNode.isLeaf()),
                ()-> assertTrue(this.byteNode.isLeaf())
        );
    }

    @Test
    void getChildren() {
        assertAll(
                ()-> assertNull(this.intNode.getChildren()),
                ()-> assertNull(this.doubleNode.getChildren()),
                ()-> assertNull(this.stringNode.getChildren()),
                ()-> assertNull(this.charNode.getChildren()),
                ()-> assertNull(this.booleanNode.getChildren()),
                ()-> assertNull(this.byteNode.getChildren())
        );
    }

    @Test
    void getName() {
        assertAll(
                () -> assertEquals("int node", this.intNode.getName()),
                () -> assertEquals("double node", this.doubleNode.getName()),
                () -> assertEquals("string node", this.stringNode.getName()),
                () -> assertEquals("char node", this.charNode.getName()),
                () -> assertEquals("boolean node", this.booleanNode.getName()),
                () -> assertEquals("byte node", this.byteNode.getName())
        );
    }

    @Test
    void getType() {
        assertAll(
                () -> assertEquals(Integer.class, this.intNode.getType()),
                () -> assertEquals(Double.class, this.doubleNode.getType()),
                () -> assertEquals(String.class, this.stringNode.getType()),
                () -> assertEquals(Character.class, this.charNode.getType()),
                () -> assertEquals(Boolean.class, this.booleanNode.getType()),
                () -> assertEquals(Byte.class, this.byteNode.getType())
        );
    }

    @Test
    void getValue() {
        assertAll(
                () -> assertEquals(3, this.intNode.getValue()),
                () -> assertEquals(3.3, this.doubleNode.getValue()),
                () -> assertEquals("3", this.stringNode.getValue()),
                () -> assertEquals('3', this.charNode.getValue()),
                () -> assertEquals(true, this.booleanNode.getValue()),
                () -> assertEquals((byte)3, this.byteNode.getValue())
        );
    }


    @Test
    void toStringOverridden() {
        assertAll(
                () -> assertEquals("int node (Integer): 3", this.intNode.toString()),
                () -> assertEquals("double node (Double): 3.3", this.doubleNode.toString()),
                () -> assertEquals("string node (String): 3", this.stringNode.toString()),
                () -> assertEquals("char node (Character): 3", this.charNode.toString()),
                () -> assertEquals("boolean node (Boolean): true", this.booleanNode.toString()),
                () -> assertEquals("byte node (Byte): 3", this.byteNode.toString())
        );
    }



}
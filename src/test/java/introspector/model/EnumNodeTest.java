/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.model;

import static org.junit.jupiter.api.Assertions.*;

import introspector.model.AbstractNode;
import introspector.model.NodeFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the EnumNode class in the model package
 */
class EnumNodeTest {

    enum Color {
        RED, BLUE, GREEN
    }

    private AbstractNode blueNode, redNode, greenNode;

    @BeforeEach
    void createColors() {
        this.blueNode = (AbstractNode) NodeFactory.createNode("BlueNode", Color.BLUE);
        this.greenNode = (AbstractNode)NodeFactory.createNode("GreenNode", Color.GREEN);
        this.redNode =(AbstractNode) NodeFactory.createNode("RedNode", Color.RED);
    }



    @Test
    void isLeaf() {
        assertAll(
                ()-> assertTrue(this.blueNode.isLeaf()),
                ()-> assertTrue(this.greenNode.isLeaf()),
                ()-> assertTrue(this.redNode.isLeaf())
        );
    }

    @Test
    void getChildren() {
        assertAll(
                ()-> assertNull(this.blueNode.getChildren()),
                ()-> assertNull(this.greenNode.getChildren()),
                ()-> assertNull(this.redNode.getChildren())
        );
    }

    @Test
    void getName() {
        assertAll(
                () -> assertEquals("BlueNode", this.blueNode.getName()),
                () -> assertEquals("GreenNode", this.greenNode.getName()),
                () -> assertEquals("RedNode", this.redNode.getName())
        );
    }


    @Test
    void getType() {
        assertAll(
                () -> assertEquals(Color.class, this.blueNode.getType()),
                () -> assertEquals(Color.class, this.greenNode.getType()),
                () -> assertEquals(Color.class, this.redNode.getType())
        );
    }

    @Test
    void getValue() {
        assertAll(
                () -> assertEquals(Color.BLUE, this.blueNode.getValue()),
                () -> assertEquals(Color.GREEN, this.greenNode.getValue()),
                () -> assertEquals(Color.RED, this.redNode.getValue())
        );
    }


    @Test
    void toStringOverridden() {
        assertAll(
                () -> assertEquals("BlueNode (Color): BLUE", this.blueNode.toString()),
                () -> assertEquals("GreenNode (Color): GREEN", this.greenNode.toString()),
                () -> assertEquals("RedNode (Color): RED", this.redNode.toString())
        );
    }



}
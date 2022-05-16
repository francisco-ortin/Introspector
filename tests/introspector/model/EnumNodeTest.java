package introspector.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnumNodeTest {

    static enum Color {
        RED, BLUE, GREEN;
    }

    private Node blueNode, redNode, greenNode;

    @BeforeEach
    void createColors() {
        this.blueNode = Node.buildNode("BlueNode", Color.BLUE, Color.BLUE.getClass());
        this.greenNode = Node.buildNode("GreenNode", Color.GREEN, Color.GREEN.getClass());
        this.redNode = Node.buildNode("RedNode", Color.RED, Color.RED.getClass());
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
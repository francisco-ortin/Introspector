/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for the CollectionNode class in the model package
 */
class CollectionNodeTest {

    /**
     * Dummy class for testing purposes.
     */
    private CollectionNode list, set, queue;

    @BeforeEach
    void createNodes() {
        this.list = new CollectionNode("list", new ArrayList<>(Arrays.asList(1, 2, 3)));
        this.set = new CollectionNode("set", new TreeSet<>(Arrays.asList(4, 5)));
        this.queue = new CollectionNode("queue", new PriorityQueue<>(Arrays.asList(6, 7, 8, 9)));
    }

    @Test
    void isLeaf() {
        assertAll(
                ()-> assertFalse(this.list.isLeaf()),
                ()-> assertFalse(this.set.isLeaf()),
                ()-> assertFalse(this.queue.isLeaf())
        );
    }

    @Test
    void getChildrenNumber() {
        assertAll(
                ()-> assertEquals(3, this.list.getChildren().size()),
                ()-> assertEquals(2, this.set.getChildren().size()),
                ()-> assertEquals(4, this.queue.getChildren().size())
        );
    }

    @Test
    void getChildrenName() {
        // int array
        List<Node> list = this.list.getChildren();
        assertEquals("list[0]", list.get(0).getName());
        assertEquals(1, list.get(0).getValue());
        assertEquals("list[1]", list.get(1).getName());
        assertEquals(2, list.get(1).getValue());
        assertEquals("list[2]", list.get(2).getName());
        assertEquals(3, list.get(2).getValue());
        // string array
        list = this.set.getChildren();
        assertEquals("set[0]", list.get(0).getName());
        assertEquals(4, list.get(0).getValue());
        assertEquals("set[1]", list.get(1).getName());
        assertEquals(5, list.get(1).getValue());
        // myClass array
        list = this.queue.getChildren();
        assertEquals("queue[0]", list.get(0).getName());
        assertEquals(6, list.get(0).getValue());
        assertEquals("queue[1]", list.get(1).getName());
        assertEquals(7, list.get(1).getValue());
        assertEquals("queue[2]", list.get(2).getName());
        assertEquals(8, list.get(2).getValue());
        assertEquals("queue[3]", list.get(3).getName());
        assertEquals(9, list.get(3).getValue());
    }

    @Test
    void toStringOverridden() {
        assertAll(
                () -> assertEquals("list (ArrayList)", this.list.toString()),
                () -> assertEquals("set (TreeSet)", this.set.toString()),
                () -> assertEquals("queue (PriorityQueue)", this.queue.toString())
        );
    }


}
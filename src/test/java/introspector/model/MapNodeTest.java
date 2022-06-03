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
 * Tests for the MapNode class in the model package
 */
class MapNodeTest {

    /**
     * Dummy class for testing purposes.
     */
    private static class MyClass implements Comparable<MyClass> {
        final private int value;
        private MyClass(int value) {
            this.value = value;
        }
        @Override
        public String toString() {
            return String.format("MyClass(%s)", this.value);
        }
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof MyClass myObject)
                return this.value == myObject.value;
            return false;
        }
        @Override
        public int compareTo(MyClass obj) {
            return this.value - obj.value;
        }
    }

    private MapNode strToInt, myClassToMyClass;
    private MyClass from, to;

    @BeforeEach
    void createNodes() {
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("one", 1);
        map1.put("two", 2);
        map1.put("three", 3);
        this.strToInt = new MapNode("strToInt", map1);
        Map<MyClass, MyClass> map2 = new TreeMap<>();
        map2.put(from = new MyClass(1), to = new MyClass(2));
        this.myClassToMyClass = new MapNode("myClassToMyClass", map2);
    }

    @Test
    void isLeaf() {
        assertAll(
                ()-> assertFalse(this.strToInt.isLeaf()),
                ()-> assertFalse(this.myClassToMyClass.isLeaf())
        );
    }

    @Test
    void getChildrenNumber() {
        assertAll(
                ()-> assertEquals(3, this.strToInt.getChildren().size()),
                ()-> assertEquals(1, this.myClassToMyClass.getChildren().size())
        );
    }

    @Test
    void getChildrenName() {
        // strToInt
        List<Node> list = this.strToInt.getChildren();
        assertEquals("strToInt[one]", list.get(0).getName());
        assertEquals(1, list.get(0).getValue());
        assertEquals("strToInt[two]", list.get(1).getName());
        assertEquals(2, list.get(1).getValue());
        assertEquals("strToInt[three]", list.get(2).getName());
        assertEquals(3, list.get(2).getValue());
        // myClassToMyClass
        list = this.myClassToMyClass.getChildren();
        assertEquals(String.format("myClassToMyClass[%s]", this.from), list.get(0).getName());
        assertEquals(this.to, list.get(0).getValue());
    }

    @Test
    void toStringOverridden() {
        assertAll(
                () -> assertEquals("strToInt (HashMap)", this.strToInt.toString()),
                () -> assertEquals("myClassToMyClass (TreeMap)", this.myClassToMyClass.toString())
        );
    }


}
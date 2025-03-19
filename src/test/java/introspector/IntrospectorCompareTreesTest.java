/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector;

import introspector.Introspector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the Introspector Facade as an API to compare trees.
 */
public class IntrospectorCompareTreesTest {

    @Test
    public void testCompareTreesBuiltinRight()  {
        Assertions.assertTrue(Introspector.compareTrees(33, 33));
        assertTrue(Introspector.compareTrees("hi", "hi"));
        assertTrue(Introspector.compareTrees(3.4, 3.4));
        assertTrue(Introspector.compareTrees(null, null));
        Object object = new Object();
        assertTrue(Introspector.compareTrees(object, object));
        assertTrue(Introspector.compareTrees(true, true));
        assertTrue(Introspector.compareTrees('a', 'a'));
    }

    @Test
    public void testCompareTreesBuiltinWrong()  {
        Assertions.assertFalse(Introspector.compareTrees(33, 34));
        Assertions.assertFalse(Introspector.compareTrees("hi", "bye"));
        Assertions.assertFalse(Introspector.compareTrees(3.4, 3.33));
        Assertions.assertFalse(Introspector.compareTrees("null", null));
        Assertions.assertFalse(Introspector.compareTrees(true, false));
        Assertions.assertFalse(Introspector.compareTrees('a', 'b'));
        Assertions.assertFalse(Introspector.compareTrees(33, 33.0));
        Assertions.assertFalse(Introspector.compareTrees("hi", 'h'));
        Assertions.assertFalse(Introspector.compareTrees(3.4, 3));
        Assertions.assertFalse(Introspector.compareTrees(null, 7));
        Assertions.assertFalse(Introspector.compareTrees("null", null));
        Assertions.assertFalse(Introspector.compareTrees(true, "true"));
        Assertions.assertFalse(Introspector.compareTrees('a', "a"));
    }

    @Test
    public void testCompareArrays()  {
        assertTrue(Introspector.compareTrees(new int[]{}, new int[]{}));
        assertTrue(Introspector.compareTrees(new int[]{1, 2}, new int[]{1, 2}));
        assertTrue(Introspector.compareTrees(new String[]{"1", "2"}, new String[]{"1", "2"}));
        assertTrue(Introspector.compareTrees(new boolean[]{true, false}, new boolean[]{true, false}));
        assertTrue(Introspector.compareTrees(new Object[]{null, null}, new Object[]{null, null}));
        Assertions.assertFalse(Introspector.compareTrees(new int[]{1, 2}, new int[]{2, 1}));
        Assertions.assertFalse(Introspector.compareTrees(new int[]{1, 2}, new int[]{1, 2, 3}));
        Assertions.assertFalse(Introspector.compareTrees(new int[]{1, 2}, "String"));
    }

    @Test
    public void testCompareCollections()  {
        assertTrue(Introspector.compareTrees(new ArrayList<Integer>(), new ArrayList<Integer>()));
        assertTrue(Introspector.compareTrees(new ArrayList<>(Arrays.asList(1, 2)), new ArrayList<>(Arrays.asList(1, 2))));
        assertTrue(Introspector.compareTrees(new HashSet<>(Arrays.asList(1.1, 2.2)), new HashSet<>(Arrays.asList(1.1, 2.2))));
        assertTrue(Introspector.compareTrees(new TreeSet<>(Arrays.asList(1.1, 2.2)), new TreeSet<>(Arrays.asList(2.2, 1.1))));
        Assertions.assertFalse(Introspector.compareTrees(new ArrayList<>(Arrays.asList(1, 2)), new ArrayList<>(Arrays.asList(2, 1))));
        Assertions.assertFalse(Introspector.compareTrees(new ArrayList<>(Arrays.asList(1, 2)), new ArrayList<>(Arrays.asList(1, 2, 3))));
        Assertions.assertFalse(Introspector.compareTrees(new HashSet<>(Arrays.asList(1.1, 2.2)), new HashSet<>(Arrays.asList(1.1, 2.2, 3.3))));
        Assertions.assertFalse(Introspector.compareTrees(new HashSet<>(Arrays.asList(1.1, 2.2)), new HashSet<>(Arrays.asList(2.2, 1.1))));
    }


    @Test
    public void testCompareMaps()  {
        assertTrue(Introspector.compareTrees(Map.of("One", 1, "Two", 2), Map.of("One", 1, "Two", 2)));
        assertTrue(Introspector.compareTrees(Map.of(1, "One", 2, "Two"), Map.of(1, "One", 2, "Two")));
        assertTrue(Introspector.compareTrees(Map.of("One", 1, "Two", 2), Map.of("Two", 2, "One", 1)));
        Assertions.assertFalse(Introspector.compareTrees(Map.of("One", 1, "Two", 2), Map.of("One", 1, "Two", 2.2)));
        Assertions.assertFalse(Introspector.compareTrees(Map.of("One", 1, "Two", 2), Map.of("One", 1)));
        Assertions.assertFalse(Introspector.compareTrees(new HashMap<>(Map.of("One", 1, "Two", 2)), new TreeMap<>(Map.of("One", 1, "Two", 2))));
    }

    // example enum type
    private enum Color {RED, GREEN, BLUE}

    @Test
    public void testCompareEnums()  {
        assertTrue(Introspector.compareTrees(Color.RED, Color.RED));
        assertTrue(Introspector.compareTrees(Color.BLUE, Color.BLUE));
        Assertions.assertFalse(Introspector.compareTrees(Color.RED, Color.GREEN));
        Assertions.assertFalse(Introspector.compareTrees(Color.RED, 1));
        Assertions.assertFalse(Introspector.compareTrees(null, Color.GREEN));
    }

    @Test
    public void testCompareOptional()  {
        assertTrue(Introspector.compareTrees(Optional.of(1), 1));
        assertTrue(Introspector.compareTrees("hi", Optional.of("hi")));
        assertTrue(Introspector.compareTrees(Optional.empty(), Optional.empty()));
        Assertions.assertFalse(Introspector.compareTrees(Optional.of(1), Optional.of(2)));
        Assertions.assertFalse(Introspector.compareTrees(Optional.of("hi"), "bye"));
        Assertions.assertFalse(Introspector.compareTrees('a', Optional.of('b')));
        Assertions.assertFalse(Introspector.compareTrees(Optional.of("h"), 'h'));
        Assertions.assertFalse(Introspector.compareTrees("b", Optional.of('b')));
        Assertions.assertFalse(Introspector.compareTrees(null, Optional.empty()));
        Assertions.assertFalse(Introspector.compareTrees(Optional.empty(), null));
    }


    // example class/record type
    private record Person(int id, String firstName, Object anything) {
    }

    @Test
    public void testCompareObjectRight()  {
        Person person = new Person(1, "Francisco", "Ortin");
        assertTrue(Introspector.compareTrees(person, new Person(1, "Francisco", "Ortin")));
        assertTrue(Introspector.compareTrees(new Person(2, "John", person),
                new Person(2, "John", new Person(1, "Francisco", "Ortin"))));
        assertTrue(Introspector.compareTrees(new Person[]{person, person},
                new Person[]{new Person(1, "Francisco", "Ortin"), new Person(1, "Francisco", "Ortin")}));
    }

    @Test
    public void testCompareObjectWrong()  {
        Person person1 = new Person(1, "Francisco", "Ortin");
        Person person2 =  new Person(1, "Francisco", "Soler");
        Assertions.assertFalse(Introspector.compareTrees(person1, person2));
        Assertions.assertFalse(Introspector.compareTrees(new Person(2, "John", person1),
                new Person(2, "John", person2)));
        Assertions.assertFalse(Introspector.compareTrees(new Person[]{person1}, new Person[]{person2}));
        Assertions.assertFalse(Introspector.compareTrees(new Person[]{person1, person1}, new Person[]{person1, person2}));
        Assertions.assertFalse(Introspector.compareTrees(person1, 1));
    }


}

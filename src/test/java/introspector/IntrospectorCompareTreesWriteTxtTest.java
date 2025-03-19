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

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the Introspector Facade as an API to compare two trees and write the differences in a txt file.
 */
public class IntrospectorCompareTreesWriteTxtTest {

    private record Person(int id, String firstName, Object anything) {
    }


    private static boolean createDirIfItDoesNotExist(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            return directory.mkdir();
        }
        return true; // already exists
    }

    @Test
    public void testWriteTreeAsTxtFullInfo() throws IOException {
        final String directoryName = "out/";
        final String outputFileName1 = directoryName + "output1.txt",
                outputFileName2 = directoryName + "output2.txt";
        createDirIfItDoesNotExist(directoryName);
        Person person1 = new Person(1, "John", new Person(3, "Alice", null));
        Person person2 = new Person(2, "Jane", new Person(3, "Alice", "Doe"));
        boolean equalTrees = Introspector.compareTreesAsTxt(person1, person2, outputFileName1, outputFileName2);
        Assertions.assertFalse(equalTrees);
        String fileContents1 = Files.readString(Path.of(outputFileName1));
        String expectedValue1 = """
               Tree1 (Person): Person[id=1, firstName=John, anything=Person[id=3, firstName=Alice, anything=null]].
               |- **id** (Integer): 1.
               |- **firstName** (String): John.
               |- anything (Person): Person[id=3, firstName=Alice, anything=null].
               |  |- id (Integer): 3.
               |  |- firstName (String): Alice.
               |  |- **anything** (Object): null.                                               
                """;
        assertTrue(fileContents1.startsWith(expectedValue1));
        String fileContents2 = Files.readString(Path.of(outputFileName2));
        String expectedValue2 = """
                Tree2 (Person): Person[id=2, firstName=Jane, anything=Person[id=3, firstName=Alice, anything=Doe]].
                |- **id** (Integer): 2.
                |- **firstName** (String): Jane.
                |- anything (Person): Person[id=3, firstName=Alice, anything=Doe].
                |  |- id (Integer): 3.
                |  |- firstName (String): Alice.
                |  |- **anything** (String): Doe.
                """;
        assertTrue(fileContents2.startsWith(expectedValue2));
    }

    @Test
    public void testWriteTreeAsTxtShortInfo() throws IOException {
        final String directoryName = "out/";
        final String outputFileName1 = directoryName + "output1.txt",
                outputFileName2 = directoryName + "output2.txt";
        createDirIfItDoesNotExist(directoryName);
        Person person1 = new Person(1, "John", new Person(3, "Alice", null));
        Person person2 = new Person(2, "Jane", new Person(3, "Alice", "Doe"));
        boolean equalTrees = Introspector.compareTreesAsTxt(person1, person2, outputFileName1, outputFileName2, false);  // false = short info
        Assertions.assertFalse(equalTrees);
        String fileContents1 = Files.readString(Path.of(outputFileName1));
        String expectedValue1 = """
                Tree1 (Person).
                |- ** id (Integer): 1 **
                |- ** firstName (String): John **
                |- anything (Person).
                |  |- id (Integer): 3.
                |  |- firstName (String): Alice.
                |  |- ** anything (Object) **
                """;
        assertTrue(fileContents1.startsWith(expectedValue1));
        String fileContents2 = Files.readString(Path.of(outputFileName2));
        String expectedValue2 = """
                Tree2 (Person).
                |- ** id (Integer): 2 **
                |- ** firstName (String): Jane **
                |- anything (Person).
                |  |- id (Integer): 3.
                |  |- firstName (String): Alice.
                |  |- ** anything (String): Doe **
                """;
        assertTrue(fileContents2.startsWith(expectedValue2));
    }

}

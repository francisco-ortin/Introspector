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
 * Tests the Introspector Facade as an API to compare two trees and write the differences in an HTML file.
 */
public class IntrospectorCompareTreesWriteHtmlTest {

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
    public void testWriteTreeAsHTMLFullInfo() throws IOException {
        final String directoryName = "out/";
        final String outputFileName1 = directoryName + "output1.html",
                outputFileName2 = directoryName + "output2.html";
        createDirIfItDoesNotExist(directoryName);
        Person person1 = new Person(1, "John", new Person(3, "Alice", null));
        Person person2 = new Person(2, "Jane", new Person(3, "Alice", "Doe"));
        boolean equalTrees = Introspector.compareTreesAsHtml(person1, person2, outputFileName1, outputFileName2);
        Assertions.assertFalse(equalTrees);
        String fileContents1 = Files.readString(Path.of(outputFileName1));
        String expectedValue1 = """
                <summary>Tree1 (Person): Person[id=1, firstName=John, anything=Person[id=3, firstName=Alice, anything=null]].</summary>
                <ul>
                  <li class="modified">id (Integer): 1.</li>
                  <li class="modified">firstName (String): John.</li>
                  <li>
                  <details>
                  <summary>anything (Person): Person[id=3, firstName=Alice, anything=null].</summary>
                  <ul>
                    <li>id (Integer): 3.</li>
                    <li>firstName (String): Alice.</li>
                    <li class="modified">anything (Object): null.</li>
                  </ul>
                  </details>
                  </li>
                </ul>
                """;
        assertTrue(fileContents1.contains(expectedValue1));
        String fileContents2 = Files.readString(Path.of(outputFileName2));
        String expectedValue2 = """
                <summary>Tree2 (Person): Person[id=2, firstName=Jane, anything=Person[id=3, firstName=Alice, anything=Doe]].</summary>
                <ul>
                  <li class="modified">id (Integer): 2.</li>
                  <li class="modified">firstName (String): Jane.</li>
                  <li>
                  <details>
                  <summary>anything (Person): Person[id=3, firstName=Alice, anything=Doe].</summary>
                  <ul>
                    <li>id (Integer): 3.</li>
                    <li>firstName (String): Alice.</li>
                    <li class="modified">anything (String): Doe.</li>
                  </ul>
                  </details>
                  </li>
                </ul>
                """;
        assertTrue(fileContents2.contains(expectedValue2));
    }

    @Test
    public void testWriteTreeAsTxtHTMLInfo() throws IOException {
        final String directoryName = "out/";
        final String outputFileName1 = directoryName + "output1.html",
                outputFileName2 = directoryName + "output2.html";
        createDirIfItDoesNotExist(directoryName);
        Person person1 = new Person(1, "John", new Person(3, "Alice", null));
        Person person2 = new Person(2, "Jane", new Person(3, "Alice", "Doe"));
        boolean equalTrees = Introspector.compareTreesAsHtml(person1, person2, outputFileName1, outputFileName2, false);  // false = short info
        Assertions.assertFalse(equalTrees);
        String fileContents1 = Files.readString(Path.of(outputFileName1));
        String expectedValue1 = """
                <summary>Tree1 (Person).</summary>
                <ul>
                  <li class="modified">id (Integer): 1.</li>
                  <li class="modified">firstName (String): John.</li>
                  <li>
                  <details>
                  <summary>anything (Person).</summary>
                  <ul>
                    <li>id (Integer): 3.</li>
                    <li>firstName (String): Alice.</li>
                    <li class="modified">anything (Object).</li>
                  </ul>
                  </details>
                  </li>
                </ul>
                """;
        assertTrue(fileContents1.contains(expectedValue1));
        String fileContents2 = Files.readString(Path.of(outputFileName2));
        String expectedValue2 = """
                <summary>Tree2 (Person).</summary>
                <ul>
                  <li class="modified">id (Integer): 2.</li>
                  <li class="modified">firstName (String): Jane.</li>
                  <li>
                  <details>
                  <summary>anything (Person).</summary>
                  <ul>
                    <li>id (Integer): 3.</li>
                    <li>firstName (String): Alice.</li>
                    <li class="modified">anything (String): Doe.</li>
                  </ul>
                  </details>
                  </li>
                </ul>
                """;
        assertTrue(fileContents2.contains(expectedValue2));
    }

}

/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the Introspector Facade as an API to write trees as text or HTML files.
 */
public class IntrospectorWriteTest {

    private static class DummyA {
        int intField = 33;
        String stringField = "hi";
        DummyB dummyBField = new DummyB();

        @Override
        public String toString() {
            return "DummyA object " + intField + " " + stringField + " " + dummyBField;
        }
    }
    private static class DummyB {
        final private char charField = 'C';
        @Override
        public String toString() {
            return "DummyB object";
        }
    }


    private static boolean createDirIfItDoesNotExist(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            return directory.mkdir();
        }
        return true; // already exists
    }

    @Test
    public void testWriteTreeAsTxt() throws IOException {
        final String directoryName = "out/";
        final String outputFileName = directoryName + "output.txt";
        createDirIfItDoesNotExist(directoryName);
        // int
        Introspector.writeTreeAsTxt(33, "tree", outputFileName);
        Path outputPath = Path.of(outputFileName);
        assertTrue(Files.readString(outputPath).startsWith("tree (Integer): 33."));
        // string
        Introspector.writeTreeAsTxt("hi", "tree", outputFileName);
        assertTrue(Files.readString(outputPath).startsWith("tree (String): hi."));
        // array
        Introspector.writeTreeAsTxt(new char[]{'h', 'i'}, "tree", outputFileName);
        String fileContents = Files.readString(outputPath);
        assertTrue(fileContents.startsWith("tree (char[]):"));
        assertTrue(fileContents.contains("|- tree[0] (Character): h."));
        assertTrue(fileContents.contains("|- tree[1] (Character): i."));
        // object
        Introspector.writeTreeAsTxt(new DummyA(), "tree", outputFileName);
        fileContents = Files.readString(outputPath);
        String expectedValue = """
                tree (DummyA): DummyA object 33 hi DummyB object.
                |- intField (Integer): 33.
                |- stringField (String): hi.
                |- dummyBField (DummyB): DummyB object.
                |  |- charField (Character): C.
                """;
        assertTrue(fileContents.startsWith(expectedValue));
    }

    @Test
    public void testWriteTreeAsTxtShortInfo() throws IOException {
        final String directoryName = "out/";
        final String outputFileName = directoryName + "output.txt";
        createDirIfItDoesNotExist(directoryName);
        // object
        Introspector.writeTreeAsTxt(new DummyB(), "tree", outputFileName, false); // short info
        Path outputPath = Path.of(outputFileName);
        String fileContents = Files.readString(outputPath);
        String expectedValue = """
                tree (DummyB).
                |- charField (Character): C.
                """;
        assertTrue(fileContents.startsWith(expectedValue));
    }

    @Test
    public void testWriteTreeAsHtml() throws IOException {
        final String directoryName = "out/";
        final String outputFileName = directoryName + "output.html";
        createDirIfItDoesNotExist(directoryName);
        // int
        Introspector.writeTreeAsHtml(33, "tree", outputFileName);
        Path outputPath = Path.of(outputFileName);
        assertTrue(Files.readString(outputPath).contains("tree (Integer): 33."));
        // string
        Introspector.writeTreeAsHtml("hi", "tree", outputFileName);
        assertTrue(Files.readString(outputPath).contains("tree (String): hi."));
        // array
        Introspector.writeTreeAsHtml(new char[]{'h', 'i'}, "tree", outputFileName);
        String fileContents = Files.readString(outputPath);
        assertTrue(fileContents.contains("tree (char[]):"));
        assertTrue(fileContents.contains("<li>tree[0] (Character): h.</li>"));
        assertTrue(fileContents.contains("<li>tree[1] (Character): i.</li>"));
        // object
        Introspector.writeTreeAsHtml(new DummyA(), "tree", outputFileName);
        fileContents = Files.readString(outputPath);
        String expectedValue = """
                <ul class="tree">
                <li>
                <details>
                <summary>tree (DummyA): DummyA object 33 hi DummyB object.</summary>
                <ul>
                  <li>intField (Integer): 33.</li>
                  <li>stringField (String): hi.</li>
                  <li>
                  <details>
                  <summary>dummyBField (DummyB): DummyB object.</summary>
                  <ul>
                    <li>charField (Character): C.</li>
                  </ul>
                  </details>
                  </li>
                </ul>
                </details>
                </li>
                </ul>
                """;
        assertTrue(fileContents.contains(expectedValue));
    }

    @Test
    public void testWriteTreeAsHtmlShortInfo() throws IOException {
        final String directoryName = "out/";
        final String outputFileName = directoryName + "output.html";
        createDirIfItDoesNotExist(directoryName);
        // object
        Introspector.writeTreeAsHtml(new DummyB(), "tree", outputFileName, false); // short info
        Path outputPath = Path.of(outputFileName);
        String fileContents = Files.readString(outputPath);
        String expectedValue = """
                <ul class="tree">
                <li>
                <details>
                <summary>tree (DummyB).</summary>
                <ul>
                  <li>charField (Character): C.</li>
                </ul>
                </details>
                </li>
                </ul>
                """;
        assertTrue(fileContents.contains(expectedValue));
    }

}

/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model.traverse;

import introspector.model.Node;
import introspector.view.ViewHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Stores a tree as a html file
 */
public class HtmlTreeSerializer implements TreeSerializer {

    /**
     * The text stream where the information is to be written
     */
    final private Writer outputTxtFile;

    /**
     * Indicates if all the info in the nodes must be displayed (i.e., toString() method of objects wrapped by nodes)
     */
    private final boolean allInfo;

    /**
     * @param fileName the name of the output txt file
     * @param allInfo if all the info in the nodes must be displayed (i.e., toString() method of objects wrapped by nodes)
     * @throws IOException a textual file is opened
     */
    public HtmlTreeSerializer(String fileName, boolean allInfo) throws IOException {
        this.outputTxtFile = new FileWriter(fileName);
        this.allInfo = allInfo;
    }

    /**
     * This constructor creates an object to write the tree in a file with all the information in the nodes
     * @param fileName the name of the file
     * @throws IOException textual file is opened
     */
    HtmlTreeSerializer(String fileName) throws IOException {
        this(fileName, true);
    }

    /**
     * @param writer the textual output stream
     * @param allInfo if all the info in the nodes must be displayed (i.e., toString() method of objects wrapped by nodes)
     */
    HtmlTreeSerializer(Writer writer, boolean allInfo) {
        this.outputTxtFile = writer;
        this.allInfo = allInfo;
    }

    /**
     * This constructor creates an object to write the tree in a file with all the information in the nodes
     * @param writer the textual output stream
     */
    HtmlTreeSerializer(Writer writer) {
        this(writer, true);
    }

    /**
     * Writes the prefix of every node
     * @param depth the depth of the node
     * @return its textual representation
     */
    private String prefix(int depth) {
        return "  ".repeat(Math.max(0, depth)) ;
    }

    /**
     * Writes one string in the output file and flushes it
     * @param str the string to be written
     * @throws IOException a textual file is written
     */
    protected void write(String str) throws IOException {
        this.outputTxtFile.write(str);
        this.outputTxtFile.flush();
    }

    /**
     * Reads the contents of a file in a resource folder
     * @param resourceName the name of the resource
     * @return the contents of the file
     */
    private  String readResourceFile(String resourceName) {
        StringBuilder sb = new StringBuilder();
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        try (InputStreamReader streamReader = new InputStreamReader(input, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    /**
     * @see TreeSerializer#beginTraverse
     */
    @Override
    public void beginTraverse() throws IOException {
        String cssContent = readResourceFile("css/template.css"),
                jsContent =readResourceFile("js/template.js");
        this.write("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <meta name="description" content="HTML view of a tree, generated with the Introspector tool">
                    <meta name="keywords" content="Introspector, tree, html, view">
                    <meta name="author" content="Francisco Ortin">
                    <title>Introspector HTML tree view</title>
                    <style>
                """ + cssContent + """
                    </style>
                    <script>
                """ + jsContent + """
                    </script>
                </head>
                
                <body>
                <button id="expandButton" onclick="buttonClickedAction()">Expand All</button>
                <ul class="tree">
                """);
    }
    
    /**
     * @see TreeSerializer#endTraverse
     */
    @Override
    public void endTraverse() throws IOException {
        this.write("""
                </ul>
                </body>
                </html>
                """);
        this.outputTxtFile.close();
    }


    /**
     * @see TreeSerializer#beforeTraversing(Node, int, boolean)
     */
    @Override
    public void beforeTraversing(Node node, int depth, boolean hasBeenVisited) {
    }

    /**
     * @see TreeSerializer#traversing(Node, int, boolean)
     */
    @Override
    public void traversing(Node node, int depth, boolean hasBeenVisited) throws IOException {
        if (node.isLeaf()) {
            write(String.format("%s<li>%s</li>\n", this.prefix(depth), this.nodeDescription(node, hasBeenVisited)));
        }
        else {
            write(String.format("%s<li>\n", this.prefix(depth)));
            write(String.format("%s<details>\n", this.prefix(depth)));
            write(String.format("%s<summary>%s</summary>\n", this.prefix(depth), this.nodeDescription(node, hasBeenVisited)));
            write(String.format("%s<ul>\n", this.prefix(depth)));
        }
    }

    /**
     * @see TreeSerializer#afterTraversing(Node, int, boolean)
     */
    @Override
    public void afterTraversing(Node node, int depth, boolean hasBeenVisited) throws IOException {
        if (!node.isLeaf()) {
            write(String.format("%s</ul>\n", this.prefix(depth)));
            write(String.format("%s</details>\n", this.prefix(depth)));
            write(String.format("%s</li>\n", this.prefix(depth)));
        }
    }

    /**
     * Returns the textual representation of a node, considering the allInfo field
     * @param node the node to be converted into string
     * @param cycle if this node is a cycle (has already been visited)
     * @return the textual representation of a node
     */
    private String nodeDescription(Node node, boolean cycle) {
        StringBuilder sb = new StringBuilder();
        if (this.allInfo) {
            sb.append(node.getName());
            Class<?> type = node.getType();
            sb.append(" (").append(type.getSimpleName()).append(")");
            if (cycle)
                    sb.append(" &lt;cyclic reference&gt;");
            sb.append(": ");
            if (node.getValue() == null)
                sb.append("null");
            else
                sb.append(node.getValue().toString());
            sb.append(".");
        }
        else
            sb.append(node).append(".");
        return sb.toString();
    }

}

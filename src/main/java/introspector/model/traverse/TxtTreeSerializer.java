/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model.traverse;


import introspector.model.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores a tree as a txt file
 */
public class TxtTreeSerializer implements TreeSerializer {

    /**
     * The text stream where the information is to be written
     */
    final private Writer outputTxtFile;

    /**
     * Indicates if all the info in the nodes must be displayed (i.e., toString() method of objects wrapped by nodes)
     */
    private final boolean allInfo;

    /**
     * Used when two trees have been compared. The nodes in this set indicates that nodes that have been modified.
     * These nodes must be written between ** and ** to indicate that they have are different from the other tree.
     */
    private final Set<Node> modifiedNodes = new HashSet<>();

    /**
     * @param fileName the name of the output txt file
     * @param allInfo if all the info in the nodes must be displayed (i.e., toString() method of objects wrapped by nodes)
     * @throws IOException a textual file is opened
     */
    public TxtTreeSerializer(String fileName, boolean allInfo) throws IOException {
        this.outputTxtFile = new FileWriter(fileName);
        this.allInfo = allInfo;
    }

    /**
     * @param fileName the name of the output txt file
     * @param allInfo if all the info in the nodes must be displayed (i.e., toString() method of objects wrapped by nodes)
     * @param modifiedNodes the nodes that have been modified in the comparison of two trees
     * @throws IOException a textual file is opened
     */
    public TxtTreeSerializer(String fileName, boolean allInfo, Set<Node> modifiedNodes) throws IOException {
        this.outputTxtFile = new FileWriter(fileName);
        this.allInfo = allInfo;
        this.modifiedNodes.addAll(modifiedNodes);
    }

    /**
     * This constructor creates an object to write the tree in a file with all the information in the nodes
     * @param fileName the name of the file
     * @throws IOException textual file is opened
     */
    TxtTreeSerializer(String fileName) throws IOException {
        this(fileName, true);
    }

    /**
     * @param writer the textual output stream
     * @param allInfo if all the info in the nodes must be displayed (i.e., toString() method of objects wrapped by nodes)
     */
    TxtTreeSerializer(Writer writer, boolean allInfo) {
        this.outputTxtFile = writer;
        this.allInfo = allInfo;
    }

    /**
     * This constructor creates an object to write the tree in a file with all the information in the nodes
     * @param writer the textual output stream
     */
    TxtTreeSerializer(Writer writer) {
        this(writer, true);
    }

    /**
     * Writes the prefix of every node
     * @param depth the depth of the node
     * @return its textual representation
     */
    private String prefix(int depth) {
        if (depth == 0)
            return "";
        return "|  ".repeat(Math.max(0, depth - 1)) + "|- ";
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
     * @see TreeSerializer#beginTraverse
     */
    @Override
    public void beginTraverse() {
    }
    
    /**
     * @see TreeSerializer#endTraverse
     */
    @Override
    public void endTraverse() throws IOException {
        this.write("\n");
        this.outputTxtFile.close();
    }

    /**
     * @see TreeSerializer#afterTraversing(Node, int, boolean)
     */
    @Override
    public void afterTraversing(Node node, int depth, boolean hasBeenVisited) {
    }

    /**
     * @see TreeSerializer#beforeTraversing(Node, int, boolean)
     */
    @Override
    public void beforeTraversing(Node node, int depth, boolean hasBeenVisited) throws IOException {
        write(prefix(depth));
    }

    /**
     * @see TreeSerializer#traversing(Node, int, boolean)
     */
    @Override
    public void traversing(Node node, int depth, boolean hasBeenVisited) throws IOException {
        if (this.allInfo) {
            StringBuilder sb = new StringBuilder();
            if (this.modifiedNodes.contains(node))
                sb.append("**");
            sb.append(node.getName());
            if (this.modifiedNodes.contains(node))
                sb.append("**");
            Class<?> type = node.getType();
            sb.append(" (").append(type.getSimpleName()).append(")");
            if (hasBeenVisited)
                sb.append(" <cyclic reference>"); // there is a cycle in the data structure (it is a graph)
            sb.append(": ");
            if (node.getValue() == null)
                sb.append("null");
            else
                sb.append(node.getValue().toString());
            sb.append(".");
            write(sb.toString());
        }
        else {
            if (this.modifiedNodes.contains(node))
                write("** ");
            write(node.toString());
            if (this.modifiedNodes.contains(node))
                write(" **");
            else
                write(".");
        }
        write("\n");
    }

}

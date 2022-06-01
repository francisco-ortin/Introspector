/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.view;


import introspector.model.Node;

import java.io.IOException;

/**
 * Defines the operations to be defined so that a tree could be stored
 */
public interface TreeSerializer {


    /**
     * This method is called at the beginning of the tree traversal
     */
    void beginTraverse() throws IOException;

    /**
     * This method is called after the whole tree has been traversed
     */
    void endTraverse() throws IOException;

    /**
     * This method is called after the whole tree has been traversed
     */
    void beforeTraversing(Node node, int n) throws IOException;

    /**
     * This method is called when a node is being traversed
     */
    void traversing(Node node, int n) throws IOException;

    /**
     * This method is called after one node is traversed
     */
    void afterTraversing(Node node, int n) throws IOException;

}

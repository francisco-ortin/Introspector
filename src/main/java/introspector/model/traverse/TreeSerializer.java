/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model.traverse;


import introspector.model.Node;

import java.io.IOException;

/**
 * Defines the operations to be defined so that a tree could be stored
 */
public interface TreeSerializer {


    /**
     * This method is called at the beginning of the tree traversal
     * @throws IOException if there is an error writing the tree
     */
    void beginTraverse() throws IOException;

    /**
     * This method is called after the whole tree has been traversed
     * @throws IOException if there is an error writing the tree
     */
    void endTraverse() throws IOException;

    /**
     * This method is called after the whole tree has been traversed
     * @param node the node that is up to be traversed
     * @param depth the depth of the node
     * @param hasBeenVisited if the node has already been visited in the current traversal (to detect cycles)
     * @throws IOException if there is an error writing the tree
     */
    void beforeTraversing(Node node, int depth, boolean hasBeenVisited) throws IOException;

    /**
     * This method is called when a node is being traversed
     * @param node the node that is up being traversed
     * @param depth the depth of the node
     * @param hasBeenVisited if the node has already been visited in the current traversal (to detect cycles)
     * @throws IOException if there is an error writing the tree
     */
    void traversing(Node node, int depth, boolean hasBeenVisited) throws IOException;

    /**
     * This method is called after one node is traversed
     * @param node the node that has just been traversed
     * @param depth the depth of the node
     * @param hasBeenVisited if the node has already been visited in the current traversal (to detect cycles)
     * @throws IOException if there is an error writing the tree
     */
    void afterTraversing(Node node, int depth, boolean hasBeenVisited) throws IOException;

}

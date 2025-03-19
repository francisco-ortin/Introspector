/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

/**
 * All nodes in the AST implement this ASTNode class.
 */
public abstract class ASTNode {

    /**
     * Line where the node is located in the source code.
     */
    int line;

    /**
     * Column where the node is located in the source code.
     */
    int column;

    /**
     * Constructor of the ASTNode class.
     * @param line Line where the node is located in the source code.
     * @param column Column where the node is located in the source code.
     */
    public ASTNode(int line, int column) {
        this.line = line;
        this.column = column;
    }

}


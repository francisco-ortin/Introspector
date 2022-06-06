/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

/**
 * This node represents any type in the language, yet to be defined.
 */
public abstract class Type extends ASTNode {

    public Type(int line, int column) {
        super(line, column);
    }

}

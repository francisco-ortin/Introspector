/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package examples.ast;

import java.util.List;
import java.util.ArrayList;


/**
 * A write statement to write an output in the console.
 */
public class Write extends Statement {

	public Expression expression;

	public Write(int line, int column, Expression expression) {
		super(line, column);
		this.expression = expression;
	}

	@Override
	public String toString() {
		return "write " + this.expression;
	}

}

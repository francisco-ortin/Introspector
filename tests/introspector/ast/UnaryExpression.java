/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */


package introspector.ast;


public class UnaryExpression extends Expresion {

	public String operator;

	public Expresion operand;

	public UnaryExpression(String operator,Expresion operand) {
		super();
		this.operator=operator;
		this.operand=operand;
	}


}

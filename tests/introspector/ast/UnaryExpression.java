
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


package introspector.ast;


public class BinaryExpression extends Expresion {

	public String operator;

	public Expresion operand1;

	public Expresion operand2;

	public BinaryExpression(String operator, Expresion operand1, Expresion operand2) {
		super();
		this.operator=operator;
		this.operand1=operand1;
		this.operand2=operand2;
	}


}

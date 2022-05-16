
package introspector.ast;


public class Asignment extends BinaryExpression {

	public Asignment(Expresion operand1,Expresion operand2) {
		super("=", operand1, operand2);
	}

	
}

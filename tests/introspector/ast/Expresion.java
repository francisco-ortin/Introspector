
package introspector.ast;

public abstract class Expresion extends Statement {
	
	public Type type;
	
	public Expresion() {
		super();
	}

	public boolean lvalue;
}

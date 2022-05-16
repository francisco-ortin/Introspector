package introspector.ast;

public class Identifier extends Expresion {

	private String name;
	
	public Identifier(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
}

/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model;

import java.util.List;

public class EnumNode extends AbstractNode  implements Node {

	public EnumNode(String name, Object value, Class<?> type) {
		super(name, value, type);
	}

	/**
	 * @see Node#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		return true;
	}

	/**
	 * @see AbstractNode#getChildren()
	 */
	public List<Node> getChildren() {
		return null;
	}

}


package introspector.model.traverse;

import java.util.AbstractMap;

/**
 * A basic tuple of two symmetric elements. The order of the elements is not important.
 */
public class SymmetricPair<T1, T2> extends AbstractMap.SimpleEntry<T1, T2> {
	public SymmetricPair(T1 key, T2 value) {
		super(key, value);
	}

	public T1 getFirst() {
		return getKey();
	}

	public T2 getSecond() {
		return getValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof SymmetricPair) {
			SymmetricPair<?, ?> pair = (SymmetricPair<?, ?>) obj;
			return (this.getFirst() == pair.getFirst() && this.getSecond() == pair.getSecond()) ||
					(this.getFirst() == pair.getSecond() && this.getSecond() == pair.getFirst());
		}
		return false;

	}
}

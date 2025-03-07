package introspector.controller;

import introspector.model.Node;
import introspector.model.traverse.TreeComparator;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.AbstractMap;

/**
 * A basic tuple of two elements.
 */
public class Pair<T1, T2> extends AbstractMap.SimpleEntry<T1, T2> {
	public Pair(T1 key, T2 value) {
		super(key, value);
	}

	public T1 getFirst() {
		return getKey();
	}

	public T2 getSecond() {
		return getValue();
	}
}

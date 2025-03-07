/**
 * Introspector, a tool to visualize as trees the structure of runtime Java programs.
 * Copyright (c) <a href="https://reflection.uniovi.es/ortin/">Francisco Ortin</a>.
 * MIT license.
 * @author Francisco Ortin
 */

package introspector.model.traverse;

import introspector.model.Node;
import introspector.model.NodeFactory;

import java.util.List;
import java.util.Map;

/**
 * MapNode provides a Node implementation to represent any value whose type is a java.util.Map.
 */
public class TraverseHelper {

	/**
	 * Returns whether the map contains a null key.
	 */
	static public boolean containsNullKey(Map<?, ?> map) {
		for(Object key : map.keySet()) {
			if(key == null) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Returns whether a node could be visited twice or more
	 * @param node the node traversed
	 * @return whether the node could be visited twice or more
	 */
	static boolean couldBeVisitedTwice(Node node) {
		return node.getValue() != null &&  // null is not considered as a repeated object
				!NodeFactory.isBuiltinType(node.getType()) && // builtin objects could be repeated
				!node.isLeaf(); // leaf nodes can only be visited once
	}

	/**
	 * Returns whether a node is a cyclic reference. That is, it has been already traversed and hence
	 * the tree is actually a graph.
	 * @param node the node traversed
	 * @param alreadyTraversed a list of previously traversed nodes
	 * @return whether the node is a cyclic reference
	 */
	static boolean hasBeenVisited(Node node, List<Node> alreadyTraversed) {
		return couldBeVisitedTwice(node) &&
				alreadyTraversed.stream().anyMatch(eachNode -> eachNode.getValue() == node.getValue());
	}


	/**
	 * Returns whether a node should be traversed. That is, it has not been traversed yet.
	 * @param node the node traversed
	 * @param alreadyTraversed a list of previously traversed nodes
	 * @return whether the node should be traversed
	 */
	static public boolean shouldBeTraversed(Node node, List<Node> alreadyTraversed) {
		boolean hasBeenVisited = hasBeenVisited(node, alreadyTraversed);
		if (!hasBeenVisited && TraverseHelper.couldBeVisitedTwice(node)) {
			alreadyTraversed.add(node);
		}
		if (TraverseHelper.couldBeVisitedTwice(node))
			// if it could be traversed twice (cyclic reference) then it should not be traversed when it has been visited
			return !hasBeenVisited;
		else // if it could be traversed once (no loop) then it should be traversed
			return true;
	}
}

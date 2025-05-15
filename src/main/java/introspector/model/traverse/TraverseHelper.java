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
import java.util.Set;

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
	 * Returns whether two nodes could be visited twice or more
	 * @param nodePair the node traversed
	 * @return whether the node could be visited twice or more
	 */
	static boolean couldBeVisitedTwice(SymmetricPair<Node, Node> nodePair) {
		return couldBeVisitedTwice(nodePair.getFirst()) && couldBeVisitedTwice(nodePair.getSecond());
	}

	/**
	 * Returns whether a node is a cyclic or alias reference. That is, it has been already traversed and hence
	 * the tree is actually a graph.
	 * @param node the node traversed
	 * @param alreadyTraversed a set of previously traversed nodes
	 * @return whether the node is a cyclic or alias reference
	 */
	static boolean hasBeenVisited(Node node, Set<Node> alreadyTraversed) {
		return couldBeVisitedTwice(node) &&
				alreadyTraversed.stream().anyMatch(eachNode -> eachNode.getValue() == node.getValue());
	}

	/**
	 * Returns whether a pair of nodes have a cyclic or alias reference. That is, it has been already traversed and hence
	 * the tree is actually a graph.
	 * @param nodePair the node traversed
	 * @param alreadyTraversed a set of previously traversed nodes
	 * @return whether the node is a cyclic or alias reference
	 */
	static boolean hasBeenVisited(SymmetricPair<Node, Node> nodePair, Set<SymmetricPair<Node, Node>> alreadyTraversed) {
		// they could be visited twice and they have been visited and...
		return couldBeVisitedTwice(nodePair.getFirst()) && couldBeVisitedTwice(nodePair.getSecond()) &&
				// ... they have been visited as a pair
				alreadyTraversed.stream().anyMatch(pair -> (pair.getFirst().getValue() == nodePair.getFirst().getValue() && pair.getSecond().getValue() == nodePair.getSecond().getValue()) ||
						(pair.getFirst().getValue() == nodePair.getSecond().getValue() && pair.getSecond().getValue() == nodePair.getFirst().getValue()));
	}


	/**
	 * Returns whether a node should be traversed. That is, it has not been traversed yet.
	 * @param node the node traversed
	 * @param alreadyTraversed a set of previously traversed nodes
	 * @return whether the node should be traversed
	 */
	static public boolean shouldBeTraversed(Node node, Set<Node> alreadyTraversed) {
		boolean hasBeenVisited = hasBeenVisited(node, alreadyTraversed);
		if (!hasBeenVisited && TraverseHelper.couldBeVisitedTwice(node)) {
			alreadyTraversed.add(node);
		}
		if (TraverseHelper.couldBeVisitedTwice(node))
			// if it could be traversed twice (cyclic or alias reference) then it should not be traversed when it has been visited
			return !hasBeenVisited;
		else // if it could be traversed once (no loop) then it should be traversed
			return true;
	}

	/**
	 * Returns whether two nodes in a comparison (two trees) should be traversed. That is, it has not been traversed yet as a pair
	 * @param nodePair the pair of nodes traversed
	 * @param alreadyTraversed a set of previously traversed nodes
	 * @return whether the node should be traversed
	 */
	static public boolean shouldBeTraversed(SymmetricPair<Node, Node> nodePair, Set<SymmetricPair<Node, Node>> alreadyTraversed) {
		boolean hasBeenVisited = hasBeenVisited(nodePair, alreadyTraversed);
		if (!hasBeenVisited && TraverseHelper.couldBeVisitedTwice(nodePair)) {
			alreadyTraversed.add(nodePair);
		}
		if (TraverseHelper.couldBeVisitedTwice(nodePair))
			// if it could be traversed twice (cyclic or alias reference) then it should not be traversed when it has been visited
			return !hasBeenVisited;
		else // if it could be traversed once (no loop) then it should be traversed
			return true;
	}


	/**
	 * Adds the new children to the modified nodes.
	 * @param children1 One list of nodes
	 * @param children2 Another list of nodes
	 * @param modifiedNodes The set of modified nodes where the new children will be added
	 */
    public static void addNewChildren(List<Node> children1, List<Node> children2, Set<Node> modifiedNodes) {
		if (children1 == null || children2 == null)
			return;
		if (children1.size() == children2.size())
			return; // they are the same size
		int minChildrenCount = Math.min(children1.size(), children2.size());
		// add the new children to the modified nodes
		List<Node> biggesList = children1.size() > children2.size() ? children1 : children2;
		for (int i=minChildrenCount; i < biggesList.size(); i++) {
			modifiedNodes.add(biggesList.get(i));
		}
    }
}

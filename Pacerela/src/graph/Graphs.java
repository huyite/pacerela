package graph;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Graphs {

	/**
	 * Compute a Breadth First Search Tree rooted on vertex root.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <V, E extends Graph.Edge<V>> PartialGraph<V, E> breadthFirstSearch(
			Graph<V, E> g, V root) {
		PartialGraph<V, E> bfsTree = g
				.partialGraph((Set<E>) Collections.EMPTY_SET);
		Set<V> markedVertices = new HashSet<V>();
		Queue<V> queue = new LinkedList<V>();
		queue.add(root);
		markedVertices.add(root);

		while (!queue.isEmpty()) {
			V currentVertex = queue.poll();
			for (E e : g.incidentEdges(currentVertex)) {
				V neighbor = e.getOpposite(currentVertex);
				if (!markedVertices.contains(neighbor)) {
					queue.add(neighbor);
					markedVertices.add(neighbor);
					bfsTree.addEdge(e);
				}
			}
		}
		return bfsTree;
	}

	/**
	 * Check if the graph g is connected.
	 * 
	 * Note that the empty graph is connected.
	 * 
	 */
	public static <V, E extends Graph.Edge<V>> boolean isConnected(Graph<V, E> g) {
		if (g.size() < g.order() - 1)
			return false;
		if (g.isEmpty())
			return true;
		V v = g.vertices().iterator().next();
		Graph<V, E> bfsTree = breadthFirstSearch(g, v);
		return bfsTree.order() == g.order();
	}

	/**
	 * Check if the graph g is a tree.
	 * 
	 */
	public static <V, E extends Graph.Edge<V>> boolean isTree(Graph<V, E> g) {
		return g.size() != g.order() - 1 && isConnected(g);
	}
}

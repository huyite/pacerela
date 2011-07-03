package graph;

import java.util.Collection;
import java.util.Set;

public interface Graph<V, E extends Graph.Edge<V>> {

	/**
	 * An edge. An edge links two vertices, called respectively the source and
	 * the target of the edge.
	 * 
	 * @param <V>
	 *            the type of vertices
	 */
	public static interface Edge<V> {
		/**
		 * Returns the vertex source of this edge.
		 * 
		 * @return the vertex source of this edge
		 */
		V source();

		/**
		 * Returns the target of the edge.
		 * 
		 * @return the target vertex
		 */
		V target();

		/**
		 * Returns the source if vertex is the target and vice-versa.
		 * 
		 * @param vertex
		 *            a vertex composing the edge
		 * @return the other vertex composing the edge
		 */
		V getOpposite(V vertex);
	}

	/**
	 * Add edge to the graph.
	 * 
	 * @param edge
	 *            the edge to be added to this graph
	 * @return <tt>true</tt> if the edge was added without error
	 * @throws UnsupportedOperationException
	 *             if the <tt>addEdge</tt> operation is not supported by this
	 *             graph
	 * @throws ClassCastException
	 *             if the class of the specified edge prevents it from being
	 *             added to this graph
	 * @throws NullPointerException
	 *             if the specified edge is null
	 * @throws IllegalArgumentException
	 *             if some property of the specified edge prevents it from being
	 *             added to this graph. Especially, the vertices composing the
	 *             edge must belong to the graph.
	 */
	boolean addEdge(E edge);

	/**
	 * Add a vertex into the graph
	 * 
	 * @param vertex
	 *            the vertex to add in the graph
	 * @return true if the vertex was added without error
	 */
	boolean addVertex(V vertex);

	/**
	 * Test if vertex1 and vertex2 are neighbors (e.g: if there is an edge from
	 * vertex1 to vertex2)
	 * 
	 * @param vertex1
	 *            source vertex
	 * @param vertex2
	 *            target vertex
	 * @return true if vertex1 and vertex2 are neighbors else it returns false
	 */
	boolean areNeighbors(V vertex1, V vertex2);

	/**
	 * Remove all edges and vertices in the graph
	 * 
	 * @return void
	 */
	void clear();

	/**
	 * Check if the graph is empty (no vertices)
	 * 
	 * @return true if there is no vertices
	 */
	boolean isEmpty();

	/**
	 * Check if vertex is in the graph.
	 * 
	 * @param vertex
	 *            vertex to check if it is in the graph
	 * @return true if vertex is in the graph
	 */
	boolean containsVertex(V vertex);

	/**
	 * Check if edge is in the graph.
	 * 
	 * @param edge
	 *            edge to check if it is in the graph
	 * @return true if edge is in the graph
	 */
	boolean containsEdge(E edge);

	/**
	 * Remove edge from the graph.
	 * 
	 * @param edge
	 *            edge to remove
	 * @return true if edge was successfully removed from the graph
	 */
	boolean removeEdge(E edge);

	/**
	 * Remove edge from the graph.
	 * 
	 * @param vertex
	 *            vertex to remove
	 * @return true if vertex was successfully removed from the graph
	 */
	boolean removeVertex(V vertex);

	/**
	 * Return all vertices of the graph.
	 * 
	 * @return a Set of vertices in the graph
	 */
	Set<V> vertices();

	/**
	 * return all edges of the graph.
	 * 
	 * @return a Set of edges in the graph
	 */
	Set<E> edges();

	/**
	 * return number of edges of the graph.
	 * 
	 * @return number of edges in the graph
	 */
	int size();

	/**
	 *return number of vertices of the graph.
	 * 
	 *@return number of vertices in the graph
	 */

	int order();

	/**
	 * return degree of a given vertex (outdegree + indegree)
	 * 
	 * @param vertex
	 *            a vertex in the graph
	 * @return outdegree + indegree
	 */
	int degree(V vertex);

	/**
	 * return indegree of a given vertex (number of incoming edges of this
	 * vertex)
	 * 
	 * @param vertex
	 *            a vertex in the graph
	 * @return number of incoming edges
	 */
	int indegree(V vertex);

	/**
	 * return outdegree.
	 * 
	 * @param vertex
	 *            a vertex in the graph
	 * @return number of outgoing edges
	 */
	int outdegree(V vertex);

	/**
	 * Gives incident (incoming + outgoing) edges of a given vertex.
	 * 
	 * @param vertex
	 *            a vertex in the graph
	 * @return iterable containing all incident edges
	 */
	Iterable<E> incidentEdges(V vertex);

	/**
	 * Gives edges linking vertex1 and vertex2
	 * 
	 * @param vertex1
	 *            a vertex in the graph that represent the source
	 * @param vertex2
	 *            a vertex in the graph that represent the target
	 * @return iterable containing all incident edges
	 */
	Iterable<E> incidentEdges(V vertex1, V vertex2);

	/**
	 * Gives incoming edges of a given vertex.
	 * 
	 * @param vertex
	 *            a vertex in the graph
	 * @return iterable containing all incoming edges
	 */
	Iterable<E> incomingEdges(V vertex);

	/**
	 * Gives outgoing edges of a given vertex.
	 * 
	 * @param vertex
	 *            a vertex in the graph
	 * @return iterable containing all outgoing edges
	 */
	Iterable<E> outgoingEdges(V vertex);

	/**
	 * Gives outgoing edges of target linking source to target.
	 * 
	 * @param source
	 *            a vertex in the graph
	 * @param target
	 *            a vertex in the graph
	 * @return iterable containing all outgoing edges linking source to target
	 */
	Iterable<E> outgoingEdges(V source, V target);

	/**
	 * Gives outgoing edges of target linking source to target.
	 * 
	 * @param V
	 * @return iterable containing all outgoing edges
	 */
	Iterable<V> neighbors(V vertex);

	/**
	 * Gives predecessors of a given vertex
	 * 
	 * @param vertex
	 *            a vertex in the graph
	 * @return iterable containing all source vertices where vertex is the
	 *         target
	 */
	Iterable<V> predecessors(V vertex);

	/**
	 * Gives successors of a given vertex
	 * 
	 * @param vertex
	 *            a vertex in the graph
	 * @return iterable containing all target vertices where vertex is the
	 *         source
	 */
	Iterable<V> successors(V vertex);

	/**
	 * Remove all edges from the graph.
	 * 
	 * @param edges
	 *            a Collection of edges in the graph
	 * @return true if all edges are removed successfully
	 */
	boolean removeAllEdges(Collection<E> edges);

	/**
	 * Remove all vertices from the graph.
	 * 
	 * @param vertices
	 *            a Collection of vertices in the graph
	 * @return true if all vertices are removed successfully
	 */
	boolean removeAllVertices(Collection<V> vertices);

	/**
	 * Returns an InducedSubgraph containing all vertices given and edges
	 * linking vertices between them
	 * 
	 * @param vertices
	 *            a Set of vertices which will compose the InducedSubgraph
	 *            returned
	 * @return an InducedSubgraph<V, E> containing all vertices given and edges
	 *         linking vertices between them
	 */
	InducedSubgraph<V, E> inducedSubgraph(Set<V> vertices);

	/**
	 * Returns a PartialGraph containing all edges given and vertices linked by
	 * them
	 * 
	 * @param edges
	 *            a Set of edges which will compose the PartialGraph returned
	 * @return a PartialGraph<V, E> containing all edges given and vertices
	 *         linked by them
	 */
	PartialGraph<V, E> partialGraph(Set<E> edges);

	/**
	 * Returns a Subgraph containing vertices and edges given
	 * 
	 * @param vertices
	 *            a Set of vertices that exists in the graph
	 * @param edges
	 *            a Set of edges that exists in the graph
	 * @return a Subgraph<V, E> containing vertices and edges
	 */
	Subgraph<V, E> subgraph(Set<V> vertices, Set<E> edges);

}

package graph;

import java.util.Collection;

/**
 * All the calls of spanning subgraph's methods become illegal as soon as the
 * super graph has been modified. So an ConcurrentModificationException will be
 * raised in this case.
 */
public interface PartialGraph<V, E extends Graph.Edge<V>> extends Subgraph<V, E> {
	/**
	 * Add an edge if it's already in the super graph. If the edge is not into
	 * the super graph, an IllegalArgumentException will be raised.
	 * 
	 * @param edge
	 *           The edge to add
	 * @exception IllegalArgumentException
	 * 
	 * @return true if the edge was succefuly added
	 */
	boolean addEdge(E edge);

	/**
	 * Unsupported operation, a vertex cannot be added into a spanning subgraph
	 * directly through this method. All the super graph's vertices have to be
	 * into this spanning subgraph. This method call will raise an
	 * UnsupportedOperationException.
	 * 
	 * @param vertex
	 *           The vertex to add
	 * @exception UnsupportedOperationException
	 * 
	 */
	boolean addVertex(V vertex);

	/**
	 * Remove an edge from the spanning subgraph.
	 * 
	 * @param edge
	 *           The edge to remove
	 *           
	 * @return true if the edge was succefuly removed 
	 */
	boolean removeEdge(E edge);

	/**
	 * Unsupported operation, a vertex cannot be removed from a spanning
	 * subgraph directly through this method. All the super graph's vertices
	 * have to be into this spanning subgraph. This method call will raise an
	 * UnsupportedOperationException.
	 * 
	 * @param vertex
	 *           The vertex to remove
	 * @exception UnsupportedOperationException
	 */
	boolean removeVertex(V vertex);

	/**
	 * Remove the whole edges from the spanning subgraph.
	 * 
	 * @param edges
	 *            The edges collection to remove
	 *            
	 * @return true if the collection was succefuly removed
	 */
	boolean removeAllEdges(Collection<E> edges);

	/**
	 * Unsupported operation, vertex cannot be removed from a spanning subgraph
	 * directly through this method. This method call will raise an
	 * UnsupportedOperationException.
	 * 
	 * @param vertices
	 *           The vertices collection to remove
	 * @exception UnsupportedOperationException
	 */
	boolean removeAllVertices(Collection<V> vertices);
}

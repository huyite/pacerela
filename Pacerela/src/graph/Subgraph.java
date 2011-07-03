package graph;


/**
 * All the calls of subgraph's methods become illegal as soon as the super graph
 * has been modified. So a ConcurrentModificationException will be raised in
 * this case.
 */
public interface Subgraph<V, E extends Graph.Edge<V>> extends Graph<V, E> {
	/**
	 * Accessor to the super graph
	 * 
	 * @return The super graph
	 */
	Graph<V, E> supergraph();
}

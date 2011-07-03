package util;

import graph.Graph;
import graph.PartialGraph;

import java.util.Iterator;

public interface RootedSpanningTree<V, E extends Graph.Edge<V>> extends
		PartialGraph<V, E> {

	/**
	 * Children of a vertex in the tree.
	 * 
	 * @param v
	 *            a vertex
	 * @return Iterator of vertices
	 */
	public Iterable<V> children(V v);

	/**
	 * Number of children in the tree for a given vertex.
	 * 
	 * @param v
	 *            a vertex
	 * @return int.
	 */
	public int nChildren(V v);

	/**
	 * Father of a given vertex in the tree.
	 * 
	 * @param v
	 *            a vertex
	 * @return a vertex.
	 */
	public V father(V v);

	/**
	 * Root of the tree.
	 * 
	 * @return a vertex.
	 */
	public V root();

	/**
	 * Check if the vertex v is the root of the tree.
	 * 
	 * @param v
	 *            a vertex
	 * @return v is the root.
	 */
	public boolean isRoot(V v);

	/**
	 * Check if the extremities of the edge are in the tree, but not the edge.
	 * 
	 * @param e
	 *            an edge
	 * @return e is a co-edge.
	 */
	public boolean isCoedge(E e);

	/**
	 * Edges of the graph which are incident to two vertices of the tree, but
	 * not in the tree.
	 * 
	 * @return Iterator on the coedges.
	 */
	public Iterable<E> coedges();

	/**
	 * Vertices which are the neighbors of a given vertex in the graph, but not
	 * in the tree.
	 * 
	 * @param v
	 *            a vertex
	 * @return Iterator of Integer
	 */
	public Iterable<V> coneighbors(V v);

	/**
	 * Number of levels.
	 * Levels are numbered from 0 to nLevels().
	 * 
	 * @return int
	 */
	public int nLevels();

	/**
	 * Vertices belonging to a given level.
	 * 
	 * @param level
	 *            int
	 * @return Iterator.
	 */
	public Iterator<V> levelVertices(int level);

	/**
	 * Level of a given vertex.
	 * 
	 * @param v
	 * @return int. a vertex
	 */
	public int vertexLevel(V v);

	/**
	 * Iterator with one edge from the father of <tt>vertex</tt> in the tree to
	 * <tt>vertex</tt>.
	 * 
	 * Note that the edge may be oriented in the other way (that is
	 * <tt>vertex is the source).
	 */
	public Iterable<E> incomingEdges(V vertex);

	/**
	 * Indegree of <tt>vertex</tt>. 0 for the root, 1 for other vertices.
	 */
	public int indegree(V vertex);

	/**
	 * Outdegree of <tt>vertex</tt>. number of children in the shortest path
	 * tree.
	 */
	public int outdegree(V vertex);

	/**
	 * Edges from <tt>vertex</tt> to its children.
	 * 
	 * Note that the edge may be oriented in the other way (that is
	 * <tt>vertex</tt> is the target).
	 */
	public Iterable<E> outgoingEdges(V vertex);

	/**
	 * Edge from <tt>source</tt> to the <tt>target</tt> or empty if the
	 * <tt>source</tt> is not the faher of the <tt>target</tt>.
	 * 
	 * Note that the edge may be oriented in the other way (that is
	 * <tt>source</tt> is the target).
	 */
	public Iterable<E> outgoingEdges(V source, V target);

	/**
	 * Singleton iterator which returns the father of <tt>vertex</tt> or empty
	 * iterator if <tt>vertex</tt> is the root of the tree.
	 */
	public Iterable<V> predecessors(V vertex);

	/**
	 * Children of <tt>vertex</tt>.
	 */
	public Iterable<V> successors(V vertex);
	
	/**
	 *Change the tree and the root into a partial graph 
	 * @param newTree
	 * 			the new tree
	 * @param newRoot
	 * 			the new root
	 */
	public void changeTreeAndRoot(PartialGraph<V, E> newTree, V newRoot);

}
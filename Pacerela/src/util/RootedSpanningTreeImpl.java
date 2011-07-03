/*
 * Created on 29/03/05
 */
package util;

import graph.Graph;
import graph.InducedSubgraph;
import graph.PartialGraph;
import graph.Subgraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import collections.Iterables;
import collections.Predicate;

public class RootedSpanningTreeImpl<V, E extends Graph.Edge<V>> implements
		RootedSpanningTree<V, E> {

	private Graph<V, E> graph;

	private PartialGraph<V, E> tree;

	private V root;

	/** List of vertices ordered by levels and fathers (vertices having the same
	  *father are contiguous)*/
	private List<V> vertices;

	/** Index of the first vertex of each level in vertices*/
	private int[] firstVertexIndices;

	/** Number of levels*/
	private int nLevels;

	/** Father of each vertex*/
	private Map<V, V> fathers;

	/** Level of each vertex*/
	private Map<V, Integer> verticesLevels;

	/**Index of the first child of each vertex in vertices*/
	private Map<V, Integer> firstChildrenIndices;

	/** Number of children for each vertex*/
	private Map<V, Integer> nChildren;

	/**
	 * Create a rooted spanning tree. Note that the data are not cloned.
	 * 
	 */
	public RootedSpanningTreeImpl(Graph<V, E> graph, PartialGraph<V, E> tree,
			V root) {
		this.graph = graph;
		this.tree = tree;
		this.root = root;
		computeLevels();
	}

	public void changeTreeAndRoot(PartialGraph<V, E> newTree, V newRoot) {
		if (! newTree.supergraph().equals(graph))
			throw new IllegalArgumentException();
		root = newRoot;
		tree = newTree;
		computeLevels();
	}

	private boolean treatChild(V child, V father, int level) {
		if (!verticesLevels.containsKey(child)) {
			fathers.put(child, father);
			verticesLevels.put(child, level);
			vertices.add(child);
			return true;
		}
		return false;
	}

	protected final void computeLevels() {
		int level = 0;
		int n = graph.order();

		List<V> oldVertices = vertices;
		Map<V, Integer> oldFirstChildrenIndices = firstChildrenIndices;
		Map<V, Integer> oldNChildren = nChildren;

		vertices = new ArrayList<V>();
		fathers = new HashMap<V, V>();
		verticesLevels = new HashMap<V, Integer>();
		firstVertexIndices = new int[n + 1];
		firstChildrenIndices = new HashMap<V, Integer>();
		nChildren = new HashMap<V, Integer>();

		vertices.add(root);
		verticesLevels.put(root, 0);
		nChildren.put(root, 0);
		int iqueue = 0;
		firstVertexIndices[0] = 0;
		while (iqueue < n) {
			V v = vertices.get(iqueue++);
			int nc = 0; // number of children of v
			firstChildrenIndices.put(v, vertices.size());
			if (verticesLevels.get(v) == level) {
				level++;
				firstVertexIndices[level] = vertices.size();
			}
			if (oldVertices != null) {
				int first = oldFirstChildrenIndices.get(v);
				for (V child : oldVertices.subList(first, first
						+ oldNChildren.get(v)))
					if (tree.areNeighbors(child, v))
						if (treatChild(child, v, level))
							nc++;
			}
			for (V neighbor : tree.neighbors(v))
				if (treatChild(neighbor, v, level))
					nc++;
			nChildren.put(v, nc);
		}
		nLevels = level;
		System.arraycopy(firstVertexIndices, 0, firstVertexIndices, 0, nLevels);
	}

	private void checkEdge(E e) {
		if (!graph.containsEdge(e))
			throw new IllegalArgumentException();
	}

	private void checkVertex(V v) {
		if (!graph.containsVertex(v))
			throw new IllegalArgumentException();
	}

	public boolean isRoot(V v) {
		checkVertex(v);
		return v.equals(root);
	}

	public Iterable<E> coedges() {
		return Iterables.iterableWithPredicate(graph.edges(),
				new Predicate<E>() {
					public boolean predicate(E e) {
						return isCoedge(e);
					}
				});
	}

	@Override
	public Iterable<V> coneighbors(V vertex) {
		checkVertex(vertex);
		final V v = vertex;
		return Iterables.iterableWithPredicate(graph.neighbors(v),
				new Predicate<V>() {
					public boolean predicate(V w) {
						return !tree.areNeighbors(v, w);
					}
				});
	}

	@Override
	public boolean isCoedge(E e) {
		checkEdge(e);
		return !tree.containsEdge(e);
	}

	@Override
	public Iterator<V> levelVertices(int level) {
		int toIndex = level < nLevels ? firstVertexIndices[level + 1]
				: vertices.size();
		return vertices.subList(firstVertexIndices[level], toIndex).iterator();
	}

	@Override
	public int nLevels() {
		return nLevels;
	}

	@Override
	public int vertexLevel(V v) {
		checkVertex(v);
		return verticesLevels.get(v);
	}

	@Override
	public Iterable<V> children(V v) {
		checkVertex(v);
		int first = firstChildrenIndices.get(v);
		return vertices.subList(first, first + nChildren.get(v));
	}

	@Override
	public V father(V v) {
		checkVertex(v);
		return fathers.get(v);
	}

	@Override
	public int nChildren(V v) {
		checkVertex(v);
		return nChildren.get(v);
	}

	@Override
	public V root() {
		return root;
	}

	@Override
	public boolean addEdge(E edge) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addVertex(V vertex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean areNeighbors(V vertex1, V vertex2) {
		return tree.areNeighbors(vertex1, vertex2);
	}

	@Override
	public void clear() {
		tree.clear();
	}

	@Override
	public boolean containsEdge(E edge) {
		return tree.containsEdge(edge);
	}

	@Override
	public boolean containsVertex(V vertex) {
		return tree.containsVertex(vertex);
	}

	@Override
	public int degree(V vertex) {
		return tree.degree(vertex);
	}

	@Override
	public Set<E> edges() {
		return tree.edges();
	}

	@Override
	public Iterable<E> incidentEdges(V vertex) {
		return tree.incidentEdges(vertex);
	}

	@Override
	public Iterable<E> incidentEdges(V vertex1, V vertex2) {
		return tree.incidentEdges(vertex1, vertex2);
	}

	@Override
	public Iterable<E> incomingEdges(V vertex) {
		return isRoot(vertex) ? Iterables.<E> emptyIterable() : tree
				.incidentEdges(vertex, father(vertex));
	}

	@Override
	public int indegree(V vertex) {
		checkVertex(vertex);
		return isRoot(vertex) ? 0 : 1;
	}

	@Override
	public InducedSubgraph<V, E> inducedSubgraph(Set<V> vertices) {
		return tree.inducedSubgraph(vertices);
	}

	@Override
	public boolean isEmpty() {
		return tree.isEmpty();
	}

	@Override
	public Iterable<V> neighbors(V vertex) {
		return tree.neighbors(vertex);
	}

	@Override
	public int order() {
		return tree.order();
	}

	@Override
	public int outdegree(V vertex) {
		checkVertex(vertex);
		return nChildren(vertex);
	}

	@Override
	public Iterable<E> outgoingEdges(V vertex) {
		final V v = vertex;
		return Iterables.iterableWithPredicate(tree.incidentEdges(vertex),
				new Predicate<E>() {
					public boolean predicate(E e) {
						return !e.getOpposite(v).equals(father(v));
					}
				});
	}

	@Override
	public Iterable<E> outgoingEdges(V source, V target) {
		if (isRoot(target) || !father(target).equals(source))
			return Iterables.<E> emptyIterable();
		else
			return tree.incidentEdges(source, target);
	}

	@Override
	public PartialGraph<V, E> partialGraph(Set<E> edges) {
		return tree.partialGraph(edges);
	}

	@Override
	public Iterable<V> predecessors(V vertex) {
		return isRoot(vertex) ? Iterables.<V> emptyIterable() : Iterables
				.singleton(father(vertex));
	}

	@Override
	public boolean removeAllEdges(Collection<E> edges) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAllVertices(Collection<V> vertices) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeEdge(E edge) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeVertex(V vertex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return tree.size();
	}

	@Override
	public Subgraph<V, E> subgraph(Set<V> vertices, Set<E> edges) {
		return tree.subgraph(vertices, edges);
	}

	@Override
	public Iterable<V> successors(V vertex) {
		return children(vertex);
	}

	@Override
	public Set<V> vertices() {
		return tree.vertices();
	}

	@Override
	public Graph<V, E> supergraph() {
		return graph;
	}

	@Override
	public String toString() {
		return tree.toString();
	}

	@Override
	public boolean equals(Object o) {
		return tree.equals(o);
	}

	@Override
	public int hashCode() {
		return tree.hashCode();
	}

}
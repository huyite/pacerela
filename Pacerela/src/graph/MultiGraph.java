package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import collections.Iterables;
import collections.Iterators;

public class MultiGraph<V, E extends Graph.Edge<V>> implements Graph<V, E> {

	private Edges edges = new Edges();

	/*
	 * These maps contains all the edges of the graph, organized in different
	 * ways.
	 * 
	 * In outgoingMultiEdges, edges are grouped following their two extremities.
	 * 
	 * In outgoingEdges (resp. incomingEdges) edges are grouped following their
	 * origin (resp. target).
	 * 
	 * In incident edges, edges are grouped following each of their extremities.
	 * That means that this map contains each edge twice except for loops which
	 * are contained only once.
	 */
	private Map<V, Map<V, Set<E>>> outgoingMultiEdges = new HashMap<V, Map<V, Set<E>>>();
	private Map<V, Set<E>> outgoingEdges = new HashMap<V, Set<E>>();
	private Map<V, Set<E>> incomingEdges = new HashMap<V, Set<E>>();
	private Map<V, Set<E>> incidentEdges = new HashMap<V, Set<E>>();

	private Vertices vertices = new Vertices();
	// There is no map successors because the sets of successors are given by
	// outgoingMultiEdges.get(v).keySet()
	private Map<V, Set<V>> predecessors = new HashMap<V, Set<V>>();
	private Map<V, Set<V>> neighbors = new HashMap<V, Set<V>>();

	/* To avoid concurrent modification on subgraphs. */
	int modCount = 0;

	private class VertexOrEdgeIterator<T> implements Iterator<T> {

		Iterator<T> delegate;

		VertexOrEdgeIterator(Iterator<T> it) {
			delegate = it;
		}

		VertexOrEdgeIterator(Iterable<T> iterable) {
			delegate = iterable.iterator();
		}

		public boolean hasNext() {
			return delegate.hasNext();
		}

		public T next() {
			return delegate.next();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private class VertexOrEdgeIterable<T> implements Iterable<T> {

		Iterable<T> delegate;

		VertexOrEdgeIterable(Iterable<T> iterable) {
			delegate = iterable;
		}

		@Override
		public Iterator<T> iterator() {
			return new VertexOrEdgeIterator<T>(delegate);
		}
	}

	/**
	 * The remove operation with an edge iterator is not supported so an
	 * unsupported operation exception will be raised in this case.
	 * 
	 * @exception UnsupportedOperationException.
	 */
	private class EdgeIterator extends VertexOrEdgeIterator<E> {

		EdgeIterator(Iterator<E> it) {
			super(it);
		}

		EdgeIterator(Iterable<E> iterable) {
			super(iterable);
		}
	}

	private class EdgeIterable extends VertexOrEdgeIterable<E> {
		EdgeIterable(Iterable<E> iterable) {
			super(iterable);
		}
	}

	/**
	 * The remove operation with a vertex iterator is not supported so an
	 * unsupported operation exception will be raised in this case.
	 * 
	 * @exception UnsupportedOperationException.
	 */
	private class VertexIterator extends VertexOrEdgeIterator<V> {

		VertexIterator(Iterator<V> it) {
			super(it);
		}

		VertexIterator(Iterable<V> iterable) {
			super(iterable);
		}
	}

	private class VertexIterable extends VertexOrEdgeIterable<V> {
		VertexIterable(Iterable<V> iterable) {
			super(iterable);
		}
	}

	private class Edges extends HashSet<E> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * If the argument is not valid (source vertex and target vertex of the
		 * Edge e do not belong to the graph) an illegal argument exception will
		 * be raised.
		 * 
		 * @param E
		 * @exception IllegalArgumentException.
		 */

		protected void checkArgument(E e) {
			if (!(vertices.contains(e.source()) && vertices
					.contains(e.target())))
				throw new IllegalArgumentException();
		}

		public boolean add(E e) {
			checkArgument(e);
			boolean modified = super.add(e);
			if (modified) {
				modCount++;
				V source = e.source();
				V target = e.target();
				if (!outgoingMultiEdges.get(source).containsKey(target)) {
					Set<E> me = new HashSet<E>();
					outgoingMultiEdges.get(source).put(target, me);
					me.add(e);
					predecessors.get(target).add(source);
					neighbors.get(source).add(target);
					neighbors.get(target).add(source);
				}
				outgoingEdges.get(source).add(e);
				incomingEdges.get(target).add(e);
				incidentEdges.get(source).add(e);
				incidentEdges.get(target).add(e);

			}
			return modified;
		}

		@SuppressWarnings("unchecked")
		public boolean remove(Object o) {
			boolean modified = super.remove(o);
			if (modified) {
				modCount++;
				Graph.Edge<V> e = (Graph.Edge<V>) o;
				V source = e.source();
				V target = e.target();
				Set<E> me = outgoingMultiEdges.get(source).get(target);
				if (me.size() > 1)
					me.remove(e);
				else {
					outgoingMultiEdges.get(source).remove(target);
					predecessors.get(target).remove(source);
					neighbors.get(source).remove(target);
					neighbors.get(target).remove(source);
				}
				outgoingEdges.get(source).remove(e);
				incomingEdges.get(target).remove(e);
				incidentEdges.get(source).remove(e);
				incidentEdges.get(target).remove(e);
			}
			return modified;
		}

		public void clear() {
			for (Object o : toArray())
				remove(o);
		}

		public Iterator<E> iterator() {
			return new EdgeIterator(super.iterator());
		}
	}

	private class Vertices extends HashSet<V> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public boolean add(V v) {
			boolean modified = super.add(v);
			if (modified) {
				modCount++;
				outgoingMultiEdges.put(v, new HashMap<V, Set<E>>());
				outgoingEdges.put(v, new HashSet<E>());
				incomingEdges.put(v, new HashSet<E>());
				incidentEdges.put(v, new HashSet<E>());
				predecessors.put(v, new HashSet<V>());
				neighbors.put(v, new HashSet<V>());
			}
			return modified;
		}

		public boolean remove(Object o) {
			boolean modified = super.remove(o);
			if (modified) {
				modCount++;
				List<E> edgesToRemove = new ArrayList<E>(incidentEdges.get(o));
				for (E e : edgesToRemove)
					edges.remove(e);
				outgoingMultiEdges.remove(o);
				outgoingEdges.remove(o);
				incomingEdges.remove(o);
				incidentEdges.remove(o);
				predecessors.remove(o);
				neighbors.remove(o);
			}
			return modified;
		}

		public void clear() {
			for (Object o : toArray())
				remove(o);
		}

		public Iterator<V> iterator() {
			return new VertexIterator(super.iterator());
		}
	}

	public MultiGraph() {
	}

	/** 
	 * Create a new MultiGraph with same vertices and edges than g. 
	 **/
	public MultiGraph(Graph<V, E> g) {
		this();
		for (V v : g.vertices())
			addVertex(v);
		for (E e : g.edges())
			addEdge(e);
	}

	public boolean addEdge(E edge) {
		return edges.add(edge);
	}

	public boolean addVertex(V vertex) {
		return vertices.add(vertex);
	}

	private void checkExistingVertex(Object o) {
		if (!vertices.contains(o))
			throw new IllegalArgumentException();
	}

	public boolean areNeighbors(V vertex1, V vertex2) {
		checkExistingVertex(vertex1);
		checkExistingVertex(vertex2);
		return neighbors.get(vertex1).contains(vertex2);
	}

	public void clear() {
		vertices.clear();
	}

	public boolean containsEdge(E edge) {
		return edges.contains(edge);
	}

	public boolean containsVertex(V vertex) {
		return vertices.contains(vertex);
	}

	/**
	 * The degree of a vertex is the number of incident edges, where a loop is
	 * counted twice.
	 */
	public int degree(V vertex) {
		checkExistingVertex(vertex);
		return outgoingEdges.get(vertex).size()
				+ incomingEdges.get(vertex).size();
	}

	public Set<E> edges() {
		return edges;
	}

	public Iterable<E> incidentEdges(V vertex) {
		checkExistingVertex(vertex);
		return new EdgeIterable(incidentEdges.get(vertex));
	}

	public Iterable<E> incidentEdges(V vertex1, V vertex2) {
		checkExistingVertex(vertex1);
		if (vertex1.equals(vertex2))
			return outgoingEdges(vertex1, vertex1);
		else {
			checkExistingVertex(vertex2);
			return Iterables.append(outgoingEdges(vertex1, vertex2),
					outgoingEdges(vertex2, vertex1));
		}
	}

	public Iterable<E> incomingEdges(V vertex) {
		checkExistingVertex(vertex);
		return new EdgeIterable(incomingEdges.get(vertex));
	}

	public int indegree(V vertex) {
		checkExistingVertex(vertex);
		return incomingEdges.get(vertex).size();
	}

	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	public Iterable<V> neighbors(V vertex) {
		checkExistingVertex(vertex);
		return new VertexIterable(neighbors.get(vertex));
	}

	public int order() {
		return vertices.size();
	}

	public int outdegree(V vertex) {
		checkExistingVertex(vertex);
		return outgoingEdges.get(vertex).size();
	}

	public Iterable<E> outgoingEdges(V vertex) {
		checkExistingVertex(vertex);
		return new EdgeIterable(outgoingEdges.get(vertex));
	}

	public Iterable<E> outgoingEdges(V vertex1, V vertex2) {
		checkExistingVertex(vertex1);
		checkExistingVertex(vertex2);
		final V v1 = vertex1;
		final V v2 = vertex2;
		return new Iterable<E>() {

			public Iterator<E> iterator() {
				return outgoingMultiEdges.get(v1).containsKey(v2) ? new EdgeIterator(
						outgoingMultiEdges.get(v1).get(v2))
						: Iterators.<E> emptyIterator();

			}

		};
	}

	public Iterable<V> predecessors(V vertex) {
		checkExistingVertex(vertex);
		return new VertexIterable(predecessors.get(vertex));
	}

	public boolean removeAllEdges(Collection<E> edges) {
		boolean modified = false;
		for (Object o : edges.toArray())
			modified |= this.edges.remove(o);
		return modified;
	}

	public boolean removeAllVertices(Collection<V> vertices) {
		boolean modified = false;
		for (Object o : vertices.toArray())
			modified |= this.vertices.remove(o);
		return modified;
	}

	public boolean removeEdge(E edge) {
		return edges.remove(edge);
	}

	public boolean removeVertex(V vertex) {
		return vertices.remove(vertex);
	}

	public int size() {
		return edges.size();
	}

	public Iterable<V> successors(V vertex) {
		checkExistingVertex(vertex);
		return new VertexIterable(outgoingMultiEdges.get(vertex).keySet());
	}

	public Set<V> vertices() {
		return vertices;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(order() + " vertices + " + size() + " edges" + "\n");
		for (V v : vertices) {
			sb.append(v + " : ");
			for (E e : outgoingEdges(v)) {
				sb.append(e + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((edges == null) ? 0 : edges.hashCode());
		result = prime * result
				+ ((vertices == null) ? 0 : vertices.hashCode());
		return result;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Graph<?, ?>))
			return false;
		Graph<?, ?> g = (Graph<?, ?>) o;
		return g.vertices().equals(vertices) && g.edges().equals(edges);
	}

	@Override
	public InducedSubgraph<V, E> inducedSubgraph(Set<V> vertices) {
		return new InducedSubMultiGraph<V, E>(this, vertices);
	}

	@Override
	public PartialGraph<V, E> partialGraph(Set<E> edges) {
		return new PartialMultiGraph<V, E>(this, edges);
	}

	@Override
	public Subgraph<V, E> subgraph(Set<V> vertices, Set<E> edges) {
		return new SubMultiGraph<V, E>(this, vertices, edges);
	}
}
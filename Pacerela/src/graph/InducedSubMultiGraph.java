package graph;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

class InducedSubMultiGraph<V, E extends Graph.Edge<V>> extends
		SubMultiGraph<V, E> implements InducedSubgraph<V, E> {

	@SuppressWarnings("unchecked")
	public InducedSubMultiGraph(MultiGraph<V, E> supergraph, Set<V> vertices) {
		super(supergraph, vertices, (Set<E>) Collections.EMPTY_SET);
		for (V v : vertices) {
			for (E e : supergraph.outgoingEdges(v)) {
				if (vertices.contains(e.target()))
					super.addEdge(e);
			}
		}
	}

	@Override
	public boolean addEdge(E edge) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addVertex(V vertex) {
		if (super.addVertex(vertex)) {
			for (E e : supergraph.incidentEdges(vertex)) {
				if (vertices().contains(e.getOpposite(vertex))) {
					super.addEdge(e);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAllEdges(Collection<E> edges) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeEdge(E edge) {
		throw new UnsupportedOperationException();
	}
}

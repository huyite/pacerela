package util;

import graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Coloring {

	/**
	 * Compute a (improper) coloring of graph g using colors from the array
	 * colors.
	 * 
	 * @param <V>
	 *            type of vertices
	 * @param <E>
	 *            type of edges
	 * @param <C>
	 *            type of colors
	 * @param g
	 *            graph to color
	 * @param colors
	 *            used for coloring
	 * @return a map which associate a color to each vertex. The coloring may be
	 *         improper, meaning that two neighbors may have the same color.
	 */
	public static <V, E extends Graph.Edge<V>, C> Map<V, C> computeImproperColoring(
			Graph<V, E> g, C[] colors) {
		Map<V, C> coloring = new HashMap<V, C>();
		Map<V, int[]> used = new HashMap<V, int[]>();
		for (V v : g.vertices()) {
			used.put(v, new int[colors.length]);
		}
		for (V v : g.vertices()) {
			int min = 0;
			// We compute the color less used by the neighbors of v.
			for (int c = 1; c < colors.length && used.get(v)[min] > 0; c++) {
				if (used.get(v)[min] > used.get(v)[c]) {
					min = c;
				}
			}
			coloring.put(v, colors[min]);
			for (V w : g.neighbors(v)) {
				used.get(w)[min]++;
			}
		}
		return coloring;
	}

	/**
	 * Compute a (proper) coloring of graph g. This coloring may be not optimal
	 * (using more colors than necessary).
	 * 
	 * @param <V>
	 *            type of vertices
	 * @param <E>
	 *            type of edges
	 * @param g
	 *            graph to color
	 * @return a map which associate a color (int) to each vertex. The coloring
	 *         is proper, meaning that two neighbors may not have the same
	 *         color.
	 */
	public static <V, E extends Graph.Edge<V>> Map<V, Integer> computeColoring(
			Graph<V, E> g) {
		Map<V, Integer> coloring = new HashMap<V, Integer>();
		List<Boolean> used = new ArrayList<Boolean>();
		// for each vertex v, we use the smallest color unused by its neighbors.
		for (V v : g.vertices()) {
			// compute used for vertex v :
			// used(i) = true if the color i < degree(v) is used by the
			// neighbors of v
			for (int i = 0; i < g.degree(v); i++)
				used.add(i, false);
			for (V w : g.neighbors(v))
				if (coloring.containsKey(w))
					used.set(coloring.get(w), true);
			int min = 0;
			while (min < g.degree(v) && used.get(min))
				min++;
			coloring.put(v, min);
		}
		return coloring;
	}
}

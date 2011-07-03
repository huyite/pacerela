
package swing;

public class Labeling {

	private String labels[] = null;

	private static final String EMPTY = "";

	/**
	 * Create a new Labeling. By default, a label is empty.
	 * 
	 * @param nVertices
	 *            un int
	 */
	public Labeling(int nVertices) {
		labels = new String[nVertices];
		for (int i = 0; i < nVertices; i++) {
			labels[i] = EMPTY;
		}
	}

	/**
	 * Allows to change the value of the label of a given vertex.
	 * 
	 * @param v
	 *            a vertex
	 * @param newLabel
	 *            String
	 */
	public void changeLabel(int v, String newLabel) {
		labels[v] = newLabel;
	}

	/**
	 * Label of a given vertex.
	 * 
	 * @return the label of v
	 * @param v
	 *            a vertex
	 */
	public String vertexLabel(int v) {
		return labels[v];
	}

	/**
	 * Labels of the graph
	 * 
	 * @return the array of the labels. Changing this array will not affect the
	 *         labeling.
	 */
	public String[] labels() {
		String[] copy = new String[labels.length];
		System.arraycopy(labels, 0, copy, 0, labels.length);
		return copy;
	}

}

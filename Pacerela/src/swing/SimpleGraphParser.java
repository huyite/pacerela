
package swing;

import java.io.BufferedReader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import graph.DirectedEdge;
import graph.Graph.Edge;


/**
 * @author baudon
 *
 * Graph parser using Astrolabe's Simple Graph format
 */
public class SimpleGraphParser implements LabeledGraphParser {

       @SuppressWarnings("unchecked")
	private Edge[] edges;

       private Labeling labels;

       private int nVertices;

       private static final boolean DEBUG = false;

       private void printDebug(String s) {
               if (DEBUG) {
                       System.out.println(s);
               }
       }

       public SimpleGraphParser() {
       }

       public String fileSuffix() {
               return "asg";
       }

       @SuppressWarnings("unchecked")
	public Edge[] edges() {
               return edges;
       }

       public Labeling labels() {
               return labels;
       }

       public int numberOfVertices() {
               return nVertices;
       }

       public void parse(BufferedReader in) throws UnsupportedEncodingException {
               try {
                       String line = in.readLine();
                       StreamTokenizer st = new StreamTokenizer(new StringReader(line));
                       st.nextToken();
                       nVertices = (int) st.nval;
                       printDebug(nVertices + " vertices");
                       labels = new Labeling(nVertices);
                       st.nextToken();
                       int nEdges = (int) st.nval;
                       printDebug(nEdges + " edges");
                       edges = new Edge[nEdges * 2];
                       int indexEdge = 0;
                       for (int i = 0; i < nVertices; i++) {
                               line = in.readLine();
                               printDebug("parsing line \"" + line + "\"");
                               st = new StreamTokenizer(new StringReader(line));
                               st.nextToken();
                               while (st.ttype != StreamTokenizer.TT_EOF) {
                                       int v = (int) st.nval;
                                       edges[indexEdge++] = new DirectedEdge<Integer>(i, v);
                                       printDebug("edge " + (indexEdge - 1) + " : " + i + " -> "
                                                       + v);
                                       st.nextToken();
                               }
                       }
                       for (int i = 0; i < nVertices; i++) {
                               String s = in.readLine();
                               printDebug("label vertex " + i + " \"" + s + "\"");
                               labels.changeLabel(i, s);
                       }
               } catch (Exception e) {
                       throw new UnsupportedEncodingException(e.getMessage());
               }
       }
}


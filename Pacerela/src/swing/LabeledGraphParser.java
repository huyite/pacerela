
package swing;

import graph.Graph.Edge;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;


/**
 * Parser for reading a graph
 */
public interface LabeledGraphParser {

       /**
        * Returns the suffix used for graph files recognized by this parser
        *
        * @return the suffix
        */
       public String fileSuffix();

       public void parse(BufferedReader in) throws UnsupportedEncodingException;

       public int numberOfVertices();

       public Labeling labels();

       @SuppressWarnings("unchecked")
	public Edge[] edges();

}
/*
 * Created on 19 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

//============================================================================
// Name        : Model.java
// Author      : SoftwareGroup3
// Version     : 0.1
// Copyright   : 
// Description : 
//============================================================================
package graph;

import java.util.ArrayList;

import util.RootedSpanningTree;
/**
 * Each Model has a spanning tree and vertices , edges 
 * 
 *
 */
public class Model {
	public ArrayList<Vertex> vertices;
	public ArrayList<DirectedEdge<Vertex>> edges;
	public RootedSpanningTree<String, ?> SpanningTree;
	public Model()
	{
		vertices=new ArrayList<Vertex>() ;
		edges=new ArrayList<DirectedEdge<Vertex>>();
	}
	public Vertex getRoot()
	{
		
		String root=this.SpanningTree.root();
		return getVertex(root);
	}
	public Vertex getVertex(String ver)
	{
		Vertex vertex = null;
		for(Vertex v:this.vertices)
		{
			String s=v.getVertex();
			if (ver.equals(s))
				vertex=v;
			
		}
		return vertex;
	}
	

}

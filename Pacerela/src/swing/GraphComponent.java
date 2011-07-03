//============================================================================
// Name        : GraphComponent.java
// Author      : SoftwareGroup3
// Version     : 0.1
// Copyright   : 
// Description : 
//============================================================================
package swing;

import graph.DirectedEdge;
import graph.Graph;
import graph.Graphs;
import graph.Model;
import graph.ModelTransition;
import graph.Vertex;
import graph.Graph.Edge;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;
import util.RootedSpanningTreeImpl;
public class GraphComponent extends JComponent implements MouseInputListener {
	/*
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Vertex> verticesCurrent = new ArrayList<Vertex>();
	private ArrayList<DirectedEdge<Vertex>> edgesCurrent = new ArrayList<DirectedEdge<Vertex>>();
	
	private ArrayList<Vertex> verticesNew = new ArrayList<Vertex>();
	private ArrayList<DirectedEdge<Vertex>> edgesNew = new ArrayList<DirectedEdge<Vertex>>();
	
	private ArrayList<Vertex> verticesOld = new ArrayList<Vertex>();
	private ArrayList<DirectedEdge<Vertex>> edgesOld = new ArrayList<DirectedEdge<Vertex>>();
	

	private double x,y;
	private double phi=2*Math.PI/3;
	private RootedSpanningTreeImpl<String, ?> SpanningTreeOld;
	private RootedSpanningTreeImpl<String, ?> SpanningTreeNew;
	private Graph<String, Graph.Edge<String>> graph;
	private Vertex rootNew=null;
	private boolean withoutCircle;
	public GraphComponent()
	{
		addMouseListener(this);
	}
	/**
	 * paint vertices and edges  
	 * 
	 *
	 */
	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		g.setColor(getForeground());
		Graphics2D g2 = (Graphics2D) g;
		int i=1;
		try{
		for (Vertex v:verticesCurrent){
		   switch(i)
			{
			     case 1:
			    	 v.setColor(Color.CYAN);
			    	 break;
			     case 2:
			    	 v.setColor(Color.RED);
			    	 break;
			     case 3:
			    	 v.setColor(Color.GREEN);
			    	 break;
			     case 5:
			    	 v.setColor(Color.PINK);
			    	 i=0;
			    	 break;
			}
			v.drawVertex(g2);
			i++;
			
		} 
		}catch(Exception e) { }
		Line2D line=new Line2D.Double();
		try{
		for(DirectedEdge<Vertex> e:edgesCurrent)
		{
			line.setLine(new Point2D.Double(e.source().getPosition().getX(),e.source().getPosition().getY()),
					       new Point2D.Double(e.target().getPosition().getX(),e.target().getPosition().getY()));
			g2.draw(line);
		
		}
		}catch(Exception e) { }
	}
	/**
	 * remove circle on vertices   
	 * 
	 *
	 */
	public void withoutCircle(boolean withoutCircle)
	{
		this.withoutCircle=withoutCircle;
		for(Vertex v:this.verticesCurrent)
		{
			v.setWithoutCircle(withoutCircle);
		}
		
		this.repaint();
	}
	public void Drawchild(double alpha,double radius,Vertex v,Vertex rootVertex)
	{
		int n=this.SpanningTreeNew.nChildren(v.getVertex());
		Vertex vtemp;
		DirectedEdge<Vertex> edge;
		if(n>0)
		{
			Iterator<String> a=this.SpanningTreeNew.children(v.getVertex()).iterator();	
			String labelV=null;
			double x,y=0;
			int i=1;
			double beta=0;
			double gama=0;
			double theta=0;
			double tempAng=Math.PI/2 -phi/2+(phi)/n+phi/(2*n);
			double tempRadius=radius;
			double ang=.0;
			while(a.hasNext())
			{
				radius=tempRadius;
				beta=Math.PI/2 -phi/2+(phi*(i-1))/n+phi/(2*n);
				theta=tempAng+(beta-tempAng)/2;
					
					
				if(theta<Math.PI/2)
				{
					ang=alpha-(Math.PI/2-theta);
				}else
					ang=alpha+(theta-Math.PI/2);
				
				if(beta<Math.PI/2)
				{
					gama=alpha-(Math.PI/2-beta);
										
				}else 
				{
					gama=alpha+(beta-Math.PI/2);
				
					
				}
				labelV=a.next();
				
				x= (v.getPosition().getX()+ radius*Math.cos(gama));
			    y= (v.getPosition().getY()+ radius*Math.sin(gama));
				vtemp = new Vertex();
				vtemp.setWithoutCircle(this.withoutCircle);
			    vtemp.setVertex(labelV);
				vtemp.setPosition(new Point2D.Double(x,y));
				
			    i++;
			    /////////
				if(n==1)
					radius=radius/2;
					else
					{
						x=(v.getPosition().getX()+radius*Math.cos(ang));
						y=(v.getPosition().getY()+radius*Math.sin(ang));
						radius=vtemp.getPosition().distance(new Point2D.Double(x,y));
					}
				
				vtemp.setRadius(radius);
				vtemp.setRadian(gama);
				vtemp.setRadiuspolar(vtemp.getPosition().distance(rootVertex.getPosition()));
				vtemp.setRadianpolar(this.anglePolar(vtemp.getPosition().getX(), vtemp.getPosition().getY()
						, rootVertex.getPosition().getX(),rootVertex.getPosition().getY()));
				verticesNew.add(vtemp);
				edge=new DirectedEdge<Vertex>(v,vtemp);
				edgesNew.add(edge);
				
				tempAng=beta;
				//////////
			    Drawchild(gama,radius,vtemp,rootVertex);
			}
			
		} 
		
	}
	/**
	 * Load graph  
	 * 
	 *
	 */
	@SuppressWarnings("unchecked")
	public void loadGraph(Graph<String, Graph.Edge<String>> graph,Vertex root)
	{
		this.graph=graph;
		EraseList(verticesCurrent);
		EraseList(edgesCurrent);
		EraseList(verticesOld);
		EraseList(edgesOld);
		EraseList(verticesNew);
		EraseList(edgesNew);
		DrawTree(this.graph,root);
		this.verticesCurrent=(ArrayList<Vertex>) this.verticesNew.clone();
		this.edgesCurrent= (ArrayList<DirectedEdge<Vertex>>) this.edgesNew.clone();
		this.verticesOld=(ArrayList<Vertex>) this.verticesNew.clone();
		this.edgesOld=(ArrayList<DirectedEdge<Vertex>>) this.edgesNew.clone();
		this.SpanningTreeOld=this.SpanningTreeNew;
		repaint();
	}
	/**
	 * compute polar angle of vertex
	 * 
	 *
	 */
	public double anglePolar(double x,double y,double x0,double y0)
	{
		return Math.atan2((y-y0),(x-x0));
	}
	/**
	 * Compute coordinate of vertices  
	 * (Algorithm 1)
	 *
	 */
	public void DrawTree(Graph<String, Graph.Edge<String>> graph,
				Vertex rootVertex) {
		       	double x0,y0=0;
				x0=rootVertex.getPosition().getX();
				y0=rootVertex.getPosition().getY();
				double r=rootVertex.getRadius();
				Vertex v;
				
				verticesNew.add(rootVertex);
				this.SpanningTreeNew=new RootedSpanningTreeImpl<String, Edge<String>>(graph
						,Graphs.breadthFirstSearch(graph, rootVertex.getVertex())
						,rootVertex.getVertex());;
				double alpha=0;
				int i=1;
				 Iterator<String> a=this.SpanningTreeNew.successors(rootVertex.getVertex()).iterator();
				int m=this.SpanningTreeNew.nChildren(rootVertex.getVertex());
				double tempAng=0;
				double tempRadius=r;
				DirectedEdge<Vertex> edge;
				while(a.hasNext()){
					r=tempRadius;
					String vertex =a.next();
					alpha=2*Math.PI*i/m;
					x=x0+r*Math.cos(alpha);
					y=y0+r*Math.sin(alpha);
					v = new Vertex();
					v.setWithoutCircle(this.withoutCircle);
					v.setVertex(vertex);
					v.setPosition(new Point2D.Double(x,y));
					i++;
					if(m==1)
						  r=r/2;
						else
						{
							x=x0+r*Math.cos(tempAng+(alpha-tempAng)/2);
							y=y0+r*Math.sin(tempAng+(alpha-tempAng)/2);
							r=v.getPosition().distance(new Point2D.Double(x,y));
						}
					v.setRadian(alpha);
					v.setRadius(r);
					v.setRadiuspolar(v.getPosition().distance(rootVertex.getPosition()));
					v.setRadianpolar(this.anglePolar(v.getPosition().getX(), v.getPosition().getY()
							, rootVertex.getPosition().getX(),rootVertex.getPosition().getY()));
					verticesNew.add(v);
					edge=new DirectedEdge<Vertex>(rootVertex,v);
					edgesNew.add(edge);
					
					tempAng=alpha;
						
					Drawchild(alpha,r,v,rootVertex);
					
				}
				
						
		}
	/**
	 * when change root, it will determine Model Dt from   
	 * new Model and old Model 
	 *
	 */

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		boolean flagAnimation=false;
		 for(Vertex v:this.verticesCurrent)
		{
			
			if(v.contains(arg0.getX(), arg0.getY()))
			{
				rootNew=new Vertex();
				rootNew.setVertex(v.getVertex());
				rootNew.setRadius(100);
				rootNew.setPosition(new Point2D.Double(400,400));
				flagAnimation=true;
				
				}
			if(flagAnimation)
			{ Thread animation= new Thread(){
				 @SuppressWarnings("unchecked")
					public  void run(){
						 double[] a= {0.05,0.1,0.15,0.2,0.3,0.4,0.5,0.8,0.85,0.9,0.95,1.};
				
						 for(double i:a)
						 {
						
					     try {
							Thread.sleep(300);
							 EraseList(verticesCurrent);
							 EraseList(edgesCurrent);
							 Model modelOld=new Model();
							 
							 modelOld.vertices=verticesOld;
							 modelOld.edges= edgesOld;
							 modelOld.SpanningTree=SpanningTreeOld;
							 
							 
							 Model modelNew=new Model();
							 EraseList(verticesNew);
							 EraseList(edgesNew);
							 DrawTree(graph, rootNew);
						     modelNew.vertices=verticesNew;
						     modelNew.edges=edgesNew;
						     modelNew.SpanningTree=SpanningTreeNew;
						     
						     ModelTransition modelTr=new  ModelTransition (modelNew,modelOld);
						     modelTr.setWithoutcircle(GraphComponent.this.withoutCircle);
						     modelTr.setparaTransit(i);
						     Model modT=modelTr.getModel();
						     
						     verticesCurrent=modT.vertices;
						     edgesCurrent=modT.edges;
						  
						     repaint();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					     }
						verticesOld=(ArrayList<Vertex>) verticesCurrent.clone();
						edgesOld =(ArrayList<DirectedEdge<Vertex>>) edgesCurrent.clone();
						 SpanningTreeOld=SpanningTreeNew;
					 }
				 };
				 animation.start();
				 }
		}
		
        
         
	}
	/**
	 * remove all element of Array   
	 * @param ArrayList a
	 *
	 */
    public void EraseList(ArrayList<?> a)
    {
    	while(!a.isEmpty())
    	a.remove(0);
    }
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
				
	
	}


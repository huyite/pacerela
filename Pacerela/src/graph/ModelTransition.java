//============================================================================
// Name        : ModelTransition.java
// Author      : SoftwareGroup3
// Version     : 0.1
// Copyright   : 
// Description : 
//============================================================================
package graph;
/**
 * ModelTransittion is used to compute Model Dt from new Model and old Model   
 * 
 *
 */
import java.awt.geom.Point2D;
import java.util.Iterator;

public class ModelTransition {
	private Model modelCurrent;
	private Model modelNew;
	private Model modelOld;
	private boolean withoutcircle;
	private double paratransit=.0;
	public void setWithoutcircle(boolean c)
	{
		this.withoutcircle=c;
	}
	public Model getModel()
	{
		return this.modelCurrent;
	}
    public ModelTransition(Model modelNew,Model modelOld)
    {
    	modelCurrent=new Model();
    	this.modelNew=modelNew;
    	this.modelOld=modelOld;
    	   	
    }
    public void setparaTransit(double t)
    {
    	this.paratransit=t;
    	String root=this.modelNew.SpanningTree.root();
    	this.travelTree(root);
    }
    /**
     * Travel tree and compute Model Dt with corresponding parameter t 
     * (Algorithm 2)
     *
     */
    public void travelTree(String v)
    {
    	
    	String child=null;
    	Vertex vertexSource=new Vertex();
    	vertexSource.setWithoutCircle(this.withoutcircle);
    	Vertex vertexTarget;
    	DirectedEdge<Vertex> edge;
    	vertexSource.setVertex(v);
    	int nChild=this.modelNew.SpanningTree.nChildren(v);
    	double x=.0;
        double y=.0;
    	double x0=.0;
    	double y0=.0;
    	Iterator<String> it=this.modelNew.SpanningTree.successors(v).iterator();
    	if (v.equals(this.modelNew.SpanningTree.root())) 
    	{
    		x0=this.modelOld.getRoot().getPosition().getX();
    		y0=this.modelOld.getRoot().getPosition().getY();
    		vertexSource.setVertex(v);
    		vertexSource.setRadian((1-this.paratransit)*this.modelOld.getVertex(v).getRadian()+this.paratransit*this.modelNew.getVertex(v).getRadian());
    		vertexSource.setRadianpolar(this.modelOld.getVertex(v).getRadianpolar());
    		vertexSource.setRadius(this.modelOld.getVertex(v).getRadius()*(1-this.paratransit)+this.modelNew.getVertex(v).getRadius()*this.paratransit);
    	    vertexSource.setRadiuspolar((1-this.paratransit)*this.modelOld.getVertex(v).getRadiuspolar());
    		x=x0+vertexSource.getRadiuspolar()*Math.cos(vertexSource.getRadianpolar());
    		y=y0+vertexSource.getRadiuspolar()*Math.sin(vertexSource.getRadianpolar());
    		vertexSource.setPosition(new Point2D.Double(x,y));
    		this.modelCurrent.vertices.add(vertexSource);
    	}
    	else
    	{
    		vertexSource=this.modelCurrent.getVertex(v);
    	}
    	if(nChild>0)
    	{
    		while(it.hasNext())
    		{
    			vertexTarget=new Vertex();
    			vertexTarget.setWithoutCircle(this.withoutcircle);
    			x0=this.modelOld.getRoot().getPosition().getX();
        		y0=this.modelOld.getRoot().getPosition().getY();
    			
    			child=it.next();
    			vertexTarget.setVertex(child);
        		vertexTarget.setRadian(this.paratransit*this.modelNew.getVertex(child).getRadian()+(1-this.paratransit)*this.modelOld.getVertex(child).getRadian());
        		vertexTarget.setRadianpolar(this.paratransit*this.modelNew.getVertex(child).getRadianpolar()+(1-this.paratransit)*this.modelOld.getVertex(child).getRadianpolar());
        		vertexTarget.setRadius(this.paratransit*this.modelNew.getVertex(child).getRadius()+(1-this.paratransit)*this.modelOld.getVertex(child).getRadius());
        	    vertexTarget.setRadiuspolar(this.paratransit*this.modelNew.getVertex(child).getRadiuspolar()+(1-this.paratransit)*this.modelOld.getVertex(child).getRadiuspolar());
        		x=x0+vertexTarget.getRadiuspolar()*Math.cos(vertexTarget.getRadianpolar());
        		y=y0+vertexTarget.getRadiuspolar()*Math.sin(vertexTarget.getRadianpolar());
        		vertexTarget.setPosition(new Point2D.Double(x,y));
        		this.modelCurrent.vertices.add(vertexTarget);
        		edge=new DirectedEdge<Vertex>(vertexSource,vertexTarget);
        		this.modelCurrent.edges.add(edge);
        		travelTree(child);
    		}
    	}
    	
    }
 
    
}

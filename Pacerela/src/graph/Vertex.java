

package graph;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

public class Vertex {
	private static final Point2D DELTA_LABEL = new Point(3, -3);
	RectangularShape shape;
	private boolean without;
	private String vertex;
	private Color color;
	private Point2D position;
	private double radian;
	private double radius;
	private double radiuspolar;
	private double radianpolar;
	public void setColor(Color c)
	{
		this.color=c;
	}
	public void setRadianpolar(double a)
	{
		this.radianpolar=a;
	}
	public double getRadianpolar()
	{
		return this.radianpolar;
	}
	public void setRadiuspolar(double r)
	{
		this.radiuspolar=r;
	}
	public double getRadiuspolar()
	{
		return this.radiuspolar;
	}
	public double getRadian()
	{
		return this.radian;
	}
	public void setRadian(double radian)
	{
		this.radian=radian;
	}
	public double getRadius()
	{
		return this.radius;
	}
	public void setRadius(double r)
	{
		 this.radius=r;
	}
	public Vertex() {
	}
	public String getVertex() {
		return this.vertex;
	}
	public void setVertex(String vertex) {
		this.vertex = vertex;
	}
	public Point2D getPosition() {
		return position;
	}
	public void setPosition(Point2D position) {
		this.position = position;
	}
	public void drawVertex(Graphics2D g2) {
	    
		shape =  new Ellipse2D.Double(position.getX()-5, position.getY()-5,10, 10);
	 	g2.setColor(Color.BLUE);
		g2.fill(shape);
	 	
	 	if(!this.without){
	 	g2.setColor(this.color);
		shape.setFrameFromCenter(this.getPosition().getX(), this.getPosition().getY()
                , this.getPosition().getX()+this.getRadius(), this.getPosition().getY()+this.getRadius());
		g2.setComposite(makeComposite(6*0.1F));
		g2.fill(shape);
		}
		g2.setColor(Color.BLACK);
		if (this.vertex != null)
			g2.drawString(this.vertex, (int) (this.getPosition().getX() + DELTA_LABEL.getX()),
					(int) (this.getPosition().getY() + DELTA_LABEL.getY()));
		
	}
	public void setWithoutCircle(boolean w)
	{
		this.without=w;
	}
	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}
	private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return(AlphaComposite.getInstance(type, alpha));
    }

}

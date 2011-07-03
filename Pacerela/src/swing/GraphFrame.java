//============================================================================
// Name        : FramesController.java
// Author      : SoftwareGroup3
// Version     : 0.1
// Copyright   : 
// Description : 
//============================================================================
package swing;
import graph.DirectedEdge;
import graph.Graph;
import graph.Graphs;
import graph.MultiGraph;
import graph.Vertex;
import graph.Graph.Edge;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
public class GraphFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FramesController controller;
	GraphComponent component;
	 Graph<String, Graph.Edge<String>> g;
	 SimpleGraphParser sp;
	  Graphs gs;
	  Vertex rootVertex=new Vertex();
	  JCheckBoxMenuItem withoutcircle;

	public GraphFrame(FramesController controller)  {
		this.controller = controller;
		component=new GraphComponent();
		component.setForeground(Color.BLACK);
		component.setBackground(Color.WHITE);
		component.setOpaque(true);
		component.setPreferredSize(new Dimension(1000, 1000));
		//this.setPreferredSize(new Dimension(1000, 9000));
		rootVertex.setPosition(new Point2D.Double(400,400));
		rootVertex.setRadius(100);	
		rootVertex.setRadiuspolar(0);
		rootVertex.setRadianpolar(0);
		
		JScrollPane scrollPane = new JScrollPane(component);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu(GraphEditor.MENU_FILE);
		JMenu menuedit=new JMenu(GraphEditor.MENU_EDIT);
		
		menuBar.add(menu);
		menuBar.add(menuedit);
		withoutcircle = new JCheckBoxMenuItem(GraphEditor.MENU_ITEM_WITHOUT_CIRCLE);
		withoutcircle.setSelected(false);
		
		menuedit.add(withoutcircle);
        withoutcircle.addActionListener(new  ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.component.withoutCircle(GraphFrame.this.withoutcircle.getState());
			}} );
		createMenuItem(menu, GraphEditor.MENU_ITEM_NEW, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.createJFrame();
			}
		});
		createMenuItem(menu, GraphEditor.MENU_ITEM_DRAW_GRAPH_WITH_ALGORITHM_1, new PushButtonActionListener(this));
		createMenuItem(menu, GraphEditor.MENU_ITEM_CLOSE, new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.deleteJFrame(GraphFrame.this);
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				GraphFrame.this.controller.deleteJFrame(GraphFrame.this);
			}
		});
		
		Container contentPane = getContentPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}
	private void createMenuItem(JMenu menu,String name, ActionListener action){
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(action);
		menu.add(menuItem);
	}
	private class PushButtonActionListener implements ActionListener{
		private JFrame f;
		public PushButtonActionListener(JFrame af) {
			this.f = af;
			}
		public void actionPerformed(ActionEvent ae){
		if(ae.getActionCommand()==GraphEditor.MENU_ITEM_DRAW_GRAPH_WITH_ALGORITHM_1){
				JFileChooser fc=new JFileChooser(System.getProperty("user.dir"));
				int fd=fc.showOpenDialog(null);
				    if(fd==JFileChooser.APPROVE_OPTION){
						sp = new SimpleGraphParser();
						try {
							sp.parse(new BufferedReader(new FileReader(fc.getSelectedFile())));
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null,"Error Graph");
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							JOptionPane.showMessageDialog(null,"Error Graph");
						}		
						rootVertex.setVertex(sp.labels().labels()[0]);
						
						g = new MultiGraph<String, Graph.Edge<String>>();
						for (int i=0;i<sp.labels().labels().length;i++)
						g.addVertex(sp.labels().labels()[i]);
						for (Edge<?> e : sp.edges())
						g.addEdge(new DirectedEdge<String>(sp.labels().labels()[Integer.parseInt(String.valueOf(e.source())) ], sp.labels().labels()[Integer.parseInt(String.valueOf( e.target()))]));
						if(Graphs.isConnected(g))
						{
						component.loadGraph(g,rootVertex);
						}else JOptionPane.showMessageDialog(null,"Graph is not completed");
					  
					}
				
			}
		}
	}

}

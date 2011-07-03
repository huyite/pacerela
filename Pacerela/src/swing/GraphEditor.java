//============================================================================
// Name        : FramesController.java
// Author      : SoftwareGroup3
// Version     : 0.1
// Copyright   : 
// Description : 
//============================================================================
package swing;


import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
public class GraphEditor implements FramesController{
	public final static String TITLE = "Pacerela";
	public static final String MENU_FILE="File";
	public static final String MENU_ITEM_NEW="New";
	public static final String MENU_ITEM_CLOSE="Close";
	public static final String MENU_EDIT="Edit";
	public static final String MENU_ITEM_WITHOUT_CIRCLE="Without Circle";
	public static final String MENU_ITEM_DRAW_GRAPH_WITH_ALGORITHM_1="Load Graph";
	public final static String DIALOG_QUIT_MSG = "Do you really want to quit ?";
	public final static String DIALOG_QUIT_TITLE = "Quit ?";
	private static List<JFrame> frames = new ArrayList<JFrame>();

	@Override
	public JFrame createJFrame() {
		// TODO Auto-generated method stub
		JFrame frame = new GraphFrame(this);
		frame.setTitle(TITLE);
		int pos = 30 * (frames.size() % 5);
		frame.setLocation(pos, pos);
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frames.add(frame);
		return frame;
	}

	@Override
	public void deleteJFrame(JFrame frame) {
		// TODO Auto-generated method stub
		if (frames.size() > 1) {
			frames.remove(frame);
			frame.dispose();
		} else {
			quit();
		}
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		int answer = JOptionPane.showConfirmDialog(null, DIALOG_QUIT_MSG,
				DIALOG_QUIT_TITLE, JOptionPane.YES_NO_OPTION);
		if (answer == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GraphEditor().createJFrame();
			}
		});
	}
}

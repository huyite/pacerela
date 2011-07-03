//============================================================================
// Name        : FramesController.java
// Author      : SoftwareGroup3
// Version     : 0.1
// Copyright   : 
// Description : 
//============================================================================

package swing;

import javax.swing.JFrame;

public interface FramesController {
	public void quit();
	public JFrame createJFrame();
	public void deleteJFrame(JFrame frame);

}

package views;

import javax.swing.JPanel;
import javax.swing.JButton;
public class FlowPanel extends JPanel {
	
	public FlowPanel() {
		for(int i = 0; i < 10; i++) {  
			JButton b = new JButton(i + "");
		}
	}

}
package componets;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;



public class ErrorLabel extends JLabel{

	public ErrorLabel() {
		setLabelStyle();
	}
	
	public ErrorLabel(String text) {
		super(text);
		setLabelStyle();
	}
	
	private void setLabelStyle() {
		setForeground(Color.RED);
		setHorizontalAlignment(SwingConstants.LEFT);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
	}
	
	
}









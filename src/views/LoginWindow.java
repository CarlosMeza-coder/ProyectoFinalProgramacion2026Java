package views;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class LoginWindow extends JFrame { 

	public LoginWindow() {

		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);

		
		ViewLogin loginView = new ViewLogin(this);
		add(loginView);

		setVisible(true);
		
	}
}

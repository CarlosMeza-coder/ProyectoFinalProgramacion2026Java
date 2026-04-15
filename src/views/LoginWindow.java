package views;

import javax.swing.JFrame;
import controladores.LoginController; 

public class LoginWindow extends JFrame { 

    public LoginWindow() {

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        ViewLogin loginView = new ViewLogin(this);
        add(loginView);

        new LoginController(loginView);

        setVisible(true);
    }
}
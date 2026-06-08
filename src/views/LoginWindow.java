package views;

import javax.swing.JFrame;
import controladores.LoginController;
import utils.AppStyles;

public class LoginWindow extends JFrame { 

    public LoginWindow() {

        setTitle("Inicio de Sesión - Sistema Escolar");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(AppStyles.BACKGROUND);

        ViewLogin loginView = new ViewLogin(this);
        add(loginView);

        new LoginController(loginView);

        setVisible(true);
    }
}
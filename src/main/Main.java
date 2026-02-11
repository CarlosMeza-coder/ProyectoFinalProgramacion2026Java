package main;

import javax.swing.JFrame;
import views.ViewLogin;

// sebas GAY
public class Main {
    public static void main(String[] args) {
    	
        JFrame ventana = new JFrame("Sistema de calificaciones");
                        
        ViewLogin login = new ViewLogin();
        ventana.add(login);      
        ventana.setSize(500, 400);          
        ventana.setVisible(true);

    }
}
package main;

import javax.swing.JFrame;
import views.ViewLogin;


public class Main {
    public static void main(String[] args) {
    	
        JFrame ventana = new JFrame("Sistema de calificaciones");
        
        ViewLogin login = new ViewLogin();
        ventana.add(login);      
        ventana.setSize(500, 400);          
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
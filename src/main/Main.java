package main;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import views.FormularioRegistro;
import views.ViewLogin;
import views.OpcionesAlumnos;

public class Main {
    public static void main(String[] args) {
        
        Toolkit herramientasGraficas = Toolkit.getDefaultToolkit();
        Image iconoDelSistema = herramientasGraficas.getImage("src/img/OIP.jpg");

        JFrame ventanaLogin = new JFrame("Sistema de calificaciones");
        ventanaLogin.setIconImage(iconoDelSistema);
        
        ViewLogin login = new ViewLogin();
        ventanaLogin.add(login);      
        ventanaLogin.setSize(400, 450); 
        ventanaLogin.setLocation(50, 100); 
        ventanaLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaLogin.setVisible(true);
        
        FormularioRegistro formulario = new FormularioRegistro();
        formulario.setLocation(460, 100); 
        
        OpcionesAlumnos opciones = new OpcionesAlumnos();
        opciones.setLocation(820, 100); 
    }
}

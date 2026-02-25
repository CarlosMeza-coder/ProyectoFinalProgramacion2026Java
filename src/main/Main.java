package main;

import javax.swing.JFrame;
import views.FormularioRegistro;
import views.ViewLogin;


public class Main {
    public static void main(String[] args) {
    	
        JFrame ventana = new JFrame("Sistema de calificaciones");
                        
        ViewLogin login = new ViewLogin();
        ventana.add(login);      
        ventana.setSize(500, 400);          
        ventana.setVisible(true);
        
        FormularioRegistro formulario = new FormularioRegistro();
		showOnScreen(1, formulario);
		
		  //Grupos grupos = new Grupos();
		//	showOnScreen(2, grupos);

    }

	private static void showOnScreen(int i, FormularioRegistro formulario) {
		// TODO Auto-generated method stub
		
	}
	
	//private static void showOnScreen(int i, Grupos grupos) {
		// TODO Auto-generated method stub
		
	}

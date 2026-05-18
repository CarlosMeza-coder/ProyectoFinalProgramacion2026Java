package main;

import views.LoginWindow;
import config.DatabaseConnection;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        
        if (DatabaseConnection.getConnection() != null) {
            new LoginWindow(); 
        } else {
            JOptionPane.showMessageDialog(null, 
                "No se pudo establecer conexión con la base de datos.\n" +
                "Verifica que MySQL Server esté activo y que tu contraseña sea correcta.", 
                "Error Crítico de Conexión", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
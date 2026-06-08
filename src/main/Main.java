package main;

import views.LoginWindow;
import config.DatabaseConnection;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt; // <-- Importamos BCrypt para que no marque error

public class Main {
    public static void main(String[] args) {
        
        // === BLOQUE TEMPORAL PARA GENERAR EL HASH DE '123456' ===
        try {
            String passOriginal = "123456";
            String hashValido = BCrypt.hashpw(passOriginal, BCrypt.gensalt());
            System.out.println("\n========================================================");
            System.out.println("CARLOS, TU HASH PARA EL SCRIPT DE MYSQL ES:");
            System.out.println(hashValido);
            System.out.println("========================================================\n");
        } catch (Exception e) {
            System.out.println("Error al generar el hash: " + e.getMessage());
        }
        // ========================================================

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
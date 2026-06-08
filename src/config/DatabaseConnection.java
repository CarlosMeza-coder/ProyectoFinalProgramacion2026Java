package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

    private static Connection connection;
    
    public static Connection getConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                Properties props = new Properties();
                
                InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config/database.properties");
                
                if (input == null) {
                    System.err.println("Error: No se encontró el archivo config/database.properties");
                    return null;
                }
                
                props.load(input);
                
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");
                String driver = props.getProperty("db.driver");
                
                // ====================================================================
                // 🔥 TRUCO MAESTRO: FORZAMOS LA URL REAL EN EL CÓDIGO 🔥
                // Esto ignora el archivo properties corrupto en la caché de Eclipse
                url = "jdbc:mysql://localhost:3306/proyecto_escolar_definitiva?useSSL=false&serverTimezone=UTC";
                // ====================================================================
                
                Class.forName(driver);
                
                connection = DriverManager.getConnection(url, user, password);
                
                // === LÍNEAS DE RASTREO PARA TU CONSOLA ===
                System.out.println(">>> ¡Conexión establecida con éxito! <<<");
                System.out.println("URL REAL MANDATORIA FORZADA: " + url);
                // =========================================
            }
            
        } catch(Exception e) {
            System.err.println("Error al conectar a la base de datos:");
            e.printStackTrace();
        }
        
        return connection;
    }
}
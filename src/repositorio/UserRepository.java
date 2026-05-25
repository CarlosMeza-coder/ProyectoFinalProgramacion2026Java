package repositorio;

import java.sql.*;
import config.DatabaseConnection;
import models.User;
import utils.PasswordUtils;

public class UserRepository {

    public User login(String email, String password) {
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
                
                if (PasswordUtils.checkPassword(password, hashedPassword)) {
                    User user = new User();
                    user.setId(rs.getInt("id_usuario"));
                    user.setEmail(rs.getString("email"));
                    user.setRol(rs.getString("rol"));
                    
                    user.setIdProfesor(rs.getInt("id_profesor"));
                    user.setMatricula(rs.getString("matricula"));
                    
                    return user;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
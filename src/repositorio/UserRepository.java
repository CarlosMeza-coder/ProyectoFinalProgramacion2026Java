package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConnection;
import models.User;
import utils.PasswordUtils;

public class UserRepository {

    public User login(String email, String password) {
        String sql = "SELECT id_usuario, email, password, rol FROM usuarios WHERE email = ?";
        
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
                    return user;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO usuarios (email, password, rol) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getEmail());
            
            String encriptada = PasswordUtils.hashPassword(user.getPass());
            stmt.setString(2, encriptada);
            
            String rolAsignado = (user.getRol() != null) ? user.getRol().toUpperCase() : "PROFESOR";
            stmt.setString(3, rolAsignado);
            
            stmt.executeUpdate();
            System.out.println("Usuario guardado exitosamente con rol: " + rolAsignado);
        }
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id_usuario, email, rol FROM usuarios";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id_usuario"));
                user.setEmail(rs.getString("email"));
                user.setRol(rs.getString("rol"));
                users.add(user);
            }
        }
        return users;
    }
}
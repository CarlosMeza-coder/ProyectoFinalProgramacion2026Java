package repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConnection;

public class AsignacionRepository {

    public List<String> getListaMaterias() {
        List<String> materias = new ArrayList<>();
        String sql = "SELECT nombre_materia FROM materias";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) materias.add(rs.getString("nombre_materia"));
        } catch (SQLException ex) { ex.printStackTrace(); }
        return materias;
    }

    public List<String> getListaProfesores() {
        List<String> profes = new ArrayList<>();
        String sql = "SELECT p.id_profesor, p.nombre, p.apellido FROM profesores p";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                profes.add(rs.getInt("id_profesor") + " - " + rs.getString("nombre") + " " + rs.getString("apellido"));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return profes;
    }

    
    public void inscribirAlumnoAMateria(String matricula, int idProfesor, String nombreMateria) throws SQLException {
        int idMateria = -1;
        String sqlMat = "SELECT id_materia FROM materias WHERE nombre_materia = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlMat)) {
            stmt.setString(1, nombreMateria);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) idMateria = rs.getInt("id_materia");
        }

        String sqlInsert = "INSERT INTO calificaciones (parcial_1, parcial_2, parcial_3, nota_final, matricula_alumno, id_materia, id_profesor) " +
                           "VALUES (0.0, 0.0, 0.0, 0.0, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlInsert)) {
            stmt.setString(1, matricula);
            stmt.setInt(2, idMateria);
            stmt.setInt(3, idProfesor);
            stmt.executeUpdate();
            System.out.println(" Inscripción realizada correctamente en la tabla 'calificaciones'.");
        }
    }
}
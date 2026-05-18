package repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConnection;
import models.Alumno;
import models.Calificacion;

public class AlumnoRepository {

    public List<Alumno> getAlumnos() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT matricula, nombre, email, semestre, grupo FROM alumnos";
        
        try (Connection connection = DatabaseConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Alumno alumno = new Alumno(
                    rs.getString("matricula"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("semestre"),
                    rs.getString("grupo")
                );
                lista.add(alumno);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public void save(Alumno alumno) throws SQLException {
        String sql = "INSERT INTO alumnos (matricula, nombre, email, semestre, grupo) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, alumno.getMatricula());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getEmail());
            stmt.setString(4, alumno.getSemestre());
            stmt.setString(5, alumno.getGrupo());
            
            stmt.executeUpdate();
            System.out.println("Alumno guardado con éxito en MySQL.");
        }
    }

    public boolean delete(String matricula) {
        String sql = "DELETE FROM alumnos WHERE matricula = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, matricula);
            int affectedRows = pst.executeUpdate();
            
            if (affectedRows > 0) {
                System.out.println("Alumno con matrícula " + matricula + " eliminado.");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(int index, Alumno updatedAlumno) {
        String sql = "UPDATE alumnos SET nombre = ?, email = ?, semestre = ?, grupo = ? WHERE matricula = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, updatedAlumno.getNombre());
            pst.setString(2, updatedAlumno.getEmail());
            pst.setString(3, updatedAlumno.getSemestre());
            pst.setString(4, updatedAlumno.getGrupo());
            pst.setString(5, updatedAlumno.getMatricula());
            
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Datos del alumno actualizados correctamente en la BD.");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Calificacion getCalificacionPorMateria(String matricula, String nombreMateria) throws SQLException {
        String sql = "SELECT c.parcial1, c.parcial2, c.parcial3, c.nota_final " +
                     "FROM calificaciones c " +
                     "JOIN materias m ON c.id_materia = m.id_materia " +
                     "WHERE c.matricula_alumno = ? AND m.nombre_materia = ?";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matricula);
            stmt.setString(2, nombreMateria);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                List<Double> parciales = new ArrayList<>();
                parciales.add(rs.getDouble("parcial1"));
                parciales.add(rs.getDouble("parcial2"));
                parciales.add(rs.getDouble("parcial3"));
                double notaFinal = rs.getDouble("nota_final");
                
                return new Calificacion(nombreMateria, parciales, notaFinal);
            }
        }
        return null;
    }

    public void guardarOActualizarNotas(String matricula, String nombreMateria, List<Double> parciales, double notaFinal) throws SQLException {
        int idMateria = -1;
        String sqlMateria = "SELECT id_materia FROM materias WHERE nombre_materia = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtMat = conn.prepareStatement(sqlMateria)) {
            stmtMat.setString(1, nombreMateria);
            ResultSet rs = stmtMat.executeQuery();
            if (rs.next()) {
                idMateria = rs.getInt("id_materia");
            }
        }

        if (idMateria == -1) return; 

        String sqlCheck = "SELECT id_calificacion FROM calificaciones WHERE matricula_alumno = ? AND id_materia = ?";
        boolean existe = false;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setString(1, matricula);
            stmtCheck.setInt(2, idMateria);
            ResultSet rs = stmtCheck.executeQuery();
            existe = rs.next();
        }

        String sqlQuery;
        if (existe) {
            sqlQuery = "UPDATE calificaciones SET parcial1 = ?, parcial2 = ?, parcial3 = ?, nota_final = ? WHERE matricula_alumno = ? AND id_materia = ?";
        } else {
            sqlQuery = "INSERT INTO calificaciones (parcial1, parcial2, parcial3, nota_final, matricula_alumno, id_materia) VALUES (?, ?, ?, ?, ?, ?)";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtFinal = conn.prepareStatement(sqlQuery)) {
            stmtFinal.setDouble(1, parciales.get(0));
            stmtFinal.setDouble(2, parciales.get(1));
            stmtFinal.setDouble(3, parciales.get(2));
            stmtFinal.setDouble(4, notaFinal);
            stmtFinal.setString(5, matricula);
            stmtFinal.setInt(6, idMateria);
            stmtFinal.executeUpdate();
        }
    }
}
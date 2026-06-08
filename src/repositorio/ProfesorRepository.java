package repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConnection;
import models.Profesor;
import utils.PasswordUtils;

public class ProfesorRepository {

    // Retorna la lista de todos los profesores con su email
    public List<Profesor> getProfesores() {
        List<Profesor> lista = new ArrayList<>();
        String sql = "SELECT p.id_profesor, p.nombre, p.apellido, u.email " +
                     "FROM profesores p " +
                     "INNER JOIN usuarios u ON p.id_usuario = u.id_usuario";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Profesor prof = new Profesor(
                    rs.getInt("id_profesor"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email")
                );
                lista.add(prof);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // Registra un profesor nuevo junto con su usuario de login
    public void save(Profesor profesor) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtProf = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Inserta el usuario con la contraseña hasheada y rol profesor (2)
            String sqlUser = "INSERT INTO usuarios (email, password, id_rol) VALUES (?, ?, ?)";
            stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            stmtUser.setString(1, profesor.getEmail());
            String claveEncriptada = PasswordUtils.hashPassword(profesor.getPassword());
            stmtUser.setString(2, claveEncriptada);
            stmtUser.setInt(3, 2);
            stmtUser.executeUpdate();

            // Obtiene el id_usuario generado por la BD
            ResultSet rsUser = stmtUser.getGeneratedKeys();
            int idUsuario = rsUser.next() ? rsUser.getInt(1) : -1;

            // Genera un número de empleado único usando el timestamp actual
            String numeroEmpleadoAutogenerado = "EMP-" + System.currentTimeMillis();

            // Inserta el profesor vinculado al usuario recién creado
            String sqlProf = "INSERT INTO profesores (num_empleado, nombre, apellido, id_usuario) VALUES (?, ?, ?, ?)";
            stmtProf = conn.prepareStatement(sqlProf);
            stmtProf.setString(1, numeroEmpleadoAutogenerado);
            stmtProf.setString(2, profesor.getNombre());
            stmtProf.setString(3, profesor.getApellido());
            stmtProf.setInt(4, idUsuario);
            stmtProf.executeUpdate();

            conn.commit();
            System.out.println("¡Profesor registrado exitosamente con contraseña personalizada!");
            
        } catch (SQLException ex) {
            if (conn != null) conn.rollback();
            throw ex;
        } finally {
            if (stmtUser != null) stmtUser.close();
            if (stmtProf != null) stmtProf.close();
            if (conn != null) conn.close();
        }
    }

    // Actualiza nombre, apellido y email de un profesor existente
    public boolean update(Profesor profesor) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Obtiene el id_usuario asociado al profesor
            int idUsuario = -1;
            String sqlGetId = "SELECT id_usuario FROM profesores WHERE id_profesor = ?";
            try (PreparedStatement psGet = conn.prepareStatement(sqlGetId)) {
                psGet.setInt(1, profesor.getIdProfesor());
                ResultSet rs = psGet.executeQuery();
                if (rs.next()) idUsuario = rs.getInt("id_usuario");
            }

            // Actualiza el email en la tabla usuarios
            if (idUsuario != -1) {
                String sqlUpdateUser = "UPDATE usuarios SET email = ? WHERE id_usuario = ?";
                try (PreparedStatement psU = conn.prepareStatement(sqlUpdateUser)) {
                    psU.setString(1, profesor.getEmail());
                    psU.setInt(2, idUsuario);
                    psU.executeUpdate();
                }
            }

            // Actualiza nombre y apellido en la tabla profesores
            String sqlUpdateAl = "UPDATE profesores SET nombre = ?, apellido = ? WHERE id_profesor = ?";
            try (PreparedStatement psA = conn.prepareStatement(sqlUpdateAl)) {
                psA.setString(1, profesor.getNombre());
                psA.setString(2, profesor.getApellido());
                psA.setInt(3, profesor.getIdProfesor());
                int affectedRows = psA.executeUpdate();
                
                // Solo confirma si realmente se modificó alguna fila
                if (affectedRows > 0) {
                    conn.commit();
                    System.out.println("✅ Datos del profesor actualizados correctamente en la BD.");
                    return true;
                }
            }
        } catch (SQLException ex) { 
            try { if (conn != null) conn.rollback(); } catch (SQLException e) {}
            ex.printStackTrace(); 
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return false;
    }

    // Elimina un profesor y su usuario asociado
    public boolean delete(int idProfesor) {
        Connection conn = null;
        PreparedStatement stmtProf = null;
        PreparedStatement stmtUser = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Guarda el id_usuario antes de borrar al profesor
            String sqlGetId = "SELECT id_usuario FROM profesores WHERE id_profesor = ?";
            int idUsuario = -1;
            try (PreparedStatement psGet = conn.prepareStatement(sqlGetId)) {
                psGet.setInt(1, idProfesor);
                ResultSet rs = psGet.executeQuery();
                if (rs.next()) idUsuario = rs.getInt("id_usuario");
            }

            // Borra al profesor primero para no violar la FK
            String sqlDelProf = "DELETE FROM profesores WHERE id_profesor = ?";
            stmtProf = conn.prepareStatement(sqlDelProf);
            stmtProf.setInt(1, idProfesor);
            stmtProf.executeUpdate();
            
            // Luego borra el usuario
            if (idUsuario != -1) {
                String sqlDelUser = "DELETE FROM usuarios WHERE id_usuario = ?";
                stmtUser = conn.prepareStatement(sqlDelUser);
                stmtUser.setInt(1, idUsuario);
                stmtUser.executeUpdate();
            }
            
            conn.commit();
            System.out.println("❌ Profesor con ID " + idProfesor + " eliminado de la BD.");
            return true;
            
        } catch (SQLException ex) {
            try { if (conn != null) conn.rollback(); } catch (SQLException e) {}
            ex.printStackTrace();
        } finally {
            try {
                if (stmtProf != null) stmtProf.close();
                if (stmtUser != null) stmtUser.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {}
        }
        return false;
    }

    // Retorna las materias y grupos asignados a un profesor
    public List<Object[]> getMisCursosYAlumnos(int idProfesor) {
        List<Object[]> lista = new ArrayList<>();
        
        // Obtiene combinaciones únicas de materia y grupo donde el profesor tiene calificaciones
        String sql = "SELECT DISTINCT m.id_materia, m.nombre_materia, g.semestre, g.nombre_grupo " +
                     "FROM calificaciones c " +
                     "JOIN materias m ON c.id_materia = m.id_materia " +
                     "JOIN alumnos a ON c.matricula_alumno = a.matricula " +
                     "JOIN grupos g ON a.id_grupo = g.id_grupo " +
                     "WHERE c.id_profesor = ?";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, idProfesor);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                // Cada fila es [id_materia, nombre_materia, semestre, nombre_grupo]
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id_materia");
                fila[1] = rs.getString("nombre_materia");
                fila[2] = rs.getString("semestre");
                fila[3] = rs.getString("nombre_grupo");
                lista.add(fila);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
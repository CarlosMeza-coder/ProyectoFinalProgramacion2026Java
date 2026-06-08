package repositorio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.DatabaseConnection;
import models.Alumno;
import models.Calificacion;

public class AlumnoRepository {

    // Retorna la lista de todos los alumnos desde la vista de la BD
    public List<Alumno> getAlumnos() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT matricula, nombre, apellido, email, semestre, grupo FROM vista_lista_alumnos";
        
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
                alumno.setApellido(rs.getString("apellido"));
                lista.add(alumno);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    // Registra un alumno nuevo junto con su usuario de login
    public void save(Alumno alumno) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtGrupo = null;
        PreparedStatement stmtUser = null;
        PreparedStatement stmtAlumno = null;
        ResultSet rsGrupo = null;
        ResultSet rsUser = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // inicia transacción

            // Busca el id del grupo por semestre y nombre
            int idGrupo = -1;
            String sqlGrupo = "SELECT id_grupo FROM grupos WHERE semestre = ? AND nombre_grupo = ?";
            stmtGrupo = conn.prepareStatement(sqlGrupo);
            stmtGrupo.setString(1, alumno.getSemestre());
            stmtGrupo.setString(2, alumno.getGrupo());
            rsGrupo = stmtGrupo.executeQuery();
            
            if (rsGrupo.next()) {
                idGrupo = rsGrupo.getInt("id_grupo");
            } else {
                throw new SQLException("El grupo '" + alumno.getSemestre() + " " + alumno.getGrupo() + "' no existe. Créalo primero.");
            }

            // Inserta el usuario con contraseña bcrypt por defecto y rol alumno
            String sqlUser = "INSERT INTO usuarios (email, password, id_rol) VALUES (?, ?, ?)";
            stmtUser = conn.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);
            stmtUser.setString(1, alumno.getEmail());
            stmtUser.setString(2, "$2a$10$6B76N88/.MLWXQoAiVQ2X.ewkqUk3Ioafk7LRfxtDzGtOm9vIg7Bq");
            stmtUser.setInt(3, 3);
            stmtUser.executeUpdate();

            // Obtiene el id generado automáticamente por la BD
            rsUser = stmtUser.getGeneratedKeys();
            int idUsuario = rsUser.next() ? rsUser.getInt(1) : -1;

            // Inserta el alumno vinculado al grupo y usuario
            String sqlAlumno = "INSERT INTO alumnos (matricula, nombre, apellido, id_grupo, id_usuario) VALUES (?, ?, ?, ?, ?)";
            stmtAlumno = conn.prepareStatement(sqlAlumno);
            stmtAlumno.setString(1, alumno.getMatricula());
            stmtAlumno.setString(2, alumno.getNombre());
            stmtAlumno.setString(3, alumno.getApellido());
            stmtAlumno.setInt(4, idGrupo);
            stmtAlumno.setInt(5, idUsuario);
            stmtAlumno.executeUpdate();

            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { }
            }
            throw ex;
        } finally {
            // Cierra todos los recursos
            if (rsGrupo != null) rsGrupo.close();
            if (rsUser != null) rsUser.close();
            if (stmtGrupo != null) stmtGrupo.close();
            if (stmtUser != null) stmtUser.close();
            if (stmtAlumno != null) stmtAlumno.close();
            if (conn != null) conn.close();
        }
    }

    // Actualiza datos del alumno y su email en la tabla usuarios
    public boolean update(Alumno alumno) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Busca el id del grupo nuevo
            int idGrupo = -1;
            String sqlGrupo = "SELECT id_grupo FROM grupos WHERE semestre = ? AND nombre_grupo = ?";
            try (PreparedStatement psGrupo = conn.prepareStatement(sqlGrupo)) {
                psGrupo.setString(1, alumno.getSemestre());
                psGrupo.setString(2, alumno.getGrupo());
                ResultSet rs = psGrupo.executeQuery();
                if (rs.next()) idGrupo = rs.getInt("id_grupo");
                else throw new SQLException("El grupo especificado no existe.");
            }

            // Obtiene el id_usuario del alumno
            int idUsuario = -1;
            String sqlIdUser = "SELECT id_usuario FROM alumnos WHERE matricula = ?";
            try (PreparedStatement psIdUser = conn.prepareStatement(sqlIdUser)) {
                psIdUser.setString(1, alumno.getMatricula());
                ResultSet rsId = psIdUser.executeQuery();
                if (rsId.next()) idUsuario = rsId.getInt("id_usuario");
            }

            // Actualiza nombre, apellido y grupo
            String sqlUpdateAl = "UPDATE alumnos SET nombre = ?, apellido = ?, id_grupo = ? WHERE matricula = ?";
            try (PreparedStatement psUpdateAl = conn.prepareStatement(sqlUpdateAl)) {
                psUpdateAl.setString(1, alumno.getNombre());
                psUpdateAl.setString(2, alumno.getApellido());
                psUpdateAl.setInt(3, idGrupo);
                psUpdateAl.setString(4, alumno.getMatricula());
                psUpdateAl.executeUpdate();
            }

            // Actualiza el email en usuarios
            if (idUsuario != -1) {
                String sqlUpdateUser = "UPDATE usuarios SET email = ? WHERE id_usuario = ?";
                try (PreparedStatement psUpdateUser = conn.prepareStatement(sqlUpdateUser)) {
                    psUpdateUser.setString(1, alumno.getEmail());
                    psUpdateUser.setInt(2, idUsuario);
                    psUpdateUser.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            try { if (conn != null) conn.rollback(); } catch (SQLException e) {}
            ex.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // Elimina el alumno y su usuario asociado
    public boolean delete(String matricula) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Guarda el id_usuario antes de borrar al alumno
            int idUsuario = -1;
            String sqlIdUser = "SELECT id_usuario FROM alumnos WHERE matricula = ?";
            try (PreparedStatement psIdUser = conn.prepareStatement(sqlIdUser)) {
                psIdUser.setString(1, matricula);
                ResultSet rsId = psIdUser.executeQuery();
                if (rsId.next()) idUsuario = rsId.getInt("id_usuario");
            }

            // Borra al alumno primero para no violar la FK
            String sqlDelAl = "DELETE FROM alumnos WHERE matricula = ?";
            try (PreparedStatement psDelAl = conn.prepareStatement(sqlDelAl)) {
                psDelAl.setString(1, matricula);
                psDelAl.executeUpdate();
            }

            // Luego borra el usuario
            if (idUsuario != -1) {
                String sqlDelUs = "DELETE FROM usuarios WHERE id_usuario = ?";
                try (PreparedStatement psDelUs = conn.prepareStatement(sqlDelUs)) {
                    psDelUs.setInt(1, idUsuario);
                    psDelUs.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            try { if (conn != null) conn.rollback(); } catch (SQLException e) {}
            ex.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // Retorna todos los grupos disponibles para llenar combos
    public List<String[]> getGruposDisponibles() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT semestre, nombre_grupo FROM grupos ORDER BY semestre, nombre_grupo";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("semestre"),
                    rs.getString("nombre_grupo")
                });
            }
        }
        return lista;
    }

    // Retorna las materias donde el profesor ya tiene calificaciones capturadas
    public List<String> getMateriasDelProfesor(int idProfesor) throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT DISTINCT m.nombre_materia " +
                     "FROM calificaciones c " +
                     "JOIN materias m ON c.id_materia = m.id_materia " +
                     "WHERE c.id_profesor = ? " +
                     "ORDER BY m.nombre_materia";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProfesor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("nombre_materia"));
            }
        }
        return lista;
    }

    // Busca las calificaciones de un alumno en una materia y profesor específicos
    public Calificacion getCalificacionPorMateria(String matricula, String nombreMateria, int idProfesor) throws SQLException {
        String sql = "SELECT c.parcial_1, c.parcial_2, c.parcial_3, c.nota_final " +
                     "FROM calificaciones c " +
                     "JOIN materias m ON c.id_materia = m.id_materia " +
                     "WHERE c.matricula_alumno = ? AND m.nombre_materia = ? AND c.id_profesor = ?";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, matricula);
            stmt.setString(2, nombreMateria);
            stmt.setInt(3, idProfesor);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                List<Double> parciales = new ArrayList<>();
                parciales.add(rs.getDouble("parcial_1"));
                parciales.add(rs.getDouble("parcial_2"));
                parciales.add(rs.getDouble("parcial_3"));
                double notaFinal = rs.getDouble("nota_final");
                return new Calificacion(nombreMateria, parciales, notaFinal);
            }
        }
        return null; // sin calificaciones registradas
    }

    // Inserta o actualiza los parciales y nota final de un alumno en una materia
    public void guardarOActualizarNotas(String matricula, String nombreMateria, List<Double> parciales, double notaFinal, int idProfesor) throws SQLException {
        // Busca el id numérico de la materia
        int idMateria = -1;
        String sqlMateria = "SELECT id_materia FROM materias WHERE nombre_materia = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtMat = conn.prepareStatement(sqlMateria)) {
            stmtMat.setString(1, nombreMateria);
            ResultSet rs = stmtMat.executeQuery();
            if (rs.next()) idMateria = rs.getInt("id_materia");
        }

        if (idMateria == -1) return; // materia no encontrada, no hace nada

        // Verifica si ya existe un registro para este alumno/materia/profesor
        String sqlCheck = "SELECT id_calificacion FROM calificaciones WHERE matricula_alumno = ? AND id_materia = ? AND id_profesor = ?";
        boolean existe = false;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck)) {
            stmtCheck.setString(1, matricula);
            stmtCheck.setInt(2, idMateria);
            stmtCheck.setInt(3, idProfesor);
            ResultSet rs = stmtCheck.executeQuery();
            existe = rs.next();
        }

        // Elige UPDATE o INSERT según si ya existía el registro
        String sqlQuery;
        if (existe) {
            sqlQuery = "UPDATE calificaciones SET parcial_1 = ?, parcial_2 = ?, parcial_3 = ?, nota_final = ? " +
                       "WHERE matricula_alumno = ? AND id_materia = ? AND id_profesor = ?";
        } else {
            sqlQuery = "INSERT INTO calificaciones (parcial_1, parcial_2, parcial_3, nota_final, matricula_alumno, id_materia, id_profesor) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmtFinal = conn.prepareStatement(sqlQuery)) {
            stmtFinal.setDouble(1, parciales.get(0));
            stmtFinal.setDouble(2, parciales.get(1));
            stmtFinal.setDouble(3, parciales.get(2));
            stmtFinal.setDouble(4, notaFinal);
            stmtFinal.setString(5, matricula);
            stmtFinal.setInt(6, idMateria);
            stmtFinal.setInt(7, idProfesor);
            stmtFinal.executeUpdate();
        }
    }

    // Retorna todas las calificaciones de un alumno con materia y nombre del profesor
    public List<Calificacion> getCalificacionesDelAlumno(String matricula) throws SQLException {
        List<Calificacion> lista = new ArrayList<>();
        
        String sql = "SELECT m.nombre_materia, p.nombre AS nombre_profesor, c.parcial_1, c.parcial_2, c.parcial_3, c.nota_final " +
                     "FROM calificaciones c " +
                     "JOIN materias m ON c.id_materia = m.id_materia " +
                     "JOIN profesores p ON c.id_profesor = p.id_profesor " +
                     "WHERE c.matricula_alumno = ? " +
                     "ORDER BY m.nombre_materia";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                List<Double> parciales = new ArrayList<>();
                parciales.add(rs.getDouble("parcial_1"));
                parciales.add(rs.getDouble("parcial_2"));
                parciales.add(rs.getDouble("parcial_3"));
                
                Calificacion cal = new Calificacion(
                    rs.getString("nombre_materia"),
                    parciales,
                    rs.getDouble("nota_final")
                );
                
                cal.setNombreProfesor(rs.getString("nombre_profesor"));
                lista.add(cal);
            }
        }
        return lista;
    }
}
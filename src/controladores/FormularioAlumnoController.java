package controladores;

import java.sql.SQLException; 
import java.util.List;
import javax.swing.JOptionPane;
import models.Alumno;
import models.AlumnoTableModel; 
import repositorio.AlumnoRepository;
import views.FormularioAlumnoPanel;
import views.MainWindow;
import excepciones.InvalidUser;

public class FormularioAlumnoController {
    
    private FormularioAlumnoPanel view;
    private MainWindow mainWindow;
    private AlumnoRepository repo;
    private AlumnoTableModel tableModel; 
    private int indexEdicion;

    public FormularioAlumnoController(FormularioAlumnoPanel view, MainWindow mainWindow, AlumnoTableModel tableModel) {
        this(view, mainWindow, tableModel, -1); 
    }

    public FormularioAlumnoController(FormularioAlumnoPanel view, MainWindow mainWindow, AlumnoTableModel tableModel, int index) {
        this.view = view;
        this.mainWindow = mainWindow;
        this.tableModel = tableModel; 
        this.repo = new AlumnoRepository();
        this.indexEdicion = index;
        this.initEvents();
    }

    private void initEvents() {
        view.getBtnGuardar().addActionListener(e -> guardarOActualizarAlumno());
    }

    private void guardarOActualizarAlumno() {
        String matricula = view.getMatricula().trim();
        String nombre = view.getNombre().trim();
        String email = view.getEmail().trim();
        String semestre = view.getSemestre();
        String grupo = view.getGrupo();

        try {
            if (matricula.isEmpty() || nombre.isEmpty() || email.isEmpty()) {
                throw new InvalidUser("Todos los campos de texto son obligatorios.");
            }

            if (nombre.length() < 3) {
                throw new InvalidUser("El nombre es demasiado corto. Ingresa al menos 3 caracteres.");
            }

            if (!matricula.matches("\\d+")) {
                throw new InvalidUser("La matrícula '" + matricula + "' es inválida. Solo debe contener números.");
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new InvalidUser("El correo electrónico '" + email + "' no tiene un formato válido.");
            }
            
            List<Alumno> alumnosExistentes = repo.getAlumnos();

            for (int i = 0; i < alumnosExistentes.size(); i++) {
                if (indexEdicion != -1 && i == indexEdicion) continue;

                Alumno existente = alumnosExistentes.get(i);

                if (existente.getMatricula().equals(matricula)) {
                    throw new InvalidUser("Error: La matrícula '" + matricula + "' ya está asignada a " + existente.getNombre());
                }

                if (existente.getEmail().equalsIgnoreCase(email)) {
                    throw new InvalidUser("Error: El correo '" + email + "' ya está registrado con otro alumno.");
                }
            }

            Alumno alumnoListo = new Alumno(matricula, nombre, email, semestre, grupo);
            
            if (indexEdicion == -1) {
                repo.save(alumnoListo); 
                
                tableModel.addRow(alumnoListo); 
                
                JOptionPane.showMessageDialog(view, "¡Alumno registrado con éxito!");
            } else {
                repo.update(indexEdicion, alumnoListo); 
                
                tableModel.updateRow(indexEdicion, alumnoListo); 
                
                JOptionPane.showMessageDialog(view, "¡Datos actualizados correctamente!");
            }
            
            mainWindow.mostrarTabla(); 
            
        } catch (InvalidUser ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Validación de Datos", JOptionPane.WARNING_MESSAGE);
            
        } catch (SQLException ex) { 
            JOptionPane.showMessageDialog(view, "Error al conectar con la base de datos: " + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Ocurrió un error inesperado: " + ex.getMessage());
        }
    }
}
package controladores;

import models.Calificacion;
import java.util.List;
import javax.swing.JOptionPane;
import repositorio.AlumnoRepository;
import utils.Session;
import views.AlumnoMainView;

public class AlumnoController {

    private final AlumnoMainView vista;
    private final AlumnoRepository repo;

    // prepara la vista del alumno y carga sus datos al abrir
    public AlumnoController(AlumnoMainView vista) {
        this.vista = vista;
        this.repo  = new AlumnoRepository();
        cargarCalificaciones(); // Llena la tabla antes de mostrar la ventana
        initListeners();
        this.vista.setVisible(true);
    }

    // Consulta las calificaciones del alumno en sesión y las muestra en la tabla
    private void cargarCalificaciones() {

        // Obtiene la matrícula del alumno que inició sesión
        String matricula = Session.getMatriculaAlumno();

        // Si no hay sesión activa, no hay nada que mostrar
        if (matricula == null || matricula.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "No hay sesión activa.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Pide al repositorio las calificaciones de esa matrícula
            List<Calificacion> misNotas = repo.getCalificacionesDelAlumno(matricula);

            vista.getModeloCalificaciones().setRowCount(0); // Limpia la tabla antes de llenarla

            // Si la lista viene vacía, avisa al alumno y termina
            if (misNotas.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                    "Aún no tienes calificaciones registradas.",
                    "Sin datos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Recorre cada calificación y agrega una fila a la tabla
            for (Calificacion c : misNotas) {
                // Si un parcial o la nota final es 0.0, muestra "-" en lugar del número
                Object p1 = c.getParciales().get(0) == 0.0 ? "-" : c.getParciales().get(0);
                Object p2 = c.getParciales().get(1) == 0.0 ? "-" : c.getParciales().get(1);
                Object p3 = c.getParciales().get(2) == 0.0 ? "-" : c.getParciales().get(2);
                Object nf = c.getNotaFinal()        == 0.0 ? "-" : c.getNotaFinal();

                vista.getModeloCalificaciones().addRow(new Object[]{
                    c.getMateria(),
                    c.getNombreProfesor(),
                    p1, p2, p3, nf
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                "Error al cargar calificaciones: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // Registra el único botón de esta vista: cerrar sesión
    private void initListeners() {
        vista.getBtnLogout().addActionListener(e -> {
            Session.logout();       // Borra los datos de la sesión actual
            vista.dispose();        // Cierra la ventana del alumno
            new views.LoginWindow().setVisible(true); // Regresa al login
        });
    }
}
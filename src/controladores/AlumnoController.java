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

    public AlumnoController(AlumnoMainView vista) {
        this.vista = vista;
        this.repo  = new AlumnoRepository();

        cargarCalificaciones();
        initListeners();

        this.vista.setVisible(true);
    }

    private void cargarCalificaciones() {
        String matricula = Session.getMatriculaAlumno();

        if (matricula == null || matricula.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "No hay sesión activa.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<Calificacion> misNotas = repo.getCalificacionesDelAlumno(matricula);

            vista.getModeloCalificaciones().setRowCount(0);

            if (misNotas.isEmpty()) {
                JOptionPane.showMessageDialog(vista,
                    "Aún no tienes calificaciones registradas.",
                    "Sin datos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Calificacion c : misNotas) {
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

    private void initListeners() {
        vista.getBtnLogout().addActionListener(e -> {
            Session.logout();
            vista.dispose();
            new views.LoginWindow().setVisible(true);
        });
    }
}
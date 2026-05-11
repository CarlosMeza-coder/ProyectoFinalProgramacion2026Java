package controladores;

import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File;
import models.Alumno;
import models.AlumnoTableModel;
import repositorio.AlumnoRepository;
import views.UsersView;
import views.MainWindow;
import services.PDFExporter;
import utils.Config;
import excepciones.InvalidUser;

public class AlumnoController {
    private UsersView view;
    private AlumnoRepository repo;
    private AlumnoTableModel model;
    private MainWindow mainWindow;

    public AlumnoController(UsersView view, MainWindow mainWindow) {
        this.view = view;
        this.mainWindow = mainWindow;
        this.repo = new AlumnoRepository();
        this.registerListeners();
    }

    private void registerListeners() {
        view.getBtnAdd().addActionListener(e -> {
            mainWindow.mostrarFormulario();
        });

        view.getBtnEdit().addActionListener(e -> {
            try {
                int row = view.getTable().getSelectedRow();
                if (row == -1) {
                    throw new InvalidUser("Por favor, selecciona un alumno de la tabla para poder editar sus datos.");
                }
                
                List<Alumno> alumnos = repo.getAlumnos();
                Alumno alumnoSeleccionado = alumnos.get(row);
                mainWindow.mostrarFormularioEdicion(alumnoSeleccionado, row);
                
            } catch (InvalidUser ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Atención", JOptionPane.WARNING_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Error al acceder a los datos: " + ex.getMessage());
            }
        });

        view.getBtnDelete().addActionListener(e -> deleteAlumno());

        view.getBtnPDF().addActionListener(e -> {
            try {
                List<Alumno> alumnos = repo.getAlumnos();
                
                if (alumnos.isEmpty()) {
                    throw new InvalidUser("No hay datos en la lista. No se puede generar un PDF vacío.");
                }

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar Reporte de Alumnos");

                String ultimaRuta = Config.get("users.export.pdf", System.getProperty("user.home"));
                fileChooser.setCurrentDirectory(new File(ultimaRuta));
                fileChooser.setSelectedFile(new File("ReporteAlumnos.pdf"));

                int seleccion = fileChooser.showSaveDialog(view);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File archivoElegido = fileChooser.getSelectedFile();
                    String dest = archivoElegido.getAbsolutePath();

                    PDFExporter.exportAlumnos(alumnos, dest);
                    Config.set("users.export.pdf", archivoElegido.getParent());

                    JOptionPane.showMessageDialog(view, "¡Reporte generado con éxito!");
                }

            } catch (InvalidUser ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error de Exportación", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Error al generar el PDF: " + ex.getMessage());
            }
        });
    }

    public void loadAlumnos() {
        try {
            List<Alumno> alumnos = repo.getAlumnos();
            model = new AlumnoTableModel(alumnos);
            view.setTableModel(model);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error al cargar la tabla: " + e.getMessage());
        }
    }

    private void deleteAlumno() {
        try {
            int row = view.getTable().getSelectedRow();
            if (row == -1) {
                throw new InvalidUser("Selecciona un alumno de la tabla para eliminarlo.");
            }

            int confirm = JOptionPane.showConfirmDialog(
                view, 
                "¿Estás seguro de que deseas eliminar permanentemente a este alumno?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                repo.delete(row);
                loadAlumnos();
                JOptionPane.showMessageDialog(view, "Alumno eliminado correctamente.");
            }
            
        } catch (InvalidUser ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Acción requerida", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error técnico al eliminar: " + e.getMessage());
        }
    }
}
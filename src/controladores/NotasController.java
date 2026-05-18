package controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Alumno;
import models.Calificacion;
import repositorio.AlumnoRepository;
import views.NotasView;
import excepciones.InvalidUser;

public class NotasController {
    private NotasView view;
    private AlumnoRepository repo;
    private List<Alumno> alumnosEnTabla; 

    public NotasController(NotasView view) {
        this.view = view;
        this.repo = new AlumnoRepository();
        this.alumnosEnTabla = new ArrayList<>();
        this.initEvents();
    }

    private void initEvents() {
        view.getBtnCargar().addActionListener(e -> cargarAlumnos());
        view.getBtnGuardar().addActionListener(e -> guardarCalificaciones());
    }

    private void cargarAlumnos() {
        String semestre = view.getSemestre();
        String grupo = view.getGrupo();
        String materia = view.getMateria();

        try {
            List<Alumno> todos = repo.getAlumnos();
            
            for (Alumno alumno : todos) {
                Calificacion notaBD = repo.getCalificacionPorMateria(alumno.getMatricula(), materia);
                if (notaBD != null) {
                    alumno.getCalificaciones().clear();
                    alumno.getCalificaciones().add(notaBD);
                }
            }

            alumnosEnTabla = todos.stream()
                .filter(a -> a.getSemestre().equals(semestre) && a.getGrupo().equals(grupo))
                .collect(Collectors.toList());

            if (alumnosEnTabla.isEmpty()) {
                throw new InvalidUser("No se encontraron alumnos en " + semestre + " grupo " + grupo);
            }

            DefaultTableModel model = view.getTableModel();
            model.setRowCount(0); 

            for (Alumno alumno : alumnosEnTabla) {
                Object[] row = new Object[6];  
                row[0] = alumno.getMatricula();
                row[1] = alumno.getNombre();
                
                Calificacion notaExistente = null;
                for (Calificacion c : alumno.getCalificaciones()) {
                    if (c.getMateria().equals(materia)) {
                        notaExistente = c;
                        break;
                    }
                }

                if (notaExistente != null && notaExistente.getParciales() != null) {
                    List<Double> p = notaExistente.getParciales();
                    row[2] = p.size() > 0 ? p.get(0) : "";  
                    row[3] = p.size() > 1 ? p.get(1) : "";
                    row[4] = p.size() > 2 ? p.get(2) : "";
                    row[5] = notaExistente.getNotaFinal();
                } else {
                    row[2] = ""; row[3] = ""; row[4] = ""; row[5] = "";
                }

                model.addRow(row);
            }
        } catch (InvalidUser ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Búsqueda vacía", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(view, "Error al cargar desde MySQL: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarCalificaciones() {
        try {
            if (alumnosEnTabla.isEmpty()) {
                throw new InvalidUser("No hay alumnos en la tabla para calificar.");
            }

            String materiaSeleccionada = view.getMateria();
            DefaultTableModel model = view.getTableModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                String matriculaTabla = model.getValueAt(i, 0).toString();
                String nombreAlumno = model.getValueAt(i, 1).toString();

                List<Double> streamNotas = new ArrayList<>();                    
                for (int col = 2; col <= 4; col++) { 
                    Object valorCelda = model.getValueAt(i, col);
                    double nota = validarNota(valorCelda, "Parcial " + (col - 1), nombreAlumno);
                    streamNotas.add(nota);
                }

                Object valorFinal = model.getValueAt(i, 5);
                double notaFinal = validarNota(valorFinal, "Final", nombreAlumno);

                repo.guardarOActualizarNotas(matriculaTabla, materiaSeleccionada, streamNotas, notaFinal);
            }

            JOptionPane.showMessageDialog(view, "Calificaciones guardadas con éxito en la base de datos.");

        } catch (InvalidUser ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error de Calificación", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error de persistencia en MySQL: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double validarNota(Object valor, String tipoNota, String nombreAlumno) throws InvalidUser {
        if (valor == null || valor.toString().trim().isEmpty()) {
            return 0.0; 
        }
        try {
            double nota = Double.parseDouble(valor.toString());
            if (nota < 0 || nota > 10) {
                throw new InvalidUser("La nota '" + tipoNota + "' de " + nombreAlumno + " debe estar entre 0 y 10.");
            }
            return nota;
        } catch (NumberFormatException e) {
            throw new InvalidUser("Error en " + nombreAlumno + ": '" + valor + "' no es un número válido para " + tipoNota);
        }
    }
}
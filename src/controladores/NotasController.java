package controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Alumno;
import models.Calificacion;
import repositorio.AlumnoRepository;
import views.NotasView;
import excepciones.InvalidUser;
import utils.Session;
import services.PDFExporter;

public class NotasController {

    private NotasView view;
    private AlumnoRepository repo;
    private List<Alumno> alumnosEnTabla; // Lista en memoria de los alumnos actualmente visibles

    // Constructor: inicializa la lista, carga los filtros y registra los eventos
    public NotasController(NotasView view) {
        this.view = view;
        this.repo = new AlumnoRepository();
        this.alumnosEnTabla = new ArrayList<>();
        cargarFiltros();   // Llena los combos antes de que el profesor interactúe
        this.initEvents();
    }

    // Llena los combos de semestre, grupo y materia con datos reales de la BD
    private void cargarFiltros() {
        try {
            int idProfesor = Session.getProfesorId(); // Solo muestra materias de este profesor

            // Trae pares [semestre, grupo] disponibles en la BD
            List<String[]> gruposBD = repo.getGruposDisponibles();

            // Usa LinkedHashSet para eliminar duplicados y mantener el orden de inserción
            Set<String> semestres = new LinkedHashSet<>();
            Set<String> gruposSet = new LinkedHashSet<>();
            for (String[] g : gruposBD) {
                semestres.add(g[0]); // Índice 0 = semestre
                gruposSet.add(g[1]); // Índice 1 = grupo
            }

            view.getCbSemestre().removeAllItems();
            view.getCbGrupo().removeAllItems();

            // Agrega los semestres en orden lógico escolar, no alfabético
            String[] ordenIdeal = {"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto", "Séptimo", "Octavo"};
            for (String semestreIdeal : ordenIdeal) {
                if (semestres.contains(semestreIdeal)) // Solo agrega los que existen en BD
                    view.getCbSemestre().addItem(semestreIdeal);
            }

            // Grupos en orden alfabético (A, B, C...)
            List<String> listaGrupos = new ArrayList<>(gruposSet);
            java.util.Collections.sort(listaGrupos);
            for (String g : listaGrupos) view.getCbGrupo().addItem(g);

            // Materias que imparte este profesor específicamente
            List<String> materias = repo.getMateriasDelProfesor(idProfesor);
            view.getCbMateria().removeAllItems();
            List<String> listaMaterias = new ArrayList<>(materias);
            java.util.Collections.sort(listaMaterias);
            for (String m : listaMaterias) view.getCbMateria().addItem(m);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error cargando filtros: " + ex.getMessage());
        }
    }

    // Conecta cada botón con su método correspondiente
    private void initEvents() {
        view.getBtnCargar().addActionListener(e -> cargarAlumnos());
        view.getBtnGuardar().addActionListener(e -> guardarCalificaciones());
        view.getBtnGenerarPDF().addActionListener(e -> prepararDatosParaPDF());
    }

    // Carga en la tabla los alumnos del semestre/grupo/materia seleccionados
    private void cargarAlumnos() {
        if (view.getCbSemestre().getSelectedItem() == null) return;

        String semestre = view.getCbSemestre().getSelectedItem().toString();
        String grupo    = view.getCbGrupo().getSelectedItem().toString();
        String materia  = view.getCbMateria().getSelectedItem().toString();
        int idProfesorLogueado = Session.getProfesorId();

        try {
            List<Alumno> todos = repo.getAlumnos(); // Trae todos los alumnos de la BD

            // Para cada alumno, intenta cargar su calificación existente en esta materia
            for (Alumno alumno : todos) {
                Calificacion notaBD = repo.getCalificacionPorMateria(
                    alumno.getMatricula(), materia, idProfesorLogueado);
                if (notaBD != null) {
                    alumno.getCalificaciones().clear();
                    alumno.getCalificaciones().add(notaBD); // Reemplaza con la nota real de BD
                }
            }

            // Filtra solo los alumnos que coinciden con el semestre y grupo seleccionados
            alumnosEnTabla = todos.stream()
                .filter(a -> a.getSemestre().equals(semestre) && a.getGrupo().equals(grupo))
                .collect(Collectors.toList());

            if (alumnosEnTabla.isEmpty()) {
                throw new InvalidUser("No se encontraron alumnos en "
                    + semestre + " grupo " + grupo + " para esta materia.");
            }

            DefaultTableModel model = view.getTableModel();
            model.setRowCount(0); // Limpia la tabla antes de llenarla

            for (Alumno alumno : alumnosEnTabla) {
                Object[] row = new Object[6]; // Matrícula, Nombre, P1, P2, P3, Final
                row[0] = alumno.getMatricula();
                row[1] = alumno.getNombre();

                // Busca si este alumno ya tiene nota guardada para esta materia
                Calificacion notaExistente = null;
                for (Calificacion c : alumno.getCalificaciones()) {
                    if (c.getMateria().equals(materia)) {
                        notaExistente = c;
                        break;
                    }
                }

                // Si tiene notas previas las muestra, si no deja las celdas vacías
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
            JOptionPane.showMessageDialog(view, ex.getMessage(),
                "Búsqueda vacía", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar desde MySQL: " + ex.getMessage(),
                "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lee la tabla fila por fila, valida las notas y las guarda en la BD
    private void guardarCalificaciones() {
        int idProfesorLogueado = Session.getProfesorId();

        try {
            if (alumnosEnTabla.isEmpty()) {
                throw new InvalidUser("No hay alumnos en la tabla para calificar.");
            }

            String materiaSeleccionada = view.getCbMateria().getSelectedItem().toString();
            DefaultTableModel model = view.getTableModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                String matriculaTabla = model.getValueAt(i, 0).toString();
                String nombreAlumno   = model.getValueAt(i, 1).toString();

                // Lee y valida las columnas 2, 3 y 4 (Parcial 1, 2 y 3)
                List<Double> streamNotas = new ArrayList<>();
                for (int col = 2; col <= 4; col++) {
                    Object valorCelda = model.getValueAt(i, col);
                    double nota = validarNota(valorCelda, "Parcial " + (col - 1), nombreAlumno);
                    streamNotas.add(nota);
                }

                // Lee y valida la columna 5 (Nota Final)
                Object valorFinal = model.getValueAt(i, 5);
                double notaFinal = validarNota(valorFinal, "Final", nombreAlumno);

                // Guarda o actualiza en BD según si ya existía un registro
                repo.guardarOActualizarNotas(
                    matriculaTabla, materiaSeleccionada, streamNotas, notaFinal, idProfesorLogueado);
            }

            JOptionPane.showMessageDialog(view, "¡Calificaciones guardadas con éxito!");

        } catch (InvalidUser ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(),
                "Error de Calificación", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error de persistencia: " + ex.getMessage(),
                "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Prepara los datos de la tabla y los envía al servicio que genera el PDF
    private void prepararDatosParaPDF() {

        // No genera PDF si la tabla está vacía
        if (view.getTableModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "No hay datos en la tabla para exportar.",
                "Tabla Vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String materia  = view.getMateria();
        String grupo    = view.getGrupo();
        String semestre = view.getSemestre();

        if (materia.isEmpty() || grupo.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Por favor, cargue una materia y grupo primero.",
                "Faltan Datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Extrae los nombres de las columnas de la tabla
        int columnCount = view.getTableModel().getColumnCount();
        String[] encabezados = new String[columnCount];
        for (int i = 0; i < columnCount; i++)
            encabezados[i] = view.getTableModel().getColumnName(i);

        // Convierte cada fila de la tabla a un arreglo de Strings para el PDF
        int rowCount = view.getTableModel().getRowCount();
        List<String[]> filas = new ArrayList<>();
        for (int i = 0; i < rowCount; i++) {
            String[] filaDatos = new String[columnCount];
            for (int j = 0; j < columnCount; j++) {
                Object valor = view.getTableModel().getValueAt(i, j);
                filaDatos[j] = (valor != null) ? valor.toString() : ""; // null se convierte a ""
            }
            filas.add(filaDatos);
        }

        // Delega la generación del archivo al servicio PDFExporter
        boolean exito = PDFExporter.crearReporteCalificaciones(
            materia, grupo, semestre, encabezados, filas);

        if (exito) {
            JOptionPane.showMessageDialog(view, "PDF generado correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Ocurrió un error al generar el PDF.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Valida que un valor de celda sea un número entre 0 y 10
    // Si está vacío devuelve 0.0, si es inválido lanza InvalidUser
    private double validarNota(Object valor, String tipoNota, String nombreAlumno) throws InvalidUser {
        if (valor == null || valor.toString().trim().isEmpty()) {
            return 0.0; // Celda vacía = nota no capturada todavía
        }
        try {
            double nota = Double.parseDouble(valor.toString());
            if (nota < 0 || nota > 10) {
                throw new InvalidUser("La nota '" + tipoNota + "' de "
                    + nombreAlumno + " debe estar entre 0 y 10.");
            }
            return nota;
        } catch (NumberFormatException e) {
            // El profesor escribió texto en lugar de número
            throw new InvalidUser("Error en " + nombreAlumno
                + ": '" + valor + "' no es un número válido.");
        }
    }
}
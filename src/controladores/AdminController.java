package controladores;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import models.Alumno;
import models.Profesor;
import repositorio.AlumnoRepository;
import repositorio.ProfesorRepository;
import repositorio.AsignacionRepository;
import views.AdminMainView;

public class AdminController {
    
    private AdminMainView view;
    private AlumnoRepository alumRepo;
    private ProfesorRepository profRepo;
    private AsignacionRepository asigRepo;

    //  recibe la vista y crea los repositorios necesarios
    public AdminController(AdminMainView view) {
        this.view = view;
        this.alumRepo = new AlumnoRepository();
        this.profRepo = new ProfesorRepository();
        this.asigRepo = new AsignacionRepository();
        
        initController();                    // Enlaza botones con sus acciones
        cargarTodasLasTablasYCombos();       // Carga datos iniciales en tablas
        view.setVisible(true);              // Muestra la ventana
    }
    
    // Registra los listeners de todos los botones y el cambio de pestaña
    private void initController() {
        view.getBtnGuardarAlum().addActionListener(e -> guardarOActualizarAlumno());
        view.getBtnEditarAlum().addActionListener(e -> prepararEdicionAlumno());
        view.getBtnEliminarAlum().addActionListener(e -> eliminarAlumno());

        view.getBtnGuardarProf().addActionListener(e -> guardarOActualizarProfesor());
        view.getBtnEditarProf().addActionListener(e -> prepararEdicionProfesor());
        view.getBtnEliminarProf().addActionListener(e -> eliminarProfesor());

        view.getBtnInscribir().addActionListener(e -> registrarInscripcion());
        
        // Cuando el admin cambia a la pestaña de inscripciones (índice 2), recarga los combos
        view.getTabbedPane().addChangeListener(e -> {
            if (view.getTabbedPane().getSelectedIndex() == 2) {
                cargarCombosInscripcion();
            }
        });
    }

    // Guarda un alumno nuevo o actualiza uno existente según el modo actual
    private void guardarOActualizarAlumno() {
        // Obtiene los valores de los campos de texto
        String mat   = view.getTxtMatricula().getText().trim();
        String nom   = view.getTxtNombreAlum().getText().trim();
        String ape   = view.getTxtApellidoAlum().getText().trim();
        String email = view.getTxtEmailAlum().getText().trim();
        String sem   = view.getCbSemestre().getSelectedItem().toString();
        String grupo = view.getTxtGrupo().getText().trim();
        
        // Valida que ningún campo esté vacío
        if (mat.isEmpty() || nom.isEmpty() || ape.isEmpty() || email.isEmpty() || grupo.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Todos los campos son obligatorios.");
            return;
        }
        
        Alumno alum = new Alumno(mat, nom, email, sem, grupo);
        alum.setApellido(ape);
        
        try {
            if (view.isModoEdicionAlumno()) {
                // Modo edición: actualiza el alumno existente
                alumRepo.update(alum);
                JOptionPane.showMessageDialog(view, "Alumno actualizado.");
            } else {
                // Modo nuevo: inserta el alumno en la BD
                alumRepo.save(alum);
                JOptionPane.showMessageDialog(view, "Alumno registrado.");
            }
            view.limpiarCamposAlumno();           // Limpia el formulario
            view.setModoEdicionAlumno(false);     // Regresa al modo "nuevo"
            view.getBtnGuardarAlum().setText("Guardar");
            actualizarTablaAlumnos();             // Refresca la tabla
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error BD Alumnos: " + ex.getMessage());
        }
    }

    // Carga los datos del alumno seleccionado en la tabla hacia el formulario para editarlo
    private void prepararEdicionAlumno() {
        int fila = view.getTablaAlumnos().getSelectedRow();
        if (fila == -1) return; // Si no hay fila seleccionada, no hace nada
        view.getTxtMatricula().setText(view.getModeloAlumnos().getValueAt(fila, 0).toString());
        view.getTxtMatricula().setEnabled(false); // La matrícula no se puede cambiar al editar
        view.getTxtNombreAlum().setText(view.getModeloAlumnos().getValueAt(fila, 1).toString());
        view.getTxtApellidoAlum().setText(view.getModeloAlumnos().getValueAt(fila, 2).toString());
        view.getTxtEmailAlum().setText(view.getModeloAlumnos().getValueAt(fila, 3).toString());
        view.getCbSemestre().setSelectedItem(view.getModeloAlumnos().getValueAt(fila, 4).toString());
        view.getTxtGrupo().setText(view.getModeloAlumnos().getValueAt(fila, 5).toString());
        view.setModoEdicionAlumno(true);
        view.getBtnGuardarAlum().setText("Actualizar");
    }

    // Elimina el alumno seleccionado en la tabla y refresca la vista
    private void eliminarAlumno() {
        int fila = view.getTablaAlumnos().getSelectedRow();
        if (fila == -1) return;
        String mat = view.getModeloAlumnos().getValueAt(fila, 0).toString();
        if (alumRepo.delete(mat)) actualizarTablaAlumnos();
    }

    // Guarda un profesor nuevo o actualiza uno existente según idEdit
    private void guardarOActualizarProfesor() {
        String nom   = view.getTxtNombreProf().getText().trim();
        String ape   = view.getTxtApellidoProf().getText().trim();
        String email = view.getTxtEmailProf().getText().trim();
        String pass  = new String(view.getTxtPassProf().getPassword()).trim();
        int idEdit   = view.getIdProfesorEdicion(); // -1 significa que es nuevo
        
        // La contraseña solo es obligatoria al crear un profesor nuevo
        if (nom.isEmpty() || ape.isEmpty() || email.isEmpty() || (idEdit == -1 && pass.isEmpty())) {
            JOptionPane.showMessageDialog(view, "Nombre, Apellido, Email y Contraseña (para nuevos) son obligatorios.");
            return;
        }
        
        Profesor prof = new Profesor();
        prof.setNombre(nom);
        prof.setApellido(ape);
        prof.setEmail(email);
        prof.setPassword(pass);
        
        try {
            if (idEdit == -1) {
                // Modo nuevo: inserta el profesor
                profRepo.save(prof);
                JOptionPane.showMessageDialog(view, "Profesor guardado correctamente.");
            } else {
                // Modo edición: actualiza el profesor con el ID recuperado
                prof.setIdProfesor(idEdit);
                profRepo.update(prof);
                JOptionPane.showMessageDialog(view, "Profesor actualizado correctamente.");
            }
            view.limpiarCamposProfesor();
            view.setIdProfesorEdicion(-1);        // Resetea el ID de edición
            view.getBtnGuardarProf().setText("Guardar Profesor");
            actualizarTablaProfesores();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    // Carga los datos del profesor seleccionado en el formulario para editarlo
    private void prepararEdicionProfesor() {
        int fila = view.getTablaProfesores().getSelectedRow();
        if (fila == -1) return;
        view.setIdProfesorEdicion((int)view.getModeloProfesores().getValueAt(fila, 0)); // Guarda el ID
        view.getTxtNombreProf().setText(view.getModeloProfesores().getValueAt(fila, 1).toString());
        view.getTxtApellidoProf().setText(view.getModeloProfesores().getValueAt(fila, 2).toString());
        view.getTxtEmailProf().setText(view.getModeloProfesores().getValueAt(fila, 3).toString());
        view.getTxtPassProf().setText(""); // Se deja vacío; solo se cambia si el admin escribe algo
        view.getBtnGuardarProf().setText("Actualizar");
    }

    // Elimina el profesor seleccionado usando su ID y refresca la tabla
    private void eliminarProfesor() {
        int fila = view.getTablaProfesores().getSelectedRow();
        if (fila == -1) return;
        int id = (int)view.getModeloProfesores().getValueAt(fila, 0);
        if (profRepo.delete(id)) actualizarTablaProfesores();
    }

    // Lee los combos y llama al repositorio para inscribir al alumno en la materia con el profesor
    private void registrarInscripcion() {
        try {
            // Extrae solo la matrícula del texto "MAT001 - Juan Pérez"
            String mat    = view.getCbAlumnosInsc().getSelectedItem().toString().split(" - ")[0];
            String matNom = view.getCbMateriasInsc().getSelectedItem().toString();
            // Extrae solo el ID del texto "3 - Prof. López"
            int idProf    = Integer.parseInt(view.getCbProfesoresInsc().getSelectedItem().toString().split(" - ")[0]);

            asigRepo.inscribirAlumnoAMateria(mat, idProf, matNom);
            JOptionPane.showMessageDialog(view, "Inscripción exitosa.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    // Carga la tabla de alumnos y profesores al iniciar
    private void cargarTodasLasTablasYCombos() {
        actualizarTablaAlumnos();
        actualizarTablaProfesores();
    }

    // Limpia y vuelve a llenar la tabla de alumnos desde la BD
    private void actualizarTablaAlumnos() {
        view.getModeloAlumnos().setRowCount(0);
        for (Alumno a : alumRepo.getAlumnos()) 
            view.getModeloAlumnos().addRow(new Object[]{a.getMatricula(), a.getNombre(), a.getApellido(), a.getEmail(), a.getSemestre(), a.getGrupo()});
    }

    // Limpia y vuelve a llenar la tabla de profesores desde la BD
    private void actualizarTablaProfesores() {
        view.getModeloProfesores().setRowCount(0);
        for (Profesor p : profRepo.getProfesores()) 
            view.getModeloProfesores().addRow(new Object[]{p.getIdProfesor(), p.getNombre(), p.getApellido(), p.getEmail()});
    }

    // Rellena los tres combos de la pestaña Inscripciones con datos frescos de la BD
    private void cargarCombosInscripcion() {
        view.getCbAlumnosInsc().removeAllItems();
        view.getCbProfesoresInsc().removeAllItems();
        view.getCbMateriasInsc().removeAllItems();

        for (Alumno a : alumRepo.getAlumnos()) 
            view.getCbAlumnosInsc().addItem(a.getMatricula() + " - " + a.getNombre() + " " + a.getApellido());
        for (Profesor p : profRepo.getProfesores()) 
            view.getCbProfesoresInsc().addItem(p.getIdProfesor() + " - " + p.getNombre());
        for (String m : asigRepo.getListaMaterias()) 
            view.getCbMateriasInsc().addItem(m);
    }
}
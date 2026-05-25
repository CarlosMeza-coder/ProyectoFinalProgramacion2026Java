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

    public AdminController(AdminMainView view) {
        this.view = view;
        this.alumRepo = new AlumnoRepository();
        this.profRepo = new ProfesorRepository();
        this.asigRepo = new AsignacionRepository();
        
        initController();
        cargarTodasLasTablasYCombos();
        view.setVisible(true);
    }
    
    private void initController() {
        view.getBtnGuardarAlum().addActionListener(e -> guardarOActualizarAlumno());
        view.getBtnEditarAlum().addActionListener(e -> prepararEdicionAlumno());
        view.getBtnEliminarAlum().addActionListener(e -> eliminarAlumno());

        view.getBtnGuardarProf().addActionListener(e -> guardarOActualizarProfesor());
        view.getBtnEditarProf().addActionListener(e -> prepararEdicionProfesor());
        view.getBtnEliminarProf().addActionListener(e -> eliminarProfesor());

        view.getBtnInscribir().addActionListener(e -> registrarInscripcion());
        
        view.getTabbedPane().addChangeListener(e -> {
            if (view.getTabbedPane().getSelectedIndex() == 2) {
                cargarCombosInscripcion();
            }
        });
    }

 
    private void guardarOActualizarAlumno() {
        String mat   = view.getTxtMatricula().getText().trim();
        String nom   = view.getTxtNombreAlum().getText().trim();
        String email = view.getTxtEmailAlum().getText().trim();
        String sem   = view.getCbSemestre().getSelectedItem().toString();
        String grupo = view.getTxtGrupo().getText().trim();
        
        if (mat.isEmpty() || nom.isEmpty() || email.isEmpty() || grupo.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Todos los campos son obligatorios.");
            return;
        }
        
        Alumno alum = new Alumno(mat, nom, email, sem, grupo);
        try {
            if (view.isModoEdicionAlumno()) {
                alumRepo.update(alum);
                JOptionPane.showMessageDialog(view, "Alumno actualizado.");
            } else {
                alumRepo.save(alum);
                JOptionPane.showMessageDialog(view, "Alumno registrado.");
            }
            view.limpiarCamposAlumno();
            view.setModoEdicionAlumno(false);
            view.getBtnGuardarAlum().setText("Guardar");
            actualizarTablaAlumnos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error BD Alumnos: " + ex.getMessage());
        }
    }

    private void prepararEdicionAlumno() {
        int fila = view.getTablaAlumnos().getSelectedRow();
        if (fila == -1) return;
        view.getTxtMatricula().setText(view.getModeloAlumnos().getValueAt(fila, 0).toString());
        view.getTxtMatricula().setEnabled(false);
        view.getTxtNombreAlum().setText(view.getModeloAlumnos().getValueAt(fila, 1).toString());
        view.getTxtEmailAlum().setText(view.getModeloAlumnos().getValueAt(fila, 2).toString());
        view.getCbSemestre().setSelectedItem(view.getModeloAlumnos().getValueAt(fila, 3).toString());
        view.getTxtGrupo().setText(view.getModeloAlumnos().getValueAt(fila, 4).toString());
        view.setModoEdicionAlumno(true);
        view.getBtnGuardarAlum().setText("Actualizar");
    }

    private void eliminarAlumno() {
        int fila = view.getTablaAlumnos().getSelectedRow();
        if (fila == -1) return;
        String mat = view.getModeloAlumnos().getValueAt(fila, 0).toString();
        if (alumRepo.delete(mat)) actualizarTablaAlumnos();
    }

  
    private void guardarOActualizarProfesor() {
        String nom   = view.getTxtNombreProf().getText().trim();
        String ape   = view.getTxtApellidoProf().getText().trim();
        String email = view.getTxtEmailProf().getText().trim();
        
        String pass  = new String(view.getTxtPassProf().getPassword()).trim();
        
        int idEdit   = view.getIdProfesorEdicion();
        
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
                profRepo.save(prof);
                JOptionPane.showMessageDialog(view, "Profesor guardado correctamente.");
            } else {
                prof.setIdProfesor(idEdit);
                profRepo.update(prof);
                JOptionPane.showMessageDialog(view, "Profesor actualizado correctamente.");
            }
            view.limpiarCamposProfesor();
            view.setIdProfesorEdicion(-1);
            view.getBtnGuardarProf().setText("Guardar Profesor");
            actualizarTablaProfesores();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void prepararEdicionProfesor() {
        int fila = view.getTablaProfesores().getSelectedRow();
        if (fila == -1) return;
        view.setIdProfesorEdicion((int)view.getModeloProfesores().getValueAt(fila, 0));
        view.getTxtNombreProf().setText(view.getModeloProfesores().getValueAt(fila, 1).toString());
        view.getTxtApellidoProf().setText(view.getModeloProfesores().getValueAt(fila, 2).toString());
        view.getTxtEmailProf().setText(view.getModeloProfesores().getValueAt(fila, 3).toString());
        
        view.getTxtPassProf().setText(""); 
        
        view.getBtnGuardarProf().setText("Actualizar");
    }

    private void eliminarProfesor() {
        int fila = view.getTablaProfesores().getSelectedRow();
        if (fila == -1) return;
        int id = (int)view.getModeloProfesores().getValueAt(fila, 0);
        if (profRepo.delete(id)) actualizarTablaProfesores();
    }

    private void registrarInscripcion() {
        try {
            String mat    = view.getCbAlumnosInsc().getSelectedItem().toString().split(" - ")[0];    // ← [0]
            String matNom = view.getCbMateriasInsc().getSelectedItem().toString();
            int idProf    = Integer.parseInt(view.getCbProfesoresInsc().getSelectedItem().toString().split(" - ")[0]); // ← [0]

            asigRepo.inscribirAlumnoAMateria(mat, idProf, matNom);
            JOptionPane.showMessageDialog(view, "Inscripción exitosa.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error: " + ex.getMessage());
        }
    }

    private void cargarTodasLasTablasYCombos() {
        actualizarTablaAlumnos();
        actualizarTablaProfesores();
    }

    private void actualizarTablaAlumnos() {
        view.getModeloAlumnos().setRowCount(0);
        for (Alumno a : alumRepo.getAlumnos()) 
            view.getModeloAlumnos().addRow(new Object[]{a.getMatricula(), a.getNombre(), a.getEmail(), a.getSemestre(), a.getGrupo()});
    }

    private void actualizarTablaProfesores() {
        view.getModeloProfesores().setRowCount(0);
        for (Profesor p : profRepo.getProfesores()) 
            view.getModeloProfesores().addRow(new Object[]{p.getIdProfesor(), p.getNombre(), p.getApellido(), p.getEmail()});
    }

    private void cargarCombosInscripcion() {
        view.getCbAlumnosInsc().removeAllItems();
        view.getCbProfesoresInsc().removeAllItems();
        view.getCbMateriasInsc().removeAllItems();

        for (Alumno a : alumRepo.getAlumnos()) 
            view.getCbAlumnosInsc().addItem(a.getMatricula() + " - " + a.getNombre());
        for (Profesor p : profRepo.getProfesores()) 
            view.getCbProfesoresInsc().addItem(p.getIdProfesor() + " - " + p.getNombre());
        for (String m : asigRepo.getListaMaterias()) 
            view.getCbMateriasInsc().addItem(m);
    }
}
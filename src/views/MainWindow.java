package views;

import javax.swing.*;
import controladores.AlumnoController;
import controladores.FormularioAlumnoController;
import models.Alumno; // IMPORTANTE: Agregamos el import de Alumno

public class MainWindow extends JFrame {

    private UsersView usersView;
    private FormularioAlumnoPanel formularioPanel;

    public MainWindow() {
        setTitle("Gestión de Alumnos - Panel Administrativo");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);

        mostrarTabla();
    }

    public void mostrarTabla() {
        getContentPane().removeAll();
        
        usersView = new UsersView();
        
        AlumnoController controller = new AlumnoController(usersView, this); 
        controller.loadAlumnos();

        add(usersView);
        
        revalidate();
        repaint();
    }


    public void mostrarFormulario() {
        getContentPane().removeAll();
        
        formularioPanel = new FormularioAlumnoPanel();
        
        new FormularioAlumnoController(formularioPanel, this);

        formularioPanel.getBtnCancelar().addActionListener(e -> mostrarTabla());

        add(formularioPanel);
        
        revalidate();
        repaint();
    }

    public void mostrarFormularioEdicion(Alumno alumno, int index) {
        getContentPane().removeAll();
        
        formularioPanel = new FormularioAlumnoPanel();

        formularioPanel.setMatricula(alumno.getMatricula());
        formularioPanel.setNombre(alumno.getNombre());
        formularioPanel.setEmail(alumno.getEmail());
        formularioPanel.setSemestre(alumno.getSemestre());
        formularioPanel.setGrupo(alumno.getGrupo());
        
        formularioPanel.getBtnGuardar().setText("Actualizar Alumno");

        new FormularioAlumnoController(formularioPanel, this, index);

        formularioPanel.getBtnCancelar().addActionListener(e -> mostrarTabla());

        add(formularioPanel);
        
        revalidate();
        repaint();
    }
}
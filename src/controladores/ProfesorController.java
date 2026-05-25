package controladores;

import javax.swing.JFrame; 
import javax.swing.JOptionPane;
import utils.Session;
import views.ProfesorMainView;
import views.MateriasView;   
import views.NotasView;      

public class ProfesorController {
    
    private ProfesorMainView view;

    public ProfesorController(ProfesorMainView view) {
        this.view = view;
        initController();
        this.view.setVisible(true);
    }

    private void initController() {
        view.getBtnMaterias().addActionListener(e -> verMisMaterias());
        view.getBtnNotas().addActionListener(e -> capturarNotas());
        
        view.getBtnLogout().addActionListener(e -> cerrarSesion());
        view.getBtnTema().addActionListener(e -> cambiarTema());
    }

    
    private void verMisMaterias() {
        int idProfesor = Session.getProfesorId();
        System.out.println("DEBUG - Abriendo vista de Materias para el ID: " + idProfesor);
        
        MateriasView panelMaterias = new MateriasView();
        
        new MateriasController(panelMaterias, idProfesor);
    }

    private void capturarNotas() {
        int idProfesor = Session.getProfesorId();
        System.out.println("DEBUG - Abriendo vista de Captura de Notas para el ID: " + idProfesor);
        
        NotasView panelNotas = new NotasView();
        
        new NotasController(panelNotas);
        
        JFrame frameNotas = new JFrame("Captura de Calificaciones - Panel Docente");
        frameNotas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        frameNotas.setSize(950, 600); 
        frameNotas.setLocationRelativeTo(null); 
        
        frameNotas.add(panelNotas); 
        frameNotas.setVisible(true); 
    }

    
    private void cerrarSesion() {
        Session.logout();
        view.dispose();
    }

    private void cambiarTema() {
        JOptionPane.showMessageDialog(view, "Función de cambio de tema en desarrollo.");
    }
}
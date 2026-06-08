package controladores;

import javax.swing.JFrame;
import utils.Session;
import utils.ThemeManager;
import views.ProfesorMainView;
import views.MateriasView;
import views.NotasView;

public class ProfesorController {

    private ProfesorMainView view;

    // recibe la vista principal del profesor y registra los botones
    public ProfesorController(ProfesorMainView view) {
        this.view = view;
        initController();
        this.view.setVisible(true);
    }

    // Conecta cada botón del menú principal con su acción
    private void initController() {
        view.getBtnMaterias().addActionListener(e -> verMisMaterias());
        view.getBtnNotas().addActionListener(e -> capturarNotas());
        view.getBtnLogout().addActionListener(e -> cerrarSesion());
        view.getBtnTema().addActionListener(e -> cambiarTema());
    }

    // Abre la ventana de materias pasándole el ID del profesor en sesión
    private void verMisMaterias() {
        int idProfesor = Session.getProfesorId();
        System.out.println("DEBUG - Abriendo vista de Materias para el ID: " + idProfesor);

        MateriasView panelMaterias = new MateriasView();
        new MateriasController(panelMaterias, idProfesor); // El controller crea su propio JFrame
    }

    // Abre la ventana de captura de calificaciones
    private void capturarNotas() {
        int idProfesor = Session.getProfesorId();
        System.out.println("DEBUG - Abriendo vista de Captura de Notas para el ID: " + idProfesor);

        NotasView panelNotas = new NotasView();
        new NotasController(panelNotas); // Inicializa la lógica y los filtros del panel

        // A diferencia de MateriasController, aquí el JFrame se construye manualmente
        JFrame frameNotas = new JFrame("Captura de Calificaciones - Panel Docente");
        frameNotas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // No cierra toda la app
        frameNotas.setSize(950, 600);
        frameNotas.setLocationRelativeTo(null); // Centra en pantalla

        frameNotas.add(panelNotas);
        frameNotas.setVisible(true);
    }

    // Cierra la sesión y cierra la ventana del profesor (regresa al login desde LoginController)
    private void cerrarSesion() {
        Session.logout();
        view.dispose();
    }

    // Alterna entre tema claro y oscuro
    private void cambiarTema() {
        ThemeManager.toggle();
    }
}
package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import views.ProfesorMainView;
import views.MateriasView;
import views.NotasView;
import views.MainWindow;
import views.LoginWindow; // Importamos el Frame del login
import views.ViewLogin;   // Importamos la vista del login

public class ProfesorController {
    private ProfesorMainView view;

    public ProfesorController(ProfesorMainView view) {
        this.view = view;
        this.registerListeners();
    }

    private void registerListeners() {
        view.getBtnAlumnos().addActionListener(e -> {
            MainWindow alumnosWin = new MainWindow();
            alumnosWin.setVisible(true);
        });

        view.getBtnMaterias().addActionListener(e -> {
            abrirVentanaHija(new MateriasView(), "Mis Materias Asignadas", 700, 400);
        });

        view.getBtnNotas().addActionListener(e -> {
            NotasView vistaNotas = new NotasView();
            
            new NotasController(vistaNotas); 
            
            abrirVentanaHija(vistaNotas, "Registro de Calificaciones", 900, 550);
        });


        view.getBtnReportes().addActionListener(e -> {
            abrirVentanaHija(new NotasView(), "Generación de Reportes", 900, 550);
        });

        view.getBtnLogout().addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                view, 
                "¿Estás seguro de que deseas cerrar sesión?", 
                "Cerrar Sesión", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                view.dispose(); 
                
                LoginWindow loginWin = new LoginWindow();
                
                ViewLogin loginView = new ViewLogin(loginWin);
                
                new LoginController(loginView);
                
                loginWin.setVisible(true);
            }
        });

        view.getBtnTema().addActionListener(e -> {
            utils.ThemeManager.toggle(); 
        });
    }

    private void abrirVentanaHija(javax.swing.JPanel panelContenido, String titulo, int ancho, int alto) {
        JFrame frame = new JFrame(titulo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panelContenido);
        frame.pack();
        frame.setSize(ancho, alto);
        frame.setLocationRelativeTo(view);
        frame.setVisible(true);
    }
}
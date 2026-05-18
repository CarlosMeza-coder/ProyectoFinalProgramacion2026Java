package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import models.User;
import repositorio.UserRepository;
import views.ViewLogin;
import views.ProfesorMainView;
import views.AdminMainView;  // Asegúrate de crear esta ventana en views
import views.AlumnoMainView; // Asegúrate de crear esta ventana en views
import views.FormularioRegistro;
import excepciones.InvalidUser;
import utils.Session;

public class LoginController {
    private ViewLogin view;
    private UserRepository repo;

    public LoginController(ViewLogin view) {
        this.view = view;
        this.repo = new UserRepository();
        this.initEvents();
    }

    private void initEvents() {
        view.getBtnLogin().addActionListener(e -> {
            String email = view.getTxtEmail().getText().trim();
            String pass = new String(view.getTxtPass().getPassword());

            try {
                if (email.isEmpty() || pass.isEmpty()) {
                    throw new InvalidUser("No puedes dejar el correo o la contraseña vacíos.");
                }

                if (!email.contains("@") || !email.contains(".")) {
                    throw new InvalidUser("El formato del correo electrónico es inválido.");
                }

                User usuarioValido = repo.login(email, pass);

                if (usuarioValido != null) {
                    Session.login(usuarioValido);
                    
                    view.getWindow().dispose();
                    
                    String rol = Session.getRole().toUpperCase();
                    
                    switch (rol) {
                        case "PROFESOR":
                            ProfesorMainView viewProfe = new ProfesorMainView();
                            new ProfesorController(viewProfe);
                            viewProfe.setVisible(true);
                            break;
                            
                        case "ADMINISTRATIVO":
                            AdminMainView viewAdmin = new AdminMainView();
                            viewAdmin.setVisible(true);
                            break;
                            
                        case "ALUMNO":
                            AlumnoMainView viewAlumno = new AlumnoMainView();
                            viewAlumno.setVisible(true);
                            break;
                            
                        default:
                            JOptionPane.showMessageDialog(view, "Error: Rol [" + rol + "] no configurado en el sistema.");
                            break;
                    }
                    
                } else {
                    throw new InvalidUser("Credenciales incorrectas. Verifica tu correo o contraseña.");
                }

            } catch (InvalidUser ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error de Inicio de Sesión", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Ocurrió un error inesperado: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        view.getLblRegister().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getWindow().dispose();
                FormularioRegistro vistaRegistro = new FormularioRegistro();
                new RegistroController(vistaRegistro);
                vistaRegistro.setVisible(true);
            }
        });
    }
}
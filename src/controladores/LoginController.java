package controladores;

import javax.swing.JOptionPane;
import models.User;
import repositorio.UserRepository;
import views.ViewLogin;
import views.ProfesorMainView;
import views.AdminMainView;  
import views.AlumnoMainView; 
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
                            break;
                            
                        case "ADMINISTRATIVO":
                            AdminMainView viewAdmin = new AdminMainView();
                            new AdminController(viewAdmin); 
                            break;
                            
                        case "ALUMNO":
                            AlumnoMainView viewAlumno = new AlumnoMainView();
                            new AlumnoController(viewAlumno); 
                            break;
                            
                        default:
                            JOptionPane.showMessageDialog(view, "Error: Rol [" + rol + "] no configurado.");
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
        
    }
}
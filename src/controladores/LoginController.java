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

    // recibe la ventana de login y registra los eventos
    public LoginController(ViewLogin view) {
        this.view = view;
        this.repo = new UserRepository();
        this.initEvents(); // Conecta el botón con la lógica de login
    }

    private void initEvents() {

        // Cuando el usuario hace clic en "Iniciar sesión"
        view.getBtnLogin().addActionListener(e -> {

            // Lee lo que escribió el usuario en los campos
            String email = view.getTxtEmail().getText().trim();
            String pass  = new String(view.getTxtPass().getPassword());

            try {
                // Validación 1: ningún campo puede estar vacío
                if (email.isEmpty() || pass.isEmpty()) {
                    throw new InvalidUser("No puedes dejar el correo o la contraseña vacíos.");
                }

                // Validación 2: formato mínimo de correo (contiene @ y .)
                if (!email.contains("@") || !email.contains(".")) {
                    throw new InvalidUser("El formato del correo electrónico es inválido.");
                }

                // Consulta la BD: si email y contraseña coinciden, devuelve el User
                User usuarioValido = repo.login(email, pass);

                if (usuarioValido != null) {

                    Session.login(usuarioValido); // Guarda el usuario en sesión global
                    view.getWindow().dispose();   // Cierra la ventana de login

                    // Lee el rol del usuario recién autenticado
                    String rol = Session.getRole().toUpperCase();

                    // Abre la vista correspondiente según el rol
                    switch (rol) {

                        case "PROFESOR":
                            ProfesorMainView viewProfe = new ProfesorMainView();
                            new ProfesorController(viewProfe);
                            break;

                        case "ADMIN":
                        case "ADMINISTRATIVO": // Ambos roles abren la misma vista de admin
                            AdminMainView viewAdmin = new AdminMainView();
                            new AdminController(viewAdmin);
                            break;

                        case "ALUMNO":
                            AlumnoMainView viewAlumno = new AlumnoMainView();
                            new AlumnoController(viewAlumno);
                            break;

                        default:
                            // Si el rol existe en BD pero no está contemplado en el código
                            JOptionPane.showMessageDialog(view, "Error: Rol [" + rol + "] no configurado.");
                            break;
                    }

                } else {
                    // El repositorio devolvió null: email o contraseña incorrectos
                    throw new InvalidUser("Credenciales incorrectas. Verifica tu correo o contraseña.");
                }

            } catch (InvalidUser ex) {
                // Error esperado: campos vacíos, formato inválido o credenciales incorrectas
                JOptionPane.showMessageDialog(view, ex.getMessage(),
                    "Error de Inicio de Sesión", JOptionPane.WARNING_MESSAGE);

            } catch (Exception ex) {
                // Error inesperado: problema de BD, conexión inesperada
                JOptionPane.showMessageDialog(view, "Ocurrió un error inesperado: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}
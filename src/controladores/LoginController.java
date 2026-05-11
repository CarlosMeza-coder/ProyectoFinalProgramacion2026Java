package controladores;

import java.io.IOException;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import models.User;
import repositorio.UserRepository;
import views.ViewLogin;
import views.ProfesorMainView;
import views.FormularioRegistro;
import excepciones.InvalidUser;
import excepciones.InvalidPassword;

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

                if (validarAcceso(email, pass)) {
                    view.getWindow().dispose();
                    ProfesorMainView mainView = new ProfesorMainView();
                    new ProfesorController(mainView);
                    mainView.setVisible(true);
                } else {
                    throw new InvalidUser("Credenciales incorrectas. Verifica tu correo o contraseña.");
                }

            } catch (InvalidUser ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error de Inicio de Sesión", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Ocurrió un error inesperado: " + ex.getMessage());
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

    private boolean validarAcceso(String email, String pass) {
        try {
            List<User> usuarios = repo.getUsers();
            return usuarios.stream().anyMatch(u -> 
                u.getEmail().equals(email) && u.getPass().equals(pass)
            );
        } catch (IOException e) {
            return false;
        }
    }
}
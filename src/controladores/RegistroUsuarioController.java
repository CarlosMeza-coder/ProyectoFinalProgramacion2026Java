package controladores;

import java.io.IOException;
import javax.swing.JOptionPane;
import models.User;
import repositorio.UserRepository;
import views.ViewRegistroUsuario;
import views.ViewLogin;

public class RegistroUsuarioController {
    private ViewRegistroUsuario view;
    private UserRepository repo;

    public RegistroUsuarioController(ViewRegistroUsuario view) {
        this.view = view;
        this.repo = new UserRepository();
        
        this.view.getBotonRegistrar().addActionListener(e -> registrar());
        this.view.getBotonVolver().addActionListener(e -> volverAlLogin());
    }

    private void registrar() {
        String email = view.getEmail().trim();
        String pass = view.getPassword().trim();

        if(email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Llena todos los campos");
            return;
        }

        try {
            User nuevo = new User(email, pass);
            repo.save(nuevo);
            JOptionPane.showMessageDialog(null, "Cuenta creada. Ya puedes iniciar sesión.");
            volverAlLogin();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en CSV");
        }
    }

    private void volverAlLogin() {
        ViewLogin loginView = new ViewLogin(view.getWindow());
        new LoginController(loginView);
        view.getWindow().setContentPane(loginView);
        view.getWindow().revalidate();
    }
}

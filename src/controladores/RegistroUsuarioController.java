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
        String pais = view.getPais().trim();
        String lenguaje = view.getLenguaje().trim();
        String genero = view.getGenero().trim();

        if(email.isEmpty() || pass.isEmpty() || pais.isEmpty() || lenguaje.isEmpty() || genero.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡Error! Todos los campos son obligatorios.");
            return;
        }

        try {
            User nuevo = new User(email, pass, pais, lenguaje, genero);
            
            repo.save(nuevo); 
            
            JOptionPane.showMessageDialog(null, "Cuenta creada exitosamente para: " + email);
            volverAlLogin();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error fatal al escribir en el archivo CSV");
        }
    }

    private void volverAlLogin() {
        ViewLogin loginView = new ViewLogin(view.getWindow());
        new LoginController(loginView);
        view.getWindow().setContentPane(loginView);
        view.getWindow().revalidate();
    }
}

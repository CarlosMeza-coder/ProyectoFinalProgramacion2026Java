package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import excepciones.InvalidPassword;
import excepciones.InvalidUser;
import models.User;
import repositorio.UserRepository;
import views.FormularioRegistro;
import views.ViewLogin;
import views.ViewRegistroUsuario;
import views.MainWindow; // <--- IMPORTANTE: Importamos la ventana de la tabla

public class LoginController {

    private ViewLogin view;

    public LoginController(ViewLogin view) {
        this.view = view;
        registerListeners();
    }

    public void registerListeners() {
        view.getBotonIngresar().addActionListener(e -> login());
        
        view.getBotonMostrarTabla().addActionListener(e -> abrirTabla());

        view.getLblRegister().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ViewRegistroUsuario vistaRegistro = new ViewRegistroUsuario(view.getWindow());
                new RegistroUsuarioController(vistaRegistro);
                view.getWindow().setContentPane(vistaRegistro);
                view.getWindow().revalidate();
            }
        });
    }

    private void abrirTabla() {
        MainWindow principal = new MainWindow();
        principal.setVisible(true);
        view.getWindow().dispose(); 
    }

    private void login() {
        view.clearErrors();

        try {
            validarCredenciales();
            FormularioRegistro vistaFormulario = new FormularioRegistro();
            new RegistroController(vistaFormulario);
            view.getWindow().dispose();
        } catch (InvalidUser e) {
            view.setEmailError(e.getMessage());
        } catch (InvalidPassword e) {
            view.setPasswordError(e.getMessage());
        }
    }

    private void validarCredenciales() throws InvalidUser, InvalidPassword {
        String emailInput = view.getEmail().trim();
        String passwordInput = view.getPassword().trim();

        if (emailInput.isEmpty()) {
            throw new InvalidUser("El correo es obligatorio");
        }

        if (passwordInput.isEmpty()) {
            throw new InvalidPassword("La contraseña es obligatoria");
        }

        try {
            UserRepository repo = new UserRepository();
            List<User> usuarios = repo.getUsers();
            
            User usuarioEncontrado = null;

            for (User u : usuarios) {
                if (u.getEmail().equals(emailInput)) {
                    usuarioEncontrado = u;
                    break;
                }
            }

            if (usuarioEncontrado == null) {
                throw new InvalidUser("Usuario no encontrado");
            }

            if (!usuarioEncontrado.getPass().equals(passwordInput)) {
                throw new InvalidPassword("Contraseña incorrecta");
            }

        } catch (InvalidUser e) {
            throw e;
        } catch (InvalidPassword e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidUser("Error de conexion con el archivo de usuarios");
        }
    }
}
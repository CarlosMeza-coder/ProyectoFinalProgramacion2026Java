package controladores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import excepciones.InvalidPassword;
import excepciones.InvalidUser;
import views.FormularioRegistro;
import views.ViewLogin;

public class LoginController {

    private ViewLogin view;

    public LoginController(ViewLogin view) {
        this.view = view;
        registerListeners();
    }

    public void registerListeners() {
        view.getBotonIngresar().addActionListener(e -> login());

        view.getLblRegister().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FormularioRegistro vistaRegistro = new FormularioRegistro();
                new RegistroController(vistaRegistro);
                
                view.getWindow().dispose();
            }
        });
    }

    private void login() {
        view.clearErrors();

        try {
            validarCredenciales();
            
            FormularioRegistro vistaRegistro = new FormularioRegistro();
            new RegistroController(vistaRegistro);
            
            view.getWindow().dispose();
        } catch (InvalidUser e) {
            view.setEmailError(e.getMessage());
        } catch (InvalidPassword e) {
            view.setPasswordError(e.getMessage());
        }
    }

    private void validarCredenciales() throws InvalidUser, InvalidPassword {
        String email = view.getEmail();
        String password = view.getPassword();

        if (email.trim().isEmpty()) {
            throw new InvalidUser("El correo es obligatorio");
        }
        
        if (!email.trim().isEmpty() && !email.equals("madero@uabcs.com")) {
            throw new InvalidUser();
        }

        if (password.trim().isEmpty()) {
            throw new InvalidPassword("La contraseña es obligatoria");
        }

        if (!password.trim().isEmpty() && !password.equals("1234")) {
            throw new InvalidPassword();
        }
    }
}
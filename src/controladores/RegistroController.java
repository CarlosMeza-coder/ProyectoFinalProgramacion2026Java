package controladores;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import models.User;
import repositorio.UserRepository;
import views.FormularioRegistro;
import views.LoginWindow;
import excepciones.InvalidUser;
import excepciones.InvalidPassword;

public class RegistroController {

    private FormularioRegistro view;
    private UserRepository repo; 

    public RegistroController(FormularioRegistro view) {
        this.view = view;
        this.repo = new UserRepository(); 
        registerListeners();
    }

    private void registerListeners() {
        view.getBtnRegistrar().addActionListener(e -> registrarUsuario());
        view.getBtnRegresar().addActionListener(e -> confirmarSalida());
        
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarSalida();
            }
        });
    }

    private void confirmarSalida() {
        int option = JOptionPane.showConfirmDialog(
            view, 
            "¿Seguro que deseas regresar? Se cancelará el registro.", 
            "Confirmar salida", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            view.dispose();
            LoginWindow login = new LoginWindow();
            login.setVisible(true);
        }
    }

    private void registrarUsuario() {
        view.limpiarErrores();
        
        String email = view.getEmail().trim();
        String password = view.getPassword().trim();

        try {
            if (email.isEmpty()) {
                view.setErrorEmail("El correo es obligatorio");
                throw new InvalidUser("El campo de correo electrónico no puede estar vacío.");
            } 
            
            if (!email.contains("@") || !email.contains(".")) {
                view.setErrorEmail("Ingrese un correo válido");
                throw new InvalidUser("El formato del correo es incorrecto (falta @ o punto).");
            }

            if (password.isEmpty()) {
                view.setErrorPassword("La contraseña es obligatoria");
                throw new InvalidPassword("La contraseña no puede estar vacía.");
            }

            if (password.length() < 6) {
                view.setErrorPassword("Mínimo 6 caracteres");
                throw new InvalidPassword("Por seguridad, la contraseña debe tener al menos 6 caracteres.");
            }

            User nuevoUsuario = new User();
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setPass(password);
            
            repo.save(nuevoUsuario);
            
            JOptionPane.showMessageDialog(view, "¡Cuenta de profesor creada con éxito!");
            
            view.dispose();
            LoginWindow login = new LoginWindow();
            login.setVisible(true);

        } catch (InvalidUser | InvalidPassword ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error en el Registro", JOptionPane.WARNING_MESSAGE);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error al guardar en la base de datos: " + ex.getMessage(), "Error de SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}
package controladores;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import views.FormularioRegistro;
import views.LoginWindow;
import views.OpcionesAlumnos;

public class RegistroController {

    private FormularioRegistro view;

    public RegistroController(FormularioRegistro view) {
        this.view = view;
        registerListeners();
    }

    private void registerListeners() {
        view.getBtnValidate().addActionListener(e -> validarFormulario());
        
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
            "¿Seguro que deseas regresar? Se perderán todos los datos", 
            "Confirmar salida", 
            JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            new LoginWindow();
            view.dispose();
        }
    }

    private void validarFormulario() {
        view.limpiarErrores();
        boolean valid = true;

        if (view.getNombre().trim().isEmpty()) {
            view.setErrorNombre("El nombre es obligatorio");
            valid = false;
        }

        if (view.getPaterno().trim().isEmpty()) {
            view.setErrorPaterno("El apellido paterno es obligatorio");
            valid = false;
        }

        if (view.getMaterno().trim().isEmpty()) {
            view.setErrorMaterno("El apellido materno es obligatorio");
            valid = false;
        }

        if (view.getMatricula().trim().isEmpty()) {
            view.setErrorMatricula("La matrícula es obligatoria");
            valid = false;
        }

        String correo = view.getCorreo();
        if (correo.trim().isEmpty()) {
            view.setErrorCorreo("El correo es obligatorio");
            valid = false;
        } else if (!correo.contains("@")) {
            view.setErrorCorreo("Email inválido");
            valid = false;
        }

        if (view.getEdad().trim().isEmpty()) {
            view.setErrorEdad("La edad es obligatoria");
            valid = false;
        }

        if (!view.isMujerSelected() && !view.isHombreSelected()) {
            view.setErrorSexo("Seleccione un sexo");
            valid = false;
        }

        if (valid) {
            new OpcionesAlumnos();
            view.dispose();
        }
    }
}

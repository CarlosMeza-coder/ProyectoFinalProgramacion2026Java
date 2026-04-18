package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ViewRegistroUsuario extends JPanel {
    private LoginWindow window;
    private JTextField txtEmail;
    private JPasswordField contrasena;
    private JButton botonRegistrar;
    private JButton botonVolver;

    public ViewRegistroUsuario(LoginWindow window) {
        this.window = window;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Crear Nueva Cuenta");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 1, 0, 5));
        form.setBackground(Color.WHITE);

        form.add(new JLabel("Correo electrónico:"));
        txtEmail = new JTextField();
        form.add(txtEmail);

        form.add(new JLabel("Contraseña:"));
        contrasena = new JPasswordField();
        form.add(contrasena);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.setBackground(Color.WHITE);

        botonRegistrar = new JButton("Registrar");
        botonRegistrar.setBackground(Color.GREEN);
        
        botonVolver = new JButton("Volver");

        buttons.add(botonRegistrar);
        buttons.add(botonVolver);
        add(buttons, BorderLayout.SOUTH);
    }

    public String getEmail() { return txtEmail.getText(); }
    public String getPassword() { return new String(contrasena.getPassword()); }
    public JButton getBotonRegistrar() { return botonRegistrar; }
    public JButton getBotonVolver() { return botonVolver; }
    public LoginWindow getWindow() { return window; }
}
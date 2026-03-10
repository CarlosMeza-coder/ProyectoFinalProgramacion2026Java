package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewLogin extends JPanel {
    
    private LoginWindow window;
    JTextField txtEmail;
    JPasswordField contrasena;
    JLabel lblEmailRequerido;
    JLabel lblContrasenaRequerida;

    public ViewLogin(LoginWindow window) {
        this.window = window;

        BorderPanel container = new BorderPanel(0, 20);
        container.setBackground(Color.WHITE);
        container.setBorder(new EmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout()); 
        add(container);

        JLabel labelTitulo = new JLabel("Inicio de sesion");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 25));
        container.addItem(labelTitulo, BorderLayout.NORTH);

        GridPanel form = new GridPanel(8, 1, 0, 5);
        form.setBackground(Color.WHITE);

        JLabel labelCorreo = new JLabel("Ingrese su correo electrónico");
        labelCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        form.addItem(labelCorreo);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 18));
        form.addItem(txtEmail);

        lblEmailRequerido = new JLabel();
        lblEmailRequerido.setForeground(Color.RED);
        lblEmailRequerido.setFont(new Font("Arial", Font.PLAIN, 12));
        form.addItem(lblEmailRequerido);

        JLabel labelPassword = new JLabel("Ingrese la contraseña");
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        form.addItem(labelPassword);

        contrasena = new JPasswordField();
        contrasena.setFont(new Font("Arial", Font.PLAIN, 18));
        form.addItem(contrasena);

        lblContrasenaRequerida = new JLabel();
        lblContrasenaRequerida.setForeground(Color.RED);
        lblContrasenaRequerida.setFont(new Font("Arial", Font.PLAIN, 12));
        form.addItem(lblContrasenaRequerida);

        container.addItem(form, BorderLayout.CENTER);

        FlowPanel buttonContainer = new FlowPanel(FlowLayout.LEFT);
        buttonContainer.setBackground(Color.WHITE);

        JButton boton = new JButton("Ingresar");
        boton.setBackground(Color.CYAN);
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setPreferredSize(new Dimension(120, 40));
        boton.setToolTipText("Clic para entrar");
        boton.addActionListener(e -> login());
        buttonContainer.addItem(boton);

        container.addItem(buttonContainer, BorderLayout.SOUTH);
    }
    
    private void login() {
        lblEmailRequerido.setText("");
        lblContrasenaRequerida.setText("");
        boolean valid = true;

        if (txtEmail.getText().trim().isEmpty()) {
            lblEmailRequerido.setText("El correo es obligatorio");
            valid = false;
        }

        if (new String(contrasena.getPassword()).trim().isEmpty()) {
            lblContrasenaRequerida.setText("La contraseña es obligatoria");
            valid = false;
        }

        if (valid) {
            new FormularioRegistro();
            this.window.dispose();
        }
    }
}
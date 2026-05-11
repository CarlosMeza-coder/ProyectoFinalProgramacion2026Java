package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class ViewLogin extends JPanel {
    
    private LoginWindow window;
    private JTextField txtEmail;
    private JPasswordField txtPass; 
    private JLabel lblEmailRequerido;
    private JLabel lblContrasenaRequerida;
    private JButton btnLogin; 
    private JButton botonMostrarTabla; 
    private JLabel lblRegister;
    
    Color defaultButtonColor;

    public ViewLogin(LoginWindow window) {
        this.window = window;

        BorderPanel container = new BorderPanel(0, 20);
        container.setBackground(Color.WHITE);
        container.setBorder(new EmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout()); 
        add(container);

        JLabel labelTitulo = new JLabel("Inicio de sesión");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 25));
        container.addItem(labelTitulo, BorderLayout.NORTH);

        GridPanel form = new GridPanel(9, 1, 0, 5);
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

        txtPass = new JPasswordField(); 
        txtPass.setFont(new Font("Arial", Font.PLAIN, 18));
        form.addItem(txtPass);

        lblContrasenaRequerida = new JLabel();
        lblContrasenaRequerida.setForeground(Color.RED);
        lblContrasenaRequerida.setFont(new Font("Arial", Font.PLAIN, 12));
        form.addItem(lblContrasenaRequerida);

        lblRegister = new JLabel("¿No tienes cuenta? Regístrate aquí");
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblRegister.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                lblRegister.setForeground(Color.GREEN);
            }
            public void mouseExited(MouseEvent e) {
                lblRegister.setForeground(Color.BLACK);
            }
        });
        
        form.addItem(lblRegister);

        container.addItem(form, BorderLayout.CENTER);

        FlowPanel buttonContainer = new FlowPanel(FlowLayout.LEFT);
        buttonContainer.setBackground(Color.WHITE);

        btnLogin = new JButton("Ingresar"); 
        btnLogin.setBackground(Color.CYAN);
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
        btnLogin.setPreferredSize(new Dimension(120, 40));
        
        defaultButtonColor = btnLogin.getBackground();
        
        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                changeBackground(btnLogin);
            }
            public void mouseExited(MouseEvent e) {
                resetBackground(btnLogin);
            }
        });

        botonMostrarTabla = new JButton("Mostrar tabla");
        botonMostrarTabla.setBackground(new Color(200, 200, 200)); 
        botonMostrarTabla.setFont(new Font("Arial", Font.BOLD, 14));
        botonMostrarTabla.setPreferredSize(new Dimension(130, 40));
        
        botonMostrarTabla.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                botonMostrarTabla.setBackground(Color.DARK_GRAY);
                botonMostrarTabla.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                botonMostrarTabla.setBackground(new Color(200, 200, 200));
                botonMostrarTabla.setForeground(Color.BLACK);
            }
        });
        
        buttonContainer.addItem(btnLogin);
        buttonContainer.addItem(botonMostrarTabla); 
        
        container.addItem(buttonContainer, BorderLayout.SOUTH);
    }
    
    private void changeBackground(JComponent c) {
        c.setBackground(Color.BLACK);
        c.setForeground(Color.WHITE);
    }

    private void resetBackground(JComponent c) {
        c.setBackground(defaultButtonColor);
        c.setForeground(Color.BLACK);
    }

    
    public JTextField getTxtEmail() { return txtEmail; }
    public JPasswordField getTxtPass() { return txtPass; }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnMostrarTabla() { return botonMostrarTabla; }
    public JLabel getLblRegister() { return lblRegister; }
    public LoginWindow getWindow() { return window; }

    public void setEmailError(String error) { lblEmailRequerido.setText(error); }
    public void setPasswordError(String error) { lblContrasenaRequerida.setText(error); }
    
    public void clearErrors() {
        lblEmailRequerido.setText("");
        lblContrasenaRequerida.setText("");
    }
}
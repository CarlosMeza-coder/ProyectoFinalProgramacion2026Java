package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import utils.AppStyles;

public class ViewLogin extends JPanel {
    
    private LoginWindow window;
    private JTextField txtEmail;
    private JPasswordField txtPass; 
    private JLabel lblEmailRequerido;
    private JLabel lblContrasenaRequerida;
    private JButton btnLogin; 
    
    private Color defaultButtonColor;

    public ViewLogin(LoginWindow window) {
        this.window = window;

        setBackground(AppStyles.BACKGROUND);
        setLayout(new BorderLayout()); 
        setBorder(new EmptyBorder(40, 60, 40, 60));

        JPanel mainCard = new JPanel(new GridLayout(1, 2));
        mainCard.setBackground(Color.WHITE);
        mainCard.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230), 1));
        add(mainCard, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(AppStyles.PRIMARY);
        leftPanel.setBorder(new EmptyBorder(40, 30, 40, 30));

        JLabel lblWelcomeTitle = new JLabel("<html><div style='text-align: center;'>Sistema<br>Escolar</div></html>", SwingConstants.CENTER);
        lblWelcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcomeTitle.setForeground(Color.WHITE);
        leftPanel.add(lblWelcomeTitle, BorderLayout.CENTER);

        JLabel lblWelcomeSub = new JLabel("Panel de Control Administrativo", SwingConstants.CENTER);
        lblWelcomeSub.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblWelcomeSub.setForeground(new Color(220, 235, 255));
        leftPanel.add(lblWelcomeSub, BorderLayout.SOUTH);

        mainCard.add(leftPanel);

        BorderPanel rightPanel = new BorderPanel(0, 15);
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel labelTitulo = new JLabel("Inicio de Sesión");
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitulo.setForeground(AppStyles.TEXT_DARK);
        rightPanel.addItem(labelTitulo, BorderLayout.NORTH);

        GridPanel form = new GridPanel(6, 1, 0, 5);
        form.setBackground(Color.WHITE);

        JLabel labelCorreo = new JLabel("Correo Institucional");
        labelCorreo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelCorreo.setForeground(new Color(110, 120, 135));
        form.addItem(labelCorreo);

        txtEmail = new JTextField();
        AppStyles.estilizarCampo(txtEmail);
        agregarEfectoFoco(txtEmail);
        form.addItem(txtEmail);

        lblEmailRequerido = new JLabel();
        lblEmailRequerido.setForeground(Color.RED);
        lblEmailRequerido.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        form.addItem(lblEmailRequerido);

        JLabel labelPassword = new JLabel("Contraseña");
        labelPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        labelPassword.setForeground(new Color(110, 120, 135));
        form.addItem(labelPassword);

        txtPass = new JPasswordField(); 
        AppStyles.estilizarCampo(txtPass);
        agregarEfectoFoco(txtPass);
        form.addItem(txtPass);

        lblContrasenaRequerida = new JLabel();
        lblContrasenaRequerida.setForeground(Color.RED);
        lblContrasenaRequerida.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        form.addItem(lblContrasenaRequerida);

        rightPanel.addItem(form, BorderLayout.CENTER);

        FlowPanel buttonContainer = new FlowPanel(FlowLayout.RIGHT);
        buttonContainer.setBackground(Color.WHITE);

        btnLogin = new JButton("Ingresar"); 
        AppStyles.estilizarBoton(btnLogin);
        btnLogin.setPreferredSize(new Dimension(140, 42));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        defaultButtonColor = btnLogin.getBackground();
        
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                changeBackground(btnLogin);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                resetBackground(btnLogin);
            }
        });

        buttonContainer.addItem(btnLogin);
        rightPanel.addItem(buttonContainer, BorderLayout.SOUTH);

        mainCard.add(rightPanel);
    }
    
    private void changeBackground(JComponent c) {
        c.setBackground(AppStyles.PRIMARY.darker());
        c.setForeground(Color.WHITE);
    }

    private void resetBackground(JComponent c) {
        c.setBackground(defaultButtonColor);
        c.setForeground(Color.WHITE);
    }

    private void agregarEfectoFoco(JTextField campo) {
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(AppStyles.PRIMARY, 1, true),
                    new EmptyBorder(6, 8, 6, 8)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(180, 195, 210), 1),
                    new EmptyBorder(6, 8, 6, 8)
                ));
            }
        });
    }

    public JTextField getTxtEmail() { return txtEmail; }
    public JPasswordField getTxtPass() { return txtPass; }
    public JButton getBtnLogin() { return btnLogin; }
    public LoginWindow getWindow() { return window; }

    public void setEmailError(String error) { lblEmailRequerido.setText(error); }
    public void setPasswordError(String error) { lblContrasenaRequerida.setText(error); }
    
    public void clearErrors() {
        lblEmailRequerido.setText("");
        lblContrasenaRequerida.setText("");
    }
}
package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton; 
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField; // Importante para ocultar la contraseña
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import componets.ErrorLabel; 

public class FormularioRegistro extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;

    private ErrorLabel errorEmail;
    private ErrorLabel errorPassword;
    
    private JButton btnRegistrar;
    private JButton btnRegresar;

    public FormularioRegistro() {
        setSize(350, 250); 
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setTitle("Registro de Nueva Cuenta");
        setLocationRelativeTo(null);
        
        inicializarComponentesDeRegistro();
        
        setVisible(true);
    }

    public void inicializarComponentesDeRegistro() {
        JLabel etiquetaTituloRegistro = new JLabel("Registrar Nuevo Profesor");
        etiquetaTituloRegistro.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaTituloRegistro.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(etiquetaTituloRegistro, BorderLayout.NORTH);

        JPanel panelFormularioRegistro = new JPanel();
        panelFormularioRegistro.setLayout(new BoxLayout(panelFormularioRegistro, BoxLayout.Y_AXIS));
        panelFormularioRegistro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        txtEmail = new JTextField(15);
        aplicarFocus(txtEmail);
        errorEmail = new ErrorLabel();
        
        txtPassword = new JPasswordField(15);
        aplicarFocus(txtPassword);
        errorPassword = new ErrorLabel();

        panelFormularioRegistro.add(createField("Correo Electrónico:", txtEmail, errorEmail));
        panelFormularioRegistro.add(createField("Contraseña:", txtPassword, errorPassword));

        JScrollPane panelDeslizableRegistro = new JScrollPane(panelFormularioRegistro);
        panelDeslizableRegistro.setBorder(null); 
        add(panelDeslizableRegistro, BorderLayout.CENTER);

        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private void aplicarFocus(JComponent campo) {
        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                campo.setBackground(Color.LIGHT_GRAY);
            }
            public void focusLost(FocusEvent e) {
                campo.setBackground(Color.WHITE);
            }
        });
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        
        btnRegistrar = new JButton("Registrar");
        panel.add(btnRegistrar);
        
        btnRegresar = new JButton("Regresar al Login");
        panel.add(btnRegresar);
        
        return panel;
    }

    private JPanel createField(String labelText, Component field, JLabel errorLabel) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        ((JComponent) field).setAlignmentX(Component.LEFT_ALIGNMENT);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(label);
        panel.add(field);
        panel.add(errorLabel);
        
        return panel;
    }

    public String getEmail() { return txtEmail.getText(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    
    public JButton getBtnRegistrar() { return btnRegistrar; }
    public JButton getBtnRegresar() { return btnRegresar; }

    public void setErrorEmail(String text) { errorEmail.setText(text); }
    public void setErrorPassword(String text) { errorPassword.setText(text); }

    public void limpiarErrores() {
        errorEmail.setText("");
        errorPassword.setText("");
    }
}
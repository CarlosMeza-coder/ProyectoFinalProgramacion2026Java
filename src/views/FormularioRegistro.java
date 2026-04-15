package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton; 
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import componets.ErrorLabel; 

public class FormularioRegistro extends JFrame {

    private JTextField campoTextoNombreAlumno;
    private JTextField campoTextoApellidoPaterno;
    private JTextField campoTextoApellidoMaterno;
    private JTextField campoTextoMatriculaAlumno;
    private JTextField campoTextoCorreoAlumno;
    private JTextField campoTextoEdadAlumno;
    
    private JRadioButton botonRadioMujer;
    private JRadioButton botonRadioHombre;

    private ErrorLabel errorNombre;
    private ErrorLabel errorPaterno;
    private ErrorLabel errorMaterno;
    private ErrorLabel errorMatricula;
    private ErrorLabel errorCorreo;
    private ErrorLabel errorEdad;
    private ErrorLabel errorSexo;
    
    private JButton btnValidate;
    private JButton btnRegresar;

    public FormularioRegistro() {
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setTitle("Registro de Alumno");
        setLocationRelativeTo(null);
        
        inicializarComponentesDeRegistro();
        
        setVisible(true);
    }

    public void inicializarComponentesDeRegistro() {
        JLabel etiquetaTituloRegistro = new JLabel("Registro nuevo alumno");
        etiquetaTituloRegistro.setHorizontalAlignment(SwingConstants.CENTER);
        add(etiquetaTituloRegistro, BorderLayout.NORTH);

        JPanel panelFormularioRegistro = new JPanel();
        panelFormularioRegistro.setLayout(new BoxLayout(panelFormularioRegistro, BoxLayout.Y_AXIS));
        panelFormularioRegistro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoTextoNombreAlumno = new JTextField(10);
        aplicarFocus(campoTextoNombreAlumno);
        errorNombre = new ErrorLabel();
        
        campoTextoApellidoPaterno = new JTextField(10);
        aplicarFocus(campoTextoApellidoPaterno);
        errorPaterno = new ErrorLabel();
        
        campoTextoApellidoMaterno = new JTextField(10);
        aplicarFocus(campoTextoApellidoMaterno);
        errorMaterno = new ErrorLabel();
        
        campoTextoMatriculaAlumno = new JTextField(10);
        aplicarFocus(campoTextoMatriculaAlumno);
        errorMatricula = new ErrorLabel();
        
        campoTextoCorreoAlumno = new JTextField(10);
        aplicarFocus(campoTextoCorreoAlumno);
        errorCorreo = new ErrorLabel();
        
        campoTextoEdadAlumno = new JTextField(20);
        aplicarFocus(campoTextoEdadAlumno);
        campoTextoEdadAlumno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        errorEdad = new ErrorLabel();

        panelFormularioRegistro.add(createField("Nombre:", campoTextoNombreAlumno, errorNombre));
        panelFormularioRegistro.add(createField("Apellido Paterno:", campoTextoApellidoPaterno, errorPaterno));
        panelFormularioRegistro.add(createField("Apellido Materno:", campoTextoApellidoMaterno, errorMaterno));
        panelFormularioRegistro.add(createField("Matrícula:", campoTextoMatriculaAlumno, errorMatricula));
        panelFormularioRegistro.add(createField("Correo:", campoTextoCorreoAlumno, errorCorreo));
        panelFormularioRegistro.add(createField("Edad:", campoTextoEdadAlumno, errorEdad));

        JPanel panelSexo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botonRadioMujer = new JRadioButton("Mujer");
        botonRadioHombre = new JRadioButton("Hombre");
        ButtonGroup grupoSexoAlumno = new ButtonGroup();
        grupoSexoAlumno.add(botonRadioMujer);
        grupoSexoAlumno.add(botonRadioHombre);
        panelSexo.add(botonRadioMujer);
        panelSexo.add(botonRadioHombre);
        
        errorSexo = new ErrorLabel();
        panelFormularioRegistro.add(createField("Sexo del alumno:", panelSexo, errorSexo));

        JScrollPane panelDeslizableRegistro = new JScrollPane(panelFormularioRegistro);
        panelDeslizableRegistro.setHorizontalScrollBar(null);
        add(panelDeslizableRegistro, BorderLayout.CENTER);

        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private void aplicarFocus(JTextField campo) {
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
        
        btnValidate = new JButton("Validar");
        panel.add(btnValidate);
        
        btnRegresar = new JButton("Regresar");
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

    public String getNombre() { return campoTextoNombreAlumno.getText(); }
    public String getPaterno() { return campoTextoApellidoPaterno.getText(); }
    public String getMaterno() { return campoTextoApellidoMaterno.getText(); }
    public String getMatricula() { return campoTextoMatriculaAlumno.getText(); }
    public String getCorreo() { return campoTextoCorreoAlumno.getText(); }
    public String getEdad() { return campoTextoEdadAlumno.getText(); }
    public boolean isMujerSelected() { return botonRadioMujer.isSelected(); }
    public boolean isHombreSelected() { return botonRadioHombre.isSelected(); }

    public void setErrorNombre(String text) { errorNombre.setText(text); }
    public void setErrorPaterno(String text) { errorPaterno.setText(text); }
    public void setErrorMaterno(String text) { errorMaterno.setText(text); }
    public void setErrorMatricula(String text) { errorMatricula.setText(text); }
    public void setErrorCorreo(String text) { errorCorreo.setText(text); }
    public void setErrorEdad(String text) { errorEdad.setText(text); }
    public void setErrorSexo(String text) { errorSexo.setText(text); }

    public void limpiarErrores() {
        errorNombre.setText("");
        errorPaterno.setText("");
        errorMaterno.setText("");
        errorMatricula.setText("");
        errorCorreo.setText("");
        errorEdad.setText("");
        errorSexo.setText("");
    }

    public JButton getBtnValidate() { return btnValidate; }
    public JButton getBtnRegresar() { return btnRegresar; }
}
package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton; 
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane; 
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

    public FormularioRegistro() {
        setSize(350, 450);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);
        setTitle("Registro de Alumno");
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(FormularioRegistro.this, "¿Seguro que deseas regresar? Se perderán todos los datos", "Confirmar salida", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    new LoginWindow();
                    dispose();
                }
            }
        });
        
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
        
        JButton btnValidate = new JButton("Validar");
        btnValidate.addActionListener(e -> validarFormulario());
        panel.add(btnValidate);
        
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas regresar? Se perderán todos los datos", "Confirmar salida", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                new LoginWindow();
                dispose();
            }
        });
        panel.add(btnRegresar);
        
        return panel;
    }

    private void validarFormulario() {
        limpiarErrores();
        
        boolean valid = true;

        if (!validarNombre()) {
            valid = false;
        }
        
        if (!validarPaterno()) {
            valid = false;
        }

        if (!validarMaterno()) {
            valid = false;
        }

        if (!validarMatricula()) {
            valid = false;
        }

        if (!validarCorreo()) {
            valid = false;
        }

        if (!validarEdad()) {
            valid = false;
        }

        if (!validarSexo()) {
            valid = false;
        }

        if (valid) {
            new OpcionesAlumnos();
            dispose();
        }
    }

    private void limpiarErrores() {
        errorNombre.setText("");
        errorPaterno.setText("");
        errorMaterno.setText("");
        errorMatricula.setText("");
        errorCorreo.setText("");
        errorEdad.setText("");
        errorSexo.setText("");
    }

    private boolean validarNombre() {
        if (campoTextoNombreAlumno.getText().trim().isEmpty()) {
            errorNombre.setText("El nombre es obligatorio");
            return false;
        }
        return true;
    }

    private boolean validarPaterno() {
        if (campoTextoApellidoPaterno.getText().trim().isEmpty()) {
            errorPaterno.setText("El apellido paterno es obligatorio");
            return false;
        }
        return true;
    }

    private boolean validarMaterno() {
        if (campoTextoApellidoMaterno.getText().trim().isEmpty()) {
            errorMaterno.setText("El apellido materno es obligatorio");
            return false;
        }
        return true;
    }

    private boolean validarMatricula() {
        if (campoTextoMatriculaAlumno.getText().trim().isEmpty()) {
            errorMatricula.setText("La matrícula es obligatoria");
            return false;
        }
        return true;
    }

    private boolean validarCorreo() {
        if (campoTextoCorreoAlumno.getText().trim().isEmpty()) {
            errorCorreo.setText("El correo es obligatorio");
            return false;
        }
        if (!campoTextoCorreoAlumno.getText().contains("@")) {
            errorCorreo.setText("Email inválido");
            return false;
        }
        return true;
    }

    private boolean validarEdad() {
        if (campoTextoEdadAlumno.getText().trim().isEmpty()) {
            errorEdad.setText("La edad es obligatoria");
            return false;
        }
        return true;
    }

    private boolean validarSexo() {
        if (!botonRadioMujer.isSelected() && !botonRadioHombre.isSelected()) {
            errorSexo.setText("Seleccione un sexo");
            return false;
        }
        return true;
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
}
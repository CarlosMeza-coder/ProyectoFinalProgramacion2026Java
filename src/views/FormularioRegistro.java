package views;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioRegistro extends JFrame {

	public FormularioRegistro() {
	    setSize(350, 450);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setResizable(true);
	    setTitle("Registro de Alumno");
	    
	    Toolkit herramientasGraficas = Toolkit.getDefaultToolkit();
	    Image iconoDelSistema = herramientasGraficas.getImage("src/img/OIP.jpg");
	    setIconImage(iconoDelSistema);
	    
	    inicializarComponentesDeRegistro();
	    
	    setVisible(true);		
	}

    public void inicializarComponentesDeRegistro() {
        JLabel etiquetaTituloRegistro = new JLabel("Registro nuevo alumno");
        add(etiquetaTituloRegistro, BorderLayout.NORTH);
        etiquetaTituloRegistro.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelFormularioRegistro = new JPanel();
        panelFormularioRegistro.setLayout(new BoxLayout(panelFormularioRegistro, BoxLayout.Y_AXIS));
        panelFormularioRegistro.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        JLabel etiquetaNombreAlumno = new JLabel("Nombre:");
        panelFormularioRegistro.add(etiquetaNombreAlumno);
        JTextField campoTextoNombreAlumno = new JTextField(10);
        panelFormularioRegistro.add(campoTextoNombreAlumno);
    
        JLabel etiquetaApellidoPaterno = new JLabel("Apellido Paterno:");
        panelFormularioRegistro.add(etiquetaApellidoPaterno);
        JTextField campoTextoApellidoPaterno = new JTextField(10);
        panelFormularioRegistro.add(campoTextoApellidoPaterno);
        
        JLabel etiquetaApellidoMaterno = new JLabel("Apellido Materno:");
        panelFormularioRegistro.add(etiquetaApellidoMaterno);
        JTextField campoTextoApellidoMaterno = new JTextField(10);
        panelFormularioRegistro.add(campoTextoApellidoMaterno);
    
        JLabel etiquetaMatriculaAlumno = new JLabel("Matricula:");
        panelFormularioRegistro.add(etiquetaMatriculaAlumno);
        JTextField campoTextoMatriculaAlumno = new JTextField(10);
        panelFormularioRegistro.add(campoTextoMatriculaAlumno);
    
        JLabel etiquetaCorreoAlumno = new JLabel("Correo:");
        panelFormularioRegistro.add(etiquetaCorreoAlumno);
        JTextField campoTextoCorreoAlumno = new JTextField(10);
        panelFormularioRegistro.add(campoTextoCorreoAlumno);
        
        JLabel etiquetaEdadAlumno = new JLabel("Edad:");
        panelFormularioRegistro.add(etiquetaEdadAlumno);
        JTextField campoTextoEdadAlumno = new JTextField(20);
        panelFormularioRegistro.add(campoTextoEdadAlumno);

        JLabel etiquetaSexoAlumno = new JLabel("Sexo del alumno:");
        panelFormularioRegistro.add(etiquetaSexoAlumno);

        JRadioButton botonRadioMujer = new JRadioButton("Mujer");
        JRadioButton botonRadioHombre = new JRadioButton("Hombre");

        ButtonGroup grupoSexoAlumno = new ButtonGroup();
        grupoSexoAlumno.add(botonRadioMujer);
        grupoSexoAlumno.add(botonRadioHombre);

        panelFormularioRegistro.add(botonRadioMujer);
        panelFormularioRegistro.add(botonRadioHombre);

        JScrollPane panelDeslizableRegistro = new JScrollPane(panelFormularioRegistro);
        panelDeslizableRegistro.setHorizontalScrollBar(null);			
        add(panelDeslizableRegistro);
    }
}

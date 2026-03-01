package views;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;

import java.awt.Toolkit;

public class OpcionesAlumnos extends JFrame {

	public OpcionesAlumnos() {
	    setSize(300, 300);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	    setResizable(true);
	    setTitle("Opciones y Becas");

	    Toolkit herramientasGraficas = Toolkit.getDefaultToolkit();
	    Image iconoDelSistema = herramientasGraficas.getImage("src/img/OIP.jpg");
	    setIconImage(iconoDelSistema);
	    
	    inicializarComponentesOpciones();
	    
	    setVisible(true);
	}

    public void inicializarComponentesOpciones() {
        JLabel etiquetaTituloOpciones = new JLabel("Opciones Adicionales");
        add(etiquetaTituloOpciones, BorderLayout.NORTH);
        etiquetaTituloOpciones.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelOpcionesAlumno = new JPanel();
        panelOpcionesAlumno.setLayout(new BoxLayout(panelOpcionesAlumno, BoxLayout.Y_AXIS));
        panelOpcionesAlumno.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel etiquetaOpcionesBecas = new JLabel("Opciones de Beca y Documentacion:");
        panelOpcionesAlumno.add(etiquetaOpcionesBecas);

        JCheckBox casillaSolicitaBeca = new JCheckBox("Solicita Beca");
        JCheckBox casillaDocumentacionCompleta = new JCheckBox("Documentacion Completa");

        panelOpcionesAlumno.add(casillaSolicitaBeca);
        panelOpcionesAlumno.add(casillaDocumentacionCompleta);

        JLabel etiquetaMateriasAlumno = new JLabel("Materias a cursar:");
        panelOpcionesAlumno.add(etiquetaMateriasAlumno);

        String[] listaDeMateriasDisponibles = {"Metodos Numericos", "Estructuras de Datos", "Bases de Datos", "Programacion"};
        JList<String> listaDesplegableMaterias = new JList<>(listaDeMateriasDisponibles);

        JScrollPane panelDeslizableMaterias = new JScrollPane(listaDesplegableMaterias);
        panelDeslizableMaterias.setPreferredSize(new java.awt.Dimension(250, 120));
        panelDeslizableMaterias.setMaximumSize(new java.awt.Dimension(220, 120));

        panelOpcionesAlumno.add(panelDeslizableMaterias);

        add(panelOpcionesAlumno, BorderLayout.CENTER);
    }
}
package views;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import componets.ErrorLabel;

public class OpcionesAlumnos extends JFrame {

    private JCheckBox casillaSolicitaBeca;
    private JCheckBox casillaDocumentacionCompleta;
    private JList<String> listaDesplegableMaterias;
    private ErrorLabel errorMaterias;
    private ErrorLabel errorDocumentacion;

    public OpcionesAlumnos() {
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setResizable(true);
        setTitle("Opciones y Becas");
        setLocationRelativeTo(null);

        Toolkit herramientasGraficas = Toolkit.getDefaultToolkit();
        Image iconoDelSistema = herramientasGraficas.getImage("src/img/OIP.jpg");
        setIconImage(iconoDelSistema);
        
        inicializarComponentesOpciones();
        add(crearPanelBotones(), BorderLayout.SOUTH);
        
        setVisible(true);
    }

    public void inicializarComponentesOpciones() {
        JLabel etiquetaTituloOpciones = new JLabel("Opciones Adicionales");
        add(etiquetaTituloOpciones, BorderLayout.NORTH);
        etiquetaTituloOpciones.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelOpcionesAlumno = new JPanel();
        panelOpcionesAlumno.setLayout(new BoxLayout(panelOpcionesAlumno, BoxLayout.Y_AXIS));
        panelOpcionesAlumno.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel etiquetaOpcionesBecas = new JLabel("Opciones de Beca y Documentacion:");
        panelOpcionesAlumno.add(etiquetaOpcionesBecas);

        casillaSolicitaBeca = new JCheckBox("Solicita Beca");
        casillaDocumentacionCompleta = new JCheckBox("Documentacion Completa");
        
        errorDocumentacion = new ErrorLabel();

        panelOpcionesAlumno.add(casillaSolicitaBeca);
        panelOpcionesAlumno.add(casillaDocumentacionCompleta);
        panelOpcionesAlumno.add(errorDocumentacion);

        JLabel etiquetaMateriasAlumno = new JLabel("Materias a cursar:");
        panelOpcionesAlumno.add(etiquetaMateriasAlumno);

        String[] listaDeMateriasDisponibles = {"Metodos Numericos", "Estructuras de Datos", "Bases de Datos", "Programacion"};
        listaDesplegableMaterias = new JList<>(listaDeMateriasDisponibles);

        JScrollPane panelDeslizableMaterias = new JScrollPane(listaDesplegableMaterias);
        panelDeslizableMaterias.setPreferredSize(new java.awt.Dimension(250, 120));
        panelDeslizableMaterias.setMaximumSize(new java.awt.Dimension(220, 120));

        panelOpcionesAlumno.add(panelDeslizableMaterias);
        
        errorMaterias = new ErrorLabel();
        panelOpcionesAlumno.add(errorMaterias);

        add(panelOpcionesAlumno, BorderLayout.CENTER);
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel();
        
        JButton btnValidar = new JButton("Validar");
        btnValidar.addActionListener(e -> validarOpciones());
        
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas regresar? Se perderan las opciones");
            if (option == JOptionPane.YES_OPTION) {
                new FormularioRegistro();
                dispose();
            }
        });
        
        panel.add(btnValidar);
        panel.add(btnRegresar);
        
        return panel;
    }
    
    private void validarOpciones() {
        boolean valido = true;
        errorMaterias.setText("");
        errorDocumentacion.setText("");
        
        if (listaDesplegableMaterias.getSelectedValuesList().isEmpty()) {
            errorMaterias.setText("Seleccione al menos una materia");
            valido = false;
        }
        
        if (!casillaDocumentacionCompleta.isSelected()) {
            errorDocumentacion.setText("Debe confirmar que entrego la documentacion");
            valido = false;
        }
        
        if (valido) {
            JOptionPane.showMessageDialog(this, "Registrado  con exito");         
            new LoginWindow();
            dispose();
        }
    }
}
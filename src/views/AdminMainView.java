package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminMainView extends JFrame {
    
    private JTabbedPane tabbedPane;
    
    private JTextField txtMatricula, txtNombreAlum, txtEmailAlum, txtGrupo;
    private JComboBox<String> cbSemestre;
    private JButton btnGuardarAlum, btnEditarAlum, btnEliminarAlum; 
    private JTable tablaAlumnos;
    private DefaultTableModel modeloAlumnos;
    private boolean modoEdicionAlumno = false; 

    private JTextField txtNombreProf, txtApellidoProf, txtEmailProf;
    private JPasswordField txtPassProf; 
    private JButton btnGuardarProf, btnEditarProf, btnEliminarProf;
    private JTable tablaProfesores;
    private DefaultTableModel modeloProfesores;
    private int idProfesorEdicion = -1; 

    private JComboBox<String> cbAlumnosInsc, cbMateriasInsc, cbProfesoresInsc;
    private JButton btnInscribir;

    public AdminMainView() {
        setTitle("Panel Administrativo - Sistema Escolar");
        setSize(950, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Gestión de Alumnos", crearPanelAlumnos());
        tabbedPane.addTab("Gestión de Profesores", crearPanelProfesores());
        tabbedPane.addTab("Asignar Materias", crearPanelInscripciones());
        
        add(tabbedPane);
    }

    private JPanel crearPanelAlumnos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Alumno"));
        
        formPanel.add(new JLabel("Matrícula:"));
        txtMatricula = new JTextField();
        formPanel.add(txtMatricula);
        
        formPanel.add(new JLabel("Nombre:"));
        txtNombreAlum = new JTextField();
        formPanel.add(txtNombreAlum);
        
        formPanel.add(new JLabel("Correo Institucional:"));
        txtEmailAlum = new JTextField();
        formPanel.add(txtEmailAlum);
        
        formPanel.add(new JLabel("Semestre:"));
        cbSemestre = new JComboBox<>(new String[]{"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto", "Séptimo", "Octavo"});
        formPanel.add(cbSemestre);
        
        formPanel.add(new JLabel("Grupo (Ej: A, B):"));
        txtGrupo = new JTextField();
        formPanel.add(txtGrupo);
        
        btnGuardarAlum = new JButton("Guardar Alumno");
        formPanel.add(new JLabel("")); 
        formPanel.add(btnGuardarAlum);

        btnEditarAlum = new JButton("Editar Seleccionado");
        btnEliminarAlum = new JButton("Eliminar Seleccionado");
        formPanel.add(btnEditarAlum);
        formPanel.add(btnEliminarAlum);

        String[] columnas = {"Matrícula", "Nombre", "Email", "Semestre", "Grupo"};
        modeloAlumnos = new DefaultTableModel(columnas, 0);
        tablaAlumnos = new JTable(modeloAlumnos);
        JScrollPane scrollPane = new JScrollPane(tablaAlumnos);
        
        panel.add(formPanel, BorderLayout.WEST);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelProfesores() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 15)); 
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Profesor"));
        
        formPanel.add(new JLabel("Nombre:"));
        txtNombreProf = new JTextField();
        formPanel.add(txtNombreProf);
        
        formPanel.add(new JLabel("Apellido:"));
        txtApellidoProf = new JTextField();
        formPanel.add(txtApellidoProf);
        
        formPanel.add(new JLabel("Correo Institucional:"));
        txtEmailProf = new JTextField();
        formPanel.add(txtEmailProf);
        
        formPanel.add(new JLabel("Contraseña Inicial:"));
        txtPassProf = new JPasswordField();
        formPanel.add(txtPassProf);
        
        btnGuardarProf = new JButton("Guardar Profesor");
        formPanel.add(new JLabel("")); 
        formPanel.add(btnGuardarProf);

        btnEditarProf = new JButton("Editar Seleccionado");
        btnEliminarProf = new JButton("Eliminar Seleccionado");
        formPanel.add(btnEditarProf);
        formPanel.add(btnEliminarProf);

        String[] columnas = {"ID", "Nombre", "Apellido", "Email"};
        modeloProfesores = new DefaultTableModel(columnas, 0);
        tablaProfesores = new JTable(modeloProfesores);
        JScrollPane scrollPane = new JScrollPane(tablaProfesores);
        
        panel.add(formPanel, BorderLayout.WEST);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelInscripciones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 20));
        formPanel.setBorder(BorderFactory.createTitledBorder("Inscribir Alumno a Materia"));
        formPanel.setPreferredSize(new Dimension(500, 250));

        formPanel.add(new JLabel("Seleccione Alumno:"));
        cbAlumnosInsc = new JComboBox<>();
        formPanel.add(cbAlumnosInsc);

        formPanel.add(new JLabel("Seleccione Materia:"));
        cbMateriasInsc = new JComboBox<>();
        formPanel.add(cbMateriasInsc);

        formPanel.add(new JLabel("Asignar Profesor:"));
        cbProfesoresInsc = new JComboBox<>();
        formPanel.add(cbProfesoresInsc);

        formPanel.add(new JLabel(""));
        btnInscribir = new JButton("Registrar Inscripción");
        btnInscribir.setBackground(new Color(45, 111, 164));
        btnInscribir.setForeground(Color.WHITE);
        formPanel.add(btnInscribir);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(formPanel, gbc);

        return panel;
    }

    public JTabbedPane getTabbedPane() { return tabbedPane; }
    
    public JTextField getTxtMatricula() { return txtMatricula; }
    public JTextField getTxtNombreAlum() { return txtNombreAlum; }
    public JTextField getTxtEmailAlum() { return txtEmailAlum; }
    public JTextField getTxtGrupo() { return txtGrupo; }
    public JComboBox<String> getCbSemestre() { return cbSemestre; }
    public JButton getBtnGuardarAlum() { return btnGuardarAlum; }
    public JButton getBtnEditarAlum() { return btnEditarAlum; } 
    public JButton getBtnEliminarAlum() { return btnEliminarAlum; }
    public JTable getTablaAlumnos() { return tablaAlumnos; }
    public DefaultTableModel getModeloAlumnos() { return modeloAlumnos; }
    public boolean isModoEdicionAlumno() { return modoEdicionAlumno; } 
    public void setModoEdicionAlumno(boolean modo) { this.modoEdicionAlumno = modo; } 

    public void limpiarCamposAlumno() {
        txtMatricula.setText("");
        txtNombreAlum.setText("");
        txtEmailAlum.setText("");
        txtGrupo.setText("");
        cbSemestre.setSelectedIndex(0);
        txtMatricula.setEnabled(true); 
        modoEdicionAlumno = false;
        btnGuardarAlum.setText("Guardar Alumno");
    }

    public JTextField getTxtNombreProf() { return txtNombreProf; }
    public JTextField getTxtApellidoProf() { return txtApellidoProf; }
    public JTextField getTxtEmailProf() { return txtEmailProf; }
    public JPasswordField getTxtPassProf() { return txtPassProf; } 
    public JButton getBtnGuardarProf() { return btnGuardarProf; }
    public JButton getBtnEditarProf() { return btnEditarProf; }
    public JButton getBtnEliminarProf() { return btnEliminarProf; }
    public JTable getTablaProfesores() { return tablaProfesores; }
    public DefaultTableModel getModeloProfesores() { return modeloProfesores; }
    public int getIdProfesorEdicion() { return idProfesorEdicion; }
    public void setIdProfesorEdicion(int id) { this.idProfesorEdicion = id; }
    
    public void limpiarCamposProfesor() {
        txtNombreProf.setText("");
        txtApellidoProf.setText("");
        txtEmailProf.setText("");
        txtPassProf.setText(""); 
        idProfesorEdicion = -1;
        btnGuardarProf.setText("Guardar Profesor");
    }

    public JComboBox<String> getCbAlumnosInsc() { return cbAlumnosInsc; }
    public JComboBox<String> getCbMateriasInsc() { return cbMateriasInsc; }
    public JComboBox<String> getCbProfesoresInsc() { return cbProfesoresInsc; }
    public JButton getBtnInscribir() { return btnInscribir; }
}
package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import utils.AppStyles;

public class AdminMainView extends JFrame {
    
    private JTabbedPane tabbedPane;
    
    private JTextField txtMatricula, txtNombreAlum, txtApellidoAlum, txtEmailAlum, txtGrupo;
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
        setSize(1150, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(AppStyles.BACKGROUND);
        
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(AppStyles.BACKGROUND);
        mainContent.setBorder(new EmptyBorder(10, 20, 20, 20));

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(AppStyles.BACKGROUND);
        
        tabbedPane.addTab("Gestión de Alumnos", crearPanelAlumnos());
        tabbedPane.addTab("Gestión de Profesores", crearPanelProfesores());
        tabbedPane.addTab("Asignar Materias", crearPanelInscripciones());
        
        mainContent.add(tabbedPane, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel crearPanelAlumnos() {
        JPanel panel = new JPanel(new BorderLayout(25, 0));
        panel.setBackground(AppStyles.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JPanel formCard = new JPanel(new BorderLayout(0, 20));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 235, 240), 1, true),
            new EmptyBorder(25, 25, 25, 25)
        ));
        formCard.setPreferredSize(new Dimension(380, 0));

        JLabel lblTituloForm = new JLabel("Registro de Alumnos");
        lblTituloForm.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloForm.setForeground(AppStyles.TEXT_DARK);
        formCard.add(lblTituloForm, BorderLayout.NORTH);

        JPanel formCampos = new JPanel(new GridLayout(6, 1, 0, 15));
        formCampos.setBackground(Color.WHITE);
        
        txtMatricula = new JTextField(); AppStyles.estilizarCampo(txtMatricula); agregarEfectoFoco(txtMatricula);
        formCampos.add(crearBloqueCampo("Matrícula", txtMatricula));
        
        JPanel rowNombreApe = new JPanel(new GridLayout(1, 2, 15, 0));
        rowNombreApe.setBackground(Color.WHITE);
        txtNombreAlum = new JTextField(); AppStyles.estilizarCampo(txtNombreAlum); agregarEfectoFoco(txtNombreAlum);
        txtApellidoAlum = new JTextField(); AppStyles.estilizarCampo(txtApellidoAlum); agregarEfectoFoco(txtApellidoAlum);
        rowNombreApe.add(crearBloqueCampo("Nombre", txtNombreAlum));
        rowNombreApe.add(crearBloqueCampo("Apellido", txtApellidoAlum));
        formCampos.add(rowNombreApe);

        txtEmailAlum = new JTextField(); AppStyles.estilizarCampo(txtEmailAlum); agregarEfectoFoco(txtEmailAlum);
        formCampos.add(crearBloqueCampo("Email Institucional", txtEmailAlum));
        
        JPanel rowSemGru = new JPanel(new GridLayout(1, 2, 15, 0));
        rowSemGru.setBackground(Color.WHITE);
        cbSemestre = new JComboBox<>(new String[]{"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto", "Séptimo", "Octavo"});
        cbSemestre.setFont(AppStyles.FUENTE_TEXTO); cbSemestre.setBackground(Color.WHITE);
        txtGrupo = new JTextField(); AppStyles.estilizarCampo(txtGrupo); agregarEfectoFoco(txtGrupo);
        rowSemGru.add(crearBloqueCampo("Semestre", cbSemestre));
        rowSemGru.add(crearBloqueCampo("Grupo", txtGrupo));
        formCampos.add(rowSemGru);

        formCard.add(formCampos, BorderLayout.CENTER);

        JPanel actionContainer = new JPanel(new BorderLayout(0, 15));
        actionContainer.setBackground(Color.WHITE);

        btnGuardarAlum = new JButton("Guardar Alumno"); AppStyles.estilizarBoton(btnGuardarAlum);
        btnGuardarAlum.setPreferredSize(new Dimension(0, 45));
        actionContainer.add(btnGuardarAlum, BorderLayout.NORTH);

        JPanel gridBotonesSecundarios = new JPanel(new GridLayout(1, 2, 15, 0));
        gridBotonesSecundarios.setBackground(Color.WHITE);
        btnEditarAlum = new JButton("Editar"); estilizarBotonSecundario(btnEditarAlum);
        btnEliminarAlum = new JButton("Eliminar"); estilizarBotonSecundario(btnEliminarAlum);
        gridBotonesSecundarios.add(btnEditarAlum);
        gridBotonesSecundarios.add(btnEliminarAlum);
        actionContainer.add(gridBotonesSecundarios, BorderLayout.SOUTH);

        formCard.add(actionContainer, BorderLayout.SOUTH);

        JPanel tableCard = new JPanel(new BorderLayout(0, 15));
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 235, 240), 1, true),
            new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel lblTituloTabla = new JLabel("Lista de Alumnos");
        lblTituloTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloTabla.setForeground(AppStyles.TEXT_DARK);
        tableCard.add(lblTituloTabla, BorderLayout.NORTH);

        String[] columnas = {"Matrícula", "Nombre", "Apellido", "Email", "Sem", "Grupo"};
        modeloAlumnos = new DefaultTableModel(columnas, 0);
        tablaAlumnos = new JTable(modeloAlumnos);
        AppStyles.estilizarTabla(tablaAlumnos);
        JScrollPane scrollPane = new JScrollPane(tablaAlumnos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableCard.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(formCard, BorderLayout.WEST);
        panel.add(tableCard, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelProfesores() {
        JPanel panel = new JPanel(new BorderLayout(25, 0));
        panel.setBackground(AppStyles.BACKGROUND);
        panel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JPanel formCard = new JPanel(new BorderLayout(0, 20));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 235, 240), 1, true),
            new EmptyBorder(25, 25, 25, 25)
        ));
        formCard.setPreferredSize(new Dimension(380, 0));

        JLabel lblTituloForm = new JLabel("Registro de Profesores");
        lblTituloForm.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloForm.setForeground(AppStyles.TEXT_DARK);
        formCard.add(lblTituloForm, BorderLayout.NORTH);

        JPanel formCampos = new JPanel(new GridLayout(4, 1, 0, 15));
        formCampos.setBackground(Color.WHITE);
        
        JPanel rowNombreApe = new JPanel(new GridLayout(1, 2, 15, 0));
        rowNombreApe.setBackground(Color.WHITE);
        txtNombreProf = new JTextField(); AppStyles.estilizarCampo(txtNombreProf); agregarEfectoFoco(txtNombreProf);
        txtApellidoProf = new JTextField(); AppStyles.estilizarCampo(txtApellidoProf); agregarEfectoFoco(txtApellidoProf);
        rowNombreApe.add(crearBloqueCampo("Nombre", txtNombreProf));
        rowNombreApe.add(crearBloqueCampo("Apellido", txtApellidoProf));
        formCampos.add(rowNombreApe);

        txtEmailProf = new JTextField(); AppStyles.estilizarCampo(txtEmailProf); agregarEfectoFoco(txtEmailProf);
        formCampos.add(crearBloqueCampo("Correo Institucional", txtEmailProf));
        
        txtPassProf = new JPasswordField(); AppStyles.estilizarCampo(txtPassProf); agregarEfectoFoco(txtPassProf);
        formCampos.add(crearBloqueCampo("Contraseña Inicial", txtPassProf));

        formCard.add(formCampos, BorderLayout.CENTER);

        JPanel actionContainer = new JPanel(new BorderLayout(0, 15));
        actionContainer.setBackground(Color.WHITE);

        btnGuardarProf = new JButton("Guardar Profesor"); AppStyles.estilizarBoton(btnGuardarProf);
        btnGuardarProf.setPreferredSize(new Dimension(0, 45));
        actionContainer.add(btnGuardarProf, BorderLayout.NORTH);

        JPanel gridBotonesSecundarios = new JPanel(new GridLayout(1, 2, 15, 0));
        gridBotonesSecundarios.setBackground(Color.WHITE);
        btnEditarProf = new JButton("Editar"); estilizarBotonSecundario(btnEditarProf);
        btnEliminarProf = new JButton("Eliminar"); estilizarBotonSecundario(btnEliminarProf);
        gridBotonesSecundarios.add(btnEditarProf);
        gridBotonesSecundarios.add(btnEliminarProf);
        actionContainer.add(gridBotonesSecundarios, BorderLayout.SOUTH);

        formCard.add(actionContainer, BorderLayout.SOUTH);

        JPanel tableCard = new JPanel(new BorderLayout(0, 15));
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 235, 240), 1, true),
            new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel lblTituloTabla = new JLabel("Lista de Profesores");
        lblTituloTabla.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloTabla.setForeground(AppStyles.TEXT_DARK);
        tableCard.add(lblTituloTabla, BorderLayout.NORTH);

        String[] columnas = {"ID", "Nombre", "Apellido", "Email"};
        modeloProfesores = new DefaultTableModel(columnas, 0);
        tablaProfesores = new JTable(modeloProfesores);
        AppStyles.estilizarTabla(tablaProfesores);
        JScrollPane scrollPane = new JScrollPane(tablaProfesores);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableCard.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(formCard, BorderLayout.WEST);
        panel.add(tableCard, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel crearPanelInscripciones() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(AppStyles.BACKGROUND);
        
        JPanel formCard = new JPanel(new BorderLayout(0, 25));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 235, 240), 1, true),
            new EmptyBorder(35, 40, 35, 40)
        ));
        formCard.setPreferredSize(new Dimension(600, 380));

        JLabel lblTitulo = new JLabel("Inscribir Alumno a Materia", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(AppStyles.TEXT_DARK);
        formCard.add(lblTitulo, BorderLayout.NORTH);

        JPanel formCampos = new JPanel(new GridLayout(3, 1, 0, 15));
        formCampos.setBackground(Color.WHITE);

        cbAlumnosInsc = new JComboBox<>(); cbAlumnosInsc.setFont(AppStyles.FUENTE_TEXTO); cbAlumnosInsc.setBackground(Color.WHITE);
        formCampos.add(crearBloqueCampo("Seleccione Alumno", cbAlumnosInsc));

        cbMateriasInsc = new JComboBox<>(); cbMateriasInsc.setFont(AppStyles.FUENTE_TEXTO); cbMateriasInsc.setBackground(Color.WHITE);
        formCampos.add(crearBloqueCampo("Seleccione Materia", cbMateriasInsc));

        cbProfesoresInsc = new JComboBox<>(); cbProfesoresInsc.setFont(AppStyles.FUENTE_TEXTO); cbProfesoresInsc.setBackground(Color.WHITE);
        formCampos.add(crearBloqueCampo("Asignar Profesor", cbProfesoresInsc));

        formCard.add(formCampos, BorderLayout.CENTER);

        btnInscribir = new JButton("Registrar Inscripción"); AppStyles.estilizarBoton(btnInscribir);
        btnInscribir.setPreferredSize(new Dimension(0, 45));
        formCard.add(btnInscribir, BorderLayout.SOUTH);

        panel.add(formCard);
        return panel;
    }

    private JPanel crearBloqueCampo(String titulo, JComponent componente) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 110, 120));
        p.add(lbl, BorderLayout.NORTH);
        p.add(componente, BorderLayout.CENTER);
        return p;
    }

    private void estilizarBotonSecundario(JButton boton) {
        boton.setFont(AppStyles.FUENTE_TEXTO);
        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.DARK_GRAY);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 210, 220), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void agregarEfectoFoco(JTextField campo) {
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(AppStyles.PRIMARY, 1, true),
                    new EmptyBorder(8, 10, 8, 10)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(210, 220, 230), 1),
                    new EmptyBorder(8, 10, 8, 10)
                ));
            }
        });
    }

    public JTabbedPane getTabbedPane() { return tabbedPane; }
    
    public JTextField getTxtMatricula() { return txtMatricula; }
    public JTextField getTxtNombreAlum() { return txtNombreAlum; }
    public JTextField getTxtApellidoAlum() { return txtApellidoAlum; }
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
        txtApellidoAlum.setText("");
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
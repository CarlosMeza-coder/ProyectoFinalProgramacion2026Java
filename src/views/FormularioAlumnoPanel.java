package views;

import java.awt.*;
import javax.swing.*;
import utils.AppFonts;

public class FormularioAlumnoPanel extends JPanel {
    private JTextField txtMatricula, txtNombre, txtEmail;
    private JComboBox<String> comboSemestre, comboGrupo;
    private JButton btnGuardar, btnCancelar;

    public FormularioAlumnoPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel title = new JLabel("Registro de Nuevo Alumno");
        title.setFont(AppFonts.bold());
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel formGrid = new JPanel(new GridLayout(5, 2, 10, 20));
        formGrid.setBackground(Color.WHITE);

        formGrid.add(new JLabel("Matrícula:"));
        txtMatricula = new JTextField();
        formGrid.add(txtMatricula);

        formGrid.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        formGrid.add(txtNombre);

        formGrid.add(new JLabel("Correo Electrónico:"));
        txtEmail = new JTextField();
        formGrid.add(txtEmail);

        formGrid.add(new JLabel("Semestre:"));
        comboSemestre = new JComboBox<>(new String[]{"1ro", "2do", "3ro", "4to", "5to", "6to", "7mo", "8vo"});
        formGrid.add(comboSemestre);

        formGrid.add(new JLabel("Grupo:"));
        comboGrupo = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        formGrid.add(comboGrupo);

        add(formGrid, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(Color.WHITE);
        
        btnCancelar = new JButton("Volver a la Lista");
        btnGuardar = new JButton("Guardar Alumno");
        btnGuardar.setBackground(new Color(45, 111, 164));
        btnGuardar.setForeground(Color.WHITE);

        actionPanel.add(btnCancelar);
        actionPanel.add(btnGuardar);
        add(actionPanel, BorderLayout.SOUTH);
    }

    public String getMatricula() { return txtMatricula.getText(); }
    public String getNombre() { return txtNombre.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getSemestre() { return comboSemestre.getSelectedItem().toString(); }
    public String getGrupo() { return comboGrupo.getSelectedItem().toString(); }
    
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnCancelar() { return btnCancelar; }

    public void setMatricula(String mat) { txtMatricula.setText(mat); }
    public void setNombre(String nom) { txtNombre.setText(nom); }
    public void setEmail(String correo) { txtEmail.setText(correo); }
    public void setSemestre(String sem) { comboSemestre.setSelectedItem(sem); }
    public void setGrupo(String gru) { comboGrupo.setSelectedItem(gru); }
}
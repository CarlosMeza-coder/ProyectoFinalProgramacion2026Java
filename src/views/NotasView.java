package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import utils.AppFonts;

public class NotasView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> comboGrupo, comboSemestre, comboMateria;
    private JButton btnCargar, btnGuardar;

    public NotasView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(new Color(245, 245, 245));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtros de Búsqueda"));

        filterPanel.add(new JLabel("Semestre:"));
        comboSemestre = new JComboBox<>(new String[]{"1ro", "2do", "3ro", "4to", "5to", "6to", "7mo", "8vo"});
        filterPanel.add(comboSemestre);

        filterPanel.add(new JLabel("Grupo:"));
        comboGrupo = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        filterPanel.add(comboGrupo);

        filterPanel.add(new JLabel("Materia:"));
        comboMateria = new JComboBox<>(new String[]{"Programación III", "Bases de Datos", "Redes"});
        filterPanel.add(comboMateria);

        btnCargar = new JButton("Cargar Alumnos");
        btnCargar.setBackground(new Color(44, 62, 80));
        btnCargar.setForeground(Color.WHITE);
        filterPanel.add(btnCargar);

        add(filterPanel, BorderLayout.NORTH);

        String[] columns = {"Matrícula", "Nombre del Alumno", "Parcial 1", "Parcial 2", "Parcial 3", "Final"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 2; 
            }
        };
        
        table = new JTable(tableModel);
        styleTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setFont(AppFonts.bold());
        btnGuardar.setBackground(new Color(39, 174, 96));
        btnGuardar.setForeground(Color.WHITE);
        bottomPanel.add(btnGuardar);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void styleTable() {
        table.setRowHeight(30);
        table.getTableHeader().setFont(AppFonts.bold());
        table.getTableHeader().setBackground(new Color(44, 62, 80));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(AppFonts.normal());
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 2; i < 6; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public JButton getBtnCargar() { return btnCargar; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JTable getTable() { return table; }
    
    public String getSemestre() { return comboSemestre.getSelectedItem().toString(); }
    public String getGrupo() { return comboGrupo.getSelectedItem().toString(); }
    public String getMateria() { return comboMateria.getSelectedItem().toString(); }
    public DefaultTableModel getTableModel() { return tableModel; }
}
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
        comboSemestre = new JComboBox<>();
        filterPanel.add(comboSemestre);

        filterPanel.add(new JLabel("Grupo:"));
        comboGrupo = new JComboBox<>();
        filterPanel.add(comboGrupo);

        filterPanel.add(new JLabel("Materia:"));
        comboMateria = new JComboBox<>();
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
                return column >= 2 && column <= 4; 
            }
        };

        tableModel.addTableModelListener(e -> {
            int col = e.getColumn();
            int row = e.getFirstRow();

            if (col < 2 || col > 4 || row < 0) return;

            try {
                Object v1 = tableModel.getValueAt(row, 2);
                Object v2 = tableModel.getValueAt(row, 3);
                Object v3 = tableModel.getValueAt(row, 4);

                double suma = 0.0;
                int parcialesEvaluados = 0;

                if (v1 != null && !v1.toString().trim().isEmpty()) {
                    suma += Double.parseDouble(v1.toString());
                    parcialesEvaluados++;
                }
                if (v2 != null && !v2.toString().trim().isEmpty()) {
                    suma += Double.parseDouble(v2.toString());
                    parcialesEvaluados++;
                }
                if (v3 != null && !v3.toString().trim().isEmpty()) {
                    suma += Double.parseDouble(v3.toString());
                    parcialesEvaluados++;
                }

                if (parcialesEvaluados > 0) {
                    double promedio = Math.round((suma / parcialesEvaluados) * 100.0) / 100.0;
                    SwingUtilities.invokeLater(() -> tableModel.setValueAt(promedio, row, 5));
                } else {
                    SwingUtilities.invokeLater(() -> tableModel.setValueAt("", row, 5));
                }

            } catch (NumberFormatException ex) {
            }
        });

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
        for (int i = 2; i < 6; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    public JButton getBtnCargar() { return btnCargar; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public JComboBox<String> getCbSemestre() { return comboSemestre; }
    public JComboBox<String> getCbGrupo() { return comboGrupo; }
    public JComboBox<String> getCbMateria() { return comboMateria; }

    public String getSemestre() { return comboSemestre.getSelectedItem().toString(); }
    public String getGrupo()    { return comboGrupo.getSelectedItem().toString(); }
    public String getMateria()  { return comboMateria.getSelectedItem().toString(); }
}
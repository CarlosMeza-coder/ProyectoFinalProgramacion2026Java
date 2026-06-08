package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import utils.AppStyles;

public class NotasView extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> comboGrupo, comboSemestre, comboMateria;
    private JButton btnCargar, btnGuardar, btnGenerarPDF;

    public NotasView() {
        setLayout(new BorderLayout(0, 20));
        setBackground(AppStyles.BACKGROUND);
        setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel filterCard = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 15));
        filterCard.setBackground(Color.WHITE);
        filterCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 235, 240), 1, true),
            new EmptyBorder(10, 15, 10, 15)
        ));

        comboSemestre = new JComboBox<>();
        comboSemestre.setFont(AppStyles.FUENTE_TEXTO);
        comboSemestre.setBackground(Color.WHITE);
        comboSemestre.setPreferredSize(new Dimension(150, 35));
        filterCard.add(crearBloqueFiltro("Semestre", comboSemestre));

        comboGrupo = new JComboBox<>();
        comboGrupo.setFont(AppStyles.FUENTE_TEXTO);
        comboGrupo.setBackground(Color.WHITE);
        comboGrupo.setPreferredSize(new Dimension(150, 35));
        filterCard.add(crearBloqueFiltro("Grupo", comboGrupo));

        comboMateria = new JComboBox<>();
        comboMateria.setFont(AppStyles.FUENTE_TEXTO);
        comboMateria.setBackground(Color.WHITE);
        comboMateria.setPreferredSize(new Dimension(200, 35));
        filterCard.add(crearBloqueFiltro("Materia", comboMateria));

        btnCargar = new JButton("Cargar Alumnos");
        AppStyles.estilizarBoton(btnCargar);
        btnCargar.setPreferredSize(new Dimension(160, 35));
        
        JPanel btnCargarWrapper = new JPanel(new BorderLayout());
        btnCargarWrapper.setBackground(Color.WHITE);
        btnCargarWrapper.setBorder(new EmptyBorder(22, 10, 0, 0)); 
        btnCargarWrapper.add(btnCargar, BorderLayout.CENTER);
        filterCard.add(btnCargarWrapper);

        add(filterCard, BorderLayout.NORTH);

        JPanel tableCard = new JPanel(new BorderLayout(0, 15));
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 235, 240), 1, true),
            new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel lblTituloTabla = new JLabel("Calificaciones de Alumnos");
        lblTituloTabla.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTituloTabla.setForeground(AppStyles.TEXT_DARK);
        tableCard.add(lblTituloTabla, BorderLayout.NORTH);

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
        AppStyles.estilizarTabla(table);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 2; i < 6; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        btnGenerarPDF = new JButton("Exportar a PDF");
        btnGenerarPDF.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGenerarPDF.setBackground(new Color(220, 53, 69));
        btnGenerarPDF.setForeground(Color.WHITE);
        btnGenerarPDF.setFocusPainted(false);
        btnGenerarPDF.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(190, 40, 55), 1),
            new EmptyBorder(10, 20, 10, 20)
        ));
        btnGenerarPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnGuardar = new JButton("Guardar Cambios");
        AppStyles.estilizarBoton(btnGuardar);
        btnGuardar.setBackground(new Color(39, 174, 96)); 
        btnGuardar.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(34, 153, 84), 1),
            new EmptyBorder(10, 25, 10, 25)
        ));

        bottomPanel.add(btnGenerarPDF);
        bottomPanel.add(btnGuardar);

        tableCard.add(bottomPanel, BorderLayout.SOUTH);

        add(tableCard, BorderLayout.CENTER);
    }

    private JPanel crearBloqueFiltro(String titulo, JComponent componente) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 110, 120));
        p.add(lbl, BorderLayout.NORTH);
        p.add(componente, BorderLayout.CENTER);
        return p;
    }

    public JButton getBtnCargar() { return btnCargar; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnGenerarPDF() { return btnGenerarPDF; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }

    public JComboBox<String> getCbSemestre() { return comboSemestre; }
    public JComboBox<String> getCbGrupo() { return comboGrupo; }
    public JComboBox<String> getCbMateria() { return comboMateria; }

    public String getSemestre() { return comboSemestre.getSelectedItem() != null ? comboSemestre.getSelectedItem().toString() : ""; }
    public String getGrupo()    { return comboGrupo.getSelectedItem() != null ? comboGrupo.getSelectedItem().toString() : ""; }
    public String getMateria()  { return comboMateria.getSelectedItem() != null ? comboMateria.getSelectedItem().toString() : ""; }
}
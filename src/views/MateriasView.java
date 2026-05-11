package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import utils.AppFonts;

public class MateriasView extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;

    public MateriasView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Materias Asignadas");
        title.setFont(AppFonts.bold());
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID Materia", "Nombre de la Materia", "Semestre", "Grupo"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        styleTable();
        
        add(new JScrollPane(table), BorderLayout.CENTER);

        tableModel.addRow(new Object[]{"PROG3", "Programación III", "3er Semestre", "A"});
        tableModel.addRow(new Object[]{"BD1", "Bases de Datos I", "3er Semestre", "B"});
    }

    private void styleTable() {
        table.setRowHeight(35);
        table.getTableHeader().setFont(AppFonts.bold());
        table.getTableHeader().setBackground(new Color(44, 62, 80));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(AppFonts.normal());

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                }
                return c;
            }
        });
    }
}

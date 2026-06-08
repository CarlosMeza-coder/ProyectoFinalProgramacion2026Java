package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import utils.AppStyles;

public class MateriasView extends JPanel {
    
    private JTable table;
    private DefaultTableModel tableModel;

    public MateriasView() {
        setLayout(new BorderLayout());
        setBackground(AppStyles.BACKGROUND);
        setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 235, 240), 1, true),
            new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel title = new JLabel("Materias Asignadas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(AppStyles.TEXT_DARK);
        card.add(title, BorderLayout.NORTH);

        String[] columns = {"ID Materia", "Nombre de la Materia", "Semestre", "Grupo"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        table = new JTable(tableModel);
        AppStyles.estilizarTabla(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        card.add(scrollPane, BorderLayout.CENTER);
        add(card, BorderLayout.CENTER);
    }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTable() { return table; }
}
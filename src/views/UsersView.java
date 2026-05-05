package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import models.UserTableModel; 
import utils.AppFonts;

public class UsersView extends JPanel {
    private JTable table;
    private JButton btnAdd, btnEdit, btnDelete, btnPDF; 

    public UsersView() {
        setLayout(new BorderLayout());
        
        table = new JTable();
        styleTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelButtons.setBackground(Color.WHITE);

        btnAdd = new JButton("Añadir");
        btnEdit = new JButton("Editar");
        btnDelete = new JButton("Eliminar");
        btnPDF = new JButton("Generar PDF"); 

        btnPDF.setBackground(new Color(45, 111, 164));
        btnPDF.setForeground(Color.WHITE);
        btnPDF.setFont(AppFonts.bold());

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnPDF);

        add(panelButtons, BorderLayout.NORTH);
    }

    public void styleTable() {
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setBackground(Color.WHITE);
        table.setFont(AppFonts.normal());
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setFont(AppFonts.bold());
        header.setPreferredSize(new Dimension(0, 40));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                return c;
            }
        });
    }

    public void setTableModel(UserTableModel model) {
        table.setModel(model);
    }

    public JTable getTable() { return table; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnPDF() { return btnPDF; } 
}
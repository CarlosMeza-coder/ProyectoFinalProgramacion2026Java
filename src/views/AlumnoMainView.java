package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import utils.AppFonts; // Asumiendo que tienes esta clase de fuentes como en las otras vistas

public class AlumnoMainView extends JFrame {
    
    private JTable tablaCalificaciones;
    private DefaultTableModel modeloCalificaciones;
    private JButton btnLogout;

    public AlumnoMainView() {
        setTitle("Portal del Alumno - Mis Calificaciones");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(44, 62, 80));
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel lblTitle = new JLabel("MIS CALIFICACIONES");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(AppFonts.bold());

        btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFocusPainted(false);
        
        header.add(lblTitle, BorderLayout.WEST);
        header.add(btnLogout, BorderLayout.EAST);

        String[] columnas = {"Materia","Profesor", "Parcial 1", "Parcial 2", "Parcial 3", "Calificación Final"};
        modeloCalificaciones = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tablaCalificaciones = new JTable(modeloCalificaciones);
        tablaCalificaciones.setRowHeight(30);
        tablaCalificaciones.getTableHeader().setFont(AppFonts.bold());
        tablaCalificaciones.getTableHeader().setBackground(new Color(45, 111, 164));
        tablaCalificaciones.getTableHeader().setForeground(Color.WHITE);
        tablaCalificaciones.setFont(AppFonts.normal());

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 2; i < 6; i++) {
            tablaCalificaciones.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelCentro.setBackground(Color.WHITE);
        panelCentro.add(new JScrollPane(tablaCalificaciones), BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
    }

    public DefaultTableModel getModeloCalificaciones() { return modeloCalificaciones; }
    public JButton getBtnLogout() { return btnLogout; }
    public JTable getTablaCalificaciones() { return tablaCalificaciones; }
}
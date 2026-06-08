package views;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.awt.*;
import utils.AppStyles;

public class AlumnoMainView extends JFrame {
    
    private JTable tablaCalificaciones;
    private DefaultTableModel modeloCalificaciones;
    private JButton btnLogout;

    public AlumnoMainView() {
        setTitle("Portal del Alumno - Mis Calificaciones");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(AppStyles.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppStyles.PRIMARY);
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel lblTitle = new JLabel("MIS CALIFICACIONES");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(AppStyles.FUENTE_TITULO);

        btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFont(AppStyles.FUENTE_TEXTO);
        btnLogout.setBackground(new Color(220, 53, 69)); 
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(new EmptyBorder(8, 15, 8, 15));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        header.add(lblTitle, BorderLayout.WEST);
        header.add(btnLogout, BorderLayout.EAST);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(AppStyles.BACKGROUND);
        mainContent.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel tableCard = new JPanel(new BorderLayout(0, 15));
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(230, 235, 240), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));

        String[] columnas = {"Materia", "Profesor", "Parcial 1", "Parcial 2", "Parcial 3", "Calificación Final"};
        modeloCalificaciones = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tablaCalificaciones = new JTable(modeloCalificaciones);
        AppStyles.estilizarTabla(tablaCalificaciones);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 2; i < 6; i++) {
            tablaCalificaciones.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tablaCalificaciones);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        tableCard.add(scrollPane, BorderLayout.CENTER);
        mainContent.add(tableCard, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
    }

    public DefaultTableModel getModeloCalificaciones() { return modeloCalificaciones; }
    public JButton getBtnLogout() { return btnLogout; }
    public JTable getTablaCalificaciones() { return tablaCalificaciones; }
}
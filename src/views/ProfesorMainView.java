package views;

import java.awt.*;
import javax.swing.*;
import utils.AppFonts;

public class ProfesorMainView extends JFrame {
    private JButton btnMaterias, btnNotas, btnLogout, btnTema;

    public ProfesorMainView() {
        setTitle("Sistema de Calificaciones - Panel Docente");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(44, 62, 80)); 
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel lblTitle = new JLabel("PANEL DOCENTE");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(AppFonts.bold()); 

        btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFocusPainted(false);

        btnTema = new JButton(" Cambiar Tema");
        btnTema.setFocusPainted(false);

        JPanel panelBotonesDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotonesDerecha.setOpaque(false); 
        panelBotonesDerecha.add(btnTema);
        panelBotonesDerecha.add(btnLogout);

        header.add(lblTitle, BorderLayout.WEST);
        header.add(panelBotonesDerecha, BorderLayout.EAST); 

        JPanel panelButtons = new JPanel(new GridLayout(1, 2, 40, 40)); 
        panelButtons.setBorder(BorderFactory.createEmptyBorder(150, 80, 150, 80));
        panelButtons.setBackground(Color.WHITE);

        btnMaterias = createMenuButton("Materias que Imparto");
        btnNotas = createMenuButton("Captura de Notas");

        panelButtons.add(btnMaterias);
        panelButtons.add(btnNotas);

        add(header, BorderLayout.NORTH);
        add(panelButtons, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(AppFonts.bold());
        btn.setBackground(new Color(45, 111, 164)); 
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(35, 90, 135));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(45, 111, 164));
            }
        });
        
        return btn;
    }

    public JButton getBtnMaterias() { return btnMaterias; }
    public JButton getBtnNotas() { return btnNotas; }
    public JButton getBtnLogout() { return btnLogout; }
    public JButton getBtnTema() { return btnTema; } 
}
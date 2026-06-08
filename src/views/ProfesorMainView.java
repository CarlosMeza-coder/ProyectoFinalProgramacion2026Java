package views;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.*;
import utils.AppStyles;

public class ProfesorMainView extends JFrame {
    private JButton btnMaterias, btnNotas, btnLogout, btnTema;

    public ProfesorMainView() {
        setTitle("Sistema de Calificaciones - Panel Docente");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(AppStyles.PRIMARY); 
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel lblTitle = new JLabel("PANEL DOCENTE");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(AppStyles.FUENTE_TITULO); 

        btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setFont(AppStyles.FUENTE_TEXTO);
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setContentAreaFilled(true);
        btnLogout.setOpaque(true);
        btnLogout.setBorder(new EmptyBorder(8, 15, 8, 15));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogout.setBackground(new Color(190, 40, 55));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnLogout.setBackground(new Color(220, 53, 69));
            }
        });

        btnTema = new JButton("Cambiar Tema");
        btnTema.setFont(AppStyles.FUENTE_TEXTO);
        btnTema.setBackground(new Color(35, 90, 135));
        btnTema.setForeground(Color.WHITE);
        btnTema.setFocusPainted(false);
        btnTema.setContentAreaFilled(true);
        btnTema.setOpaque(true);
        btnTema.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(25, 75, 115), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        btnTema.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnTema.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTema.setBackground(new Color(25, 75, 115));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnTema.setBackground(new Color(35, 90, 135));
            }
        });

        JPanel panelBotonesDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        panelBotonesDerecha.setOpaque(false); 
        panelBotonesDerecha.add(btnTema);
        panelBotonesDerecha.add(btnLogout);

        header.add(lblTitle, BorderLayout.WEST);
        header.add(panelBotonesDerecha, BorderLayout.EAST); 

        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(UIManager.getColor("Panel.background")); 

        JPanel panelButtons = new JPanel(new GridLayout(1, 2, 40, 0)); 
        panelButtons.setOpaque(false);
        panelButtons.setPreferredSize(new Dimension(650, 220));

        btnMaterias = createMenuButton("Materias que Imparto");
        btnNotas = createMenuButton("Captura de Notas");

        panelButtons.add(btnMaterias);
        panelButtons.add(btnNotas);

        mainContent.add(panelButtons);

        add(header, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        btn.setBackground(UIManager.getColor("EditorPane.background")); 
        btn.setForeground(UIManager.getColor("Label.foreground")); 
        
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(UIManager.getColor("Component.borderColor"), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(AppStyles.PRIMARY);
                btn.setForeground(Color.WHITE);
                btn.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(AppStyles.PRIMARY.darker(), 1, true),
                    new EmptyBorder(20, 20, 20, 20)
                ));
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(UIManager.getColor("EditorPane.background"));
                btn.setForeground(UIManager.getColor("Label.foreground"));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(UIManager.getColor("Component.borderColor"), 1, true),
                    new EmptyBorder(20, 20, 20, 20)
                ));
            }
        });
        
        return btn;
    }

    public JButton getBtnMaterias() { return btnMaterias; }
    public JButton getBtnNotas() { return btnNotas; }
    public JButton getBtnLogout() { return btnLogout; }
    public JButton getBtnTema() { return btnTema; } 
}
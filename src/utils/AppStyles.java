package utils;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class AppStyles {
    
    public static final Color PRIMARY = new Color(45, 111, 164);     
    public static final Color BACKGROUND = new Color(240, 244, 248); 
    public static final Color TEXT_DARK = new Color(40, 44, 52);    
    
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FUENTE_TEXTO = new Font("Segoe UI", Font.PLAIN, 14);

    public static void estilizarBoton(JButton boton) {
        boton.setFont(FUENTE_TEXTO);
        boton.setBackground(PRIMARY);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false); 
        boton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(PRIMARY.darker(), 1),
            new EmptyBorder(8, 15, 8, 15) 
        ));
    }
    
    public static void estilizarCampo(JTextField campo) {
        campo.setFont(FUENTE_TEXTO);
        campo.setForeground(TEXT_DARK);
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 210, 220), 1), 
            new EmptyBorder(6, 8, 6, 8) 
        ));
    }

    public static void estilizarTabla(JTable tabla) {
        tabla.setRowHeight(25); 
        tabla.setFont(FUENTE_TEXTO);
        tabla.setForeground(TEXT_DARK);
        tabla.setSelectionBackground(new Color(210, 225, 240));
        tabla.setSelectionForeground(TEXT_DARK);
        tabla.setShowVerticalLines(false); 
        
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(PRIMARY);
        tabla.getTableHeader().setForeground(Color.WHITE);
    }
}
package views;
import javax.swing.*;

public class AdminMainView extends JFrame {
    public AdminMainView() {
        setTitle("Panel Administrativo");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        add(new JLabel("Bienvenido, Administrador", 
            SwingConstants.CENTER));
    }
}
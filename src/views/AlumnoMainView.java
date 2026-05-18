package views;
import javax.swing.*;

public class AlumnoMainView extends JFrame {
    public AlumnoMainView() {
        setTitle("Portal del Alumno");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        add(new JLabel("Bienvenido, Alumno", 
            SwingConstants.CENTER));
    }
}
package views;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class ViewLogin extends JPanel {

    public ViewLogin() {
        setLayout(null);
        setBackground(Color.WHITE); 

        JLabel label = new JLabel("Inicio de sesion");
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setBounds(10, 20, 300, 40); 
        add(label);

 
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        textField.setBounds(10, 80, 250, 40); 
        add(textField);

        JPasswordField password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 18));
        password.setBounds(10, 140, 250, 40); 
        add(password);

        JButton boton = new JButton("Ingresar");
        boton.setBounds(10, 200, 120, 40); 
        boton.setBackground(Color.MAGENTA);
        boton.setForeground(Color.BLACK);
        boton.setToolTipText("Clic para entrar");
        add(boton);
    }
}
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
        JLabel invalido = new JLabel("algun datos es erroneo");
        invalido.setFont(new Font("Arial", Font.BOLD, 12));
        invalido.setBounds(10, 110, 255, 40); 
        invalido.setForeground(Color.RED);
        add(invalido);
        

        JPasswordField password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 18));
        password.setBounds(10, 140, 250, 40); 
        add(password);
        JLabel labelpas = new JLabel("algun datos es erroneo");
        labelpas.setFont(new Font("Arial", Font.BOLD, 12));
        labelpas.setBounds(10, 170, 255, 40); 
        labelpas.setForeground(Color.RED);
        add(labelpas);
        


        JButton boton = new JButton("Ingresar");
        boton.setBounds(10, 200, 120, 40); 
        boton.setBackground(Color.MAGENTA);
        boton.setForeground(Color.BLACK);
        boton.setToolTipText("Clic para entrar");
        add(boton);
    }
}
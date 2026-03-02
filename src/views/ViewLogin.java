package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

public class ViewLogin extends JPanel {

    public ViewLogin() {
        BorderPanel container = new BorderPanel(0, 20);
        container.setBackground(Color.WHITE);
        container.setBorder(new EmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout()); 
        add(container);

        JLabel labelTitulo = new JLabel("Inicio de sesión");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 25));
        container.addItem(labelTitulo, BorderLayout.NORTH);

        GridPanel form = new GridPanel(6, 1, 0, 5);
        form.setBackground(Color.WHITE);

        JLabel labelCorreo = new JLabel("Ingrese su correo electrónico");
        labelCorreo.setFont(new Font("Arial", Font.PLAIN, 14));
        form.addItem(labelCorreo);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 18));
        form.addItem(textField);

        JLabel invalidoCorreo = new JLabel("algún dato es erróneo");
        invalidoCorreo.setFont(new Font("Arial", Font.BOLD, 12));
        invalidoCorreo.setForeground(Color.RED);
        form.addItem(invalidoCorreo);

        JLabel labelPassword = new JLabel("Ingrese la contraseña");
        labelPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        form.addItem(labelPassword);

        JPasswordField password = new JPasswordField();
        password.setFont(new Font("Arial", Font.PLAIN, 18));
        form.addItem(password);

        JLabel invalidoPassword = new JLabel("algún dato es erróneo");
        invalidoPassword.setFont(new Font("Arial", Font.BOLD, 12));
        invalidoPassword.setForeground(Color.RED);
        form.addItem(invalidoPassword);

        container.addItem(form, BorderLayout.CENTER);

        FlowPanel buttonContainer = new FlowPanel(FlowLayout.LEFT);
        buttonContainer.setBackground(Color.WHITE);

        JButton boton = new JButton("Ingresar");
        boton.setBackground(Color.CYAN);
        boton.setForeground(Color.BLACK);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setPreferredSize(new Dimension(120, 40));
        boton.setToolTipText("Clic para entrar");
        buttonContainer.addItem(boton);
        
        /*boton.addActionListener(new ActionListener() {
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        	System.out.println("se hizo clic en el boton");
        	JOptionPane.showInternalMessageDialog(null,
        			"se inicio la sesion",
        			"sesion iniciada",
        			JOptionPane.INFORMATION_MESSAGE);
        		
        	
        	}
        });*/
        boton.addActionListener(e -> {
        JOptionPane.showInternalMessageDialog(
        		null, 
        		"se inicio la sesion",
    			"sesion iniciada",
    			JOptionPane.INFORMATION_MESSAGE);
        		login();
        });
        
        
        
        
        
        boton.addActionListener(e -> login());
        
        container.addItem(buttonContainer, BorderLayout.SOUTH);
    }

	private Object login() {
		// TODO Auto-generated method stub
		return null;
	}
}

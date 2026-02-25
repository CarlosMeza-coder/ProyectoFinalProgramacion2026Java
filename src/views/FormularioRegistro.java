package views;

import java.awt.BorderLayout;
import javax.swing.JRadioButton;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioRegistro extends JFrame {

	
	public FormularioRegistro() {
		
	
			
			setSize(300, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(true);
			setTitle("Registro");
			setLocationRelativeTo(null);
			
			Toolkit tk = Toolkit.getDefaultToolkit();
			Image icono = tk.getImage("src/img/icono.png");
			setIconImage(icono);
			
			inicializarComponentes();
			
			setVisible(true);		
		}
		
		

	public void inicializarComponentes() {
		
		
		JLabel lblTitulo = new JLabel("Registro nuevo alumno");
		
		add(lblTitulo, BorderLayout.NORTH);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelComponentes = new JPanel();
		panelComponentes.setLayout(new BoxLayout(panelComponentes, BoxLayout.Y_AXIS));
		panelComponentes.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		 
		
			JLabel lblnom = new JLabel("Nombre " );
			panelComponentes.add(lblnom);
			JTextField txtnom = new JTextField(10);
			panelComponentes.add(txtnom);
		
			JLabel lblapeP = new JLabel("Apellido Paterno " );
			panelComponentes.add(lblapeP);
			JTextField txtapeP = new JTextField(10);
			panelComponentes.add(txtapeP);
			
			JLabel lblapeM = new JLabel("Apellido Materno " );
			panelComponentes.add(lblapeM);
			JTextField txtapeM = new JTextField(10);
			panelComponentes.add(txtapeM);
		
			JLabel lblmat = new JLabel("Matricula" );
			panelComponentes.add(lblmat);
			JTextField txtmat = new JTextField(10);
			panelComponentes.add(txtmat);
		
			JLabel lblcor = new JLabel("Correo" );
			panelComponentes.add(lblcor);
			JTextField txtcor = new JTextField(10);
			panelComponentes.add(txtcor);
			
			JLabel lbled = new JLabel("Edad" );
			panelComponentes.add(lbled);
			JTextField txted = new JTextField(20);
			panelComponentes.add(txted);				
		
			JScrollPane scroll = new JScrollPane(panelComponentes);
			scroll.setHorizontalScrollBar(null);			
			add(scroll);
			
			
			
	
	}
	
	
		
		
		
		
		
		
		
	
	}



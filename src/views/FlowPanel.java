package views;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Component;

public class FlowPanel extends JPanel {

    public FlowPanel() {
        setLayout(new FlowLayout());
    }

    public FlowPanel(int align) {
        setLayout(new FlowLayout(align));
    }

    public FlowPanel(int align, int hgap, int vgap) {
       // setLayout(new FlowLayout(align, hgap, vgap));
    }

    public void addItem(Component item) {
        add(item);
        //Aqui se agrega checkbutton constructor string, booleanose agrega con add
        // Todo sobre los botones 
        JCheckBox chkAceptoCondiciones = new JCheckBox("Acepto condiciones", true);
		add(chkAceptoCondiciones);
		
        JRadioButton rbMujer = new JRadioButton("Mujer");
		add(rbMujer);
		JRadioButton rbHombre = new JRadioButton("Hombre");
		add(rbHombre);
        
        
        
    }
}
package views;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Component;

public class BorderPanel extends JPanel {
    
    public BorderPanel() {
        setLayout(new BorderLayout());
    }

    public BorderPanel(int hgap, int vgap) {
        setLayout(new BorderLayout(hgap, vgap));
    }

    public void addItem(Component item, String position) {
        add(item, position);
    }
}
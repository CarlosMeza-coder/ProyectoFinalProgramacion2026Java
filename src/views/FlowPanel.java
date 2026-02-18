package views;

import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Component;

public class FlowPanel extends JPanel {

    public FlowPanel() {
        setLayout(new FlowLayout());
    }

    public FlowPanel(int align) {
        setLayout(new FlowLayout(align));
    }

    public FlowPanel(int align, int hgap, int vgap) {
        setLayout(new FlowLayout(align, hgap, vgap));
    }

    public void addItem(Component item) {
        add(item);
    }
}
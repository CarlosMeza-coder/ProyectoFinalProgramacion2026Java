package views;

import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.Component;

public class GridPanel extends JPanel {

    public GridPanel() {
        setLayout(new GridLayout());
    }

    public GridPanel(int rows, int cols) {
        setLayout(new GridLayout(rows, cols));
    }

    public GridPanel(int rows, int cols, int hgap, int vgap) {
        setLayout(new GridLayout(rows, cols, hgap, vgap));
    }

    public void addItem(Component item) {
        add(item);
    }
}

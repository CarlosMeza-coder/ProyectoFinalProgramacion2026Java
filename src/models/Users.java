package models;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Users extends JPanel {
    private JTable table;
    private UserTableModel model;

    public Users(List<User> users) {
        setLayout(new BorderLayout());

        model = new UserTableModel(users);
        table = new JTable(model);

        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}

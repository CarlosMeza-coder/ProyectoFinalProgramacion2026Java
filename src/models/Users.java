package models;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Users extends JPanel {
    private JTable table;
    private UserTableModel model;

    public Users(String csvPath) {
        setLayout(new BorderLayout());

        List<User> users = loadUsersFromCsv(csvPath);
        model = new UserTableModel(users);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private List<User> loadUsersFromCsv(String path) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) { 
                    firstLine = false;
                    continue;
                }
                users.add(User.fromCsv(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}

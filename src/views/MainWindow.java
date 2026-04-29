package views;

import javax.swing.*;

import models.Users;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Sistema de Usuarios");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Users usersPanel = new Users("Users.csv");
        add(usersPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}

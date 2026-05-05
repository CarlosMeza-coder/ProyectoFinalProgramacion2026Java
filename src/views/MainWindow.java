package views;

import javax.swing.*;
import controladores.UserController; // Importamos el nuevo controlador

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Sistema de Usuarios - Modo Desarrollo");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);

        UsersView usersView = new UsersView();

        UserController controller = new UserController(usersView);

        controller.loadUsers();

        add(usersView);

        setVisible(true);
    }
}
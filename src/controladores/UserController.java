package controladores;

import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import models.User;
import models.UserTableModel;
import repositorio.UserRepository;
import views.UsersView;
import views.UserFormDialog;
import services.PDFExporter; // Importamos el exportador

public class UserController {
    private UsersView view;
    private UserRepository repo;
    private UserTableModel model;

    public UserController(UsersView view) {
        this.view = view;
        this.repo = new UserRepository();
        this.registerListeners();
    }

    private void registerListeners() {
        view.getBtnAdd().addActionListener(e -> openForm(null));

        view.getBtnEdit().addActionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Selecciona un usuario para editar");
                return;
            }
            openForm(model.getUserAt(row));
        });

        view.getBtnDelete().addActionListener(e -> deleteUser());

        view.getBtnPDF().addActionListener(e -> {
            try {
                List<User> users = repo.getUsers();
                if (users.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "No hay usuarios para exportar.");
                    return;
                }

                String dest = "src/assets/files/ReporteUsuarios.pdf";
                
                PDFExporter.export(users, dest);

                JOptionPane.showMessageDialog(view, "¡Reporte PDF generado con éxito!\nUbicación: " + dest);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Error al generar el PDF: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    public void loadUsers() {
        try {
            List<User> users = repo.getUsers();
            model = new UserTableModel(users);
            view.setTableModel(model);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error al cargar datos: " + e.getMessage());
        }
    }

    private void openForm(User userToEdit) {
        UserFormDialog dialog = new UserFormDialog(null, userToEdit);
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            try {
                if (userToEdit == null) {
                    repo.save(dialog.getUser());
                } else {
                    int row = view.getTable().getSelectedRow();
                    repo.update(row, dialog.getUser());
                }
                loadUsers(); 
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Error al guardar: " + e.getMessage());
            }
        }
    }

    private void deleteUser() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Selecciona un usuario para eliminar");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "¿Seguro que quieres borrar este usuario?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                repo.delete(row);
                loadUsers(); 
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Error al eliminar: " + e.getMessage());
            }
        }
    }
}
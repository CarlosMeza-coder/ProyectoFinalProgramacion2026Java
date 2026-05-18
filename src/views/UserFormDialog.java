package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import models.User;

public class UserFormDialog extends JDialog {

    private JTextField txtEmail;
    private JPasswordField txtPass;
    private JButton btnSave, btnCancel;

    private User user;
    private boolean saved = false;

    public UserFormDialog(Frame parent, User user) {
        super(parent, true); 
        this.user = user;

        setTitle(user == null ? "Agregar usuario" : "Editar usuario");
        setSize(350, 300); 
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(44, 62, 80));
        JLabel lblTitulo = new JLabel(getTitle());
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBorder(new EmptyBorder(20, 30, 20, 30));

        txtEmail = new JTextField();
        txtPass = new JPasswordField();

        panelForm.add(new JLabel("Email:"));
        panelForm.add(txtEmail);
        panelForm.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panelForm.add(new JLabel("Contraseña:"));
        panelForm.add(txtPass);
        panelForm.add(Box.createRigidArea(new Dimension(0, 10)));

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Guardar");
        btnCancel = new JButton("Cancelar");
        
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());

        panelBotones.add(btnSave);
        panelBotones.add(btnCancel);
        add(panelBotones, BorderLayout.SOUTH);

        loadData();
    }

    private void loadData() {
        if (user != null) {
            txtEmail.setText(user.getEmail());
            txtPass.setText(user.getPass());           
        }
    }

    private void save() {
        String email = txtEmail.getText();
        String pass = new String(txtPass.getPassword());


        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llena todos los campos");
            return;
        }

        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setPass(pass);
        } else {
            user.setEmail(email);
            user.setPass(pass);            
        }

        saved = true;
        dispose();
    }

    public boolean isSaved() { return saved; }
    public User getUser() { return user; }
}
package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import models.User;

public class UserFormDialog extends JDialog {

    private JTextField txtEmail;
    private JPasswordField txtPass;
    private JComboBox<String> cboPais;
    private JTextField txtLenguaje;
    private JComboBox<String> cboGenero;
    private JButton btnSave, btnCancel;

    private User user;
    private boolean saved = false;

    public UserFormDialog(Frame parent, User user) {
        super(parent, true); 
        this.user = user;

        setTitle(user == null ? "Agregar usuario" : "Editar usuario");
        setSize(350, 450);
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
        
        String[] paises = {"México", "España", "Argentina", "Colombia"};
        cboPais = new JComboBox<>(paises);
        
        txtLenguaje = new JTextField();
        
        String[] generos = {"hombre", "mujer", "otro"};
        cboGenero = new JComboBox<>(generos);

        panelForm.add(new JLabel("Email:"));
        panelForm.add(txtEmail);
        panelForm.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panelForm.add(new JLabel("Contraseña:"));
        panelForm.add(txtPass);
        panelForm.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panelForm.add(new JLabel("País:"));
        panelForm.add(cboPais);
        panelForm.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panelForm.add(new JLabel("Lenguaje principal:"));
        panelForm.add(txtLenguaje);
        panelForm.add(Box.createRigidArea(new Dimension(0, 10)));
        
        panelForm.add(new JLabel("Género:"));
        panelForm.add(cboGenero);

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
            cboPais.setSelectedItem(user.getPais());
            txtLenguaje.setText(user.getLenguaje());
            cboGenero.setSelectedItem(user.getGenero());
        }
    }

    private void save() {
        String email = txtEmail.getText();
        String pass = new String(txtPass.getPassword());
        String pais = (String) cboPais.getSelectedItem();
        String lenguaje = txtLenguaje.getText();
        String genero = (String) cboGenero.getSelectedItem();

        if (user == null) {
            user = new User(email, pass, pais, lenguaje, genero);
        } else {
            user.setEmail(email);
            user.setPass(pass);
            user.setPais(pais);
            user.setLenguaje(lenguaje);
            user.setGenero(genero);
        }

        saved = true;
        dispose();
    }

    public boolean isSaved() { return saved; }
    public User getUser() { return user; }
}

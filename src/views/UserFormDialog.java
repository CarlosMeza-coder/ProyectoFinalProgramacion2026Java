package views;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import models.User;
import utils.AppStyles;

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
        setSize(420, 360); 
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(AppStyles.PRIMARY);
        panelTitulo.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblTitulo = new JLabel(getTitle());
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(AppStyles.FUENTE_TITULO);
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(2, 1, 0, 20));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(new EmptyBorder(25, 40, 25, 40));

        txtEmail = new JTextField();
        AppStyles.estilizarCampo(txtEmail);
        agregarEfectoFoco(txtEmail);
        
        txtPass = new JPasswordField();
        AppStyles.estilizarCampo(txtPass);
        agregarEfectoFoco(txtPass);

        panelForm.add(crearBloqueCampo("Email Institucional", txtEmail));
        panelForm.add(crearBloqueCampo("Contraseña", txtPass));

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        panelBotones.setBackground(new Color(248, 250, 252));
        panelBotones.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(225, 230, 235)));
        
        btnSave = new JButton("Guardar");
        AppStyles.estilizarBoton(btnSave);
        btnSave.setPreferredSize(new Dimension(120, 40));
        
        btnCancel = new JButton("Cancelar");
        estilizarBotonSecundario(btnCancel);
        btnCancel.setPreferredSize(new Dimension(120, 40));
        
        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());

        panelBotones.add(btnCancel);
        panelBotones.add(btnSave);
        add(panelBotones, BorderLayout.SOUTH);

        loadData();
    }

    private JPanel crearBloqueCampo(String titulo, JComponent componente) {
        JPanel p = new JPanel(new BorderLayout(0, 5));
        p.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(100, 110, 120));
        p.add(lbl, BorderLayout.NORTH);
        p.add(componente, BorderLayout.CENTER);
        return p;
    }

    private void agregarEfectoFoco(JTextField campo) {
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(AppStyles.PRIMARY, 1, true),
                    new EmptyBorder(8, 10, 8, 10)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(210, 220, 230), 1),
                    new EmptyBorder(8, 10, 8, 10)
                ));
            }
        });
    }

    private void estilizarBotonSecundario(JButton boton) {
        boton.setFont(AppStyles.FUENTE_TEXTO);
        boton.setBackground(Color.WHITE);
        boton.setForeground(Color.DARK_GRAY);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 210, 220), 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
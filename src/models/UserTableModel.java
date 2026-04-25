package models;


import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Email", "Pass", "pais", "Lenguaje", "Genero"};
    private List<User> users;

    public UserTableModel(List<User> users) {
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User u = users.get(rowIndex);
        switch (columnIndex) {
            case 0: return u.getEmail();
            case 1: return u.getPassword();
            case 2: return u.getPais();
            case 3: return u.getLenguaje();
            case 4: return u.getGenero();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}


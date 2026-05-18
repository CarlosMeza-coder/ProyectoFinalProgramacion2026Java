package models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AlumnoTableModel extends AbstractTableModel {
    private final List<Alumno> alumnos;
    private final String[] columnNames = {"Matrícula", "Nombre", "Email", "Semestre", "Grupo"};

    public AlumnoTableModel(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public int getRowCount() {
        return alumnos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Alumno alumno = alumnos.get(rowIndex);
        switch (columnIndex) {
            case 0: return alumno.getMatricula();
            case 1: return alumno.getNombre();
            case 2: return alumno.getEmail();
            case 3: return alumno.getSemestre();
            case 4: return alumno.getGrupo();
            default: return null;
        }
    }

    public Alumno getAlumnoAt(int row) {
        return alumnos.get(row);
    }


    public void removeRow(int row) {
        alumnos.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void addRow(Alumno alumno) {
        alumnos.add(alumno);
        int row = alumnos.size() - 1;
        fireTableRowsInserted(row, row);
    }

    public void updateRow(int row, Alumno alumno) {
        alumnos.set(row, alumno);
        fireTableRowsUpdated(row, row);
    }
}
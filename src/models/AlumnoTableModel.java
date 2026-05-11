package models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AlumnoTableModel extends AbstractTableModel {
    
    private final String[] columns = {"Matrícula", "Nombre", "Email", "Semestre", "Grupo"};
    private List<Alumno> alumnos;

    public AlumnoTableModel(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public int getRowCount() {
        return alumnos.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
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
}
package controladores;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import repositorio.ProfesorRepository;
import views.MateriasView;

public class MateriasController {
    private MateriasView view;
    private ProfesorRepository profRepo;
    private int idProfesor;

    public MateriasController(MateriasView view, int idProfesor) {
        this.view = view;
        this.profRepo = new ProfesorRepository();
        this.idProfesor = idProfesor;
        
        cargarDatosTabla();
        mostrarEnVentana();
    }

    private void cargarDatosTabla() {
        DefaultTableModel modelo = view.getTableModel();
        modelo.setRowCount(0); 

        List<Object[]> misMaterias = profRepo.getMisCursosYAlumnos(idProfesor); 
        
        for (Object[] fila : misMaterias) {
            modelo.addRow(fila);
        }
    }

    private void mostrarEnVentana() {
        JFrame frame = new JFrame("Mis Materias");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setLocationRelativeTo(null); 
        
        frame.add(view);
        frame.setVisible(true);
    }
}
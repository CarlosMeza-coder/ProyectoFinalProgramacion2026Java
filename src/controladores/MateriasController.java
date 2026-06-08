package controladores;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import repositorio.ProfesorRepository;
import views.MateriasView;

public class MateriasController {

    private MateriasView view;
    private ProfesorRepository profRepo;
    private int idProfesor; // ID del profesor cuyas materias se van a mostrar

    //  recibe la vista y el ID del profesor que abrió esta ventana
    public MateriasController(MateriasView view, int idProfesor) {
        this.view = view;
        this.profRepo = new ProfesorRepository();
        this.idProfesor = idProfesor;

        cargarDatosTabla(); // Llena la tabla antes de mostrar la ventana
        mostrarEnVentana(); // Crea y muestra el JFrame con la vista adentro
    }

    // Consulta las materias y alumnos del profesor y los pone en la tabla
    private void cargarDatosTabla() {
        DefaultTableModel modelo = view.getTableModel();
        modelo.setRowCount(0); // Limpia la tabla por si tenía datos anteriores

        // Pide al repositorio la lista de cursos y alumnos de este profesor
        List<Object[]> misMaterias = profRepo.getMisCursosYAlumnos(idProfesor);

        // Cada Object[] ya viene listo para insertarse como una fila
        for (Object[] fila : misMaterias) {
            modelo.addRow(fila);
        }
    }

    // Crea un JFrame, mete la vista adentro y lo muestra centrado en pantalla
    private void mostrarEnVentana() {
        JFrame frame = new JFrame("Mis Materias");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Solo cierra esta ventana, no toda la app
        frame.setSize(700, 450);
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla

        frame.add(view); // La vista se inserta dentro del JFrame
        frame.setVisible(true);
    }
}
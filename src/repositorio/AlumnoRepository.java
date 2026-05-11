package repositorio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.Alumno;

public class AlumnoRepository {
    private final String FILE = "src/assets/files/alumnos.json";
    private final ObjectMapper mapper;

    public AlumnoRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public List<Alumno> getAlumnos() throws IOException {
        File file = new File(FILE);
        if (!file.exists() || file.length() == 0) return new ArrayList<>();
        return mapper.readValue(file, new TypeReference<List<Alumno>>() {});
    }

    public void save(Alumno alumno) throws IOException {
        List<Alumno> alumnos = getAlumnos();
        alumnos.add(alumno);
        mapper.writeValue(new File(FILE), alumnos);
    }
    
    public void delete(int index) throws IOException {
        List<Alumno> alumnos = getAlumnos();
        if (index >= 0 && index < alumnos.size()) {
            alumnos.remove(index); 
            updateAll(alumnos);
        }
    }
    
    public void updateAll(List<Alumno> alumnos) throws IOException {
        mapper.writeValue(new File(FILE), alumnos);
    }

    public void updateAlumno(int index, Alumno alumnoActualizado) throws IOException {
        List<Alumno> alumnos = getAlumnos();
        if (index >= 0 && index < alumnos.size()) {
            alumnos.set(index, alumnoActualizado); 
            updateAll(alumnos);
        }
    }
}
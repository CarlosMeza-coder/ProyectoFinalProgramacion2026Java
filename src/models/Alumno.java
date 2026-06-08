package models;

import java.util.ArrayList;
import java.util.List;

public class Alumno {
    private String matricula;
    private String nombre;
    private String apellido;
    private String email;
    private String semestre;
    private String grupo;
    private List<Calificacion> calificaciones; 

    public Alumno() {
        this.calificaciones = new ArrayList<>();
    }

    public Alumno(String matricula, String nombre, String email, String semestre, String grupo) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.email = email;
        this.semestre = semestre;
        this.grupo = grupo;
        this.calificaciones = new ArrayList<>(); 
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public List<Calificacion> getCalificaciones() { return calificaciones; }
    public void setCalificaciones(List<Calificacion> calificaciones) { this.calificaciones = calificaciones; }
}
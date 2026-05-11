package models;

import java.util.ArrayList;
import java.util.List;

public class Calificacion {
    private String materia;
    private List<Double> parciales;
    private double notaFinal;

    public Calificacion() {
        this.parciales = new ArrayList<>();
    }

    public Calificacion(String materia, List<Double> parciales, double notaFinal) {
        this.materia = materia;
        this.parciales = parciales != null ? parciales : new ArrayList<>(); 
        this.notaFinal = notaFinal;
    }

    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public List<Double> getParciales() { return parciales; }
    public void setParciales(List<Double> parciales) { this.parciales = parciales; }

    public double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(double notaFinal) { this.notaFinal = notaFinal; }
}
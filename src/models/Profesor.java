package models;

public class Profesor {
    private int idProfesor;
    private String nombre;
    private String apellido;
    private String email;
    private int idUsuario;
    private String password; 

    public Profesor() {}

    public Profesor(int idProfesor, String nombre, String apellido, String email) {
        this.idProfesor = idProfesor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    public int getIdProfesor() { return idProfesor; }
    public void setIdProfesor(int idProfesor) { this.idProfesor = idProfesor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
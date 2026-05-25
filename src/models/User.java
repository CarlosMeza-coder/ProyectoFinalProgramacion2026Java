package models;

public class User {
    private int id;
    private String email;
    private String pass;
    private String rol;
    private int idProfesor;  
    private String matricula; 

    public User() {}

    public User(int id, String email, String rol) {
        this.id = id;
        this.email = email;
        this.rol = rol;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public int getIdProfesor() { return idProfesor; }
    public void setIdProfesor(int idProfesor) { this.idProfesor = idProfesor; }
    
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}
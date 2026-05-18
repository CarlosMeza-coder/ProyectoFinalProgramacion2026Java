package models;

public class User {
    private int id;
    private String email;
    private String pass;
    private String rol;

    public User() {}

    public User(String email, String pass, String rol) {
        this.email = email;
        this.pass = pass;
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
}
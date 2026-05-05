package models;

public class User {
    private String email;
    private String pass;
    private String pais;
    private String lenguaje;
    private String genero;

    public User() {
    }

    public User(String email, String pass, String pais, String lenguaje, String genero) {
        this.email = email;
        this.pass = pass;
        this.pais = pais;
        this.lenguaje = lenguaje;
        this.genero = genero;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getLenguaje() { return lenguaje; }
    public void setLenguaje(String lenguaje) { this.lenguaje = lenguaje; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}

package models;

public class User {
    private String email;
    private String pass;
    private String pais;
    private String lenguaje;
    private String genero;

    public User(String email, String pass, String pais, String lenguaje, String genero) {
        this.email = email;
        this.pass = pass;
        this.pais = pais;
        this.lenguaje = lenguaje;
        this.genero = genero;
    }
    
    public User (String email, String pass) {
    	 this.email = email;
         this.pass = pass;
    }
    
    
    
    
    
    public static User fromCsv(String line) {
        String[] parts = line.split(",");
        return new User(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    public String toCsv() {
        return String.join(",", email, pass, pais, lenguaje, genero);
    }

   
    public String getEmail() { return email; }
    public String getPass() { return pass; }
    public String getpais() { return pais; }
    public String getlenguaje() { return lenguaje; }
    public String getgenero() { return genero; }

	public Object getPais() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getLenguaje() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getGenero() {
		// TODO Auto-generated method stub
		return null;
	}
}

package excepciones;

public class InvalidPassword extends Exception {
    
    public InvalidPassword() {
        super("Contraseña incorrecta");
    }
    
    public InvalidPassword(String message) {
        super(message);
    }
}

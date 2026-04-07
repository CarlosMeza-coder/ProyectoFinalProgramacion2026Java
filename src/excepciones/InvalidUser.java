package excepciones;

public class InvalidUser extends Exception {
	
    public InvalidUser () {
    	super ("No se encontro al usuario");
    }
    
    public InvalidUser (String messages) {
    	super (messages);
    }
}

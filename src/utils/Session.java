package utils;

import models.User;

public class Session {
    
    private static User currentUser;
    
    public static void login(User user) {
        currentUser = user;
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }
    
    public static void logout() {
        currentUser = null;
    }
    
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
    
    public static String getRole() {
        return currentUser != null ? currentUser.getRol().toUpperCase() : null;
    }
    
    
    public static int getProfesorId() {
        return (currentUser != null) ? currentUser.getIdProfesor() : -1;
    }
    
    public static String getMatriculaAlumno() {
        return (currentUser != null) ? currentUser.getMatricula() : null;
    }
}
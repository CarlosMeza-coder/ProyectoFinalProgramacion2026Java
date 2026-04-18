package models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String email;
    private String password;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String toCsv() {
        return email + "," + password;
    }

    public static User fromCsv(String userData) {
        String[] data = userData.split(","); 
        
        String email = data[0];    
        String password = data[1];
        
        return new User(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
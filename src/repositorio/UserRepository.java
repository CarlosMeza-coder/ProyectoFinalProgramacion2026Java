package repositorio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import models.User;

public class UserRepository {

    private final String FILE = "src/assets/files/users.csv";
    
    public void save(User user) throws IOException {
        File file = new File(FILE);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE, true), StandardCharsets.UTF_8))) {
            writer.write(user.toCsv());
            writer.newLine();
        }
    }
    
    public List<User> getUsers() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(FILE);

        if (!file.exists()) {
            return users;
        }
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    users.add(User.fromCsv(line));
                }
            }
        }
        return users;
    }
}
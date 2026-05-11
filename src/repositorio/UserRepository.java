package repositorio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.User;

public class UserRepository {
    private final String FILE = "src/assets/files/users.json";
    private final ObjectMapper mapper;

    public UserRepository() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public List<User> getUsers() throws IOException {
        File file = new File(FILE);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            System.err.println("Error al procesar el JSON de usuarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void updateAll(List<User> users) throws IOException {
        mapper.writeValue(new File(FILE), users);
    }

    public void save(User user) throws IOException {
        List<User> users = getUsers();
        users.add(user);
        updateAll(users);
    }
}
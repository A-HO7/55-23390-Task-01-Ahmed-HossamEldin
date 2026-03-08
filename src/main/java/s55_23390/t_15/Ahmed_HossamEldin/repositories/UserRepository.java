package s55_23390.t_15.Ahmed_HossamEldin.repositories;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import s55_23390.t_15.Ahmed_HossamEldin.models.User;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private List<User> users;
    private java.io.File jsonFile;

    public UserRepository() {
        InputStream inputStream = getClass().getResourceAsStream("/users.json");
        if (inputStream == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to read users.json");
        }
        try {
            this.jsonFile = new java.io.File(getClass().getResource("/users.json").toURI());
        } catch (Exception e) {
            this.jsonFile = new java.io.File("/data/users.json");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.users = objectMapper.readValue(inputStream, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error parsing users.json", e);
        }
    }

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    public User save(User user) {
        user.setId(java.util.UUID.randomUUID().toString());
        users.add(user);
        try {
            new ObjectMapper().writeValue(jsonFile, users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to write to users.json", e);
        }
        return user;
    }

    public Optional<User> update(String id, User updated) {
        Optional<User> existingUserOpt = findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setUsername(updated.getUsername());
            existingUser.setEmail(updated.getEmail());
            try {
                new ObjectMapper().writeValue(jsonFile, users);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to write to users.json", e);
            }
            return Optional.of(existingUser);
        }
        return Optional.empty();
    }

    public boolean deleteById(String id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
        if (removed) {
            try {
                new ObjectMapper().writeValue(jsonFile, users);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to write to users.json", e);
            }
        }
        return removed;
    }
}

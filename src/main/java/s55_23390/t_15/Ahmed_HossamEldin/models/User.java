package s55_23390.t_15.Ahmed_HossamEldin.models;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private String id;
    private String username;
    private String email;
    private LocalDateTime createdAt;

    // Empty constructor
    public User() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    // Constructor without id
    public User(String username, String email) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    // Full constructor
    public User(String id, String username, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

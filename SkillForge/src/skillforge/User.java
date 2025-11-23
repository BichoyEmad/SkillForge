/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
// User.java
import java.util.UUID;

public abstract class User {
    protected String userId;
    protected String role; // "student" or "instructor"
    protected String username;
    protected String email;
    protected String passwordHash;

    public User() {} // for Gson

    public User(String role, String username, String email, String passwordHash) {
        this.userId = UUID.randomUUID().toString();
        this.role = role;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // getters / setters
    public String getUserId() { return userId; }
    public String getRole() { return role; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
// UserManager.java
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;

public class UserManager {
    private List<User> users;
    private final String usersFile = "users.json";

    public UserManager() {
        Type userListType = new TypeToken<List<User>>() {}.getType();
        // Because Gson cannot know subclasses automatically, we will load raw Json and deserialize manually:
        users = loadUsers();
    }

    private List<User> loadUsers() {
        // read raw Json and handle subclassing manually
        try {
            String raw = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("users.json")));
            if (raw.trim().isEmpty()) raw = "[]";
            com.google.gson.JsonArray arr = com.google.gson.JsonParser.parseString(raw).getAsJsonArray();
            List<User> list = new ArrayList<>();
            for (com.google.gson.JsonElement e : arr) {
                com.google.gson.JsonObject obj = e.getAsJsonObject();
                String role = obj.get("role").getAsString();
                if ("student".equals(role)) {
                    Student s = new com.google.gson.Gson().fromJson(e, Student.class);
                    list.add(s);
                } else if ("instructor".equals(role)) {
                    Instructor i = new com.google.gson.Gson().fromJson(e, Instructor.class);
                    list.add(i);
                } else {
                    // fallback to generic user (shouldn't happen)
                    User u = new com.google.gson.Gson().fromJson(e, User.class);
                    list.add(u);
                }
            }
            return list;
        } catch (Exception ex) {
            // if file not exists or empty, initialize file
            JsonUtil.writeList("users.json", new ArrayList<>());
            return new ArrayList<>();
        }
    }

    private void saveUsers() {
        // because users is heterogeneous, write as list of Objects
        JsonUtil.writeList("users.json", users);
    }

    // Validation simple email regex
    private boolean validEmail(String email) {
        if (email == null) return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

    public String hashPassword(String password) {
        return SecurityUtil.sha256(password);
    }

    public boolean signupStudent(String username, String email, String password) {
        if (username == null || email == null || password == null) return false;
        if (!validEmail(email)) return false;
        if (findByEmail(email) != null) return false;
        Student s = new Student(username, email, hashPassword(password));
        users.add(s);
        saveUsers();
        return true;
    }

    public boolean signupInstructor(String username, String email, String password) {
        if (username == null || email == null || password == null) return false;
        if (!validEmail(email)) return false;
        if (findByEmail(email) != null) return false;
        Instructor i = new Instructor(username, email, hashPassword(password));
        users.add(i);
        saveUsers();
        return true;
    }

    public User login(String email, String password) {
        User u = findByEmail(email);
        if (u == null) return null;
        String h = hashPassword(password);
        if (h.equals(u.getPasswordHash())) return u;
        return null;
    }

    public User findByEmail(String email) {
        for (User u : users) if (u.getEmail().equalsIgnoreCase(email)) return u;
        return null;
    }

    public User findById(String userId) {
        for (User u : users) if (u.getUserId().equals(userId)) return u;
        return null;
    }

    public List<User> getAllUsers() { return users; }

    public boolean saveAll() { saveUsers(); return true; }
}


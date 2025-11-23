/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

public class UserManager {
    private List<User> users;
    private final String usersFile = "users.json";

    public UserManager() {
        users = loadUsers();
    }

    private List<User> loadUsers() {
        try {
            Path p = Paths.get(usersFile);
            if (!Files.exists(p)) {
                Files.createFile(p);
                Files.write(p, "[]".getBytes());
            }
            String raw = new String(Files.readAllBytes(p));
            if (raw.trim().isEmpty()) raw = "[]";
            JsonArray arr = JsonParser.parseString(raw).getAsJsonArray();
            List<User> list = new ArrayList<>();
            Gson g = new Gson();
            for (JsonElement e : arr) {
                JsonObject obj = e.getAsJsonObject();
                String role = obj.has("role") ? obj.get("role").getAsString() : "student";
                if ("student".equalsIgnoreCase(role)) list.add(g.fromJson(e, Student.class));
                else if ("instructor".equalsIgnoreCase(role)) list.add(g.fromJson(e, Instructor.class));
                else if ("admin".equalsIgnoreCase(role)) list.add(g.fromJson(e, Admin.class));
                else list.add(g.fromJson(e, User.class)); // fallback
            }
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            JSonUtil.writeList(usersFile, new ArrayList<>());
            return new ArrayList<>();
        }
    }

    private void saveUsers() {
        JSonUtil.writeList(usersFile, users);
    }

    private boolean validEmail(String email) {
        if (email == null) return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, email);
    }

    public String hashPassword(String password) {
        return SecurityUtil.sha256(password);
    }

    public Student signupStudent(String username, String email, String password) {
        if (username==null || email==null || password==null) return null;
        if (!validEmail(email)) return null;
        if (findByEmail(email) != null) return null;
        Student s = new Student(username, email, hashPassword(password));
        users.add(s);
        saveUsers();
        return s;
    }

    public Instructor signupInstructor(String username, String email, String password) {
        if (username==null || email==null || password==null) return null;
        if (!validEmail(email)) return null;
        if (findByEmail(email) != null) return null;
        Instructor i = new Instructor(username, email, hashPassword(password));
        users.add(i);
        saveUsers();
        return i;
    }

    public Admin signupAdmin(String username, String email, String password) {
        if (username==null || email==null || password==null) return null;
        if (!validEmail(email)) return null;
        if (findByEmail(email) != null) return null;
        Admin a = new Admin(username, email, hashPassword(password));
        users.add(a);
        saveUsers();
        return a;
    }

    public User login(String email, String password) {
        User u = findByEmail(email);
        if (u == null) return null;
        if (hashPassword(password).equals(u.getPasswordHash())) return u;
        return null;
    }

    public User findByEmail(String email) {
        if (email == null) return null;
        for (User u : users) if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(email)) return u;
        return null;
    }

    public User findById(String userId) {
        if (userId == null) return null;
        for (User u : users) if (u.getUserId() != null && u.getUserId().equals(userId)) return u;
        return null;
    }

    public List<User> getAllUsers() { return users; }

    public boolean saveAll() { saveUsers(); return true; }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
// Instructor.java
import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {
    private List<String> createdCourses = new ArrayList<>();

    public Instructor() { super(); }

    public Instructor(String username, String email, String passwordHash) {
        super("instructor", username, email, passwordHash);
    }

    public List<String> getCreatedCourses() { return createdCourses; }
    public void addCourseCreated(String courseId) { createdCourses.add(courseId); }
    public void removeCourseCreated(String courseId) { createdCourses.remove(courseId); }
}


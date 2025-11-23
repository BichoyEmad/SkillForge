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
import java.util.*;

public class Instructor extends User {
    private List<String> createdCourses;

    public Instructor() {
        super();
        this.role = "instructor";
        createdCourses = new ArrayList<>();
    }

    public Instructor(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "instructor");
        createdCourses = new ArrayList<>();
    }

    public void addCourseCreated(String courseId) {
        if (!createdCourses.contains(courseId)) createdCourses.add(courseId);
    }

    public void removeCourseCreated(String courseId) {
        createdCourses.remove(courseId);
    }

    public List<String> getCreatedCourses() { return createdCourses; }
}



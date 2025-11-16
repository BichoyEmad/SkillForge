/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
// Student.java
import java.util.*;

public class Student extends User {
    // enrolledCourses: list of courseIds
    private List<String> enrolledCourses = new ArrayList<>();
    // progress: map courseId -> set of completed lessonIds
    private Map<String, Set<String>> progress = new HashMap<>();

    public Student() { super(); }

    public Student(String username, String email, String passwordHash) {
        super("student", username, email, passwordHash);
    }

    public List<String> getEnrolledCourses() { return enrolledCourses; }
    public Map<String, Set<String>> getProgress() { return progress; }

    public void enroll(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
            progress.putIfAbsent(courseId, new HashSet<>());
        }
    }

    public void markLessonCompleted(String courseId, String lessonId) {
        progress.putIfAbsent(courseId, new HashSet<>());
        progress.get(courseId).add(lessonId);
        if (!enrolledCourses.contains(courseId)) enrolledCourses.add(courseId);
    }

    public boolean isLessonCompleted(String courseId, String lessonId) {
        return progress.containsKey(courseId) && progress.get(courseId).contains(lessonId);
    }
}


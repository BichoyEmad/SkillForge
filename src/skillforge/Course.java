/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author hp
 */
// Course.java
import java.util.*;

public class Course {
    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private List<Lesson> lessons = new ArrayList<>();
    private List<String> students = new ArrayList<>(); // student userIds

    public Course() {}

    public Course(String title, String description, String instructorId) {
        this.courseId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
    }

    public String getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getInstructorId() { return instructorId; }
    public List<Lesson> getLessons() { return lessons; }
    public List<String> getStudents() { return students; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    public void addLesson(Lesson l) { lessons.add(l); }
    public void removeLesson(String lessonId) {
        lessons.removeIf(l -> l.getLessonId().equals(lessonId));
    }
    public Lesson getLessonById(String lessonId) {
        for (Lesson l : lessons) if (l.getLessonId().equals(lessonId)) return l;
        return null;
    }

    public void enrollStudent(String studentId) {
        if (!students.contains(studentId)) students.add(studentId);
    }

    public void unenrollStudent(String studentId) {
        students.remove(studentId);
    }
}
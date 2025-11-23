/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
import java.util.*;

public class Course {
    public enum ApprovalStatus { PENDING, APPROVED, REJECTED }

    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
    private List<Lesson> lessons = new ArrayList<>();
    private List<String> students = new ArrayList<>();
    private Map<String, Certificate> certificates = new HashMap<>();

    public Course() {}
    public Course(String title, String description, String instructorId) {
        this.courseId = java.util.UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
    }

    public String getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getInstructorId() { return instructorId; }
    public ApprovalStatus getApprovalStatus() { return approvalStatus; }

    public void setTitle(String t) { this.title = t; }
    public void setDescription(String d) { this.description = d; }
    public void setApprovalStatus(ApprovalStatus s) { this.approvalStatus = s; }

    public List<Lesson> getLessons() { return lessons; }
    public void addLesson(Lesson l) { lessons.add(l); }
    public void removeLesson(String lessonId) { lessons.removeIf(x -> x.getLessonId().equals(lessonId)); }
    public Lesson getLessonById(String lessonId) {
        for (Lesson l : lessons) if (l.getLessonId().equals(lessonId)) return l;
        return null;
    }

    public void enrollStudent(String studentId) { if (!students.contains(studentId)) students.add(studentId); }
    public List<String> getStudents() { return students; }

    public void addCertificate(String studentId, Certificate cert) { certificates.put(studentId, cert); }
    public Map<String, Certificate> getCertificates() { return certificates; }
}

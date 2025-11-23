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
    // completedLessons key = courseId::lessonId -> boolean
    private Map<String, Boolean> completedLessons;
    // attempts: courseId -> (lessonId -> attemptsUsed)
    private Map<String, Map<String, Integer>> attemptsCount;
    // bestScores: courseId -> (lessonId -> bestPercent)
    private Map<String, Map<String, Integer>> bestScores;
    private Set<String> enrolledCourses;
    private List<Certificate> certificates;

    public Student() {
        super();
        this.role = "student";
        completedLessons = new HashMap<>();
        attemptsCount = new HashMap<>();
        bestScores = new HashMap<>();
        enrolledCourses = new LinkedHashSet<>();
        certificates = new ArrayList<>();
    }

    public Student(String username, String email, String passwordHash) {
        super(username, email, passwordHash, "student");
        completedLessons = new HashMap<>();
        attemptsCount = new HashMap<>();
        bestScores = new HashMap<>();
        enrolledCourses = new LinkedHashSet<>();
        certificates = new ArrayList<>();
    }

    private String makeKey(String courseId, String lessonId) {
        return courseId + "::" + lessonId;
    }

    // enrollment
    public void enroll(String courseId) { enrolledCourses.add(courseId); }
    public Set<String> getEnrolledCourses() { return enrolledCourses; }

    // mark completed
    public void markLessonCompleted(String courseId, String lessonId) {
        completedLessons.put(makeKey(courseId, lessonId), true);
    }

    public boolean isLessonCompleted(String courseId, String lessonId) {
        return completedLessons.getOrDefault(makeKey(courseId, lessonId), false);
    }

    // attempts management
    public int getAttempts(String courseId, String lessonId) {
        return attemptsCount.getOrDefault(courseId, Collections.emptyMap()).getOrDefault(lessonId, 0);
    }

    public void incrementAttempts(String courseId, String lessonId) {
        attemptsCount.putIfAbsent(courseId, new HashMap<>());
        Map<String,Integer> m = attemptsCount.get(courseId);
        m.put(lessonId, m.getOrDefault(lessonId, 0) + 1);
    }

    // best score management
    public void updateBestScore(String courseId, String lessonId, int percent) {
        bestScores.putIfAbsent(courseId, new HashMap<>());
        Map<String,Integer> m = bestScores.get(courseId);
        int prev = m.getOrDefault(lessonId, 0);
        if (percent > prev) m.put(lessonId, percent);
    }

    public int getBestScore(String courseId, String lessonId) {
        return bestScores.getOrDefault(courseId, Collections.emptyMap()).getOrDefault(lessonId, 0);
    }

    // certificates
    public void addCertificate(Certificate cert) { certificates.add(cert); }
    public List<Certificate> getCertificates() { return certificates; }

    // some optional helpers (not strictly necessary but useful)
    public Map<String, Map<String, Integer>> getAllAttempts() { return attemptsCount; }
    public Map<String, Map<String, Integer>> getAllBestScores() { return bestScores; }
}


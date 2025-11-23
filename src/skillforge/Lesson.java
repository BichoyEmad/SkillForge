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

public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private Quiz quiz;

    private Map<String,Integer> studentBestPercent = new HashMap<>();
    private Map<String,int[]> studentLastAttempt = new HashMap<>();

    public Lesson() {}
    public Lesson(String title, String content) {
        this.lessonId = java.util.UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
    }

    public String getLessonId() { return lessonId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Quiz getQuiz() { return quiz; }

    public void setTitle(String t) { this.title = t; }
    public void setContent(String c) { this.content = c; }
    public void setQuiz(Quiz q) { this.quiz = q; }

    public void recordStudentQuizResult(String studentId, int correct, int total) {
        int percent = total == 0 ? 0 : (int) Math.round((correct * 100.0) / total);
        studentLastAttempt.put(studentId, new int[]{correct, total});
        int prevBest = studentBestPercent.getOrDefault(studentId, 0);
        if (percent > prevBest) studentBestPercent.put(studentId, percent);
    }

    public int getStudentBestPercent(String studentId) {
        return studentBestPercent.getOrDefault(studentId, 0);
    }

    public Map<String,Object> getStatistics() {
        Map<String,Object> s = new HashMap<>();
        s.put("lessonId", lessonId);
        s.put("title", title);
        s.put("studentsAttempted", studentLastAttempt.size());
        int totalCorrect = 0;
        int totalQuestions = 0;
        for (int[] arr : studentLastAttempt.values()) {
            totalCorrect += arr[0];
            totalQuestions += arr[1];
        }
        double avgPercent = totalQuestions == 0 ? 0.0 : (totalCorrect * 100.0 / totalQuestions);
        s.put("averagePercent", avgPercent);
        int sumBest = 0;
        for (int val : studentBestPercent.values()) sumBest += val;
        double avgBest = studentBestPercent.isEmpty() ? 0.0 : (sumBest * 1.0 / studentBestPercent.size());
        s.put("averageBestPercent", avgBest);
        return s;
    }
}




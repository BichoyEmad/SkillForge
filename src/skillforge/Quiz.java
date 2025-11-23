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

public class Quiz {
    private String quizId;
    private List<Question> questions = new ArrayList<>();

    public Quiz() { this.quizId = java.util.UUID.randomUUID().toString(); }

    public List<Question> getQuestions() { return questions; }
    public void addQuestion(Question q) { questions.add(q); }
    public String getQuizId() { return quizId; }
}

package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import skillforge.Course;
import skillforge.CourseManager;
import skillforge.Lesson;
import skillforge.Question;
import skillforge.Quiz;
import skillforge.Student;
import skillforge.UserManager;

public class SolveQuiz extends JPanel {

    private final List<Integer> selectedIndices;
    private final JPanel questionsPanel;
    private final JScrollPane scrollPane;
    private final UserManager userManager;
    private final CourseManager courseManager;
    private final Course c;
    private final Student s;
    private final Lesson l;

    public SolveQuiz(MainWindow mainWindow, UserManager userManager,
                     CourseManager courseManager, Course c, Lesson l, Student s, Quiz quiz) {
        super(new BorderLayout(10, 10));
        this.courseManager = courseManager;
        this.userManager = userManager;
        this.c = c;
        this.s = s;
        this.l = l;

        selectedIndices = new ArrayList<>();
        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            selectedIndices.add(-1);
        }

        JPanel topPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        topPanel.add(GUIHelpers.getFormattedButton("Back", e -> mainWindow.setPanel(
                new ViewStudentLessons(mainWindow, userManager, courseManager, c, s))));
        topPanel.add(GUIHelpers.getFormattedButton("Submit", e -> handleSubmit(mainWindow)));
        add(topPanel, BorderLayout.NORTH);

        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(null);

        int questionIndex = 0;
        for (Question q : quiz.getQuestions()) {
            JPanel qv = studentQuestionView(q, questionIndex);
            questionsPanel.add(qv);
            questionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            questionIndex++;
        }

        scrollPane = new JScrollPane(questionsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void handleSubmit(MainWindow mainWindow) {
        for (int i = 0; i < selectedIndices.size(); i++) {
            if (selectedIndices.get(i) == -1) {
                JOptionPane.showMessageDialog(this, "Please answer question " + (i + 1));
                return;
            }
        }

        Map<Integer, Integer> answers = new HashMap<>();
        for (int i = 0; i < selectedIndices.size(); i++) {
            answers.put(i, selectedIndices.get(i));
        }

        String result = courseManager.takeQuiz(c.getCourseId(), l.getLessonId(), s.getUserId(), answers);
        StringBuilder bd = new StringBuilder();
        

        answers.forEach((qIdx, optIdx) -> bd.append("Q").append(qIdx).append(1).append(": selected option index ").append(optIdx).append("\n"));
        JOptionPane.showMessageDialog(this, bd.toString());
        if (result != null) {
            JOptionPane.showMessageDialog(this, "Quiz submitted!");
            JOptionPane.showMessageDialog(mainWindow, result);
        } else {
            JOptionPane.showMessageDialog(mainWindow, "Submission Failed!");
        }
        mainWindow.setPanel(new ViewStudentLessons(mainWindow, userManager, courseManager, c, s));
    }

    private JPanel studentQuestionView(Question question, int questionIndex) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    panel.setBorder(BorderFactory.createTitledBorder("Question " + (questionIndex + 1)));

    // Padding inside panel
    panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Question " + (questionIndex + 1)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10))
    );

    // Question label
    JLabel questionLabel = GUIHelpers.getFormattedFormLabel(question.getQuestionText());
    questionLabel.setFont(questionLabel.getFont().deriveFont(18f));
    questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(questionLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    ButtonGroup buttonGroup = new ButtonGroup();
    List<String> options = question.getOptions();
    for (int i = 0; i < options.size(); i++) {
        JRadioButton rb = new JRadioButton(options.get(i));
        rb.setFont(rb.getFont().deriveFont(16f));
        rb.setAlignmentX(Component.LEFT_ALIGNMENT);
        rb.setFocusPainted(false);
        rb.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        final int optionIndex = i;
        rb.addActionListener(e -> selectedIndices.set(questionIndex, optionIndex));
        buttonGroup.add(rb);
        panel.add(rb);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    return panel;
}

}

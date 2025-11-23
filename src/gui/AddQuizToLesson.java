package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import skillforge.Course;
import skillforge.CourseManager;
import skillforge.Instructor;
import skillforge.Lesson;
import skillforge.Question;
import skillforge.Quiz;
import skillforge.UserManager;

public class AddQuizToLesson extends JPanel {

    private JTable optionsTable;
    private JPanel contentPanel;
    private JTextArea inputQuestion;
    private MainWindow mainWindow;
    private Quiz quiz;

    public AddQuizToLesson(MainWindow mainWindow, Instructor instructor,
            UserManager userManager, CourseManager courseManager,
            Course c, Lesson l) {

        super(new BorderLayout(25, 25));
        this.mainWindow = mainWindow;

        quiz = new Quiz();

        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(GUIHelpers.getFormattedTitle("Add Quiz to Lesson"), BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(null);

        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.add(GUIHelpers.getFormattedFormLabel("Question:"), BorderLayout.WEST);
        inputQuestion = GUIHelpers.getFormattedTextArea();
        topPanel.add(inputQuestion, BorderLayout.CENTER);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        String[][] defaultOptions = {
            {"", "false"},
            {"", "true"}
        };
        String[] columns = {"Option", "Is Correct"};

        optionsTable = GUIHelpers.getFormattedTable(defaultOptions, columns);
        contentPanel.add(new JScrollPane(optionsTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 4, 15, 15));

        bottomPanel.add(GUIHelpers.getFormattedButton("New Question", (ActionEvent e) -> {
            if (addQuestionToQuiz()) {
                resetDefaultOptions();
                inputQuestion.setText("");
            }
        }));

        bottomPanel.add(GUIHelpers.getFormattedButton("Add New Option", (ActionEvent e) -> {

            int oldRows = optionsTable.getRowCount();
            int cols = optionsTable.getColumnCount();

            String[][] newData = new String[oldRows + 1][cols];
            for (int r = 0; r < oldRows; r++) {
                for (int cIndex = 0; cIndex < cols; cIndex++) {
                    newData[r][cIndex] = optionsTable.getValueAt(r, cIndex).toString();
                }
            }

            newData[oldRows][0] = "";
            newData[oldRows][1] = "false";

            optionsTable.setModel(new DefaultTableModel(newData, columns));
            optionsTable = GUIHelpers.getFormattedTable(newData, columns);
            contentPanel.remove(((BorderLayout) contentPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER));
            contentPanel.add(new JScrollPane(optionsTable), BorderLayout.CENTER);

            contentPanel.revalidate();
            contentPanel.repaint();
        }));

        bottomPanel.add(GUIHelpers.getFormattedButton("Remove Option", (ActionEvent e) -> {
            int selectedRow = optionsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(mainWindow, "Please select an option to remove");
                return;
            }

            int oldRows = optionsTable.getRowCount();
            int cols = optionsTable.getColumnCount();

            if (oldRows <= 2) {
                JOptionPane.showMessageDialog(mainWindow, "At least 2 options must remain");
                return;
            }

            String[][] newData = new String[oldRows - 1][cols];
            int newRow = 0;
            for (int r = 0; r < oldRows; r++) {
                if (r == selectedRow) {
                    continue;
                }
                for (int j = 0; j < cols; j++) {
                    newData[newRow][j] = optionsTable.getValueAt(r, j).toString();
                }
                newRow++;
            }

            optionsTable.setModel(new DefaultTableModel(newData, columns));
            optionsTable = GUIHelpers.getFormattedTable(newData, columns);

            contentPanel.remove(((BorderLayout) contentPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER));
            contentPanel.add(new JScrollPane(optionsTable), BorderLayout.CENTER);

            contentPanel.revalidate();
            contentPanel.repaint();
        }));

        bottomPanel.add(GUIHelpers.getFormattedButton("Submit", (ActionEvent e) -> {
            if (addQuestionToQuiz()) {
                boolean ok = courseManager.addQuizToLesson(c.getCourseId(), l.getLessonId(), quiz, instructor.getUserId());
                if (ok) {
                    JOptionPane.showMessageDialog(mainWindow, "Quiz Saved!");
                    mainWindow.setPanel(new InstructorDashboard(mainWindow, userManager, courseManager, instructor));
                } else {
                    JOptionPane.showMessageDialog(mainWindow, "Operation Failed");
                }
            }
        }));

        bottomPanel.add(GUIHelpers.getFormattedButton("Cancel", (ActionEvent e) -> {
            mainWindow.setPanel(new InstructorDashboard(mainWindow, userManager, courseManager, instructor));
        }));

        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void resetDefaultOptions() {
        String[][] defaultOptions = {
            {"", "false"},
            {"", "true"}
        };
        String[] columns = {"Option", "Is Correct"};

        optionsTable = GUIHelpers.getFormattedTable(defaultOptions, columns);
        contentPanel.remove(((BorderLayout) contentPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER));
        contentPanel.add(new JScrollPane(optionsTable), BorderLayout.CENTER);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private boolean addQuestionToQuiz() {
        String question = inputQuestion.getText().trim();
        if (question == null || question.isBlank()) {
            JOptionPane.showMessageDialog(mainWindow, "Question cannot be empty");
            return false;
        }
        int correctIndex = -1;
        List<String> options = new ArrayList<>();
        TableModel model = optionsTable.getModel();
        for (int i = 0; i < optionsTable.getRowCount(); i++) {
            String option = model.getValueAt(i, 0).toString();
            if (option == null || option.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Option at row " + (i + 1) + " cannot be empty");
                return false;
            }
            options.add(option);
            String isCorrect = model.getValueAt(i, 1).toString();
            if (isCorrect == null || isCorrect.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Specify wether option at row " + (i + 1) + " correct or not");
                return false;
            }
            if (Boolean.parseBoolean(isCorrect) == true) {
                if (correctIndex != -1) {
                    JOptionPane.showMessageDialog(mainWindow, "Every question should have 1 only correct answer");
                    return false;
                }
                correctIndex = i;
            }
        }
        if (!options.isEmpty() && correctIndex != -1) {
            quiz.addQuestion(new Question(question, options, correctIndex));
            JOptionPane.showMessageDialog(mainWindow, "Question added successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(mainWindow, "Failed to add question");
            return false;
        }
    }
}

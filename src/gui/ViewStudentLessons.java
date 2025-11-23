package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import skillforge.Course;
import skillforge.CourseManager;
import skillforge.Lesson;
import skillforge.Student;
import skillforge.UserManager;

public class ViewStudentLessons extends JPanel {

    public ViewStudentLessons(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager, Course c, Student s) {
        super(new BorderLayout(25, 25));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(null);

        add(GUIHelpers.getFormattedTitle(c.getTitle() + " Lessons"), BorderLayout.NORTH);

        LessonsTable lessonsTable = new LessonsTable(mainWindow, c.getLessons(), c, s);
        add(lessonsTable, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        bottomPanel.setBackground(null);

        bottomPanel.add(GUIHelpers.getFormattedButton("Back", (ActionEvent e) -> {
            mainWindow.setPanel(new ViewStudentCourses(mainWindow, userManager, courseManager, s));
        }));

        bottomPanel.add(GUIHelpers.getFormattedButton("Mark Completed", (ActionEvent e) -> {
            Lesson selectedLesson = lessonsTable.getSelected();
            if (selectedLesson == null) {
                return;
            }
            boolean ok = courseManager.markLessonCompleted(c.getCourseId(),
                    selectedLesson.getLessonId(), s.getUserId());
            JOptionPane.showMessageDialog(mainWindow, ok ? "Marked completed." : "Failed.");
            lessonsTable.refreshList(c.getLessons(), c, s);
        }));

        bottomPanel.add(GUIHelpers.getFormattedButton("Lesson Quiz", (ActionEvent e) -> {
            Lesson selectedLesson = lessonsTable.getSelected();
            if (selectedLesson == null) {
                return;
            }
            if (selectedLesson.getQuiz() == null) {
                JOptionPane.showMessageDialog(mainWindow, "No quiz for this lesson.");
                return;
            }
            int at = s.getAttempts(c.getCourseId(), selectedLesson.getLessonId());
                if (at >= 2) {
                    JOptionPane.showMessageDialog(mainWindow, "No attempts left for this lesson");
                    return;
                }
                mainWindow.setPanel(new SolveQuiz(mainWindow, userManager,
                        courseManager, c, selectedLesson, s, selectedLesson.getQuiz()));
        }));

        add(bottomPanel, BorderLayout.SOUTH);
    }

}

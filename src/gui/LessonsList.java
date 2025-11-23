package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import skillforge.Course;
import skillforge.CourseManager;
import skillforge.Instructor;
import skillforge.Lesson;
import skillforge.UserManager;


public class LessonsList extends JPanel {

    public LessonsList(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager, Instructor instructor, Course c) {
        super(new BorderLayout(25, 25));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(null);

        add(GUIHelpers.getFormattedTitle("Select Lesson"), BorderLayout.NORTH);
        
        LessonsTable lessonsTable = new LessonsTable(mainWindow, c.getLessons());
        add(lessonsTable, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 5, 15, 15));
        bottomPanel.setBackground(null);
        
        bottomPanel.add(GUIHelpers.getFormattedButton("New", (ActionEvent e) -> {
            mainWindow.setPanel(new AddLessonToCourse(mainWindow, instructor, userManager, courseManager, c));
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Create Quiz", (ActionEvent e) -> {
            Lesson selectedLesson = lessonsTable.getSelected();
            if (selectedLesson == null) return;
            mainWindow.setPanel(new AddQuizToLesson(mainWindow, instructor, userManager, courseManager, c, selectedLesson));
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Edit", (ActionEvent e) -> {
            Lesson selectedLesson = lessonsTable.getSelected();
            if (selectedLesson == null) return;
            mainWindow.setPanel(new UpdateLesson(mainWindow, instructor, userManager, courseManager, c, selectedLesson));
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Stats", (ActionEvent e) -> {
            Lesson selectedLesson = lessonsTable.getSelected();
            if (selectedLesson == null) return;
            LessonStats frame = new LessonStats(selectedLesson.getStatistics());
            frame.setLocationRelativeTo(mainWindow);
            frame.setVisible(true);
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Delete", (ActionEvent e) -> {
            Lesson selectedLesson = lessonsTable.getSelected();
            if (selectedLesson == null) return;
            int res = JOptionPane.showConfirmDialog(mainWindow, "Are you sure that you want to delete?");
            if (res != JOptionPane.YES_OPTION) return;
            boolean deleted = courseManager.deleteLesson(c.getCourseId(), selectedLesson.getLessonId(), instructor.getUserId());
            JOptionPane.showMessageDialog(mainWindow, deleted ? "Lesson deleted successfully" : "Failed to delete lesson");
            if (deleted) lessonsTable.refreshList(c.getLessons());
        }));

        bottomPanel.add(GUIHelpers.getFormattedButton("Back", ((e) -> {
            mainWindow.setPanel(new InstructorDashboard(mainWindow,
                    userManager, courseManager, instructor));
        })));

        add(bottomPanel, BorderLayout.SOUTH);
    }

}
package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import skillforge.Course;
import skillforge.Course.ApprovalStatus;
import skillforge.CourseManager;
import skillforge.Student;
import skillforge.UserManager;

public class BrowseCourses extends JPanel {

    public BrowseCourses(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager, Student student) {
        super(new BorderLayout(25, 25));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(null);

        add(GUIHelpers.getFormattedTitle("Browse Courses"), BorderLayout.NORTH);
        
        ArrayList<Course> courses = new ArrayList<>(courseManager.listAllCourses());
        courses.removeIf(c -> c.getApprovalStatus() != ApprovalStatus.APPROVED);

        CoursesTable coursesTable = new CoursesTable(mainWindow, courses);
        add(coursesTable, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        bottomPanel.setBackground(null);

        bottomPanel.add(GUIHelpers.getFormattedButton("Back", ((e) -> {
            mainWindow.setPanel(new StudentDashboard(mainWindow, userManager, courseManager, student));
        })));

        bottomPanel.add(GUIHelpers.getFormattedButton("Enroll", (ActionEvent e) -> {
            Course selectedCourse = coursesTable.getSelected();
            if (selectedCourse == null) {
                return;
            }
            boolean ok = courseManager.enrollStudent(
                    selectedCourse.getCourseId(),
                    student.getUserId()
            );
            JOptionPane.showMessageDialog(
                    mainWindow, ok ? "Enrolled successfully" : "Failed to enroll");
            mainWindow.setPanel(new BrowseCourses(mainWindow, userManager, courseManager, student));
        }));

        add(bottomPanel, BorderLayout.SOUTH);
    }

}

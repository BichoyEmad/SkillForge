package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import skillforge.CourseManager;
import skillforge.Student;
import skillforge.UserManager;

public class StudentDashboard extends JPanel {
    
    
    public StudentDashboard(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager, Student student) {
        super(new GridLayout(4, 1, 15, 15));
        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(75, 150, 75, 150));
        
        add(GUIHelpers.getFormattedTitle("Student Dashboard"));
        add(GUIHelpers.getFormattedButton("Browse Courses", (ActionEvent e) -> {
            mainWindow.setPanel(new BrowseCourses(mainWindow, userManager, courseManager, student)
            );
        }));

        add(GUIHelpers.getFormattedButton("View my Courses", (ActionEvent e) -> {
            mainWindow.setPanel(new ViewStudentCourses(mainWindow, userManager, courseManager, student));
        }));

        add(GUIHelpers.getFormattedButton("Logout", (ActionEvent e) -> {
            // Logout
        }));
    }
    
}

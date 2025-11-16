package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import skillforge.Course;
import skillforge.CourseManager;
import skillforge.Student;
import skillforge.UserManager;


public class ViewStudentCourses extends JPanel {


    public ViewStudentCourses(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager, Student student) {
        super(new BorderLayout(25, 25));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(null);

        add(GUIHelpers.getFormattedTitle("My Courses"), BorderLayout.NORTH);
        
        ArrayList<Course> courses = new ArrayList<>();
        for (String cid : student.getEnrolledCourses()) {
            Course c = courseManager.findCourseById(cid);
            if (c != null) {
                courses.add(c);
            }
        }
        
        CoursesTable coursesTable = new CoursesTable(mainWindow, courses);
        add(coursesTable, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        bottomPanel.setBackground(null);

        bottomPanel.add(GUIHelpers.getFormattedButton("Back", (e) -> {
            mainWindow.setPanel(new StudentDashboard(mainWindow, userManager, courseManager, student));
        }));

        bottomPanel.add(GUIHelpers.getFormattedButton("Lessons", (ActionEvent e) -> {
            Course selectedCourse = coursesTable.getSelected();
            if (selectedCourse == null) return;
            mainWindow.setPanel(new ViewStudentLessons(mainWindow, userManager, courseManager, selectedCourse, student));
        }));

        add(bottomPanel, BorderLayout.SOUTH);
    }

}
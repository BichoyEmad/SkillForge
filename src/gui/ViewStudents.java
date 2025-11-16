package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import skillforge.Course;
import skillforge.CourseManager;
import skillforge.Instructor;
import skillforge.Student;
import skillforge.UserManager;

public class ViewStudents extends JPanel {

    public ViewStudents(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager, Instructor instructor, Course c) {
        super(new BorderLayout(25, 25));
        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        List<Student> students = courseManager.getEnrolledStudents(c.getCourseId());

        add(GUIHelpers.getFormattedTitle("Students List"), BorderLayout.NORTH);
        StudentsTable studentssTable = new StudentsTable(mainWindow,
                new ArrayList<>(students));
        add(studentssTable, BorderLayout.CENTER);

        add(GUIHelpers.getFormattedButton("Back", (ActionEvent e) -> {
            mainWindow.setPanel(new InstructorDashboard(mainWindow,
                    userManager, courseManager, instructor));
        }), BorderLayout.SOUTH);
    }

}

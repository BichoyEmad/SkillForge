package gui;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import skillforge.Course;
import skillforge.CourseManager;
import skillforge.Instructor;
import skillforge.UserManager;


public class InstructorDashboard extends JPanel {
    
    private final CourseManager courseManager;
    private final Instructor instructor;

    public InstructorDashboard(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager, Instructor instructor) {
        super(new BorderLayout(25, 25));
        this.courseManager = courseManager;
        this.instructor = instructor;
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(null);

        add(GUIHelpers.getFormattedTitle("Instructor Courses"), BorderLayout.NORTH);
        
        CoursesTable coursesTable = new CoursesTable(mainWindow, getInstructorCourses());
        add(coursesTable, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 6, 15, 15));
        bottomPanel.setBackground(null);

        bottomPanel.add(GUIHelpers.getFormattedButton("New", (ActionEvent e) -> {
            mainWindow.setPanel(new CreateCourse(mainWindow, instructor, userManager, courseManager));
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Edit", (ActionEvent e) -> {
            if (coursesTable.getSelected() != null)
                mainWindow.setPanel(new UpdateCourse(mainWindow, instructor,
                        userManager, courseManager, coursesTable.getSelected()));
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Delete", (ActionEvent e) -> {
            if (coursesTable.getSelected() == null) return;
            Course c = coursesTable.getSelected();
            int res = JOptionPane.showConfirmDialog(mainWindow, "Are you sure that you want to delete?");
            if (res != JOptionPane.YES_OPTION) return;
            boolean deleted = courseManager.deleteCourse(c.getCourseId(), instructor.getUserId());
            JOptionPane.showMessageDialog(mainWindow, deleted ? "Course deleted successfully" : "Failed to delete course");
            if (deleted) coursesTable.refreshList(getInstructorCourses());
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Lessons", (ActionEvent e) -> {
            if (coursesTable.getSelected() == null) return;
            Course c = coursesTable.getSelected();
            mainWindow.setPanel(new LessonsList(mainWindow, userManager,
                    courseManager, instructor, c));
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Students", (ActionEvent e) -> {
            if (coursesTable.getSelected() == null) return;
            Course c = coursesTable.getSelected();
            mainWindow.setPanel(new ViewStudents(mainWindow, userManager,
                        courseManager, instructor, (Course) c));
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Logout", (ActionEvent e) -> {
            // logout
        }));

        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private ArrayList getInstructorCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        for (Course c : courseManager.listAllCourses()) {
            if (c.getInstructorId().equals(instructor.getUserId())) {
                courses.add(c);
            }
        }
        return courses;
    }

}
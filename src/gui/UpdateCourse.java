package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import skillforge.Course;
import skillforge.CourseManager;
import skillforge.Instructor;
import skillforge.UserManager;

public class UpdateCourse extends JPanel {

    public UpdateCourse(MainWindow mainWindow, Instructor instructor,
            UserManager userManager, CourseManager courseManager, Course c) {
        super(new BorderLayout(25, 25));
        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(135, 110, 135, 110));

        add(GUIHelpers.getFormattedTitle("Edit Course"), BorderLayout.NORTH);

        JPanel gridView = new JPanel(new GridLayout(3, 2, 15, 15));
        gridView.setBackground(null);

        gridView.add(GUIHelpers.getFormattedFormLabel("Title:"));
        JTextField inputTitle = GUIHelpers.getFormattedFormTextField();
        inputTitle.setText(c.getTitle());
        gridView.add(inputTitle);

        gridView.add(GUIHelpers.getFormattedFormLabel("Description:"));
        JTextField inputDesc = GUIHelpers.getFormattedFormTextField();
        inputDesc.setText(c.getDescription());
        gridView.add(inputDesc);

        gridView.add(GUIHelpers.getFormattedButton("Back", (ActionEvent e) -> {
            mainWindow.setPanel(new InstructorDashboard(mainWindow, userManager,
            courseManager, instructor));
        }));

        gridView.add(GUIHelpers.getFormattedButton("Save", (ActionEvent e) -> {
            String title = inputTitle.getText().trim();
            String description = inputDesc.getText().trim();

            if (title.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter title");
                return;
            }
            if (description.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter description");
                return;
            }
            if(courseManager.editCourse(c.getCourseId(), title, description, instructor.getUserId())) {
                JOptionPane.showMessageDialog(mainWindow, "Course edited successfully");
                mainWindow.setPanel(new InstructorDashboard(mainWindow,
                        userManager, courseManager, instructor));
            } else {
                JOptionPane.showMessageDialog(mainWindow, "Failed to edit course");
            }
        }));

        add(gridView, BorderLayout.CENTER);
    }

}

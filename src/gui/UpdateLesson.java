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
import skillforge.Lesson;
import skillforge.UserManager;

public class UpdateLesson extends JPanel {

    public UpdateLesson(MainWindow mainWindow, Instructor instructor,
            UserManager userManager, CourseManager courseManager, Course c, Lesson l) {
        super(new BorderLayout(25, 25));
        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(135, 110, 135, 110));

        add(GUIHelpers.getFormattedTitle("Edit to Course"), BorderLayout.NORTH);

        JPanel gridView = new JPanel(new GridLayout(3, 2, 15, 15));
        gridView.setBackground(null);

        gridView.add(GUIHelpers.getFormattedFormLabel("Title:"));
        JTextField inputTitle = GUIHelpers.getFormattedFormTextField();
        inputTitle.setText(l.getTitle());
        gridView.add(inputTitle);

        gridView.add(GUIHelpers.getFormattedFormLabel("Content:"));
        JTextField inputDesc = GUIHelpers.getFormattedFormTextField();
        inputDesc.setText(l.getContent());
        gridView.add(inputDesc);

        gridView.add(GUIHelpers.getFormattedButton("Back", (ActionEvent e) -> {
            mainWindow.setPanel(new LessonsList(mainWindow, userManager,
                    courseManager, instructor, c));
        }));

        gridView.add(GUIHelpers.getFormattedButton("Add", (ActionEvent e) -> {
            String title = inputTitle.getText().trim();
            String description = inputDesc.getText().trim();

            if (title.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter title");
                return;
            }
            if (description.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter content");
                return;
            }
            boolean edited = courseManager.editLesson(c.getCourseId(), l.getLessonId(), title, description, instructor.getUserId());
            if(edited) {
                JOptionPane.showMessageDialog(mainWindow, "Lesson edited successfully");
                mainWindow.setPanel(new LessonsList(mainWindow, userManager,
                    courseManager, instructor, c));
            } else {
                JOptionPane.showMessageDialog(mainWindow, "Failed to edit lesson");
            }
        }));

        add(gridView, BorderLayout.CENTER);
    }

}

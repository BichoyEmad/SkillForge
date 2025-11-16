package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import skillforge.CourseManager;
import skillforge.Instructor;
import skillforge.Student;
import skillforge.UserManager;

public class Signup extends JPanel {

    public Signup(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager, boolean studentSignup) {
        super(new BorderLayout(25, 25));
        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(100, 110, 100, 110));

        String title = (studentSignup ? "Student" : "Instructor") + " Signup";
        add(GUIHelpers.getFormattedTitle(title), BorderLayout.NORTH);

        JPanel gridView = new JPanel(new GridLayout(4, 2, 15, 15));
        gridView.setBackground(null);

        gridView.add(GUIHelpers.getFormattedFormLabel("Username:"));
        JTextField inputUsername = GUIHelpers.getFormattedFormTextField();
        gridView.add(inputUsername);

        gridView.add(GUIHelpers.getFormattedFormLabel("Email:"));
        JTextField inputEmail = GUIHelpers.getFormattedFormTextField();
        gridView.add(inputEmail);

        gridView.add(GUIHelpers.getFormattedFormLabel("Password:"));
        JPasswordField inputPassword = GUIHelpers.getFormattedFormPasswordField();
        gridView.add(inputPassword);

        gridView.add(GUIHelpers.getFormattedButton("Back", (ActionEvent e) -> {
            mainWindow.setPanel(new WelcomePanel(mainWindow, userManager, courseManager));
        }));

        gridView.add(GUIHelpers.getFormattedButton("Signup", (ActionEvent e) -> {
            String username = inputUsername.getText().trim().toLowerCase();
            String email = inputEmail.getText().trim();
            String password = String.valueOf(inputPassword.getPassword());

            if (username.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter your username");
                return;
            }
            if (email.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter your email");
                return;
            }
            if (password.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter your password");
                return;
            }
            if (studentSignup) {
                Student signupStudent = userManager.signupStudent(username, email, password);
                if (signupStudent != null) {
                    mainWindow.setPanel(new StudentDashboard(mainWindow,
                            userManager, courseManager, signupStudent));
                } else {
                    JOptionPane.showMessageDialog(mainWindow, "Failed to create account (maybe email exists/invalid).");
                }
            } else {
                Instructor instructor = userManager.signupInstructor(username,
                        email, password);
                if (instructor != null) {
                    mainWindow.setPanel(new InstructorDashboard(mainWindow,
                            userManager, courseManager, instructor));
                } else {
                    JOptionPane.showMessageDialog(mainWindow, "Failed to create account (maybe email exists/invalid).");
                }
            }

        }));

        add(gridView, BorderLayout.CENTER);
    }

}

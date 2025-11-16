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
import skillforge.User;
import skillforge.UserManager;


public class Login extends JPanel {
    
    public Login(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager) {
        super(new BorderLayout(25, 25));
        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(135, 110, 135, 110));
        
        add(GUIHelpers.getFormattedTitle("Welcome"), BorderLayout.NORTH);
        
        JPanel gridView = new JPanel(new GridLayout(3, 2, 15, 15));
        gridView.setBackground(null);
        
        gridView.add(GUIHelpers.getFormattedFormLabel("Email:"));
        JTextField inputEmail = GUIHelpers.getFormattedFormTextField();
        gridView.add(inputEmail);
        
        gridView.add(GUIHelpers.getFormattedFormLabel("Password:"));
        JPasswordField inputPassword = GUIHelpers.getFormattedFormPasswordField();
        gridView.add(inputPassword);
        
        gridView.add(GUIHelpers.getFormattedButton("Back", (ActionEvent e) -> {
            mainWindow.setPanel(new WelcomePanel(mainWindow, userManager, courseManager));
        }));
        
        gridView.add(GUIHelpers.getFormattedButton("Login", (ActionEvent e) -> {
            String email = inputEmail.getText().trim().toLowerCase();
            String password = String.valueOf(inputPassword.getPassword());
            
            if (email.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter your email");
                return;
            }
            if (password.isBlank()) {
                JOptionPane.showMessageDialog(mainWindow, "Please enter your password");
                return;
            }
             User logged = userManager.login(email, password);
            if (logged == null) {
                JOptionPane.showMessageDialog(mainWindow, "Login failed.");
            } else {
                if (logged instanceof Instructor) {
                    mainWindow.setPanel(new InstructorDashboard(mainWindow,
                            userManager, courseManager, (Instructor) logged));
                } else if (logged instanceof Student) {
                    mainWindow.setPanel(new StudentDashboard(mainWindow,
                            userManager, courseManager, (Student) logged));
                }
            }
        }));
        
        add(gridView, BorderLayout.CENTER);
    }
    
}

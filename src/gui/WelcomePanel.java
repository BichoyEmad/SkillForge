package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import skillforge.CourseManager;
import skillforge.UserManager;

public class WelcomePanel extends JPanel {

    public WelcomePanel(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager) {
        super(new GridLayout(5, 1, 15, 15));
        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(65, 150, 90, 150));
       
        add(GUIHelpers.getFormattedTitle("SkillForge"));

        add(GUIHelpers.getFormattedButton("Signup Student", (ActionEvent e) -> {
             mainWindow.setPanel(new Signup(mainWindow, userManager, courseManager, "Student"));
        }));
        add(GUIHelpers.getFormattedButton("Signup Instructor", (ActionEvent e) -> {
            mainWindow.setPanel(new Signup(mainWindow, userManager, courseManager, "Instructor"));
        }));
        add(GUIHelpers.getFormattedButton("Signup Admin", (ActionEvent e) -> {
            mainWindow.setPanel(new Signup(mainWindow, userManager, courseManager, "Admin"));
        }));
        add(GUIHelpers.getFormattedButton("Login", (ActionEvent e) -> {
             mainWindow.setPanel(new Login(mainWindow, userManager, courseManager));
        }));
    }

}

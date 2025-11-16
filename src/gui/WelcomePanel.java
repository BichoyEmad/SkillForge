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
        super(new GridLayout(4, 1, 15, 15));
        setBackground(null);
        setBorder(BorderFactory.createEmptyBorder(75, 150, 110, 150));
       
        add(GUIHelpers.getFormattedTitle("SkillForge"));

        add(GUIHelpers.getFormattedButton("Signup Student", (ActionEvent e) -> {
             mainWindow.setPanel(new Signup(mainWindow, userManager, courseManager, true));
        }));
        add(GUIHelpers.getFormattedButton("Signup Instructor", (ActionEvent e) -> {
            mainWindow.setPanel(new Signup(mainWindow, userManager, courseManager, false));
        }));
        add(GUIHelpers.getFormattedButton("Login", (ActionEvent e) -> {
             mainWindow.setPanel(new Login(mainWindow, userManager, courseManager));
        }));
    }

}

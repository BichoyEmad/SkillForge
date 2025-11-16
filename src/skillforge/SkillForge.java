/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package skillforge;

import gui.MainWindow;
import gui.WelcomePanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class SkillForge {

    public static void main(String[] args) {
        UserManager um = new UserManager();
        CourseManager cm = new CourseManager(um);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.err.println("Cannot set look and feel to cross platform");
        } finally {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setPanel(new WelcomePanel(mainWindow, um, cm));
            mainWindow.setVisible(true);
        }
    }
    
}

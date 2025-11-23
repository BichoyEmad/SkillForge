package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import skillforge.Admin;
import skillforge.Course;
import skillforge.CourseManager;
import skillforge.UserManager;

public class AdminDashboard extends JPanel {
    
    public AdminDashboard(MainWindow mainWindow, UserManager userManager,
            CourseManager courseManager, Admin admin) {
         super(new BorderLayout(25, 25));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(null);

        add(GUIHelpers.getFormattedTitle("Admin Dashboard"), BorderLayout.NORTH);
        
        CoursesTable coursesTable = new CoursesTable(mainWindow, courseManager.listAllCourses());
        add(coursesTable, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 15, 15));
        bottomPanel.setBackground(null);

        bottomPanel.add(GUIHelpers.getFormattedButton("Approve Course", (ActionEvent e) -> {
            if (coursesTable.getSelected().getApprovalStatus() != Course.ApprovalStatus.PENDING) {
                JOptionPane.showMessageDialog(mainWindow, "Course already handled");
            } else {
                boolean approved = courseManager.approveCourse(coursesTable.getSelected().getCourseId(), true);
                if (approved) {
                    JOptionPane.showMessageDialog(mainWindow, "Course approved successfully");
                } else {
                    JOptionPane.showMessageDialog(mainWindow, "Operation failed");
                }
                mainWindow.setPanel(new AdminDashboard(mainWindow, userManager, courseManager, admin));
            }
        }));
        
        bottomPanel.add(GUIHelpers.getFormattedButton("Reject Course", (ActionEvent e) -> {
            if (coursesTable.getSelected().getApprovalStatus() != Course.ApprovalStatus.PENDING) {
                JOptionPane.showMessageDialog(mainWindow, "Course already handled");
            } else {
                boolean rejected = courseManager.approveCourse(coursesTable.getSelected().getCourseId(), false);
                if (rejected) {
                    JOptionPane.showMessageDialog(mainWindow, "Course rejected successfully");
                } else {
                    JOptionPane.showMessageDialog(mainWindow, "Operation failed");
                }
                mainWindow.setPanel(new AdminDashboard(mainWindow, userManager, courseManager, admin));
            }
        }));
           
        bottomPanel.add(GUIHelpers.getFormattedButton("Logout", (ActionEvent e) -> {
            mainWindow.setPanel(new WelcomePanel(mainWindow, userManager, courseManager));
        }));

        add(bottomPanel, BorderLayout.SOUTH);
    }
    
}

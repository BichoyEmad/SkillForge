package gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import skillforge.Course;


public class CoursesTable extends JScrollPane {
    
    private JTable table;
    private final List<Course> courses;
    private final MainWindow mainWindow;
    private static final String[] HEADER = {"Course ID", "Title", "Description", "Status"};
    
    public CoursesTable(MainWindow mainWindow, List<Course> courses) {
        super();
        this.mainWindow = mainWindow;
        this.courses = courses;
        setBackground(null);
        String[][] tableData = new String[courses.size()][3];
        for (int i=0;i<courses.size();i++) {
            Course s = courses.get(i);
            tableData[i] = new String[] {String.valueOf(s.getCourseId()),
                s.getTitle(), s.getDescription(), s.getApprovalStatus().toString()};
        }
        
        table = GUIHelpers.getFormattedTable(tableData, HEADER);
        setViewportView(table);
    }
    
    public Course getSelected() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(mainWindow, "No course was selected");
            return null;
        }
        return courses.get(table.getSelectedRow());
    }
    
    public void refreshList(List<Course> courses) {
        String[][] tableData = new String[courses.size()][3];
        for (int i=0;i<courses.size();i++) {
            Course s = courses.get(i);
            tableData[i] = new String[] {String.valueOf(s.getCourseId()),
                s.getTitle(), s.getDescription(), s.getApprovalStatus().toString()};
        }
        
        table = GUIHelpers.getFormattedTable(tableData, HEADER);
        setViewportView(table);
    }
    
}

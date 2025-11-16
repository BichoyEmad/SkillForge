package gui;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import skillforge.Student;


public class StudentsTable extends JScrollPane {
    
    private final JTable table;
    private final ArrayList<Student> students;
    private final MainWindow mainWindow;
    
    public StudentsTable(MainWindow mainWindow, ArrayList<Student> students) {
        super();
        this.mainWindow = mainWindow;
        this.students = students;
        setBackground(null);
        String[][] tableData = new String[students.size()][3];
        for (int i=0;i<students.size();i++) {
            Student s = students.get(i);
            tableData[i] = new String[] {String.valueOf(s.getUserId()),
                s.getUsername(), s.getEmail()};
        }
        String[] header = {"Student ID", "Username", "Email"};
        
        table = GUIHelpers.getFormattedTable(tableData, header);
        setViewportView(table);
    }
    
    public Student getSelected() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(mainWindow, "No student was selected");
            return null;
        }
        return students.get(table.getSelectedRow());
    }
    
}

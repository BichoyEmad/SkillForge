package gui;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import skillforge.Course;
import skillforge.Lesson;
import skillforge.Student;


public class LessonsTable extends JScrollPane {
    
    private JTable table;
    private final List<Lesson> lessons;
    private final MainWindow mainWindow;
    
    public LessonsTable(MainWindow mainWindow, List<Lesson> lessons) {
        super();
        this.mainWindow = mainWindow;
        this.lessons = lessons;
        setBackground(null);
        String[][] tableData = new String[lessons.size()][4];
        for (int i=0;i<lessons.size();i++) {
            Lesson s = lessons.get(i);
            tableData[i] = new String[] {String.valueOf(s.getLessonId()),
                s.getTitle(), s.getContent()};
        }
        
        table = GUIHelpers.getFormattedTable(tableData, new String[] {"Lesson ID", "Title", "Content"});
        setViewportView(table);
    }
    
    public LessonsTable(MainWindow mainWindow, List<Lesson> lessons, Course c, Student student) {
        super();
        this.mainWindow = mainWindow;
        this.lessons = lessons;
        setBackground(null);
        String[][] tableData = new String[lessons.size()][5];
        for (int i=0;i<lessons.size();i++) {
            Lesson s = lessons.get(i);
            tableData[i] = new String[] {String.valueOf(s.getLessonId()),
                s.getTitle(), s.getContent(),
                String.valueOf(student.isLessonCompleted(c.getCourseId(), s.getLessonId()))};
        }
        
        table = GUIHelpers.getFormattedTable(tableData, new String[] {"Lesson ID", "Title", "Content", "Completed"});
        setViewportView(table);
    }
    
    public Lesson getSelected() {
        if (table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(mainWindow, "No lesson was selected");
            return null;
        }
        return lessons.get(table.getSelectedRow());
    }
    
    public void refreshList(List<Lesson> lessons) {
        String[][] tableData = new String[lessons.size()][4];
        for (int i=0;i<lessons.size();i++) {
            Lesson s = lessons.get(i);
            tableData[i] = new String[] {String.valueOf(s.getLessonId()),
                s.getTitle(), s.getContent()};
        }
        
        table = GUIHelpers.getFormattedTable(tableData, new String[] {"Lesson ID", "Title", "Content"});
        setViewportView(table);
    }
    
    public void refreshList(List<Lesson> lessons, Course c, Student student) {
        String[][] tableData = new String[lessons.size()][5];
        for (int i=0;i<lessons.size();i++) {
            Lesson s = lessons.get(i);
            tableData[i] = new String[] {String.valueOf(s.getLessonId()),
                s.getTitle(), s.getContent(),
                String.valueOf(student.isLessonCompleted(c.getCourseId(), s.getLessonId()))};
        }
        
        table = GUIHelpers.getFormattedTable(tableData, new String[] {"Lesson ID", "Title", "Content", "Completed"});
        setViewportView(table);
    }
    
}

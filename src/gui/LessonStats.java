package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

public class LessonStats extends JFrame {

    public LessonStats(Map<String, Object> stats) {
        super("Lesson Analytics - " + stats.get("title"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(0, 1));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int totalStudents = (int) stats.get("studentsAttempted");
        double avgPercent = (double) stats.get("averagePercent");
        double avgBestPercent = (double) stats.get("averageBestPercent");

        topPanel.add(new JLabel("Lesson: " + stats.get("title")));
        topPanel.add(new JLabel("Students attempted: " + totalStudents));
        topPanel.add(new JLabel(String.format("Average percent: %.2f%%", avgPercent)));
        topPanel.add(new JLabel(String.format("Average best percent: %.2f%%", avgBestPercent)));

        add(topPanel, BorderLayout.NORTH);

        JPanel chartsPanel = new JPanel(new GridLayout(1, 3));

        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        barDataset.addValue(avgPercent, "Average %", "All Students");
        JFreeChart barChart = ChartFactory.createBarChart("Average Percent", "Category", "Percent", barDataset);
        CategoryPlot plot = barChart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 100);

        chartsPanel.add(new ChartPanel(barChart));

        DefaultCategoryDataset barDataset2 = new DefaultCategoryDataset();
        barDataset2.addValue(avgBestPercent, "Best %", "All Students");
        JFreeChart barChart2 = ChartFactory.createBarChart("Average Best Percent", "Category", "Percent", barDataset2);
        CategoryPlot plot2 = barChart2.getCategoryPlot();
        NumberAxis rangeAxis2 = (NumberAxis) plot2.getRangeAxis();
        rangeAxis2.setRange(0, 100);
        chartsPanel.add(new ChartPanel(barChart2));

        add(chartsPanel, BorderLayout.CENTER);
    }

}

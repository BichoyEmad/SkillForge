package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class GUIHelpers {

    public static JButton getFormattedButton(String text, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 16));
        btn.addActionListener(listener);
        btn.setPreferredSize(new Dimension(120, 50));
        return btn;
    }

    public static JLabel getFormattedTitle(String text) {
        JLabel title = new JLabel(text);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        return title;
    }

    public static JLabel getFormattedFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    public static JTextField getFormattedFormTextField() {
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        return textField;
    }

    public static JPasswordField getFormattedFormPasswordField() {
        JPasswordField textField = new JPasswordField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        return textField;
    }

    public static JTable getFormattedTable(String[][] data, String[] header) {
        JTable table = new JTable(data, header);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader tableHeader = table.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));
        tableHeader.setBackground(Color.LIGHT_GRAY);
        tableHeader.setForeground(Color.BLACK);

        table.setRowHeight(25);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);

        return table;
    }
    
    public static JComboBox getFormattedComboBox(String[] options) {
        JComboBox comboBox = new JComboBox(options);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        return comboBox;
    }

}

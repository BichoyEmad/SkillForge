package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import skillforge.Certificate;

public class CertificateFrame extends JFrame {

    public CertificateFrame(List<Certificate> certificates) {
        super("Certificates");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Certificate cert : certificates) {
            JPanel certPanel = createCertificatePanel(cert);
            mainPanel.add(certPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }

    private JPanel createCertificatePanel(Certificate cert) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 150));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(Color.BLACK, 2, true));

        // Certificate info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Certificate of Completion", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel courseIdLabel = new JLabel("Course ID: " + cert.getCourseId());
        courseIdLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        courseIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel certIdLabel = new JLabel("Certificate ID: " + cert.getCertificateId());
        certIdLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        certIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dateLabel = new JLabel("Issued On: " + cert.getIssueDate());
        dateLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(courseIdLabel);
        infoPanel.add(certIdLabel);
        infoPanel.add(dateLabel);

        panel.add(infoPanel, BorderLayout.CENTER);

        return panel;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
import java.time.LocalDate;

public class Certificate {
    private String certificateId;
    private String studentId;
    private String courseId;
    private String issueDate;

    public Certificate() {}
    public Certificate(String studentId, String courseId) {
        this.certificateId = java.util.UUID.randomUUID().toString();
        this.studentId = studentId;
        this.courseId = courseId;
        this.issueDate = LocalDate.now().toString();
    }

    public String getCertificateId() { return certificateId; }
    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
    public String getIssueDate() { return issueDate; }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
// Lesson.java
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private List<String> resources = new ArrayList<>();

    public Lesson() {}

    public Lesson(String title, String content) {
        this.lessonId = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
    }

    public String getLessonId() { return lessonId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public List<String> getResources() { return resources; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void addResource(String r) { resources.add(r); }
}


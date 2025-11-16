package skillforge;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.*;

public class CourseManager {
    private List<Course> courses;
    private final String coursesFile = "courses.json";
    private UserManager userManager;

    public CourseManager(UserManager userManager) {
        this.userManager = userManager;
        courses = loadCourses();
    }

    private List<Course> loadCourses() {
        try {
            List<Course> list = JsonUtil.readList("courses.json", new TypeToken<List<Course>>() {}.getType());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveCourses() {
        JsonUtil.writeList("courses.json", courses);
    }

    public Course createCourse(String title, String description, String instructorId) {
        Instructor instr = null;
        User u = userManager.findById(instructorId);
        if (u instanceof Instructor) instr = (Instructor) u;
        if (instr == null) return null;
        Course c = new Course(title, description, instructorId);
        courses.add(c);
        instr.addCourseCreated(c.getCourseId());
        userManager.saveAll();
        saveCourses();
        return c;
    }

    public boolean editCourse(String courseId, String title, String description, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null) return false;
        if (!c.getInstructorId().equals(instructorId)) return false; // only owner
        c.setTitle(title);
        c.setDescription(description);
        saveCourses();
        return true;
    }

    public boolean deleteCourse(String courseId, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null) return false;
        if (!c.getInstructorId().equals(instructorId)) return false;
        courses.remove(c);
        User u = userManager.findById(instructorId);
        if (u instanceof Instructor) ((Instructor)u).removeCourseCreated(courseId);
        userManager.saveAll();
        saveCourses();
        return true;
    }

    public Course findCourseById(String id) {
        for (Course c : courses) if (c.getCourseId().equals(id)) return c;
        return null;
    }

    public List<Course> listAllCourses() { return courses; }

    public Lesson addLesson(String courseId, String title, String content, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null) return null;
        if (!c.getInstructorId().equals(instructorId)) return null;
        Lesson l = new Lesson(title, content);
        c.addLesson(l);
        saveCourses();
        return l;
    }

    public boolean editLesson(String courseId, String lessonId, String title, String content, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null) return false;
        if (!c.getInstructorId().equals(instructorId)) return false;
        Lesson l = c.getLessonById(lessonId);
        if (l == null) return false;
        l.setTitle(title);
        l.setContent(content);
        saveCourses();
        return true;
    }

    public boolean deleteLesson(String courseId, String lessonId, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null) return false;
        if (!c.getInstructorId().equals(instructorId)) return false;
        c.removeLesson(lessonId);
        saveCourses();
        return true;
    }

    public boolean enrollStudent(String courseId, String studentId) {
        Course c = findCourseById(courseId);
        if (c == null) return false;
        User u = userManager.findById(studentId);
        if (!(u instanceof Student)) return false;
        If(c.getStudents(). contains(student.id))return false;

        If(
                c.enrollStudent(studentId);
        ((Student)u).enroll(courseId);
        userManager.saveAll();
        saveCourses();
        return true;
    }

    public List<Student> getEnrolledStudents(String courseId) {
        Course c = findCourseById(courseId);
        if (c == null) return new ArrayList<>();
        List<Student> res = new ArrayList<>();
        for (String sid : c.getStudents()) {
            User u = userManager.findById(sid);
            if (u instanceof Student) res.add((Student) u);
        }
        return res;
    }

    public boolean markLessonCompleted(String courseId, String lessonId, String studentId) {
        Course c = findCourseById(courseId);
        if (c == null) return false;
        Lesson l = c.getLessonById(lessonId);
        if (l == null) return false;
        User u = userManager.findById(studentId);
        if (!(u instanceof Student)) return false;
        ((Student)u).markLessonCompleted(courseId, lessonId);
        userManager.saveAll();
        return true;
    }
}
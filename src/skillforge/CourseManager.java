/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.*;

public class CourseManager {
    private List<Course> courses;
    private final String coursesFile = "courses.json";
    private UserManager userManager;

    private static final int MAX_ATTEMPTS = 2;
    private static final double PASS_PERCENT = 50.0;

    public CourseManager(UserManager userManager) {
        this.userManager = userManager;
        courses = loadCourses();
    }

    private List<Course> loadCourses() {
        try {
            List<Course> list = JSonUtil.readList(coursesFile, new TypeToken<List<Course>>() {}.getType());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveCourses() {
        JSonUtil.writeList(coursesFile, courses);
    }

    // Course management
    public Course createCourse(String title, String description, String instructorId) {
        User u = userManager.findById(instructorId);
        if (!(u instanceof Instructor)) return null;
        Course c = new Course(title, description, instructorId);
        courses.add(c);
        ((Instructor)u).addCourseCreated(c.getCourseId());
        userManager.saveAll();
        saveCourses();
        return c;
    }

    public boolean editCourse(String courseId, String title, String description, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null || !c.getInstructorId().equals(instructorId)) return false;
        c.setTitle(title);
        c.setDescription(description);
        saveCourses();
        return true;
    }

    public boolean deleteCourse(String courseId, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null || !c.getInstructorId().equals(instructorId)) return false;
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

    public List<Course> listApprovedCourses() {
        List<Course> res = new ArrayList<>();
        for (Course c : courses) if (c.getApprovalStatus() == Course.ApprovalStatus.APPROVED) res.add(c);
        return res;
    }

    public boolean approveCourse(String courseId, boolean approve) {
        Course c = findCourseById(courseId);
        if (c == null) return false;
        c.setApprovalStatus(approve ? Course.ApprovalStatus.APPROVED : Course.ApprovalStatus.REJECTED);
        saveCourses();
        return true;
    }

    // Lesson management
    public Lesson addLesson(String courseId, String title, String content, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null || !c.getInstructorId().equals(instructorId)) return null;
        Lesson l = new Lesson(title, content);
        c.addLesson(l);
        saveCourses();
        return l;
    }

    public boolean editLesson(String courseId, String lessonId, String title, String content, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null || !c.getInstructorId().equals(instructorId)) return false;
        Lesson l = c.getLessonById(lessonId);
        if (l == null) return false;
        l.setTitle(title);
        l.setContent(content);
        saveCourses();
        return true;
    }

    public boolean deleteLesson(String courseId, String lessonId, String instructorId) {
        Course c = findCourseById(courseId);
        if (c == null || !c.getInstructorId().equals(instructorId)) return false;
        c.removeLesson(lessonId);
        saveCourses();
        return true;
    }

    public boolean addQuizToLesson(String courseId, String lessonId, Quiz quiz, String instructorId){
        Course c = findCourseById(courseId);
        if (c == null) return false;
        if (!c.getInstructorId().equals(instructorId)) return false;
        Lesson l = c.getLessonById(lessonId);
        if (l == null) return false;
        l.setQuiz(quiz);
        saveCourses();
        return true;
    }

    // Enrollment
    public boolean enrollStudent(String courseId, String studentId) {
        Course c = findCourseById(courseId);
        if (c == null) return false;
        User u = userManager.findById(studentId);
        if (!(u instanceof Student)) return false;
        if (c.getStudents().contains(studentId)) return false;

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

    // Mark lesson completed (manual)
    public boolean markLessonCompleted(String courseId, String lessonId, String studentId) {
        Course c = findCourseById(courseId);
        if (c == null) return false;
        Lesson l = c.getLessonById(lessonId);
        if (l == null) return false;
        User u = userManager.findById(studentId);
        if (!(u instanceof Student)) return false;
        ((Student)u).markLessonCompleted(courseId, lessonId);
        userManager.saveAll();
        saveCourses();
        return true;
    }

    // Take quiz
    public String takeQuiz(String courseId, String lessonId, String studentId, Map<Integer,Integer> answers){
        Course c = findCourseById(courseId);
        if (c==null) return null;
        Lesson l = c.getLessonById(lessonId);
        if (l==null || l.getQuiz()==null) return null;
        User u = userManager.findById(studentId);
        if (!(u instanceof Student)) return null;
        Student st = (Student) u;

        int attempts = st.getAttempts(courseId, lessonId);
        if (attempts >= MAX_ATTEMPTS) {
            System.out.println("You have used all attempts for this quiz.");
            return null;
        }

        // grade
        List<Question> qs = l.getQuiz().getQuestions();
        int correct = 0;
        for (int i=0;i<qs.size();i++){
            if (answers.getOrDefault(i, -1) == qs.get(i).getCorrectOptionIndex()) correct++;
        }
        double percent = (correct * 100.0) / Math.max(1, qs.size());

        // record attempts and best
        st.incrementAttempts(courseId, lessonId);
        st.updateBestScore(courseId, lessonId, (int)Math.round(percent));

        // record in lesson statistics
        l.recordStudentQuizResult(studentId, correct, qs.size());

        // display
        StringBuilder bd = new StringBuilder();
        bd.append("Result for lesson '" + l.getTitle() + "': " + correct + "/" + qs.size()+"\n");
        bd.append("Percentage: " + String.format("%.2f", percent) + "%"+"\n");
        boolean passedNow = percent >= PASS_PERCENT;
        int best = st.getBestScore(courseId, lessonId);
        bd.append("Passed this attempt: " + (passedNow ? "YES" : "NO") + " | Your best: " + best + "%"+"\n");

        if (best >= PASS_PERCENT) st.markLessonCompleted(courseId, lessonId);

        // check all lessons passed
        boolean allPassed = true;
        for (Lesson lesson : c.getLessons()){
            if (st.getBestScore(courseId, lesson.getLessonId()) < PASS_PERCENT) { allPassed = false; break; }
        }
        if (allPassed) {
            boolean alreadyHas = false;
            for (Certificate ct : st.getCertificates()) if (ct.getCourseId().equals(courseId)) { alreadyHas = true; break; }
            if (!alreadyHas) {
                Certificate cert = new Certificate(st.getUserId(), courseId);
                st.addCertificate(cert);
                c.addCertificate(st.getUserId(), cert);
                bd.append("🎉 Congratulations! Certificate generated: " + cert.getCertificateId());
            }
        }

        userManager.saveAll();
        saveCourses();
        return bd.toString();
    }

    // Statistics: per course
    public Map<String,Object> getCourseStatistics(String courseId) {
        Course c = findCourseById(courseId);
        if (c == null) return new HashMap<>();
        Map<String,Object> out = new HashMap<>();
        out.put("courseId", c.getCourseId());
        out.put("title", c.getTitle());
        List<Map<String,Object>> lessonsStats = new ArrayList<>();
        for (Lesson l : c.getLessons()) lessonsStats.add(l.getStatistics());
        out.put("lessons", lessonsStats);
        return out;
    }
}


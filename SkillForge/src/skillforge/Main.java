/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforge;

/**
 *
 * @author root
 */
// Main.java
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserManager um = new UserManager();
        CourseManager cm = new CourseManager(um);

        // مثال بسيط للتشغيل في الكونسول
        Scanner in = new Scanner(System.in);
        System.out.println("Simple SkillForge Backend Demo");
        System.out.println("1) signup student  2) signup instructor  3) login  0) exit");

        while (true) {
            System.out.print("> ");
            String cmd = in.nextLine().trim();
            if (cmd.equals("0")) break;
            if (cmd.equals("1")) {
                System.out.print("username: "); String u = in.nextLine();
                System.out.print("email: "); String e = in.nextLine();
                System.out.print("password: "); String p = in.nextLine();
                boolean ok = um.signupStudent(u,e,p);
                System.out.println(ok ? "Student created." : "Failed to create student (maybe email exists/invalid).");
            } else if (cmd.equals("2")) {
                System.out.print("username: "); String u = in.nextLine();
                System.out.print("email: "); String e = in.nextLine();
                System.out.print("password: "); String p = in.nextLine();
                boolean ok = um.signupInstructor(u,e,p);
                System.out.println(ok ? "Instructor created." : "Failed to create instructor.");
            } else if (cmd.equals("3")) {
                System.out.print("email: "); String e = in.nextLine();
                System.out.print("password: "); String p = in.nextLine();
                User logged = um.login(e,p);
                if (logged == null) { System.out.println("Login failed."); continue; }
                System.out.println("Logged in as: " + logged.getUsername() + " (" + logged.getRole() + ")");
                if (logged instanceof Instructor) {
                    Instructor instr = (Instructor) logged;
                    System.out.println("a) create course  b) list my courses  c) add lesson to course d) view students e) logout");
                    String sub = in.nextLine().trim();
                    if (sub.equals("a")) {
                        System.out.print("title: "); String t = in.nextLine();
                        System.out.print("desc: "); String d = in.nextLine();
                        Course c = cm.createCourse(t,d,instr.getUserId());
                        System.out.println("Created courseId: " + (c!=null ? c.getCourseId() : "failed"));
                    } else if (sub.equals("b")) {
                        List<Course> list = cm.listAllCourses();
                        for (Course c : list) {
                            if (c.getInstructorId().equals(instr.getUserId())) {
                                System.out.println(c.getCourseId() + " | " + c.getTitle());
                            }
                        }
                    } else if (sub.equals("c")) {
                        System.out.print("courseId: "); String cid = in.nextLine();
                        System.out.print("lesson title: "); String lt = in.nextLine();
                        System.out.print("lesson content: "); String lc = in.nextLine();
                        Lesson l = cm.addLesson(cid, lt, lc, instr.getUserId());
                        System.out.println(l != null ? "Lesson added: " + l.getLessonId() : "Failed");
                    } else if (sub.equals("d")) {
                        System.out.print("courseId: "); String cid = in.nextLine();
                        var students = cm.getEnrolledStudents(cid);
                        for (var s : students) System.out.println(s.getUserId()+" | "+s.getUsername());
                    }
                } else if (logged instanceof Student) {
                    Student st = (Student) logged;
                    System.out.println("a) browse courses  b) enroll c) view my courses d) mark lesson e) logout");
                    String sub = in.nextLine().trim();
                    if (sub.equals("a")) {
                        var list = cm.listAllCourses();
                        for (Course c : list) System.out.println(c.getCourseId()+" | "+c.getTitle());
                    } else if (sub.equals("b")) {
                        System.out.print("courseId: "); String cid = in.nextLine();
                        boolean ok = cm.enrollStudent(cid, st.getUserId());
                        System.out.println(ok ? "Enrolled." : "Failed to enroll.");
                    } else if (sub.equals("c")) {
                        for (String cid : st.getEnrolledCourses()) {
                            Course c = cm.findCourseById(cid);
                            if (c != null) System.out.println(cid+" | "+c.getTitle());
                        }
                    } else if (sub.equals("d")) {
                        System.out.print("courseId: "); String cid = in.nextLine();
                        System.out.print("lessonId: "); String lid = in.nextLine();
                        boolean ok = cm.markLessonCompleted(cid, lid, st.getUserId());
                        System.out.println(ok ? "Marked completed." : "Failed.");
                    }
                }
            } else {
                System.out.println("unknown command");
            }
        }
        in.close();
        System.out.println("bye");
    }
}

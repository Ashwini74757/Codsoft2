import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    int enrolledStudents;
    String schedule;

    Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = 0;
        this.schedule = schedule;
    }

    boolean isAvailable() {
        return enrolledStudents < capacity;
    }

    void enroll() {
        if (isAvailable()) {
            enrolledStudents++;
        }
    }

    void drop() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }

    @Override
    public String toString() {
        return String.format("Course Code: %s\nTitle: %s\nDescription: %s\nCapacity: %d\nEnrolled: %d\nSchedule: %s",
                courseCode, title, description, capacity, enrolledStudents, schedule);
    }
}

class Student {
    String studentId;
    String name;
    List<Course> registeredCourses;

    Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    void registerCourse(Course course) {
        if (course.isAvailable()) {
            registeredCourses.add(course);
            course.enroll();
            System.out.println("Successfully registered for " + course.title);
        } else {
            System.out.println("Course " + course.title + " is full.");
        }
    }

    void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.drop();
            System.out.println("Successfully dropped " + course.title);
        } else {
            System.out.println("You are not registered for " + course.title);
        }
    }

    void listRegisteredCourses() {
        if (registeredCourses.isEmpty()) {
            System.out.println("No registered courses.");
        } else {
            System.out.println("Registered Courses:");
            for (Course course : registeredCourses) {
                System.out.println(course.title);
            }
        }
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Create some courses
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CS101", "Introduction to Computer Science", "Basic concepts of programming.", 3, "Mon-Wed-Fri 10:00-11:00 AM"));
        courses.add(new Course("MATH101", "Calculus I", "Introduction to calculus.", 2, "Tue-Thu 11:00 AM-12:30 PM"));
        courses.add(new Course("ENG101", "English Literature", "Study of English literature.", 5, "Mon-Wed 1:00-2:30 PM"));

        // Create a student
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();
        Student student = new Student(studentId, studentName);

        int choice;
        do {
            System.out.println("\n1. List Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. List Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Courses:");
                    for (Course course : courses) {
                        System.out.println(course);
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.print("Enter the course code to register: ");
                    String registerCode = scanner.nextLine();
                    Course courseToRegister = findCourse(courses, registerCode);
                    if (courseToRegister != null) {
                        student.registerCourse(courseToRegister);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the course code to drop: ");
                    String dropCode = scanner.nextLine();
                    Course courseToDrop = findCourse(courses, dropCode);
                    if (courseToDrop != null) {
                        student.dropCourse(courseToDrop);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 4:
                    student.listRegisteredCourses();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        
        scanner.close();
    }

    private static Course findCourse(List<Course> courses, String courseCode) {
        for (Course course : courses) {
            if (course.courseCode.equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
}
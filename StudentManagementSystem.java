import java.sql.*;
import java.util.*;

class Student {
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

public class StudentManagementSystem {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/studentmanagement";
    private static final String JDBC_USER = "root"; 
    private static final String JDBC_PASSWORD = "S@2915djkq#"; 

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("1. Add Student");
                System.out.println("2. Add Course to Student");
                System.out.println("3. Set Grade for Course");
                System.out.println("4. Mark Attendance");
                System.out.println("5. View Student Details");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addStudent(scanner, connection);
                        break;
                    case 2:
                        addCourseToStudent(scanner, connection);
                        break;
                    case 3:
                        setGradeForCourse(scanner, connection);
                        break;
                    case 4:
                        markAttendance(scanner, connection);
                        break;
                    case 5:
                        viewStudentDetails(scanner, connection);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addStudent(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        String sql = "INSERT INTO Students (id, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.setString(2, name);
            statement.executeUpdate();
            System.out.println("Student added successfully!");
        }
    }

    private static void addCourseToStudent(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();

        String sql = "INSERT INTO Courses (course_name, student_id, grade) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, courseName);
            statement.setString(2, studentId);
            statement.setDouble(3, 0.0); // Default grade
            statement.executeUpdate();
            System.out.println("Course added successfully!");
        }
    }

    private static void setGradeForCourse(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();
        System.out.print("Enter grade: ");
        double grade = scanner.nextDouble();

        String sql = "UPDATE Courses SET grade = ? WHERE student_id = ? AND course_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, grade);
            statement.setString(2, studentId);
            statement.setString(3, courseName);
            statement.executeUpdate();
            System.out.println("Grade set successfully!");
        }
    }

    private static void markAttendance(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        String sql = "INSERT INTO Attendance (student_id, attendance_date) VALUES (?, NOW())";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentId);
            statement.executeUpdate();
            System.out.println("Attendance marked successfully!");
        }
    }

    private static void viewStudentDetails(Scanner scanner, Connection connection) throws SQLException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        String sql = "SELECT * FROM Students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("Student ID: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));

                sql = "SELECT * FROM Courses WHERE student_id = ?";
                try (PreparedStatement coursesStatement = connection.prepareStatement(sql)) {
                    coursesStatement.setString(1, studentId);
                    ResultSet coursesRs = coursesStatement.executeQuery();
                    System.out.println("Courses and Grades:");
                    while (coursesRs.next()) {
                        System.out.println(coursesRs.getString("course_name") + ": " + coursesRs.getDouble("grade"));
                    }
                }

                sql = "SELECT COUNT(*) AS attendance_count FROM Attendance WHERE student_id = ?";
                try (PreparedStatement attendanceStatement = connection.prepareStatement(sql)) {
                    attendanceStatement.setString(1, studentId);
                    ResultSet attendanceRs = attendanceStatement.executeQuery();
                    if (attendanceRs.next()) {
                        System.out.println("Attendance: " + attendanceRs.getInt("attendance_count"));
                    }
                }
            } else {
                System.out.println("Student not found!");
            }
        }
    }
}


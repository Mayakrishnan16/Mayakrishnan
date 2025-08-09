import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * The Student class represents a single student in the system.
 * It is a simple data class that holds student information.
 * It implements Serializable to allow its objects to be written to a file.
 */
class Student implements Serializable {
    private static final long serialVersionUID = 1L; // For version control during serialization
    private String name;
    private int rollNumber;
    private char grade;

    // Constructor
    public Student(String name, int rollNumber, char grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
               "name='" + name + '\'' +
               ", rollNumber=" + rollNumber +
               ", grade=" + grade +
               '}';
    }
}

/**
 * The StudentManagementSystem class manages a collection of Student objects.
 * It provides methods for adding, removing, searching, and displaying students.
 * It also handles the file I/O for persistent storage.
 */
public class StudentManagementSystem {

    private List<Student> students;
    private Scanner scanner;
    private static final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        // Load student data from file at startup
        loadStudentsFromFile();
    }

    // Main method to run the application
    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.run();
    }

    /**
     * The main application loop.
     * Displays a menu and handles user choices.
     */
    public void run() {
        boolean exit = false;
        while (!exit) {
            displayMenu();
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    removeStudent();
                    break;
                case "3":
                    searchStudent();
                    break;
                case "4":
                    displayAllStudents();
                    break;
                case "5":
                    editStudent();
                    break;
                case "6":
                    exit = true;
                    saveStudentsToFile(); // Save data before exiting
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 6.");
                    break;
            }
        }
        scanner.close();
    }

    /**
     * Displays the main menu options to the user.
     */
    private void displayMenu() {
        System.out.println("\n--- Student Management System ---");
        System.out.println("1. Add a new student");
        System.out.println("2. Remove a student");
        System.out.println("3. Search for a student");
        System.out.println("4. Display all students");
        System.out.println("5. Edit an existing student's information");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Adds a new student to the system after validating input.
     */
    private void addStudent() {
        System.out.println("\n--- Add New Student ---");

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }

        System.out.print("Enter roll number: ");
        int rollNumber = 0;
        try {
            rollNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid roll number. Please enter a number.");
            return;
        }

        System.out.print("Enter grade (A, B, C, D, F): ");
        String gradeStr = scanner.nextLine().toUpperCase();
        if (gradeStr.length() != 1 || "ABCDEF".indexOf(gradeStr.charAt(0)) == -1) {
            System.out.println("Error: Invalid grade. Please enter a single character from A-F.");
            return;
        }
        char grade = gradeStr.charAt(0);

        students.add(new Student(name, rollNumber, grade));
        System.out.println("Student added successfully!");
        saveStudentsToFile();
    }

    /**
     * Removes a student by their roll number.
     */
    private void removeStudent() {
        System.out.println("\n--- Remove Student ---");
        System.out.print("Enter the roll number of the student to remove: ");
        int rollNumber = 0;
        try {
            rollNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid roll number. Please enter a number.");
            return;
        }
        
        Student studentToRemove = findStudentByRollNumber(rollNumber);
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            System.out.println("Student with roll number " + rollNumber + " removed successfully.");
            saveStudentsToFile();
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    /**
     * Searches for a student by their roll number and displays their details.
     */
    private void searchStudent() {
        System.out.println("\n--- Search for Student ---");
        System.out.print("Enter the roll number of the student to search: ");
        int rollNumber = 0;
        try {
            rollNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid roll number. Please enter a number.");
            return;
        }

        Student foundStudent = findStudentByRollNumber(rollNumber);
        if (foundStudent != null) {
            System.out.println("Student found: " + foundStudent);
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    /**
     * Edits an existing student's information.
     */
    private void editStudent() {
        System.out.println("\n--- Edit Student Information ---");
        System.out.print("Enter the roll number of the student to edit: ");
        int rollNumber = 0;
        try {
            rollNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid roll number. Please enter a number.");
            return;
        }

        Student studentToEdit = findStudentByRollNumber(rollNumber);
        if (studentToEdit != null) {
            System.out.println("Editing student: " + studentToEdit);

            System.out.print("Enter new name (or press Enter to keep current): ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                studentToEdit.setName(newName);
            }

            System.out.print("Enter new grade (A, B, C, D, F) (or press Enter to keep current): ");
            String newGradeStr = scanner.nextLine().toUpperCase();
            if (!newGradeStr.trim().isEmpty()) {
                if (newGradeStr.length() == 1 && "ABCDEF".indexOf(newGradeStr.charAt(0)) != -1) {
                    studentToEdit.setGrade(newGradeStr.charAt(0));
                } else {
                    System.out.println("Invalid grade. Keeping the old grade.");
                }
            }

            System.out.println("Student information updated successfully!");
            saveStudentsToFile();
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    /**
     * Displays all students currently in the system.
     */
    private void displayAllStudents() {
        System.out.println("\n--- All Students ---");
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    /**
     * Helper method to find a student by their roll number.
     * @param rollNumber The roll number to search for.
     * @return The Student object if found, otherwise null.
     */
    private Student findStudentByRollNumber(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    /**
     * Saves the current list of students to a file using object serialization.
     */
    private void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Student data saved to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving student data: " + e.getMessage());
        }
    }

    /**
     * Loads the list of students from a file using object deserialization.
     */
    private void loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
            System.out.println("Student data loaded from " + FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("No existing student data found. Starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading student data: " + e.getMessage());
        }
    }
}

import java.util.Scanner;

public class StudentGradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Take marks obtained in each subject
        System.out.println("Enter the number of subjects: ");
        int numberOfSubjects = scanner.nextInt();

        int totalMarks = 0;
        for (int i = 1; i <= numberOfSubjects; i++) {
            System.out.print("Enter marks for subject " + i + " (out of 100): ");
            int marks = scanner.nextInt();
            // Basic validation to ensure marks are within a reasonable range
            if (marks >= 0 && marks <= 100) {
                totalMarks += marks;
            } else {
                System.out.println("Invalid marks entered. Please enter a value between 0 and 100.");
                i--; // Decrement i to re-enter marks for the current subject
            }
        }

        // Calculate Average Percentage
        double averagePercentage = (double) totalMarks / numberOfSubjects;

        // Grade Calculation
        char grade;
        if (averagePercentage >= 90) {
            grade = 'A';
        } else if (averagePercentage >= 80) {
            grade = 'B';
        } else if (averagePercentage >= 70) {
            grade = 'C';
        } else if (averagePercentage >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }

        // Display Results
        System.out.println("\n--- Results ---");
        System.out.println("Total Marks: " + totalMarks + "/" + (numberOfSubjects * 100));
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade: " + grade);
        System.out.println("---------------");

        scanner.close();
    }
}
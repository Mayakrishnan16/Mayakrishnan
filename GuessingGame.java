import java.util.Random;
import java.util.Scanner;

public class GuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int roundsWon = 0;
        int totalAttempts = 0;
        final int MAX_ATTEMPTS = 5;

        System.out.println("Welcome to the Number Guessing Game!");

        boolean playAgain = true;
        while (playAgain) {
            int secretNumber = random.nextInt(100) + 1;
            int attemptsThisRound = 0;
            boolean hasGuessedCorrectly = false;

            System.out.println("\n--- New Round ---");
            System.out.println("I have selected a number between 1 and 100. You have " + MAX_ATTEMPTS + " attempts.");

            for (int i = 1; i <= MAX_ATTEMPTS; i++) {
                System.out.print("Attempt " + i + "/" + MAX_ATTEMPTS + ": Enter your guess: ");
                
                // Input validation to handle non-integer input
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a whole number.");
                    System.out.print("Attempt " + i + "/" + MAX_ATTEMPTS + ": Enter your guess: ");
                    scanner.next(); // Clear the invalid input
                }
                int guess = scanner.nextInt();
                attemptsThisRound++;

                if (guess < secretNumber) {
                    System.out.println("Too low!");
                } else if (guess > secretNumber) {
                    System.out.println("Too high!");
                } else {
                    System.out.println("Congratulations! You guessed the number " + secretNumber + " correctly!");
                    roundsWon++;
                    totalAttempts += attemptsThisRound;
                    hasGuessedCorrectly = true;
                    break; // Exit the inner loop since the guess is correct
                }
            }

            if (!hasGuessedCorrectly) {
                System.out.println("\nSorry, you've used all your attempts. The secret number was " + secretNumber + ".");
                totalAttempts += MAX_ATTEMPTS;
            }

            // Display score
            System.out.println("\n--- Current Score ---");
            System.out.println("Rounds Won: " + roundsWon);
            System.out.println("Total Attempts: " + totalAttempts);
            System.out.println("---------------------");

            // Ask to play again
            System.out.print("Do you want to play again? (yes/no): ");
            scanner.nextLine(); // Consume the newline character left from the previous input
            String response = scanner.nextLine().toLowerCase();
            playAgain = response.equals("yes");
        }

        System.out.println("\nThank you for playing! Final Score:");
        System.out.println("Rounds Won: " + roundsWon);
        System.out.println("Total Attempts: " + totalAttempts);
        System.out.println("Goodbye!");
        scanner.close();
    }
}
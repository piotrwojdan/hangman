import java.io.File;
import java.io.IOException;
import java.util.*;

public class Hangman {
    private static final String FILE_NAME = "/Users/piotrwojdan/IdeaProjects/hangman/src/words_alpha.txt";
    private static final ArrayList<String> dictionary = new ArrayList<>(20000);
    private static String toBeGuessed;
    private static boolean guessed = false;
    private static int lives;
    private static final ArrayList<Character> guessedLetters = new ArrayList<>();

    public static void main(String[] args) {

        if (!getPasswordToBeGuessed())
            return;

        Scanner keyboardScanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose level of difficulty: 1 - hard; 2 - medium; 3 - easy: ");
            try {
                int input = keyboardScanner.nextInt();
                if (input == 1) lives = 4;
                else if (input == 2) lives = 7;
                else lives = 10;
                break;
            } catch (InputMismatchException e) {
                System.out.println("Choose a number between 1-3!");
            } finally {
                keyboardScanner.nextLine();         // in order for scanner to work properly
            }
        }


//        keyboardScanner.useDelimiter("");       // to scan just one character with next() method

        while (!guessed && lives > 0) {
            printCurrentWordState();

            System.out.println("You have " + lives + " lives.");
            System.out.println("You've already used those letters: ");
            for (Character guessedLetter : guessedLetters) System.out.print(guessedLetter + " ");
            System.out.println("\nPlease guess a letter: ");

            // if more than one letter passed it dismisses the remaining ones
            char guess = keyboardScanner.next().toLowerCase().charAt(0);
            while (!Character.isLetter(guess)) {
                System.out.println("You need to pass in a letter!");
                guess = keyboardScanner.next().toLowerCase().charAt(0);
            }

            if (!guessedLetters.contains(guess)) {
                guessedLetters.add(guess);
                if (!toBeGuessed.contains(Character.toString(guess)))
                    lives--;

                boolean temp = true;
                for (int i = 0; i < toBeGuessed.length(); i++)
                    temp = temp && guessedLetters.contains(toBeGuessed.charAt(i));
                guessed = temp;
            } else {
                System.out.println("You've already tried this letter, try again!");
            }
        }

        if (lives > 0)
            System.out.println("Congrats, you guessed the word " + toBeGuessed + "!");
        else
            System.out.println("Try your luck next time, the word was " + toBeGuessed);

    }

    private static boolean getPasswordToBeGuessed() {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            while (fileScanner.hasNext())
                dictionary.add(fileScanner.nextLine());

        } catch (IOException e) {
            System.out.println("You cannot play the game cause there's something wrong with opening the file.");
            return false;
        }

        Random r = new Random();
        toBeGuessed = dictionary.get(r.nextInt(dictionary.size()));
        return true;
    }

    private static void printCurrentWordState() {
        for (int i = 0; i < toBeGuessed.length(); i++) {
            if (guessedLetters.contains(toBeGuessed.charAt(i)))
                System.out.print(toBeGuessed.charAt(i) + " ");
            else
                System.out.print("_ ");
        }
        System.out.println();
    }
}

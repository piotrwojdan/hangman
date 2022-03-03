import java.io.File;
import java.io.IOException;
import java.util.*;

public class Hangman {
    private static final String FILE_NAME = "/Users/piotrwojdan/IdeaProjects/hangman/src/words_alpha.txt";
    private static ArrayList<String> dictionary = new ArrayList<>(20000);
    private static String toBeGuessed;
    private static boolean guessed = false;
    private static int lives = 10;
    private static ArrayList<Character> guessedLetters = new ArrayList<>();

    public static void main(String[] args) {

        try (Scanner s = new Scanner(new File(FILE_NAME))) {
            while (s.hasNext())
                dictionary.add(s.nextLine());

        } catch (IOException e) {
            System.out.println("You cannot play the game cause there's something wrong with the file.");
            return;
        }

        Random r = new Random();
        toBeGuessed = dictionary.get(r.nextInt(dictionary.size()));
//        System.out.println(toBeGuessed);

        Scanner s = new Scanner(System.in);

        while (!guessed && lives > 0) {
            printCurrentWordState();

            System.out.println("You have " + lives + " lives.");
            System.out.println("You've already used those letters: ");
            for (Character guessedLetter : guessedLetters) System.out.print(guessedLetter + " ");
            System.out.println("\nPlease guess another letter: ");

            Character guess = s.nextLine().charAt(0);

            if (!guessedLetters.contains(guess)) {
                guessedLetters.add(guess);
                if (!toBeGuessed.contains(guess.toString()))
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

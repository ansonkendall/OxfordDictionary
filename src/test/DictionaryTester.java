package test;

import java.util.Scanner;

import app.Dictionary;

public class DictionaryTester {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String word = Dictionary.getRandomWord();
		String definition = Dictionary.getDefinition(word);
		String hiddenWord = word.replaceAll("[a-z]", "_ ");
		String guess = "";

		// Show definition
		System.out.println(definition);

		// Show hidden word
		System.out.println(hiddenWord);

		// Ask user to guess
		System.out.print("What is the word?");
		guess = scan.nextLine();

		// Check answer
		if (guess.equals(word)) {
			System.out.println("\nCorrect!");
		} else {
			System.out.println("\nIncorrect!");

			// Give definition of the word the user guessed
            System.out.println("The definition of " + guess + " is:");
            System.out.printf(Dictionary.getDefinition(guess));

            // Show the un-hidden word
            System.out.println("The real word is " + word + ".");
		}

		scan.close();
	}
}

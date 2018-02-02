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

		// Show defintion
		System.out.println(definition);

		// Show hidden word
		System.out.println(hiddenWord);

		// Ask user to guess
		System.out.println("What is the word?");
		guess = scan.nextLine();

		// Check answer
		if (guess.equals(word)) {
			System.out.println("Correct!");
		} else {
			System.out.println("Incorrect!");
		}

		// Show the un-hidden word
		System.out.println("The word is " + word + ".");
		
		scan.close();
	}
}

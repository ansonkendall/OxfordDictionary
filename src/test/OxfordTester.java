package test;

import java.util.Scanner;

import app.Oxford;

public class OxfordTester {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		String s = Oxford.getRandomWord();
		
		System.out.println(Oxford.getDefinition(s));
		
		System.out.println(s.replaceAll("[a-z]", "_ "));
		
		System.out.println("What is the word?");
		if (scan.nextLine().equals(s)) {
			System.out.println("Correct!");
		} else {
			System.out.println("Incorrect!");
		}
		
		System.out.println("The word is " + s + ".");
		
		scan.close();
	}
}

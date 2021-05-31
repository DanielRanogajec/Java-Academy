package technical.task;

import java.util.Scanner;

/**
 * Application which takes a sentence as first input and set of characters as second input of System.in.
 * 
 * For example:
 * Sentence should look like: "I love to work in global logic!".
 * Set of characters should look like: "LOGIC".
 * 
 * Application will print frequencies for those characters in each word.
 * 
 * @author Daniel_Ranogajec
 *
 */
public class App {
	
	public static void main(String[] args) {
		
		try (Scanner sc = new Scanner(System.in)) {
			String phrase = "";
			while (phrase.isEmpty()) {
				System.out.print("Insert a sentence for which you would like to know the frequencies of the characters: ");
				phrase = sc.nextLine();
			}
			
			String characters = "";
			while (characters.isEmpty()) {
				System.out.print("Insert a set of characters: ");
				characters = sc.nextLine();
			}
			
			System.out.println();
			Parser parser = new Parser(phrase, characters);
			parser.printAll();
		}
		
	}

}

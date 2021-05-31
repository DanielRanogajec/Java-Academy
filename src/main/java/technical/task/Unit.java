package technical.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * Unit is a class used for parsing given word with the given set of characters.
 * 
 * After data is parsed, we get a new set of characters which are the characters
 * from the original set of characters that appeared in the given word.
 * 
 * Also, we get a counter that counts how many characters from given set of characters
 * appeared in the word.
 * 
 * Unit is considered an equal with another unit if their original words are the same size,
 * and their new set of characters are the same.
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Unit {
	
	private String word;
	private char[] characters;
	private Set<Character> containingChars;
	private int numOfChars;
	
	/**
	 * Constructor method that takes a word and parses it with given set of characters.
	 * @param word String that will be parsed
	 * @param characters char[] representation of set of characters
	 * @throws NullPointerException if given word or characters is <code>null</code>
	 * @throws IllegalArgumentException if given word or characters are empty
	 */
	public Unit(String word, char[] characters) {
		this(word, String.valueOf(characters));
	}
	
	/**
	 * Constructor method that takes a word and parses it with given set of characters.
	 * @param word String that will be parsed
	 * @param characters String representation of set of characters
	 * @throws NullPointerException if given word or characters is <code>null</code>
	 * @throws IllegalArgumentException if given word or characters are empty
	 */
	public Unit(String word, String characters) {
		
		if (word == null || characters == null) 
			throw new NullPointerException("Given word and characters can't be null!");
		
		if (word.isEmpty() || characters.isEmpty())
			throw new IllegalArgumentException("Given word and characters can't be empty!");
		

		this.word = word.toLowerCase();
		this.characters = characters.toLowerCase().toCharArray();
		this.containingChars = new LinkedHashSet<>();
		this.numOfChars = 0;
		
		parse();
		
		List<Character> tempList = new ArrayList<>(containingChars);
		containingChars.clear();
		
		for (char c : this.characters) {
			if (tempList.contains(c)) {
				containingChars.add(c);
			}
		}
	}
	
	/**
	 * Private method used for parsing the word with given set of characters.
	 */
	private void parse() {
		Set<Character> chars = new HashSet<Character>();
		for (char c : characters) {
			chars.add(c);
		}
		
		for (char c : word.toCharArray()) {
			if (chars.contains(c)) {
				containingChars.add(c);
				numOfChars++;
			}
		}
	}

	/**
	 * Method that returns the length of the given word.
	 * @return length of given word
	 */
	public int getSize() {
		return word.length();
	}
	
	/**
	 * Method that returns set of containing characters from the word as String.
	 * @return String representing the set of containing characters from the word
	 */
	public String getCharsAsString() {
		return containingChars.toString();
	}

	/**
	 * Method that returns set of containing characters from the word.
	 * @return Set of containing characters from the word
	 */
	public Set<Character> getChars() {
		return containingChars;
	}
	
	/**
	 * Method that returns the number of characters from the given set of characters in the given word.
	 * @return the number of characters
	 */
	public int getNumOfChars() {
		return numOfChars;
	}

	/**
	 * Method used in the class Parser when joining two equal Units.
	 * @param number of characters from the given set of characters from another Unit
	 */
	void increaseCounter(int k) {
		numOfChars += k;
	}
	
	/**
	 * Method used for getting a frequency of characters in the relation to the total number
	 * of characters in the phrase.
	 * @param totalNumberOfCharacters number of characters from given set of characters in the phrase
	 * @return frequency of characters from the given set of characters in the given word
	 * @throws IllegalArgumentException if totalNumberOfCharacters is less than 1
	 */
	public double getFrequency(int totalNumberOfCharacters) {
		if (totalNumberOfCharacters <= 0) 
			throw new IllegalArgumentException("Total number of characters has to be more than 0");
		return (double)numOfChars/totalNumberOfCharacters;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return containingChars.toString().replace("[", "(").replace("]", ")") + ", " + word.length();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((containingChars == null) ? 0 : containingChars.hashCode());
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unit other = (Unit) obj;
		if (containingChars == null) {
			if (other.containingChars != null)
				return false;
		} else if (!containingChars.equals(other.containingChars))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (word.length() != other.word.length())
			return false;
		return true;
	}

}

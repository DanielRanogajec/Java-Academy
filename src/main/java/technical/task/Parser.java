package technical.task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser is a class used for parsing given phrase with given set of characters.
 * 
 * Given data is parsed and a list of units is created.
 * 
 * Each unit contains set of characters (the ones from given characters that appeared in the word)
 * that were in the given word, the size of the word and the number of characters from the given set 
 * of characters that were in the word.
 * 
 * Each unit inside the list is sorted by frequencies, followed by size of the word and the characters inside.
 * 
 * All special characters (!,?,-...) from the given phrase are removed.
 * 
 * If no characters from the set of characters appear in the word that is being parsed, 
 * that unit won't appear in the list.
 * 
 * Calling the method printAll() prints all units with their frequencies and prints total frequency 
 * of the given set of characters in the given phrase.
 * 
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Parser {
	
	private String phrase;
	private char[] characters;
	private int totalNumOfChars;
	private List<Unit> list;
	
	/**
	 * Constructor method that takes phrase and parses it with given set of characters.
	 * @param phrase String that will be parsed
	 * @param characters String representation of set of characters
	 * @throws NullPointerException if given phrase or characters is <code>null</code>
	 * @throws IllegalArgumentException if given phrase or characters are empty
	 */
	public Parser(String phrase, String characters) {
		this(phrase, characters.toCharArray());
	}
	
	/**
	 * Constructor method that takes phrase and parses it with given set of characters.
	 * @param phrase String that will be parsed
	 * @param characters char[] representation of set of characters
	 * @throws NullPointerException if given phrase or characters is <code>null</code>
	 * @throws IllegalArgumentException if given phrase or characters are empty
	 */
	public Parser(String phrase, char[] characters) {
		
		if (phrase == null || characters == null) 
			throw new NullPointerException("Given phrase and characters can't be null!");
		
		if (phrase.isEmpty() || characters.length == 0)
			throw new IllegalArgumentException("Given phrase and characters can't be empty!");
		
		this.phrase = phrase.replaceAll("[^a-zA-Z0-9\\s]", "");
		this.characters = characters;
		this.totalNumOfChars = 0;
		
		this.list = parse();
	}
	
	/**
	 * Constructor method that reads the file that should only contain 2 rows.
	 * First row should contain the phrase.
	 * Second row should contain the set of characters.
	 * @param File that contains the phrase and the set of characters
	 * @throws IllegalArgumentException if given file doesn't have two rows
	 */
	public Parser(File f) {
		try {
			List<String> lines = Files.readAllLines(f.toPath());
			if (lines.size() != 2)
				throw new IllegalArgumentException("Given file must have only two rows. First for must contain "
						+ "a phrase and the second must contain the set of characters.");
			this.phrase = lines.get(0).replaceAll("[^a-zA-Z0-9\\s]", "");
			this.characters = lines.get(1).toCharArray();
			
			this.totalNumOfChars = 0;
			
			this.list = parse();
			
		} catch (IOException e) {
			System.out.println("I/O Exception occured.");
		}
	}
	
	/**
	 * Private method used for parsing data.
	 * @return list of units
	 */
	private List<Unit> parse() {

		String[] phraseSpt = phrase.split("\\s+");
		
		List<Unit> list = new ArrayList<>();
		for (String s : phraseSpt) {
			Unit unit = new Unit(s, characters);
			totalNumOfChars += unit.getNumOfChars();
			if (unit.getNumOfChars() != 0)
				list.add(unit);
		}
		
		Collections.sort(list, Comparator.comparing(Unit::getNumOfChars)
				.thenComparing(Unit::getSize).thenComparing(Unit::getCharsAsString));
		
		for (int i = 0; i < list.size() - 1;) {
			if (list.get(i).equals(list.get(i+1))) {
				int k = list.get(i+1).getNumOfChars();
				list.remove(i+1);
				list.get(i).increaseCounter(k);
			} else {
				i++;
			}
		}
		
		return list;
		
	}
	
	/**
	 * Method that prints all units with their frequencies.
	 * After printing all units, method prints total frequency of given set of characters in given phrase.
	 */
	public void printAll() {
		
		for (Unit unit : list) {
			print(unit);
		}
		
		System.out.println("Total Frequency: " + String.format("%.2f", getTotalFrequency()) + " (" + totalNumOfChars + "/" + phrase.replaceAll("\\s+", "").length() + ")");	
	}

	/**
	 * Method that prints the given unit with its frequency.
	 * @param unit Unit that you want to print
	 */
	public void print(Unit unit) {
		System.out.println("{" + unit + "} = " + String.format("%.2f", unit.getFrequency(totalNumOfChars)) + " (" + unit.getNumOfChars() + "/" + totalNumOfChars + ")");
	}
	
	/**
	 * Method that prints the unit at the specified position in the list.
	 * @param position of the unit in the list of units
	 * @throws IllegalArgumentException if index is less then 0 or more or equal then the size of the list
	 */
	public void print(int index) {
		if (index < 0 || index >= list.size())
			throw new IllegalArgumentException("Index must be between 0 and the size of list of units!");
		print(list.get(index));
	}

	/**
	 * Method that returns given phrase stripped of special characters.
	 * @return phrase without special characters
	 */
	public String getPhrase() {
		return phrase;
	}

	/**
	 * Method that returns given set of characters
	 * @return char[] that represents set of given characters
	 */
	public char[] getCharacters() {
		return characters;
	}
	
	/**
	 * Method that returns total frequency of special characters in the phrase.
	 * @return total frequency
	 */
	public double getTotalFrequency() {
		return (double)totalNumOfChars/phrase.replaceAll("\\s+", "").length();
	}
	
	/**
	 * Method that returns the number of characters from given set of characters that appear in the phrase.
	 * @return number of given characters from the given phrase
	 */
	public int getTotalNumOfChars() {
		return totalNumOfChars;
	}
	
	/**
	 * Method that returns list of all parsed units
	 * @return list of all parsed units
	 */
	public List<Unit> getAllUnits() {
		return list;
	}
	
	/**
	 * Method that returns specific unit from the list of all units
	 * @param position of the unit in the list of units
	 * @return Unit that lies on given position
	 * @throws IllegalArgumentException if index is less then 0 or more or equal then the size of the list
	 */
	public Unit getUnit(int index) {
		if (index < 0 || index >= list.size())
			throw new IllegalArgumentException("Index must be between 0 and the size of list of units!");
		return list.get(index);
	}
	
	/**
	 * Method that returns Map with each unit as key and its frequency as value.
	 * @return map of all units as keys and its frequencies as values
	 */
	public Map<Unit, Double> getAllUnitsWithFrequencies() {
		Map<Unit, Double> map = new HashMap<>();
		list.forEach(l -> map.put(l, l.getFrequency(totalNumOfChars)));
		
		return map;
	}
	
	/**
	 * Method used for printing units and their frequencies to the given file.
	 * @param File that you want to print the data in
	 * @throws IOException if the file doesn't exist, if the file is a directory or if the file can't be opened
	 */
	public void printToFile(File f) throws IOException {
		FileWriter fw = new FileWriter(f);
		for (Unit unit : list) {
			fw.write("{" + unit + "} = " + String.format("%.2f", unit.getFrequency(totalNumOfChars)) + " (" + unit.getNumOfChars() + "/" + totalNumOfChars + ")\n");
		}
		fw.write("Total Frequency: " + String.format("%.2f", getTotalFrequency()) + " (" + totalNumOfChars + "/" + phrase.replaceAll("\\s+", "").length() + ")");
		fw.close();
	}

}

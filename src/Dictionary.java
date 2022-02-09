/**
* Loads a set of words from file, provides search a word/find number of (unique) words functionality
* @author  Maria Amirova
* @version 1.0 
*/
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Dictionary {

	/**
	 * Main method
	 * <p>provides a user with menu options (1 to 6, press 7 to exit)
	 * adds a set of words from a file by splitting the text and transform all the words to LowerCase
	 * forms a TreeMap (dictionary)
	 * performs the number of word occurrence search, prints out this number
	 * prints out the number of all words in a file
	 * prints out the number of unique words in a file
	 * provides an option to ignore articles ("a", "an", "the")
	 * provides function to reset a dictionary (remove everything from the TreeMap
	 * @param args - Passed in parameters from the command line.
	 */
	public static void main(String[] args) throws IOException {

		Scanner scan = new Scanner(System.in);
		TreeMap<String, Integer> dictionary = new TreeMap<>();//create a new TreeMap to be a dictionary

		String choice = "1";
		boolean ignoreArticles = false;// count articles ("a", "an" and "the") by default;

		 while (!choice.equals("7")) {//"7" choice for exit
			System.out.println("Dictionary\n");
			System.out.println("*********************************************************\n");
			System.out.println("1. Add words to the Dictionary from file");
			System.out.println("2. Search a word in the Dictionary");
			System.out.println("3. Display number of unique words in the Dictionary");
			System.out.println("4. Display number of all words in the Dictionary");
			System.out.println("5. Reset Dictionary");
			System.out.println("6. Ignore definite and indefinite articles");
			System.out.println("7. Exit");
			System.out.print("\nEnter your option : ");
			choice = scan.next();

			switch (choice) {

			case "1":// load a file
				String[] words;
				ArrayList<String> lines = new ArrayList<>();
				BufferedReader reader;
				System.out.print("Enter the file name to add words to dictionary (Raven.txt)");
				String fileName = scan.next();//determine the name of a given file
				try {
					reader = new BufferedReader(new FileReader(fileName));//read a file
					String line = reader.readLine();
					lines.add(line);
					System.out.println("Program has read the file\n");
					while (line != null) {// ArrayList "lines" keeps all the lines from a file
						line = reader.readLine();
						if (line != null)
							lines.add(line);
					}
				} catch (FileNotFoundException e) {
					System.out.println("The file not found");//print out this message if the file is not found
				}
				for (String l : lines) {
					l = l.toLowerCase();// convert each line to lower case
					words = l.split("[^a-z]+");// split each line into separate words

					for (String key : words) {// Irritate through words to add each one to dictionary
						if(key.length()!=0)//ignore "new lines" words 
						dictionary.put(key, dictionary.getOrDefault(key, 0) + 1);// value increased by one if word is presented in the file more than one time
					}
				}
				break;

			case "2":// word search
				System.out.print("Enter the word you want to search : ");
				String word = scan.next();// a word from a file
				int occurence = 0;// how many time is the word presented in the file
				if (ignoreArticles == false) {// count articles
					occurence = dictionary.getOrDefault(word, 0);// Returns the value to which the specified key is
																	// mapped, or defaultValue if this map contains no
																	// mapping for the key
				} else {// ignore articles

					if (!word.equals("a") && !word.equals("an") && !word.equals("the")) {

						occurence = dictionary.getOrDefault(word, 0);
					}
				}
				System.out.println(word + " occurs " + occurence + " times\n");
				break;

			case "3":// unique words amount
				int uniqueWordsCount = 0;
				for (Map.Entry<String, Integer> wrd : dictionary.entrySet()) {// iterate through all wrd(K,V) in the
																				// dictionary

						if (ignoreArticles == true) {// not count articles
							if (!wrd.getKey().equals("a") && !wrd.getKey().equals("an")
									&& !wrd.getKey().equals("the")) {

								uniqueWordsCount++;// add words
							}
						} else {// count articles
							uniqueWordsCount++;// add words
						}
					
				}
				System.out.println("Dictionary has " + uniqueWordsCount + " unique words\n");
				break;

			case "4":// all words amount
				int allWordsCount = 0; // dictionary size (amount of words)
				for (Map.Entry<String, Integer> wrd : dictionary.entrySet()) {
					if (ignoreArticles==false) {
						allWordsCount = allWordsCount + wrd.getValue();
					}
					else{
						if (!wrd.getKey().equalsIgnoreCase("a") && !wrd.getKey().equalsIgnoreCase("an")
								&& !wrd.getKey().equalsIgnoreCase("the")) {
							allWordsCount = allWordsCount + wrd.getValue();// add words
						}
					}
				}
				System.out.println("Dictionary has " + allWordsCount + " words\n");
				break;

			case "5":// reset the dictionary
				dictionary.clear();//remove the content of a TreeMap
				System.out.println("Program has removed all the words\n");
				break;

			case "6":// set the rule to ignore all the articles
				ignoreArticles = true;
				System.out.println("Ignore definite and indefinite articles has been set to " + ignoreArticles + "\n");
				break;

			case "7":// exit
				System.out.println("Good bye.... hope to see you soon");
				break;

			default://any input other than 1-7 prints out the message and ask to enter the option again
				System.out.println("Input Mismatch Exception while reading user's option from main menu\n");
				break;
			}
		}

	}
}

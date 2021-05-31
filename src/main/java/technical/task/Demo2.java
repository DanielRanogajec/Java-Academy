package technical.task;

import java.io.File;
import java.io.IOException;

/**
 * Demo program that saves the frequencies of characters L,O,G,I,C 
 * in the sentence "I love to work in global logic!" to the file in src/main/resources.
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Demo2 {

	public static void main(String[] args){
		
		File f = new File("src/main/resources/logic");
		
		Parser p = new Parser(f);
		
		try {
			p.printToFile(new File("src/main/resources/logicOutput"));
			System.out.println("File with the output was created. Check src/main/resources directory!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}

package technical.task;

/**
 * Demo program that prints frequencies of characters L,O,G,I,C 
 * in the sentence "I love to work in global logic!".
 * 
 * @author Daniel_Ranogajec
 *
 */
public class Demo {

	public static void main(String[] args) {
		
		Parser p = new Parser("I love to work in global logic!", "LOGIC");
		p.printAll();
		
	}
}

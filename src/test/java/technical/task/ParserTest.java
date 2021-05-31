package technical.task;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ParserTest {

	@Test
	public void testNullConstructor() {		
		assertThrows(NullPointerException.class, () -> new Parser(null, ""));
	}
	
	@Test
	public void testEmptyString() {
		assertThrows(IllegalArgumentException.class, () -> new Parser("", ""));
	}
	
	@Test
	public void testSpecialCharacters() {
		assertEquals("test", new Parser("test(!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~);", "test").getPhrase());
	}
	
	@Test
	public void testSorting() {
		Parser p = new Parser ("test t ttttt testttt", "test");
		List<Unit> list = p.getAllUnits();
		
		assertEquals("(t), 1", list.get(0).toString());
		assertEquals("(t, e, s), 4", list.get(1).toString());
		assertEquals("(t), 5", list.get(2).toString());
		assertEquals("(t, e, s), 7", list.get(3).toString());		
	}
	
	@Test
	public void testGrouping() {
		Parser p = new Parser ("test test", "test");
		List<Unit> list = p.getAllUnits();
		
		assertEquals(1, list.size());
		assertEquals(8, list.get(0).getNumOfChars());

	}
	
	@Test
	public void testTotalFrequency() {
		Parser p = new Parser("test testing", "t");
		
		assertEquals(4.0/11, p.getTotalFrequency());
	}
	
	@Test
	public void testPrintException() {
		Parser p = new Parser("test", "t");
		
		assertThrows(IllegalArgumentException.class, () -> p.print(-1));
		assertThrows(IllegalArgumentException.class, () -> p.print(1));
	}
	
	@Test
	public void testMappingUnitsWithFrequencies() {
		Map<Unit, Double> map = new Parser("test testing", "t").getAllUnitsWithFrequencies();
		
		assertEquals(2, map.size());
        for (Map.Entry<Unit, Double> entry : map.entrySet()) 
    		assertEquals(0.5, entry.getValue());
	}
	
	@Test
	public void testParsingWithoutNoMatchingCharacters() {
		Parser p = new Parser ("aaaaa aaa a", "b");
		
		assertEquals(0, p.getAllUnits().size());
	}
	
	@Test
	public void testParsingWithOneMatchingCharacter() {
		Parser p = new Parser ("aaaaa aaa ab", "b");
		
		assertEquals(1, p.getAllUnits().size());
		assertEquals(1.0/10, p.getTotalFrequency());
	}
	
}

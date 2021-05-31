package technical.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UnitTest {
	
	@Test
	public void testNullConstructor() {		
		assertThrows(NullPointerException.class, () -> new Unit(null, ""));
	}
	
	@Test
	public void testEmptyString() {
		assertThrows(IllegalArgumentException.class, () -> new Unit("", ""));
	}

	@Test
	public void testCaseInsensitive() {
		Unit u1 = new Unit("test", "T");
		Unit u2 = new Unit("TEST", "t");
		
		assertEquals("(t), 4", u1.toString());
		assertEquals(2, u1.getNumOfChars());
		assertEquals("(t), 4", u2.toString());
		assertEquals(2, u2.getNumOfChars());
	}
	
	@Test
	public void testFrequency() {
		Parser p = new Parser("test testing", "t");
		
		assertEquals(0.5, p.getUnit(0).getFrequency(p.getTotalNumOfChars()));
	}
	
	@Test
	public void testEquals() {
		Unit u1 = new Unit("level", "l");
		Unit u2 = new Unit("plate", "l");
		
		assertEquals(true, u1.equals(u2));
		assertEquals(3, u1.getNumOfChars() + u2.getNumOfChars());		
	}
	
	@Test
	public void testCharacters() {
		Unit u = new Unit("test", "tabc");
		
		assertEquals(1, u.getChars().size());
		assertEquals(2, u.getNumOfChars());
	}
	
	
}

package edu.tum.cup2.generator.terminals;

import static edu.tum.cup2.grammar.SpecialTerminals.Placeholder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.util.ArrayTools;


/**
 * Test cases for a {@link EfficientTerminalSet}.
 * 
 * @author Andreas Wenger
 */
public class EfficientTerminalSetTest
{
	
	public enum MyTerminals implements Terminal
	{
		a, b, c, d, e,
		_000, _001, _002, _003, _004, _005, _006, _007, _008, _009,
		_010, _011, _012, _013, _014, _015, _016, _017, _018, _019,
		_020, _021, _022, _023, _024, _025, _026, _027, _028, _029,
		f, g, h
	}
	
	
	@Test public void test()
	{
		EfficientTerminalSet t = new EfficientTerminalSet(
			new ArrayTools<Terminal>().toLinkedList(MyTerminals.values()));
		assertEquals(0, t.getTerminals().size());
		assertFalse(t.contains(Placeholder));
		t = t.plus(Placeholder);
		assertEquals(1, t.getTerminals().size());
		assertTrue(t.contains(Placeholder));
		t = t.plus(MyTerminals.d);
		assertEquals(2, t.getTerminals().size());
		assertTrue(t.contains(MyTerminals.d));
		t = t.plus(MyTerminals.g);
		assertEquals(3, t.getTerminals().size());
		assertTrue(t.contains(MyTerminals.g));
	}
	

}

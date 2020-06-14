package edu.tum.cup2.generator.items;

import org.junit.Test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.generator.items.LR0ItemTest.MyNonTerminal.A;
import static edu.tum.cup2.generator.items.LR0ItemTest.MyNonTerminal.B;
import static edu.tum.cup2.generator.items.LR0ItemTest.MyTerminal.a;
import static org.junit.Assert.fail;

/**
 * Test cases for the {@link LR0Item} class.
 * 
 * @author Andreas Wenger
 */
public class LR0ItemTest
{
	
	public enum MyTerminal
		implements Terminal
	{
		a;
	}
	
	
	public enum MyNonTerminal
		implements NonTerminal
	{
		A, B;
	}
	
	
	/**
	 * Shift as long as possible, then an exception must be thrown.
	 */
	@Test public void shiftTest()
	{
		LR0Item item = new LR0Item(new Production(0, A, A, a, B), 0);
		//shifting 3 times must work
		try
		{
			item = item.shift();
			item = item.shift();
			item = item.shift();
		}
		catch (Exception ex)
		{
			fail();
		}
		//then it must fail
		try
		{
			item = item.shift();
			fail();
		}
		catch (Exception ex)
		{
		}
	}
	

}

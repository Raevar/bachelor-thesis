package edu.tum.cup2.generator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;


/**
 * Tests for the {@link NullableSet} class.
 * 
 * @author Andreas Wenger
 */
public class NullableSetTest
{
	
	private enum T implements Terminal
	{
		a
	}
	
	
	private enum N implements NonTerminal
	{
		A, B, C, D, E, F
	}
	
	
	@Test public void test()
	{
		LinkedList<Production> prods = new LinkedList<Production>();
	  // A → ɛBɛ   C → ɛAD     E → BA
		// B → ɛɛ    D → ɛɛɛaɛ   F → AC
		//nullable: A, B, E
		prods.add(new Production(0, N.A, N.B)); 
		prods.add(new Production(0, N.B)); 
		prods.add(new Production(0, N.C, N.A, N.D));
		prods.add(new Production(0, N.D, T.a));
		prods.add(new Production(0, N.E, N.B, N.A));
		prods.add(new Production(0, N.E, N.A, N.C));
		NullableSet ret = new NullableSet(prods);
		assertEquals(3, ret.size());
		assertTrue(ret.contains(N.A));
		assertTrue(ret.contains(N.B));
		assertTrue(ret.contains(N.E));
	}

}

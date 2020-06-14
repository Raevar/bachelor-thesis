package edu.tum.cup2.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;

import edu.tum.cup2.generator.items.Item;
import edu.tum.cup2.generator.states.State;


/**
 * Tools for testing {@link NFA}s
 * 
 * @author Gero
 * 
 */
public class NFATestTools
{
	public static <I extends Item, S extends State<I>> void testEquals(NFA<I, S> a1, NFA<I, S> a2)
	{
		assertEquals(a1.getStartState(), a2.getStartState());
		testCollection(a1.getStates(), a2.getStates());
		testCollection(a1.getEdges(), a2.getEdges());
		testCollection(a1.getEndStates(), a2.getEndStates());
	}
	
	
	public static <T> void testCollection(Collection<T> c1, Collection<T> c2)
	{
		assertEquals(c1.size(), c2.size());
		for (T item : c1)
		{
			final boolean contains = c2.contains(item);
			if (!contains)
			{
				fail("No equivalent item for: " + c1);
			}
		}
	}
}

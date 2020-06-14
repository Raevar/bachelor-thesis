package edu.tum.cup2.generator.states;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.tum.cup2.generator.GrammarInfo;
import edu.tum.cup2.generator.items.LR0Item;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.test.GrammarAppel_3_20_withSP;
import edu.tum.cup2.util.ArrayTools;


/**
 * Test cases for the {@link LR0State} class.
 * 
 * @author Andreas Wenger
 */
public class LR0StateTest
{
	
	/**
	 * Tests the closure method, using the grammar 3.20 from Appel's book.
	 */
	@Test public void closureTest()
	{
		Grammar grammar = new GrammarAppel_3_20_withSP().getGrammar();
		//compute closure of "S → (.L)" (production 1, position 1) as described on page 61 for state 3.
		LR0State state = createLR0State(
			new LR0Item(grammar.getProductionAt(1), 1)); //S → (.L)
		LR0State closure = state.closure(new GrammarInfo(grammar));
		//result must have 5 items
		assertEquals(5, closure.items.size());
		assertTrue(closure.items.contains(new LR0Item(grammar.getProductionAt(1), 1))); //S → (.L)
		assertTrue(closure.items.contains(new LR0Item(grammar.getProductionAt(4), 0))); //L → .L,S
		assertTrue(closure.items.contains(new LR0Item(grammar.getProductionAt(3), 0))); //L → .S
		assertTrue(closure.items.contains(new LR0Item(grammar.getProductionAt(1), 0))); //S → .(L)
		assertTrue(closure.items.contains(new LR0Item(grammar.getProductionAt(2), 0))); //S → .x
	}
	
	
	/**
	 * Tests the goTo method (with closure), using the grammar 3.20 from Appel's book.
	 */
	@Test public void goToTest()
	{
		Grammar grammar = new GrammarAppel_3_20_withSP().getGrammar();
		GrammarInfo grammarInfo = new GrammarInfo(grammar);
		//compute goto of state 8 with symbol "(" to state 3
		//as shown in figure 3.20.
		LR0State state = createLR0State(
			new LR0Item(grammar.getProductionAt(4), 2),  //S → L,.S
			new LR0Item(grammar.getProductionAt(1), 0),  //S → .(L)
			new LR0Item(grammar.getProductionAt(2), 0)); //S → .x
		LR0State goTo = state.goTo(edu.tum.cup2.test.GrammarAppel_3_20_withSP.Terminals.leftbr).closure(grammarInfo); //with closure
		//result must have 5 items
		assertEquals(5, goTo.items.size());
		assertTrue(goTo.items.contains(new LR0Item(grammar.getProductionAt(1), 1))); //S → (.L)
		assertTrue(goTo.items.contains(new LR0Item(grammar.getProductionAt(3), 0))); //L → .S
		assertTrue(goTo.items.contains(new LR0Item(grammar.getProductionAt(4), 0))); //L → .L, S
		assertTrue(goTo.items.contains(new LR0Item(grammar.getProductionAt(1), 0))); //S → .(L)
		assertTrue(goTo.items.contains(new LR0Item(grammar.getProductionAt(2), 0))); //S → .x
	}
	
	
	private LR0State createLR0State(LR0Item... items)
	{
		return new LR0State(ArrayTools.toHashSet(items));
	}
	

}

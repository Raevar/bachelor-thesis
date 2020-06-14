package edu.tum.cup2.generator.states;

import org.junit.Test;

import edu.tum.cup2.generator.GrammarInfo;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.terminals.TerminalSet;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.test.GrammarAppel_3_26;
import edu.tum.cup2.test.SpecJava14;
import edu.tum.cup2.util.ArrayTools;

import static edu.tum.cup2.grammar.SpecialTerminals.Placeholder;
import static edu.tum.cup2.test.GrammarAppel_3_26.T.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Test cases for the {@link LR1State} class.
 * 
 * @author Andreas Wenger
 */
public class LR1StateTest
{
	
	/**
	 * Tests the closure method, using the grammar 3.26 from Appel's book.
	 * Expected results are read from page 66.
	 */
	@Test public void closureTest()
	{
		Grammar grammar = new GrammarAppel_3_26().getWith$();
		GrammarInfo grammarInfo = new GrammarInfo(grammar);
		//compute closure of "S' → .S$" (production 0, position 0) as seen on page 66.
		LR1State state = createLR1State(
			new LR1Item(grammar.getProductionAt(0), 0, grammarInfo.getTerminalSet(Placeholder))); //S' → .S$
		LR1State closure = state.closure(new GrammarInfo(grammar));
		//result must have 8 items
		assertEquals(8, closure.getSimpleItemsCount());
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(0), 0, Placeholder)); //S → .S$ , ?
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(1), 0, $));  //S → .V=E , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(2), 0, $));  //S → .E   , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(3), 0, $));  //E → .V   , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(4), 0, $));  //V → .x   , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(5), 0, $));  //V → .*E  , $
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(4), 0, eq)); //V → .x   , =
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(5), 0, eq)); //S → .*E  , =
	}
	
	@Test public void recursiveCircularClosureTest(){
		Grammar grammar = new SpecJava14().getGrammar();
		GrammarInfo grammarInfo = new GrammarInfo(grammar);
		LR1State state = createLR1State(
				new LR1Item(grammar.getProductionAt(42),1,grammarInfo.getTerminalSet(Placeholder))
		);
		LR1State closure = state.closure(grammarInfo);
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(30), 0, SpecJava14.Terminals.SEMICOLON));
		assertTrue(closure.containsSimpleItem(grammar.getProductionAt(30), 0, SpecJava14.Terminals.DOT));
		assertTrue(!closure.containsSimpleItem(grammar.getProductionAt(30), 0, Placeholder));
	}
		

	
	/**
	 * Tests the goTo method (with closure), using the grammar 3.26 from Appel's book.
	 * Expected results are read from page 65. (google books)
	 * TODO: all page numbers from the same edition!!! give exact information
	 */
	@Test public void goToTest()
	{
		Grammar g = new GrammarAppel_3_26().getWith$();
		GrammarInfo grammarInfo = new GrammarInfo(g);
		TerminalSet t$ = grammarInfo.getTerminalSet($);
		//compute goto of state 3 with symbol "=" to state 4
		//as shown in figure 3.20.
		LR1State state = createLR1State(
			new LR1Item(g.getProductionAt(1), 1, t$),  //S → V.=E , $
			new LR1Item(g.getProductionAt(3), 1, t$)); //E → V.
		LR1State goTo = state.goTo(eq).closure(grammarInfo); //with closure
		//result must have 4 items
		assertEquals(4, goTo.items.size());
		assertTrue(goTo.items.contains(new LR1Item(g.getProductionAt(1), 2, t$))); //S → V=.E , $
		assertTrue(goTo.items.contains(new LR1Item(g.getProductionAt(3), 0, t$))); //E → .V   , $
		assertTrue(goTo.items.contains(new LR1Item(g.getProductionAt(4), 0, t$))); //V → .x   , $
		assertTrue(goTo.items.contains(new LR1Item(g.getProductionAt(5), 0, t$))); //V → .*E  , $
	}
	
	
	private LR1State createLR1State(LR1Item... items)
	{
		return new LR1State(ArrayTools.toHashSet(items));
	}
	

}

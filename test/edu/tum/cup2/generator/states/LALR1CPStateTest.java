package edu.tum.cup2.generator.states;

import static edu.tum.cup2.grammar.SpecialTerminals.Placeholder;
import static edu.tum.cup2.test.GrammarAppel_3_26.T.$;
import static edu.tum.cup2.test.GrammarAppel_3_26.T.eq;
import static edu.tum.cup2.util.CollectionTools.set;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.tum.cup2.generator.GrammarInfo;
import edu.tum.cup2.generator.items.CPGoToLink;
import edu.tum.cup2.generator.items.LALR1CPItem;
import edu.tum.cup2.generator.items.LR0Item;
import edu.tum.cup2.generator.terminals.EfficientTerminalSet;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.test.GrammarAppel_3_26;
import edu.tum.cup2.util.Tuple2;


/**
 * Test cases for the {@link LALR1CPState} class.
 * 
 * @author Andreas Wenger
 */
public class LALR1CPStateTest
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
		LALR1CPState state = createLALR1CPState(
			new LALR1CPItem(new LR0Item(grammar.getProductionAt(0), 0), grammarInfo.getTerminalSet(Placeholder))); //S' → .S$
		LALR1CPState closure = state.closure(grammarInfo);
		//result must have 8 items
		assertEquals(6, closure.getItemsCount());
		Set<LR0Item> closureKernels = closure.getStrippedItems();
		//S → .S$ , ? , no CP links
		LR0Item startKernel = new LR0Item(grammar.getProductionAt(0), 0);
		LR0Item kernel = startKernel;
		assertTrue(closureKernels.contains(kernel)); 
		LALR1CPItem closureItem = closure.getItemWithLookaheadByLR0Item(kernel);
		assertEquals(1, closureItem.getLookaheads().getTerminals().size());
		assertTrue(closureItem.getLookaheads().getTerminals().contains(Placeholder));
		assertEquals(0, closureItem.getClosureLinks().size());
		//S → .V=E , $ , no CP links
		kernel = new LR0Item(grammar.getProductionAt(1), 0);
		assertTrue(closureKernels.contains(kernel)); 
		closureItem = closure.getItemWithLookaheadByLR0Item(kernel);
		assertEquals(1, closureItem.getLookaheads().getTerminals().size());
		assertTrue(closureItem.getLookaheads().getTerminals().contains($));
		assertEquals(0, closureItem.getClosureLinks().size());
		//S → .E   , $ , CP link to E → .V
		kernel = new LR0Item(grammar.getProductionAt(2), 0);
		assertTrue(closureKernels.contains(kernel)); 
		closureItem = closure.getItemWithLookaheadByLR0Item(kernel);
		assertEquals(1, closureItem.getLookaheads().getTerminals().size());
		assertTrue(closureItem.getLookaheads().getTerminals().contains($));
		assertEquals(1, closureItem.getClosureLinks().size());
		assertTrue(closureItem.getClosureLinks().contains(new LR0Item(grammar.getProductionAt(3), 0)));
		//E → .V   , ∅ , CP links to V → .x and V → .*E
		kernel = new LR0Item(grammar.getProductionAt(3), 0);
		assertTrue(closureKernels.contains(kernel)); 
		closureItem = closure.getItemWithLookaheadByLR0Item(kernel);
		assertEquals(0, closureItem.getLookaheads().getTerminals().size());
		assertEquals(2, closureItem.getClosureLinks().size());
		assertTrue(closureItem.getClosureLinks().contains(new LR0Item(grammar.getProductionAt(4), 0)));
		assertTrue(closureItem.getClosureLinks().contains(new LR0Item(grammar.getProductionAt(5), 0)));
		//V → .x   , = , no CP links
		kernel = new LR0Item(grammar.getProductionAt(4), 0);
		assertTrue(closureKernels.contains(kernel)); 
		closureItem = closure.getItemWithLookaheadByLR0Item(kernel);
		assertEquals(1, closureItem.getLookaheads().getTerminals().size());
		assertTrue(closureItem.getLookaheads().getTerminals().contains(eq));
		assertEquals(0, closureItem.getClosureLinks().size());
		//V → .*E  , = , no CP links
		kernel = new LR0Item(grammar.getProductionAt(5), 0);
		assertTrue(closureKernels.contains(kernel)); 
		closureItem = closure.getItemWithLookaheadByLR0Item(kernel);
		assertEquals(1, closureItem.getLookaheads().getTerminals().size());
		assertTrue(closureItem.getLookaheads().getTerminals().contains(eq));
		assertEquals(0, closureItem.getClosureLinks().size());
	}
	
	
	/**
	 * Tests the goToCP method, using the grammar 3.26 from Appel's book.
	 * Expected results are read from page 65. (google books)
	 * TODO: all page numbers from the same edition!!! give exact information
	 */
	@Test public void goToCPTest()
	{
		Grammar g = new GrammarAppel_3_26().getWith$();
		GrammarInfo grammarInfo = new GrammarInfo(g);
		EfficientTerminalSet t$ = grammarInfo.getTerminalSet($);
		//compute goto of state 3 with symbol "=" to state 4
		//as shown in figure 3.20.
		LALR1CPState state = createLALR1CPState(
			new LALR1CPItem(new LR0Item(g.getProductionAt(1), 1), t$),  //S → V.=E , $
			new LALR1CPItem(new LR0Item(g.getProductionAt(3), 1), t$)); //E → V.   , ∅
		Tuple2<LALR1CPState, List<CPGoToLink>> goTo = state.goToCP(eq);
		//result must have 1 item and 1 CP link
		assertEquals(1, goTo.get1().getItemsCount());
		LR0Item targetItem = goTo.get1().getFirstItem().getLR0Item();
		assertTrue(new LR0Item(g.getProductionAt(1), 2).equals(targetItem));
		assertEquals(1, goTo.get2().size());
		assertTrue(goTo.get2().get(0).getTargetItem().equals(targetItem));
	}
	
	
	private LALR1CPState createLALR1CPState(LALR1CPItem... items)
	{
		Set<LR0Item> kernels = set();
		for (LALR1CPItem item : items)
			kernels.add(item.getLR0Item());
		return new LALR1CPState(Arrays.asList(items));
	}
	

}

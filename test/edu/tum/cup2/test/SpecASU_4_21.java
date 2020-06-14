package edu.tum.cup2.test;

import org.junit.Test;

import edu.tum.cup2.generator.Automaton;
import edu.tum.cup2.generator.GrammarInfo;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.states.LR1State;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;


import static edu.tum.cup2.grammar.SpecialTerminals.Placeholder;
import static edu.tum.cup2.test.SpecASU_4_21.NonTerminals.C;
import static edu.tum.cup2.test.SpecASU_4_21.NonTerminals.S;
import static edu.tum.cup2.test.SpecASU_4_21.NonTerminals.Sp;
import static edu.tum.cup2.test.SpecASU_4_21.Terminals.c;
import static edu.tum.cup2.test.SpecASU_4_21.Terminals.d;
import static edu.tum.cup2.util.ArrayTools.toHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Grammar 4.21 from ASU99, page 283 (german edition),
 * 
 * Used for testing the construction of the LR(1) DFA.
 * 
 * @author Andreas Wenger
 */
public class SpecASU_4_21
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
		c, d
	}
	
	public enum NonTerminals implements NonTerminal
	{
		Sp, S, C
	}

	
	public SpecASU_4_21()
	{
		grammar(
			prod(Sp,     rhs(S)),
			prod(S,      rhs(C, C)),
			prod(C,      rhs(c, C),
				           rhs(d)));
	}
	
	
	/**
	 * LR(1).
	 */
	@Test public void testLR1()
		throws Exception
	{
		Grammar grammar = getGrammar();
		GrammarInfo gi = new GrammarInfo(grammar);
		LR1Generator gen = new LR1Generator(this, Verbosity.Detailled, false);
		Automaton<LR1Item, LR1State> dfa = gen.createAutomaton();
		Production p0 = gen.getGrammar().getProductionAt(0); //S' → S
		Production p1 = gen.getGrammar().getProductionAt(1); //S → C C
		Production p2 = gen.getGrammar().getProductionAt(2); //C → c C
		Production p3 = gen.getGrammar().getProductionAt(3); //C → d
		//10 states
		assertEquals(10, dfa.getStates().size());
		//I0 (kernel and closure)
		LR1State kernel = new LR1State(toHashSet(new LR1Item(p0, 0, gi.getTerminalSet(Placeholder))));
		assertTrue(dfa.getStates().contains(kernel));
		assertEquals(kernel.closure(new GrammarInfo(grammar)), new LR1State(toHashSet(
			new LR1Item(p0, 0, gi.getTerminalSet(Placeholder)),
			new LR1Item(p1, 0, gi.getTerminalSet(Placeholder)),
			new LR1Item(p2, 0, gi.getTerminalSet(c).plus(d)),
			new LR1Item(p3, 0, gi.getTerminalSet(c).plus(d)))));
		//I1 (kernel)
		kernel = new LR1State(toHashSet(new LR1Item(p0, 1, gi.getTerminalSet(Placeholder))));
		assertTrue(dfa.getStates().contains(kernel));
		//I2 (kernel)
		kernel = new LR1State(toHashSet(new LR1Item(p1, 1, gi.getTerminalSet(Placeholder))));
		assertTrue(dfa.getStates().contains(kernel));
		//I3 (kernel and closure)
		kernel = new LR1State(toHashSet(new LR1Item(p2, 1, gi.getTerminalSet(c).plus(d))));
		assertTrue(dfa.getStates().contains(kernel));
		assertEquals(kernel.closure(new GrammarInfo(grammar)), new LR1State(toHashSet(
			new LR1Item(p2, 1, gi.getTerminalSet(c).plus(d)),
			new LR1Item(p2, 0, gi.getTerminalSet(c).plus(d)),
			new LR1Item(p3, 0, gi.getTerminalSet(c).plus(d)))));
		//I4 (kernel)
		kernel = new LR1State(toHashSet(new LR1Item(p3, 1, gi.getTerminalSet(c).plus(d))));
		assertTrue(dfa.getStates().contains(kernel));
		//I5 (kernel)
		kernel = new LR1State(toHashSet(new LR1Item(p1, 2, gi.getTerminalSet(Placeholder))));
		assertTrue(dfa.getStates().contains(kernel));
		//I6 (kernel)
		kernel = new LR1State(toHashSet(new LR1Item(p2, 1, gi.getTerminalSet(Placeholder))));
		assertTrue(dfa.getStates().contains(kernel));
		//I7 (kernel)
		kernel = new LR1State(toHashSet(new LR1Item(p3, 1, gi.getTerminalSet(Placeholder))));
		assertTrue(dfa.getStates().contains(kernel));
		//I8 (kernel)
		kernel = new LR1State(toHashSet(new LR1Item(p2, 2, gi.getTerminalSet(c).plus(d))));
		assertTrue(dfa.getStates().contains(kernel));
		//I9 (kernel)
		kernel = new LR1State(toHashSet(new LR1Item(p2, 2, gi.getTerminalSet(Placeholder))));
		assertTrue(dfa.getStates().contains(kernel));
	}
	

}


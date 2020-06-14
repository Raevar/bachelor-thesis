package edu.tum.cup2.generator;

import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.test.GrammarAppel_3_12.MyNonTerminal.X;
import static edu.tum.cup2.test.GrammarAppel_3_12.MyNonTerminal.Y;
import static edu.tum.cup2.test.GrammarAppel_3_12.MyNonTerminal.Z;
import static edu.tum.cup2.test.GrammarAppel_3_12.MyTerminal.a;
import static edu.tum.cup2.test.GrammarAppel_3_12.MyTerminal.c;
import static edu.tum.cup2.test.GrammarAppel_3_12.MyTerminal.d;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import edu.tum.cup2.generator.terminals.TerminalSet;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.test.GrammarASU_4_11;
import edu.tum.cup2.test.GrammarAppel_3_12;


/**
 * Tests for the {@link FirstSets} class.
 * 
 * @author Andreas Wenger
 */
public class FirstSetsTest
{
	
	/**
	 * Tests with {@link GrammarAppel_3_12}.
	 * Appel ignores epsilons in FIRST sets, but we do not
	 * (according to ASU99).
	 */
	@Test public void test1()
	{
		Grammar grammar = new GrammarAppel_3_12().get();
		FirstSets ret = new FirstSets(grammar, new NullableSet(grammar.getProductions()));
		//X
		TerminalSet set = ret.get(X);
		assertEquals(3, set.getTerminals().size());
		assertTrue(set.contains(Epsilon));
		assertTrue(set.contains(a));
		assertTrue(set.contains(c));
		//Y
		set = ret.get(Y);
		assertEquals(2, set.getTerminals().size());
		assertTrue(set.contains(Epsilon));
		assertTrue(set.contains(c));
		//Z
		set = ret.get(Z);
		assertEquals(3, set.getTerminals().size());
		assertTrue(set.contains(a));
		assertTrue(set.contains(c));
		assertTrue(set.contains(d));
	}
	
	
	/**
	 * Tests with {@link GrammarASU_4_11}.
	 * See ASU99, page 231 (german edition).
	 */
	@Test public void test2()
	{
		Grammar grammar = new GrammarASU_4_11().get();
		FirstSets ret = new FirstSets(grammar, new NullableSet(grammar.getProductions()));
		//E
		TerminalSet set = ret.get(GrammarASU_4_11.N.E);
		assertEquals(2, set.getTerminals().size());
		assertTrue(set.contains(GrammarASU_4_11.T.lbr));
		assertTrue(set.contains(GrammarASU_4_11.T.id));
		//T
		set = ret.get(GrammarASU_4_11.N.T);
		assertEquals(2, set.getTerminals().size());
		assertTrue(set.contains(GrammarASU_4_11.T.lbr));
		assertTrue(set.contains(GrammarASU_4_11.T.id));
		//F
		set = ret.get(GrammarASU_4_11.N.F);
		assertEquals(2, set.getTerminals().size());
		assertTrue(set.contains(GrammarASU_4_11.T.lbr));
		assertTrue(set.contains(GrammarASU_4_11.T.id));
		//Epr
		set = ret.get(GrammarASU_4_11.N.Epr);
		assertEquals(2, set.getTerminals().size());
		assertTrue(set.contains(GrammarASU_4_11.T.plus));
		assertTrue(set.contains(Epsilon));
		//Tpr
		set = ret.get(GrammarASU_4_11.N.Tpr);
		assertEquals(2, set.getTerminals().size());
		assertTrue(set.contains(GrammarASU_4_11.T.mult));
		assertTrue(set.contains(Epsilon));
	}

}

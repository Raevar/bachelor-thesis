package edu.tum.cup2.generator;

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
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.test.GrammarAppel_3_12;

/**
 * Tests for {@link FollowSets}
 * 
 * @author Gero
 * 
 */
public class FollowSetsTest
{
	@Test
	public void testFollowSets1()
	{
		Grammar grammar = new GrammarAppel_3_12().get();
		NullableSet nullableSet = new NullableSet(grammar.getProductions());
		FirstSets firstSets = new FirstSets(grammar, nullableSet);
		FollowSets followSets = new FollowSets(grammar, firstSets);
		
		// FOLLOW(X) = {a, c, d, $}
		TerminalSet set = followSets.get(X);
		assertEquals(4, set.getTerminals().size());
		assertTrue(set.contains(a));
		assertTrue(set.contains(c));
		assertTrue(set.contains(d));
		assertTrue(set.contains(SpecialTerminals.EndOfInputStream));
		
		// FOLLOW(Y) = {a, c, d, $}
		set = followSets.get(Y);
		assertEquals(4, set.getTerminals().size());
		assertTrue(set.contains(a));
		assertTrue(set.contains(c));
		assertTrue(set.contains(d));
		assertTrue(set.contains(SpecialTerminals.EndOfInputStream));
		
		// FOLLOW(Z) = {$}
		set = followSets.get(Z);
		assertEquals(1, set.getTerminals().size());
		assertTrue(set.contains(SpecialTerminals.EndOfInputStream));
	}
}

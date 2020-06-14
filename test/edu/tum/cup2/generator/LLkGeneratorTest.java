package edu.tum.cup2.generator;

import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import edu.tum.cup2.generator.exceptions.LLkGeneratorException;
import edu.tum.cup2.generator.items.LLkItem;
import edu.tum.cup2.generator.states.LLkState;
import edu.tum.cup2.generator.terminals.ITerminalSeqSet;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.precedences.Associativity;
import edu.tum.cup2.precedences.Precedences;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.GrammarLectureLLk;
import edu.tum.cup2.test.SpecMiniJavaNoActions;


/**
 * Tests for class {@link LLkGenerator}
 * 
 * @author Gero
 * 
 */
public class LLkGeneratorTest
{
	@Test
	public void testGrammarLectureLLk()
	{
		// Setup
		final Grammar gr = new GrammarLectureLLk().get().extendByAuxStartProduction();
		
		// Generate NFA
		final List<Associativity> associativity = Collections.emptyList();
		final Precedences precedences = new Precedences(associativity);
		final LLkGenerator gen;
		try
		{
			gen = new LLkGenerator(gr, precedences, Verbosity.None, 2);
		} catch (LLkGeneratorException e)
		{
			fail(e.getMessage());
			return;
		}
		
		// Check
		final NFA<LLkItem, LLkState> generatedAutomaton = gen.getAutomaton();
		final NFA<LLkItem, LLkState> expectedAutomaton = GrammarLectureLLk.getLL2Automaton();
		// System.out.println(generatedAutomaton.toString());
		// System.out.println(expectedAutomaton.toString());
		
		// Equality of NFAs
		NFATestTools.testEquals(expectedAutomaton, generatedAutomaton);
		
		// Test equality of the lookaheads
		testEqualLookaheads(expectedAutomaton.getStates(), generatedAutomaton.getStates());
	}
	
	
//	@Test
//	public void testGrammarSlidesLL1()
//	{
//		// Setup
//		final Grammar gr = new GrammarSlidesLL1().get().extendByAuxStartProduction();
//		
//		// Generate NFA
//		final List<Associativity> associativity = Collections.emptyList();
//		final Precedences precedences = new Precedences(associativity);
//		final LLkGenerator gen;
//		try
//		{
//			gen = new LLkGenerator(gr, precedences, Verbosity.None, 2);
//		} catch (LLkGeneratorException e)
//		{
//			fail(e.getMessage());
//			return;
//		}
//		
//		// Check
//		final NFA<LLkItem, LLkState> generatedAutomaton = gen.getAutomaton();
//		final NFA<LLkItem, LLkState> expectedAutomaton = GrammarSlidesLL1.getLL1Automaton();
//		// System.out.println(generatedAutomaton.toString());
//		// System.out.println(expectedAutomaton.toString());
//		
//		// Equality of NFAs
//		NFATestTools.testEquals(expectedAutomaton, generatedAutomaton);
//		
//		// Test equality of the lookaheads
//		testEqualLookaheads(expectedAutomaton.getStates(), generatedAutomaton.getStates());
//	}
	
	
	@Test
	public void testMiniJava()
	{
		// Get specification
		final CUP2Specification minijSpec = new SpecMiniJavaNoActions();
		final Grammar gr = minijSpec.getGrammar().extendByAuxStartProduction();
		
		try
		{
			new LLkGenerator(gr, minijSpec.getPrecedences(), Verbosity.None, 2);
			fail("MiniJava should not be parsable as it contains left recursion!");
		} catch (LLkGeneratorException e)
		{
			// Success
		}
	}
	
	
	private static void testEqualLookaheads(Set<LLkState> expectedStates, Set<LLkState> actualStates)
	{
		for (LLkState actState : actualStates)
		{
			final LLkState expState = get(expectedStates, actState);
			if (expState == actState)
			{
				continue; // Identity => lookaheads are equal
			} else
			{
				final ITerminalSeqSet expLookahead = expState.getLookahead();
				final ITerminalSeqSet genLookahead = actState.getLookahead();
				final boolean result = expLookahead.equals(genLookahead);
				if (!result)
				{
					fail("Expected state " + expState + " lookaheads doesn't match the generated lookaheads: " + actState);
				}
			}
		}
	}
	
	
	/**
	 * @param set
	 * @param equal
	 * @return The first item in the given {@link Set} that {@link Object#equals(Object)} the given one
	 */
	private static <S> S get(Set<S> set, S equal)
	{
		for (S item : set)
		{
			if (item.equals(equal))
			{
				return item;
			}
		}
		return null;
	}
}

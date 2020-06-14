package edu.tum.cup2.generator;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.tum.cup2.generator.exceptions.LLkGeneratorException;
import edu.tum.cup2.generator.items.LLkItem;
import edu.tum.cup2.generator.terminals.ITerminalSeq;
import edu.tum.cup2.generator.terminals.ITerminalSeqSet;
import edu.tum.cup2.generator.terminals.TerminalSeqSetf;
import edu.tum.cup2.generator.terminals.TerminalSeqf;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.parser.tables.TerminalTreeMap;
import edu.tum.cup2.test.GrammarLectureLLk;
import edu.tum.cup2.test.GrammarLectureLLk.T;


/**
 * Test for class {@link LookaheadGenerator}
 * 
 * @author Gero
 * 
 */
public class LookaheadGeneratorTest
{
	@Test
	public void testLookaheadGeneration()
	{
		Grammar gr = new GrammarLectureLLk().get();
		gr = gr.extendByAuxStartProduction();
		final LookaheadGenerator gen = new LookaheadGenerator(gr);
		
		// Test working
		testLookaheadForItem(gen, GrammarLectureLLk.K2.s_$aSb, 2, gr);
		testLookaheadForItem(gen, GrammarLectureLLk.K2.s_$e, 2, gr);
		testLookaheadForItem(gen, GrammarLectureLLk.K2.S_$s$, 2, gr);
		testLookaheadForItem(gen, GrammarLectureLLk.K2.s_a$Sb, 2, gr);
		testLookaheadForItem(gen, GrammarLectureLLk.K2.s_aS$b, 2, gr);
		testLookaheadForItem(gen, GrammarLectureLLk.K2.s_aSb$, 2, gr);
		testLookaheadForItem(gen, GrammarLectureLLk.K2.S_s$$, 2, gr);
		
		
		// Test exact
		final ITerminalSeq t_bb = new TerminalSeqf(T.b, T.b);
		final ITerminalSeq t_aa = new TerminalSeqf(T.a, T.a);
		final ITerminalSeq t_ab = new TerminalSeqf(T.a, T.b);
		final ITerminalSeq t_$ = new TerminalSeqf(SpecialTerminals.EndOfInputStream);
		final ITerminalSeq t_b$ = new TerminalSeqf(T.b, SpecialTerminals.EndOfInputStream);
		
		final ITerminalSeqSet la_aa_ab = new TerminalSeqSetf(t_aa, t_ab);
		final ITerminalSeqSet la_bb_b$_$ = new TerminalSeqSetf(t_bb, t_b$, t_$);
		final ITerminalSeqSet la_aa_ab_$ = new TerminalSeqSetf(t_aa, t_ab, t_$);
		final ITerminalSeqSet la_aa_ab_bb_b$ = new TerminalSeqSetf(t_aa, t_ab, t_bb, t_b$);
		final ITerminalSeqSet la_bb_b$ = new TerminalSeqSetf(t_bb, t_b$);
		final ITerminalSeqSet la_$ = new TerminalSeqSetf(t_$);
		
		testLookaheadForItemExact(gen, GrammarLectureLLk.K2.s_$aSb, 2, la_aa_ab);
		testLookaheadForItemExact(gen, GrammarLectureLLk.K2.s_$e, 2, la_bb_b$_$);
		testLookaheadForItemExact(gen, GrammarLectureLLk.K2.S_$s$, 2, la_aa_ab_$);
		testLookaheadForItemExact(gen, GrammarLectureLLk.K2.s_a$Sb, 2, la_aa_ab_bb_b$);
		testLookaheadForItemExact(gen, GrammarLectureLLk.K2.s_aS$b, 2, la_bb_b$);
		testLookaheadForItemExact(gen, GrammarLectureLLk.K2.s_aSb$, 2, la_bb_b$_$);
		testLookaheadForItemExact(gen, GrammarLectureLLk.K2.S_s$$, 2, la_$);
	}
	
	
	private static void testLookaheadForItem(LookaheadGenerator gen, LLkItem item, int laLength, Grammar gr)
	{
		try
		{
			final ITerminalSeqSet result = gen.calcLookahead(item, laLength).seal();
			
			final TerminalTreeMap<Boolean> resultTree = new TerminalTreeMap<Boolean>(gr);
			for (ITerminalSeq seq : result)
			{
				resultTree.put(seq, Boolean.TRUE);
			}
			
			final ITerminalSeqSet expectedResult = item.getLookaheads();
			for (ITerminalSeq seq : expectedResult)
			{
				assertTrue(resultTree.containsValueFor(seq));
			}
		} catch (LLkGeneratorException e)
		{
			fail(e.getMessage());
		}
	}
	
	
	private static void testLookaheadForItemExact(LookaheadGenerator gen, LLkItem item, int laLength,
			ITerminalSeqSet expectedResult)
	{
		try
		{
			final ITerminalSeqSet result = gen.calcLookahead(item, laLength).seal();
			
			assertTrue(result.equals(expectedResult));
		} catch (LLkGeneratorException e)
		{
			fail(e.getMessage());
		}
	}
}

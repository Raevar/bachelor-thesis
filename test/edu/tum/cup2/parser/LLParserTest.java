package edu.tum.cup2.parser;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import edu.tum.cup2.generator.LLkGenerator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.exceptions.LLkGeneratorException;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.parser.exceptions.LLkParserException;
import edu.tum.cup2.parser.tables.LLkParsingTable;
import edu.tum.cup2.precedences.Associativity;
import edu.tum.cup2.precedences.Precedences;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.test.GrammarLectureLLk;
import edu.tum.cup2.test.GrammarLectureLLk.T;


/**
 * Playground for the implementation of a the LL(k) parser
 * 
 * @author Gero
 * 
 */
public class LLParserTest
{
	@Test
	public void testLLkParserWithDefinedGrammar()
	{
		final Grammar gr = new GrammarLectureLLk().get().extendByAuxStartProduction();
		
		final LLkParsingTable tbl = new LLkParsingTable(gr, 2);
		LLkParsingTableTest.fillWithGrammarLectureLL2(tbl);
		
		test1(tbl);
	}
	
	
	@Test
	public void testLLkParserWithGeneratedGrammar()
	{
		final Grammar gr = new GrammarLectureLLk().get().extendByAuxStartProduction();
		
		final List<Associativity> preds = Collections.emptyList();
		final Precedences pred = new Precedences(preds);
		final LLkGenerator gen;
		try
		{
			gen = new LLkGenerator(gr, pred, Verbosity.None, 2);
		} catch (LLkGeneratorException err)
		{
			fail(err.getMessage());
			return;
		}
		
		test1(gen.getParsingTable());
	}
	
	
//	@Test
//	public void testLLkParserWithGeneratedGrammar2()
//	{
//		final Grammar gr = new GrammarSlidesLL1().get().extendByAuxStartProduction();
//		
//		final List<Associativity> preds = Collections.emptyList();
//		final Precedences pred = new Precedences(preds);
//		final LLkGenerator gen;
//		try
//		{
//			gen = new LLkGenerator(gr, pred, Verbosity.None, 2);
//		} catch (LLkGeneratorException err)
//		{
//			fail(err.getMessage());
//			return;
//		}
//		
//		// Parse SlidesLL1
//		final LLkParser parser = new LLkParser(gen.getParsingTable());
//		try
//		{
//			parser.parse(new TestScanner(GrammarSlidesLL1.testCase1()));
//		} catch (LLkParserException e)
//		{
//			fail(e.getMessage());
//		} catch (IOException e)
//		{
//			fail(e.getMessage());
//		}
//	}
	
	
	private void test1(LLkParsingTable tbl)
	{
		LLkParser parser = new LLkParser(tbl);
		
		// Should work
		try
		{
			parser.parse(new TestScanner(T.a, T.a, T.a, T.a, T.b, T.b, T.b, T.b, SpecialTerminals.EndOfInputStream));
		} catch (IOException e)
		{
			fail(e.getMessage());
		} catch (LLkParserException e)
		{
			fail(e.getMessage());
		}
		
		// One b too much
		parser = new LLkParser(tbl);
		try
		{
			parser.parse(new TestScanner(T.a, T.a, T.a, T.b, T.b, T.b, T.b, SpecialTerminals.EndOfInputStream));
			fail("aaabbbb is not part of this grammar!");
		} catch (IOException e)
		{
			fail(e.getMessage());
		} catch (LLkParserException e)
		{
		}
		
		// Missing b
		parser = new LLkParser(tbl);
		try
		{
			parser.parse(new TestScanner(T.a, T.a, T.a, T.b, T.b, SpecialTerminals.EndOfInputStream));
			fail("Accepted too early! aaabb is not part of this grammar!");
		} catch (IOException e)
		{
			fail(e.getMessage());
		} catch (LLkParserException e)
		{
			// Success!!
		}
	}
}

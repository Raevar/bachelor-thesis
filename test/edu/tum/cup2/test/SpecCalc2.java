package edu.tum.cup2.test;

import edu.tum.cup2.generator.LALR1SCCGenerator;
import java.io.File;
import java.io.IOException;

import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.exceptions.ShiftReduceConflict;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.exceptions.LRParserException;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.SpecCalc2.NonTerminals.*;
import static edu.tum.cup2.test.SpecCalc2.Terminals.*;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;


/**
 * Specification of a very simple calculator, that
 * computes x#y#...#z, # beeing + or -, given x, y, ... z
 * (LR(1)).
 * 
 * @author Andreas Wenger
 */
public class SpecCalc2
	extends CUP2Specification
{
	
	private final static boolean DEBUG = false;
	

	public enum Terminals implements Terminal
	{
		NUMBER, PLUS, MINUS
	}
	
	public enum NonTerminals implements NonTerminal
	{
		s, expr
	}
	
	//symbols with values
	public class NUMBER extends SymbolValue<Integer>{};
	public class expr   extends SymbolValue<Integer>{};

	
	public SpecCalc2()
	{
		grammar(
			prod(s,    rhs(expr),
			             new Action() { public int a(Integer result)
			               { if (DEBUG) System.out.println("Final result: " + result); return result; }}),
			prod(expr, rhs(expr, PLUS, NUMBER),
				           new Action() { public int a(Integer n1, Integer n2)
				             { if (DEBUG) System.out.println(n1 + " + " + n2); return n1 + n2; }},
				         rhs(expr, MINUS, NUMBER),
					         new Action() { public int a(Integer n1, Integer n2)
					           { if (DEBUG) System.out.println(n1 + " - " + n2); return n1 - n2; }},
				         rhs(NUMBER),
					         new Action() { public int a(Integer number)
					           { if (DEBUG) System.out.println(number); return number; }}));
	}
	
	
	
	/**
	 * LR(0) generator must fail.
	 */
	@Test public void testLR0()
	{
		try
		{
			LRParsingTable table = new LR0Generator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("calc2.html"));
			fail("Shift/reduce conflict expected!");
		}
		catch (ShiftReduceConflict ex)
		{
			System.out.println(ex.getMessage());
		}
		catch (GeneratorException ex)
		{
			fail("Wrong exception");
		}
	}
	
	
	/**
	 * LR(1) must work. Compute 13+25-39+2.
	 */
	@Test public void testCalc2()
		throws GeneratorException, LRParserException, IOException
	{
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("calc2.html")); //TEST
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(PLUS),
			terminal(NUMBER, 25),
			terminal(MINUS),
			terminal(NUMBER, 39),
			terminal(PLUS),
			terminal(NUMBER, 2)));
		//result must be 1
		assertEquals(1, result);
	}
	/**
	 * LR(1) must work. Compute 13+25-39+2.
	 */
	@Test public void testCalcLALR2()
		throws GeneratorException, LRParserException, IOException
	{
		LRParsingTable table = new LALR1SCCGenerator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("calc2.html")); //TEST
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(PLUS),
			terminal(NUMBER, 25),
			terminal(MINUS),
			terminal(NUMBER, 39),
			terminal(PLUS),
			terminal(NUMBER, 2)));
		//result must be 1
		assertEquals(1, result);
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new SpecCalc2());
	}
	
	@Test public void parse10times()
		throws GeneratorException, LRParserException, IOException
	{
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("calc2.html")); //TEST
		LRParser p = new LRParser(table);
		for (int i=0; i<10; i++){
			Object result = p.parse(new TestScanner(
				terminal(NUMBER, 13),
				terminal(PLUS),
				terminal(NUMBER, 25),
				terminal(MINUS),
				terminal(NUMBER, 39),
				terminal(PLUS),
				terminal(NUMBER, 2)));
			if (result==null || !result.equals(1)) fail("Failed on try: "+(i+1)+" -- result : "+result);
		}
	}


}


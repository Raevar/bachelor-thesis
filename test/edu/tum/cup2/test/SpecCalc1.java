package edu.tum.cup2.test;

import edu.tum.cup2.generator.LALR1SCCGenerator;
import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.SpecCalc1.NonTerminals.*;
import static edu.tum.cup2.test.SpecCalc1.Terminals.*;
import static org.junit.Assert.assertEquals;

/**
 * Specification of a very simple calculator, that
 * computes x+y, given x and y (LR(0)).
 * 
 * @author Andreas Wenger
 */
public class SpecCalc1
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
		NUMBER, PLUS
	}
	
	public enum NonTerminals implements NonTerminal
	{
		s
	}
	
	//symbols with values
	public class NUMBER extends SymbolValue<Integer>{};

	
	public SpecCalc1()
	{
		grammar(
			prod(s,  rhs(NUMBER, PLUS, NUMBER),
			           new Action() { public int a(Integer n1, Integer n2)
			            { return n1 + n2; }}
			)  /*end of production*/
		) /*end of grammar*/;
	}
	
	
	/**
	 * Compute 13+25.
	 */
	@Test public void testCalc1()
		throws Exception
	{
		LRParsingTable table = new LR0Generator(this).getParsingTable();
    LRParsingTableDump.dumpToHTML(table, new File("calc1_lr0.html")); //TEST
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(PLUS),
			terminal(NUMBER, 25)));
		//result must be 38
		assertEquals(38, result);
	}
	
	
	/**
	 * LR(1).
	 */
	@Test public void testLR1()
		throws Exception
	{
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("calc1.html")); //TEST
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(PLUS),
			terminal(NUMBER, 25)));
		//result must be 38
		assertEquals(38, result);
	}
	@Test public void testLALR1SCC()
		throws Exception
	{
			LRParsingTable table = new LALR1SCCGenerator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("calc4-lalr.html")); //TEST
        		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(PLUS),
			terminal(NUMBER, 25)));
		//result must be 38
		assertEquals(38, result);
	}	
	
	@Test public void testTime()
	{
		Time.measureTime(new SpecCalc1());
	}

}


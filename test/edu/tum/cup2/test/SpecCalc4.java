package edu.tum.cup2.test;

import edu.tum.cup2.generator.LALR1SCCGenerator;
import java.io.Serializable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import edu.tum.cup2.generator.LALR1Generator;
import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.LR1toLALRGenerator;
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
import static edu.tum.cup2.test.SpecCalc4.NonTerminals.*;
import static edu.tum.cup2.test.SpecCalc4.Terminals.*;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * Specification of a calculator for arithmetic terms using
 * +, -, *, /, ( and ) and integer terminals.
 * 
 * It is LR(1).
 * 
 * @author Andreas Wenger
 */
@SuppressWarnings("serial") //MH first try to get rid of warning caused through serializable Action
public class SpecCalc4
	extends CUP2Specification
	implements Serializable
{
	
	private final static long serialVersionUID = 1L;
	
	private final static boolean DEBUG = false;
	
	private String collect = "";
	
	public enum Terminals implements Terminal
	{
		NUMBER, PLUS, MINUS, MULT, DIV, LBR, RBR
	}
	
	public enum NonTerminals implements NonTerminal
	{
		expr
	}
	
	//symbols with values
	public class NUMBER extends SymbolValue<Integer>{};
	public class expr   extends SymbolValue<Integer>{};

	public SpecCalc4()
	{
		precedences(
			left(RBR),
			left(MULT, DIV),
			left(PLUS, MINUS));
		grammar(
			prod(expr,
			           rhs(NUMBER),
                       new Action() { 
									public int a(Integer n) { collect += n; return n; }},
			           rhs(expr, PLUS, expr),
					         new Action() { {}
									 public int a(Integer n1, Integer n2) { collect += n1 + " + " + n2; return n1 + n2; }},
					       rhs(expr, MINUS, expr),
					         new Action() {
									public int a(Integer n1, Integer n2) { return n1 - n2; }},
					       rhs(expr, MULT, expr),
					         new Action() { 
									public int a(Integer n1, Integer n2) { return n1 * n2; }},
					       rhs(expr, DIV, expr),
					         new Action() { 
									public int a(Integer n1, Integer n2) { return n1 / n2; }},
					       rhs(LBR, expr, RBR),
					         new Action() { 
									public int a(Integer n) { return n; }}));
					       
	}
	
	/**
	 * LR(0) generator must fail without precedences.
	 */
	@Test public void testLR0()
	{
		try
		{
		  precedences();
			new LR0Generator(this).getParsingTable();
			fail("Shift/reduce conflict expected!");
		}
		catch (ShiftReduceConflict ex)
		{
			//ok
		}
		catch (GeneratorException ex)
		{
			fail("Wrong exception");
		}
	}
	
	
	/**
	 * LR(1) must work. Compute 13+25-39+2.
	 */
	@Test public void testLR1_1()
		throws GeneratorException, LRParserException, IOException
	{
		try
		{
			LRParsingTable table = new LR1Generator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("calc4_lr0.html")); //TEST
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
		catch (GeneratorException ex)
		{
			fail(ex.getMessage());
		}
	}
	
	
	/**
	 * LR(1) must work. Compute (4).
	 */
	@Test public void testLR1_2()
		throws GeneratorException, LRParserException, IOException
	{
		try
		{
			LRParsingTable table = new LR1Generator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("calc4_lr1.html")); //TEST
			Object result = new LRParser(table).parse(new TestScanner(
				terminal(LBR),
				terminal(NUMBER, 4),
				terminal(RBR)));
			//result must be 4
			assertEquals(4, result);
		}
		catch (GeneratorException ex)
		{
			fail(ex.getMessage());
		}
	}
	
	
	/**
	 * LR(1) must work. Compute 13+25*23+4/2*((4+3)*2)*5.
	 */
	@Test public void testLR1_3()
		throws GeneratorException, LRParserException, IOException
	{
		try
		{
			LRParsingTable table = new LR1Generator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("calc4.html")); //TEST
			Object result = new LRParser(table).parse(new TestScanner(
				terminal(NUMBER, 13),
				terminal(PLUS),
				terminal(NUMBER, 25),
				terminal(MULT),
				terminal(NUMBER, 23),
				terminal(PLUS),
				terminal(NUMBER, 4),
				terminal(DIV),
				terminal(NUMBER, 2),
				terminal(MULT),
				terminal(LBR),
				terminal(LBR),
				terminal(NUMBER, 4),
				terminal(PLUS),
				terminal(NUMBER, 3),
				terminal(RBR),
				terminal(MULT),
				terminal(NUMBER, 2),
				terminal(RBR),
				terminal(MULT),
				terminal(NUMBER, 5)));
			//result must be 728
			assertEquals(728, result);
		}
		catch (GeneratorException ex)
		{
			fail(ex.getMessage());
		}
	}
	
	
	/**
	 * LR(1)-to-LALR must work. Compute 13+25-39+2.
	 */
	@Test public void testLR1toLALR_1()
		throws GeneratorException, LRParserException, IOException
	{
		try
		{
			LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("calc4-lalr.html")); //TEST
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
		catch (GeneratorException ex)
		{
			fail(ex.getMessage());
		}
	}
	
	
	/**
	 * LALR(1) must work. Compute 13+25-39+2.
	 */
	@Test public void testLALR1()
		throws GeneratorException, LRParserException, IOException
	{
		try
		{
			LRParsingTable table = new LALR1Generator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("calc4-lalr.html")); //TEST
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
		catch (GeneratorException ex)
		{
			fail(ex.getMessage());
		}
	}
	/**
	 * LALR(1) must work. Compute 13+25-39+2.
	 */
	@Test public void testLALR1SCC()
		throws GeneratorException, LRParserException, IOException
	{
		try
		{
			LRParsingTable table = new LALR1SCCGenerator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("calc4-lalr.html")); //TEST
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
		catch (GeneratorException ex)
		{
			fail(ex.getMessage());
		}
	}

	
	@Test public void testTime()
	{
		Time.measureTime(new SpecCalc4());
	}
	
	
	@Test public void testJFlexScanner()
	{
		try
		{
			LRParsingTable table = new LR1Generator(this).getParsingTable();
			Object result = new LRParser(table).parse(
				new SpecCalc4Scanner(new FileReader("SpecCalc4TestInput.txt")));
			//result must be 728
			assertEquals(728, result);
		}
		catch (Exception ex)
		{
			fail(ex.getMessage());
		}
	}

}


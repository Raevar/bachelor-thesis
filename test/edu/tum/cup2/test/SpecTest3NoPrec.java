package edu.tum.cup2.test;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.exceptions.GeneratorException;
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
import static edu.tum.cup2.test.SpecTest3NoPrec.NonTerminals.*;
import static edu.tum.cup2.test.SpecTest3NoPrec.Terminals.*;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * ^, *, + grammar, using no precedences.
 * 
 * @author Andreas Wenger
 */
public class SpecTest3NoPrec
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		INT, POW, MUL, PLUS, LBR, RBR
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		term, pow, mul, plus, brackets
	}
	
	//symbols with values
	public class INT extends SymbolValue<Integer>{};
	public class term extends SymbolValue<Integer>{};
	public class pow extends SymbolValue<Integer>{};
	public class mul extends SymbolValue<Integer>{};
	public class plus extends SymbolValue<Integer>{};
	public class brackets extends SymbolValue<Integer>{};

	
	public SpecTest3NoPrec()
	{
		grammar(
			prod(term,
                    rhs(plus),
                    new Action() { public int a(Integer n) { return n; }}),
      prod(plus,
                    rhs(mul),
                    new Action() { public int a(Integer n) { return n; }},
                    rhs(plus, PLUS, mul),
                    new Action() { public int a(Integer n1, Integer n2) { return n1 + n2; }}),
      prod(mul,
                    rhs(pow),
                    new Action() { public int a(Integer n) { return n; }},
                    rhs(mul, MUL, pow),
                    new Action() { public int a(Integer n1, Integer n2) { return n1 * n2; }}),
			prod(pow,
				            rhs(brackets),
				            new Action() { public int a(Integer n) { return n; }},
				            rhs(pow, POW, brackets),
				            new Action() { public int a(Integer n1, Integer n2) { return (int) Math.pow(n1, n2); }}),
			prod(brackets,
					          rhs(INT),
					          new Action() { public int a(Integer n) { return n; }},
					          rhs(LBR, term, RBR),
					          new Action() { public int a(Integer n) { return n; }})	            
		);
	}

	
	
	/**
	 * LR(1) must work.
	 */
	@Test public void testLR1()
		throws Exception
	{
		try
		{
			LRParsingTable table = new LR1Generator(this, Verbosity.Detailled).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("SpecTest3NoPrec.html")); //TEST
			//check generated parser with a small computation
			Integer result = (Integer) new LRParser(table).parse(new TestScanner(
				terminal(INT, 32),
				terminal(PLUS),
				terminal(INT, 4),
				terminal(POW),
				terminal(LBR),
				terminal(INT, 2),
				terminal(MUL),
				terminal(INT, 3),
				terminal(RBR),
				terminal(MUL),
				terminal(INT, 3),
				terminal(PLUS),
				terminal(INT, 500)));
			//result must be 32 + 4 ^ (2 * 3) * 3 + 500 = 12820
			assertEquals(new Integer(12820), result);
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
			fail();
		}
	}

	
	/**
	 * LR(1)-to-LALR must work.
	 */
	@Test public void testLR1toLALR()
	{
		try
		{
			LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("SpecTest3NoPrec-lalr.html")); //TEST
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
			fail();
		}
	}

}
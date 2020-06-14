package edu.tum.cup2.test.lr1blowup;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
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
import edu.tum.cup2.test.Time;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.lr1blowup.Spec1NoPrec.NonTerminals.*;
import static edu.tum.cup2.test.lr1blowup.Spec1NoPrec.Terminals.*;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * Grammar to test LR(1)-blowup.
 * 
 * @author Andreas Wenger
 */
public class Spec1NoPrec
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		TERMINAL, LBR, RBR, OP1
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		term, brackets, op1
	}

	//symbols with values
	public class TERMINAL extends SymbolValue<Integer>{};
	public class term extends SymbolValue<Integer>{};
	public class brackets extends SymbolValue<Integer>{};
	public class op1 extends SymbolValue<Integer>{};
	
	
	public Spec1NoPrec()
	{
		grammar(
			prod(term,
                    rhs(op1),
                    new Action() { public int a(Integer n) { return n; }}),
			prod(op1,
				            rhs(brackets),
				            new Action() { public int a(Integer n) { return n; }},
				            rhs(op1, OP1, brackets),
				            new Action() { public int a(Integer n1, Integer n2) { return n1 + n2; }}),
			prod(brackets,
					          rhs(TERMINAL),
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
			LRParsingTableDump.dumpToHTML(table, new File("lr1blowup.Spec1NoPrec.html")); //TEST
			//check generated parser with a small computation
			Integer result = (Integer) new LRParser(table).parse(new TestScanner(
				terminal(TERMINAL, 32),
				terminal(OP1),
				terminal(LBR),
				terminal(LBR),
				terminal(TERMINAL, 5),
				terminal(OP1),
				terminal(TERMINAL, 3),
				terminal(RBR),
				terminal(OP1),
				terminal(TERMINAL, 10),
				terminal(RBR),
				terminal(OP1),
				terminal(TERMINAL, 3)));
			//result must be 32 + ((5 + 3) + 10) + 3 = 53
			assertEquals(new Integer(53), result);
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new Spec1NoPrec());
	}


}
package edu.tum.cup2.test;

import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.grammar.SpecialTerminals.$a;
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.SpecActions2.NonTerminals.*;
import static edu.tum.cup2.test.SpecActions2.Terminals.*;
import static org.junit.Assert.assertEquals;

/**
 * Specification of a very simple calculator, to
 * test embedded actions. LR(0).
 * 
 * @author Andreas Wenger
 */
public class SpecActions2
	extends CUP2Specification
{
	
	private int testSum = 0;
	

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

	
	public SpecActions2()
	{
		grammar(
			prod(s,  rhs(NUMBER, $a, PLUS, $a, NUMBER),
		             new Action() { public void a(Integer n) { testSum += n; }},
		             new Action() { public void a(Integer n) { testSum += n; }},
			           new Action() { public void a(Integer n1, Integer n2) { testSum += (n1 + n2) * 2; }}));
	}
	
	
	/**
	 * LR(0).
	 */
	@Test public void testLR0()
		throws Exception
	{
		LRParsingTable table = new LR0Generator(this).getParsingTable();
		testSum = 0;
		new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 3),
			terminal(PLUS),
			terminal(NUMBER, 5)));
		//result must be 3 + 3 + (3 + 5) * 2 = 22
		assertEquals(22, testSum);
	}
	
	
	/**
	 * LR(1).
	 */
	@Test public void testLR1()
		throws Exception
	{
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		testSum = 0;
		new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 3),
			terminal(PLUS),
			terminal(NUMBER, 5)));
		//result must be 3 + 3 + (3 + 5) * 2 = 22
		assertEquals(22, testSum);
	}
	
	
	

}


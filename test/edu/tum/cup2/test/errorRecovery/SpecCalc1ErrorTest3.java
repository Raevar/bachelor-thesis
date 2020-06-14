package edu.tum.cup2.test.errorRecovery;

import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.errorRecovery.SpecCalc1ErrorTest3.NonTerminals.*;
import static edu.tum.cup2.test.errorRecovery.SpecCalc1ErrorTest3.Terminals.*;


/**
 * Specification of a very simple calculator, that
 * computes x+y, given x and y (LR(0)).
 * 
 * @author Stefan Dangl
 */
public class SpecCalc1ErrorTest3
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
		NUMBER, PLUS, MINUS
	}
	
	public enum NonTerminals implements NonTerminal
	{
		s
	}
	
	//symbols with values
	public class NUMBER extends SymbolValue<Integer>{};

	
	@SuppressWarnings("serial")
	public SpecCalc1ErrorTest3()
	{
		grammar(
			prod(s,  rhs(NUMBER, PLUS, NUMBER),
      	new Action() {
					@SuppressWarnings("unused")
					public Integer a(Integer n1, Integer n2)
						{ return n1 + n2; }
				}
			),
			prod(s,  rhs(NUMBER, MINUS, NUMBER),
      	new Action() {
					@SuppressWarnings("unused")
					public Integer a(Integer n1, Integer n2)
						{ return n1 + n2; }
				}
			)
		);
	}
	
	
	/**
	 * LR(0) : 13 +
	 */
	@Test(expected=edu.tum.cup2.parser.exceptions.EndOfInputstreamException.class)
	public void testCalc1()
		throws Exception
	{
		System.out.println("testCalc1()");
		LRParsingTable table = new LR0Generator(this).getParsingTable();
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(PLUS)
		));
	}
	
	/**
	 * LR(0) : 13 + + 2
	 */
	@Test(expected=edu.tum.cup2.parser.exceptions.MissingErrorRecoveryException.class)
	public void testCalc2()
		throws Exception
	{
		System.out.println("testCalc2()");
		LRParsingTable table = new LR0Generator(this).getParsingTable();
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(PLUS),
			terminal(PLUS),
			terminal(NUMBER, 2)
		));
	}
	
	/**
	 * LR(0) : 13 2
	 */
	@Test(expected=edu.tum.cup2.parser.exceptions.MissingErrorRecoveryException.class)
	public void testCalc3()
		throws Exception
	{
		System.out.println("testCalc3()");
		LRParsingTable table = new LR0Generator(this).getParsingTable();
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(NUMBER, 2)
		));
	}
	
	

}


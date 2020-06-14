package edu.tum.cup2.test.errorRecovery;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.ParserInterface;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.errorRecovery.SpecCalc1ErrorTest.NonTerminals.*;
import static edu.tum.cup2.test.errorRecovery.SpecCalc1ErrorTest.Terminals.*;
import static org.junit.Assert.assertEquals;

/**
 * Specification of a strange error-catching calculator,
 * which for input
 * 	a+b
 * 	c+d
 * 	...
 * returns
 * 	(a+b)+(c+d)+...
 * 
 * Tests especially error-recovery with LR(0).
 * 
 * @author Stefan Dangl
 */
public class SpecCalc1ErrorTest
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
		NUMBER, PLUS, EOL, MINUS
	}
	
	public enum NonTerminals implements NonTerminal
	{
		s, line
	}
	
	//symbols with values
	public class NUMBER extends SymbolValue<Integer>{};
	public class line extends SymbolValue<Integer>{};
	public class s extends SymbolValue<Integer>{};

	
	public SpecCalc1ErrorTest()
	{
	  
	  register(
	      new ParserInterface()
	      {
	        @Override
	        public int getErrorSyncSize() {
	          return 1; // we only use very simple constructs 
	        }
	      }
	  );
	  
		grammar(
			prod(s,  	rhs(),
        new Action() { @SuppressWarnings("unused")
				public Integer a()
           { return 0; }}
			),
			prod(s,  	rhs(line,s),
		      new Action() { @SuppressWarnings("unused")
					public Integer a(Integer n1, Integer n2)
		         { return n1+n2; }}
			),
			
			prod(line,  	rhs(NUMBER, PLUS, NUMBER, EOL),
			          new Action() { @SuppressWarnings("unused")
								public Integer a(Integer n1, Integer n2)
			             { return n1 + n2; }}
			),
			prod(line,  	rhs(NUMBER, MINUS, NUMBER, EOL),
          new Action() { @SuppressWarnings("unused")
					public Integer a(Integer n1, Integer n2)
             { return n1 - n2; }}
			),

			prod(line,  	rhs(SpecialTerminals.Error, EOL),
          new Action() { @SuppressWarnings("unused")
						public Integer a(ErrorInformation x)
						{/*
          	 	System.out.println("Bad input in line!");
          	 	System.out.println("Popped Values:");
          	 	for(Object o:x.getPoppedValues())
          	 		System.out.println("  "+o);
          	 	System.out.println("Correct tokens:");
          	 	for(ScannerToken<Object> o:x.getCorrectTokens())
          	 		System.out.println("  "+o);
          	 	System.out.println("Expected terminals:");
          	 	for(Terminal t:x.getExpectedTerminals())
          	 		System.out.println("  "+t);
          	 	System.out.println("Bad tokens:");
          	 	for(ScannerToken<Object> o:x.getBadTokens())
          	 		System.out.println("  "+o);*/
          	 	System.out.println("Bad input before EOL : "+x);
             	return 0; 
						}
					}
			)
			
		);
	}
	
	
	/**
	 * LR(0) :  + 25 \n
	 */
	/*
	@Test public void testLR0_001()
		throws Exception
	{
		System.out.println("testLR0_001() : \n + 25 \n");
		LRParsingTable table = new LR0Generator(getGrammar()).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("SpecCalc1ErrorTest_LR0.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(PLUS),
			terminal(NUMBER, 25),
			terminal(EOL)
		));
		assertEquals(null, result);
	}*/
	/**
	 * LR(0) :  1 + 25
	 *//*
	@Test(expected=edu.tum.cup2.parser.exceptions.MissingErrorRecoveryException.class)
	public void testLR0_002()
		throws Exception
	{
		System.out.println("testLR0_002() : \n 1 + 25 \n ");
		LRParsingTable table = new LR0Generator(getGrammar()).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("SpecCalc1ErrorTest_LR0.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 1),
			terminal(PLUS),
			terminal(NUMBER, 25)
		));
		assertEquals(null, result);
	}*/


  /**
	 * LR(1) :  + 25 \n
	 */
	@Test public void testLR1_001()
		throws Exception
	{
		System.out.println("\ntestLR1_001() : \n + 25");
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("SpecCalc1ErrorTest_LR1.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(PLUS),
			terminal(NUMBER, 25),
			terminal(EOL)
		));
		
		assertEquals(0, result);
	}
	
	
	/**
	 * LR(1) :  13 25 \n
	 */
	@Test public void testLR1_002()
		throws Exception
	{
		System.out.println("\ntestLR1_002() : \n 13 25");
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("SpecCalc1ErrorTest_LR1.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(NUMBER, 25),
			terminal(EOL)
		),true);
		assertEquals(0, result);
	}
	
	/**
	 * LR(1) :     1 2 \n 3 4 \n
	 *  first error -^     ^- second error
	 *  error-distance: 3
	 */
	@Test public void testLR1_doubleError()
		throws Exception
	{
		System.out.println("\ntestLR1_doubleError() : \n 1 2 \n 3 4");
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("SpecCalc1ErrorTest_LR1.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 1),
			terminal(NUMBER, 2),
			terminal(EOL),
			terminal(NUMBER, 3),
			terminal(NUMBER, 4),
			terminal(EOL)
		), true);
		assertEquals(0, result);
	}
	
	/**
	 * LR(1) :  13 + 25
	 * -> Exception, because recovery-symbol EOL does not occur.
	 */
	@Test(expected=edu.tum.cup2.parser.exceptions.EndOfInputstreamException.class)
	public void testLR1_003()
		throws Exception
	{
		System.out.println("\ntestLR1_003() : \n 13 + 25");
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("SpecCalc1ErrorTest_LR1.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 13),
			terminal(PLUS),
			terminal(NUMBER, 25)
		));
	}
	
	/**
	 * LR(1) :     1 2 \n 3 + 4 \n 7 + 8 \n \n + \n 15 - 2 \n
	 */
	@Test public void testLR1_multipleErrors()
		throws Exception
	{
		System.out.println("\ntestLR1_multipleErrors() : \n 1 2 \n 3 + 4 \n 7 + 8 \n \n + \n 15 - 2");
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("SpecCalc1ErrorTest_LR1.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(NUMBER, 1,  0, 0),
			terminal(NUMBER, 2,  0, 1),
			terminal(EOL,        0, 2),
			terminal(NUMBER, 3,  1, 0),
			terminal(PLUS,       1, 1),
			terminal(NUMBER, 4,  1, 2),
			terminal(EOL,        1, 3),
			terminal(NUMBER, 7,  2, 0),
			terminal(PLUS,       2, 1),
			terminal(NUMBER, 8,  2, 2),
			terminal(EOL,        2, 3),
			terminal(EOL,        3, 0),
			terminal(PLUS,       4, 0),
			terminal(EOL,        4, 1),
			terminal(NUMBER, 15, 5, 0),
			terminal(MINUS,      5, 1),
			terminal(NUMBER, 2,  5, 2),
			terminal(EOL,        5, 3)
		), true);
		assertEquals(0+(3+4)+(7+8)+0+0+(15-2), result);
	}
	

	/**
	 * This method was written, because a reduction is possible, but
	 * an error at the beginning of the next line caused the parser
	 * to create error of both lines :(
	 * 
	 * LR(1) :     3 + 4 \n - \n + \n
	 */
	@Test public void testLR1_reduceInFaceOfError()
	throws Exception
{
	System.out.println("\ntestLR1_reduceInFaceOfError() : \n 3 + 4 \n - \n +");
	LRParsingTable table = new LR1Generator(this).getParsingTable();
	LRParsingTableDump.dumpToHTML(table, new File("SpecCalc1ErrorTest_LR1.html"));
	Object result = new LRParser(table).parse(new TestScanner(
		terminal(NUMBER, 3),
		terminal(PLUS),
		terminal(NUMBER, 4),
		terminal(EOL),
		terminal(MINUS),
		terminal(EOL),
		terminal(PLUS),
		terminal(EOL)
	));
	assertEquals((3+4)+0+0, result);
}

}


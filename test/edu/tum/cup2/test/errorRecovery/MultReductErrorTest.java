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
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.semantics.SymbolValue.NoValue;
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.errorRecovery.MultReductErrorTest.NonTerminals.*;
import static edu.tum.cup2.test.errorRecovery.MultReductErrorTest.Terminals.*;
import static org.junit.Assert.assertEquals;

public class MultReductErrorTest
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
		a, b, c, d
	}
	
	public enum NonTerminals implements NonTerminal
	{
		S, X, Y
	}
	
	public MultReductErrorTest()
	{
		grammar(
			prod(S,  	rhs(X,c),
        new Action() {
					public void a()
          { System.out.println("S->Xc"); }
        }
			),
			prod(S,  	rhs(Y,d),
	      new Action() {
					public void a()
			    { System.out.println("S->Yd"); }
	      }
			),

			prod(S,  	rhs(SpecialTerminals.Error),
        new Action() {
					public void a(ErrorInformation x)
					{
        	 	System.out.println(x);
					}
				}
			),
			
			prod(X,  	rhs(a,b),
	      new Action() {
					public void a()
	        { System.out.println("X->ab"); }
	      }
			),
			prod(X,  	rhs(SpecialTerminals.Error),
	        new Action() {
					public void a(ErrorInformation x)
	           { System.out.println(x); }}
				),
			prod(Y,  	rhs(a,b),
		      new Action() {
						public void a()
		        { System.out.println("Y->ab"); }
		      }
			),
			prod(Y,  	rhs(SpecialTerminals.Error),
	        new Action() {
					public void a(ErrorInformation x)
	           { System.out.println(x); }}
			)

			
		);
	}
	
	@Test public void testValid1()
	throws Exception
	{
		System.out.println("testValid1() : a b c");
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("MultReductErrorTest.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(a),
			terminal(b),
			terminal(c)
		));
		assertEquals(NoValue, result);
	}
	
	@Test public void testValid2()
	throws Exception
	{
		System.out.println("testValid2() : a b d");
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("MultReductErrorTest.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(a),
			terminal(b),
			terminal(d)
		));
		assertEquals(NoValue, result);
	}
	
	@Test public void testInvalid1()
	throws Exception
	{
		System.out.println("testInvalid1() : d b a");
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("MultReductErrorTest.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(d),
			terminal(b),
			terminal(a)
		),false);
		assertEquals(NoValue, result);
	}
	
	@Test public void testInvalid2()
	throws Exception
	{
		System.out.println("testInvalid2() : a b a");
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("MultReductErrorTest.html"));
		Object result = new LRParser(table).parse(new TestScanner(
			terminal(a),
			terminal(b),
			terminal(a)
		),true);
		assertEquals(NoValue, result);
	}
}


package edu.tum.cup2.test.errorRecovery;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.errorRecovery.MultReductErrorTest2.NonTerminals.*;
import static edu.tum.cup2.test.errorRecovery.MultReductErrorTest2.Terminals.*;

public class MultReductErrorTest2
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
		a, b, c, d, e
	}
	
	public enum NonTerminals implements NonTerminal
	{
		S, X, Y
	}
	
	//symbols with values
	/*
	public class A extends SymbolValue<Integer>{};
	public class B extends SymbolValue<Integer>{};
	public class s extends SymbolValue<Integer>{};
	public class a extends SymbolValue<Integer>{};
	public class b extends SymbolValue<Integer>{};
	 */
	
	public MultReductErrorTest2()
	{
		grammar(
			prod(S,  	rhs(X),
        new Action() {
					public void a()
          { System.out.println("S->X"); }
        }
			),
			prod(S,  	rhs(Y),
	      new Action() {
					public void a()
			    { System.out.println("S->Y"); }
	      }
			),
/*
			prod(S,  	rhs(SpecialTerminals.Error),
        new Action() {
					public void a(ErrorInformation x)
					{
        	 	System.out.println(x);
					}
				}
			),
			*/
			prod(X,  	rhs(a,b,c),
	      new Action() {
					public void a()
	        { System.out.println("X->abc"); }
	      }
			),
			prod(X,  	rhs(SpecialTerminals.Error),
	        new Action() {
					public void a(ErrorInformation x)
	           { System.out.println(x); }}
				),
			prod(Y,  	rhs(a,b,c,d,e),
		      new Action() {
						public void a()
		        { System.out.println("Y->abcde"); }
		      }
			),
			prod(Y,  	rhs(SpecialTerminals.Error),
	        new Action() {
					public void a(ErrorInformation x)
	           { System.out.println(x); }}
			)

			
		);
	}
	/**
	 * Should throw Reduce-Reduce-Conflict
	 **/
	@Test(expected=edu.tum.cup2.generator.exceptions.ReduceReduceConflict.class)
	public void test()
	throws Exception
	{
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("MultReductErrorTest2.html"));
		junit.framework.Assert.fail();
	}
}


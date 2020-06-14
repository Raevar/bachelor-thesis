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
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.SpecTest2.NonTerminals.*;
import static edu.tum.cup2.test.SpecTest2.Terminals.*;
import static junit.framework.Assert.fail;


/**
 * Meaningless grammar that was created to find a bug in the LR generator,
 * which lets the generator produce more states than needed.
 * 
 * @author Andreas Wenger
 */
public class SpecTest2
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		LPAR, RPAR, NOT, AND, TRUE
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		expr, cond
	}

	
	public SpecTest2()
	{
		precedences(left(AND));
		grammar(
			prod(cond,
				            rhs(LPAR, cond, RPAR),
				            rhs(NOT, expr),
				            rhs(cond, AND, cond)),
			prod(expr,
				            rhs(TRUE),
				            rhs(LPAR, expr, RPAR))
		);
	}

	
	
	/**
	 * LR(1) must work.
	 */
	@Test public void testLR1()
	{
		try
		{
			LRParsingTable table = new LR1Generator(this, Verbosity.Detailled).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("SpecTest2.html")); //TEST
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
			LRParsingTableDump.dumpToHTML(table, new File("SpecTest2-lalr.html")); //TEST
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
			fail();
		}
	}

}
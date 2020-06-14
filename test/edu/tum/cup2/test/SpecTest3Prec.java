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
import static edu.tum.cup2.test.SpecTest3Prec.NonTerminals.*;
import static edu.tum.cup2.test.SpecTest3Prec.Terminals.*;
import static junit.framework.Assert.fail;

/**
 * ^, *, + grammar, using precedences.
 * 
 * @author Andreas Wenger
 */
public class SpecTest3Prec
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
		term
	}

	
	public SpecTest3Prec()
	{
		precedences(left(POW, MUL, PLUS)); //left is wrong for POW, but doesn't matter here
		grammar(
			prod(term,
				            rhs(INT),
				            rhs(term, PLUS, term),
				            rhs(term, MUL, term),
				            rhs(term, POW, term),
				            rhs(LBR, term, RBR))
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
			LRParsingTableDump.dumpToHTML(table, new File("SpecTest3Prec.html")); //TEST
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
			LRParsingTableDump.dumpToHTML(table, new File("SpecTest3Prec-lalr.html")); //TEST
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
			fail();
		}
	}

}
package edu.tum.cup2.test;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.SpecTest1.NonTerminals.*;
import static edu.tum.cup2.test.SpecTest1.Terminals.*;
import static junit.framework.Assert.fail;

/**
 * Meaningless grammar that was created to find a bug in the old LR generator,
 * which lets the generator produce more states than needed.
 * 
 * It contains two groups of balanced brackets with each an END-symbol
 * in the middle, e.g.
 * (((((END)))))(((END)))
 * 
 * @author Andreas Wenger
 */
public class SpecTest1
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		LBR, RBR, END
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		start, inbrackets
	}

	
	public SpecTest1()
	{
		grammar(
			prod(start,
				            rhs(inbrackets, inbrackets)),
			prod(inbrackets,
				            rhs(END),
				            rhs(LBR, inbrackets, RBR))
		);		       
	}
	
	
	/**
	 * LR(1) must work.
	 */
	@Test public void testLR1()
	{
		try
		{
			LRParsingTable table = new LR1Generator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("SpecTest1.html")); //TEST
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
			LRParsingTableDump.dumpToHTML(table, new File("SpecTest1-lalr.html")); //TEST
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
			fail();
		}
	}

}
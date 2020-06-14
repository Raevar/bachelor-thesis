package edu.tum.cup2.test;

import edu.tum.cup2.io.GraphBuilderVisitor;
import edu.tum.cup2.generator.LR1Generator;
import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.exceptions.ShiftReduceConflict;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.SpecAppel_3_23.NonTerminals.*;
import static edu.tum.cup2.test.SpecAppel_3_23.Terminals.*;
import static junit.framework.Assert.fail;


/**
 * Grammar 3.23 (SLR) from Appel's book, page 64.
 * 
 * @author Andreas Wenger
 */
public class SpecAppel_3_23
	extends CUP2Specification
{
	
	public enum Terminals implements Terminal
	{
		x, PLUS, $
	}
	
	public enum NonTerminals implements NonTerminal
	{
		S, E, T
	}


	public SpecAppel_3_23()
	{
		grammar(
			prod(S,  rhs(E, $)),
			prod(E,  rhs(T, PLUS, E),
				       rhs(T)),
			prod(T,  rhs(x)));
	}
	
        @Test public void testLR1()
	{
		try
		{
                        LR1Generator gen = new LR1Generator(this);
			LRParsingTable table = gen.getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("appel_3_23-LR1.html"));
                        GraphBuilderVisitor gbvLALR1 = new GraphBuilderVisitor();
                        gbvLALR1.visit(gen.getAutomaton());
                        gbvLALR1.saveGraphVizInput(this.getClass().getName()+"-LR1.dot");

		}
		catch (ShiftReduceConflict ex)
		{
			fail("No Shift/reduce conflict expected!");
                        //ok
		}
		catch (GeneratorException ex)
		{
			fail("Wrong exception");
		}
	}	
	
	/**
	 * Test with LR(0) generator: Must fail with shift/reduce-conflict.
	 */
	@Test public void testLR0()
	{
		try
		{
			LRParsingTable table = new LR0Generator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("appel_3_23-LR0.html"));
			fail("Shift/reduce conflict expected!");
		}
		catch (ShiftReduceConflict ex)
		{
			//ok
		}
		catch (GeneratorException ex)
		{
			fail("Wrong exception");
		}
	}

}

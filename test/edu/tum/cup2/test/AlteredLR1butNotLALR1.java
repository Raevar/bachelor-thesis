package edu.tum.cup2.test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.exceptions.ShiftReduceConflict;
import edu.tum.cup2.io.GraphBuilderVisitor;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import static edu.tum.cup2.test.AlteredLR1butNotLALR1.NonTerminals.*;
import static edu.tum.cup2.test.AlteredLR1butNotLALR1.Terminals.*;
import edu.tum.cup2.spec.CUP2Specification;
import java.io.File;
import org.junit.Test;
import static junit.framework.Assert.fail;


/**
 * Das ist eine Grammtik, die zwar LR1, aber nicht LALR1 ist.
 * 
 * @author Daniel Altmann
 */
public class AlteredLR1butNotLALR1 extends CUP2Specification
{


	public enum Terminals implements edu.tum.cup2.grammar.Terminal
	{
		a, b, e;
	}
	
	
	public enum NonTerminals implements edu.tum.cup2.grammar.NonTerminal
	{
		S, E, F, E_tick;
	}

	public AlteredLR1butNotLALR1() {
		grammar(
			prod(S, rhs(a, E, a)),
			prod(S, rhs(b, E_tick, b)),
			prod(S, rhs(a, F, b)),
			prod(S, rhs(b, F, a)),
			prod(E_tick, rhs(e)),
			prod(E, rhs(e)),
			prod(F, rhs(e))
		);
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
	

}

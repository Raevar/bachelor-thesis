package edu.tum.cup2.test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.exceptions.ShiftReduceConflict;
import edu.tum.cup2.io.GraphBuilderVisitor;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import static edu.tum.cup2.test.LR1example.NonTerminals.*;
import static edu.tum.cup2.test.LR1example.Terminals.*;
import edu.tum.cup2.spec.CUP2Specification;
import java.io.File;
import org.junit.Test;
import static junit.framework.Assert.fail;


/**
 * Das ist eine Grammtik, die zwar LR1, aber nicht LALR1 ist.
 * 
 * @author Daniel Altmann
 */
public class LR1example extends CUP2Specification
{


	public enum Terminals implements edu.tum.cup2.grammar.Terminal
	{
		eq, star, id;
	}
	
	
	public enum NonTerminals implements edu.tum.cup2.grammar.NonTerminal
	{
		S, E, L, R;
	}

	public LR1example() {
		grammar(
			prod(S, rhs(E)),
			prod(E, rhs(L,eq,R)),
			prod(E, rhs(R)),
			prod(L, rhs(star,R)),
			prod(L, rhs(id)),
			prod(R, rhs(L))
		);
	}
      @Test public void testLR1()
	{
		try
		{
                        LR1Generator gen = new LR1Generator(this);
			LRParsingTable table = gen.getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File(this.getClass().getName()+"-LR1.html"));
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

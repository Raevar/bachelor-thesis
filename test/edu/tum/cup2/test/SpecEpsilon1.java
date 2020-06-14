package edu.tum.cup2.test;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.test.SpecEpsilon1.NonTerminals.*;
import static edu.tum.cup2.test.SpecEpsilon1.Terminals.*;


/**
 * Specification of a very simple grammar, that
 * contains epsilons.
 * 
 * It specifies the language ("A"*)"FINISH", e.g.
 * FINISH
 * A FINISH
 * A A FINISH
 * A A A FINISH
 * ...
 * 
 * @author Andreas Wenger
 */
public class SpecEpsilon1
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
		A, FINISH
	}
	
	public enum NonTerminals implements NonTerminal
	{
		start, list
	}

	
	public SpecEpsilon1()
	{
		grammar(
			prod(start,  rhs(list, FINISH)),
			prod(list,   rhs(list, A),
				             new Action() { public void a() { count++; }}, //count A's
			             rhs()));
	}
			
	
	private int count = 0;
	
	
	/**
	 * Test the grammar with LR(0) and LR(1).
	 */
	@Test public void test()
		throws Exception
	{
		for (int lr = 0; lr <= 1; lr++)
		{
			LRParsingTable table;
			if (lr == 0)
			{
				table = new LR0Generator(this).getParsingTable();
				LRParsingTableDump.dumpToHTML(table, new File("SpecEpsilon1-LR0.html")); //TEST
			}
			else
			{
				table = new LR1Generator(this).getParsingTable();
				LRParsingTableDump.dumpToHTML(table, new File("SpecEpsilon1-LR1.html")); //TEST
			}
			//test the words "FINISH" to "A A A ... A FINISH" (10 A's)
			for (int i = 0; i <= 10; i++)
			{
				count = 0;
				Terminal[] input = new Terminal[i+1];
				for (int i2 = 0; i2 < i; i2++)
					input[i2] = A;
				input[i] = FINISH;
				new LRParser(table).parse(new TestScanner(input));
				Assert.assertEquals(i, count);
			}
		}
	}
	
	
	

}


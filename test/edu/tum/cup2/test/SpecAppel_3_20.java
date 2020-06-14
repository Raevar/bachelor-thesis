package edu.tum.cup2.test;

import edu.tum.cup2.generator.LALR1SCCGenerator;
import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR0ParallelGenerator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.SpecAppel_3_20.NonTerminals.*;
import static edu.tum.cup2.test.SpecAppel_3_20.Terminals.*;

/**
 * Specification for Grammar 3.20 (LR(0)) from Appel's book, page 60.
 * 
 * @author Andreas Wenger, Johannes Schamburger
 */
public class SpecAppel_3_20 extends CUP2Specification {

	public enum Terminals implements Terminal {
		leftbr, rightbr, x, comma, $
	}

	public enum NonTerminals implements NonTerminal {
		S_primed, S, L
	}

	public SpecAppel_3_20() {
		grammar(prod(S_primed, rhs(S, $)), 
                        prod(S, rhs(leftbr, L, rightbr),
                                rhs(x)), 
                        prod(L, rhs(S), 
                                rhs(L, comma, S)));
	}

	/**
	 * LR(1).
	 */
	public void testLR1() throws Exception {
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table,
				new File("SpecAppel_3_20.LR1.html")); // TEST
	}

	/**
	 * LR(0)
	 */
	public void testLR0() throws Exception {
		LRParsingTable table = new LR0Generator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table,
				new File("SpecAppel_3_20.LR0.html")); // TEST


	}

	public void testLR0Parallel() throws Exception {
		LRParsingTable table = new LR0ParallelGenerator(this, Verbosity.Sparse).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File(
				"SpecAppel_3_20.LR0Parallel.html")); // TEST


	}
	public void testLALR1SCC() throws Exception {
		LRParsingTable table = new LALR1SCCGenerator(this, Verbosity.Sparse).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File(
				"SpecAppel_3_20.LR0Parallel.html")); // TEST


	}

	@Test public void testTime() {
		Time.measureTime(new SpecAppel_3_20());
	}

}

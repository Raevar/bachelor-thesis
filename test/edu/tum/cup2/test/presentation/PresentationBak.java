package edu.tum.cup2.test.presentation;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.exceptions.LRParserException;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.TestScanner.t;
import static edu.tum.cup2.scanner.TestScanner.v;
import static edu.tum.cup2.test.presentation.PresentationBak.Terminals.*;
import static edu.tum.cup2.test.presentation.PresentationBak.NonTerminals.*;


/**
 * Demo specification.
 * 
 * @author Andreas Wenger
 */
public class PresentationBak
	extends CUP2Specification
{
	
	public enum Terminals implements Terminal
	{
		INT, PLUS, TIMES;
	}
	
	public enum NonTerminals implements NonTerminal
	{
		expr;
	}
	
	
	public class INT extends SymbolValue<Integer>{};
	public class expr extends SymbolValue<Integer>{};
	
	
	public PresentationBak()
	{
		precedences(left(TIMES), left(PLUS));
		
		grammar(
			prod(expr,
				rhs(INT),
					new Action() { public Integer a(Integer n) { return n; } },
				rhs(expr, PLUS, expr),
					new Action() { public Integer a(Integer n1, Integer n2) { return n1 + n2; } },
				rhs(expr, TIMES, expr),
					new Action() { public Integer a(Integer n1, Integer n2) { return n1 * n2; } }));
	}
	
	
	@Test public void test()
		throws GeneratorException, LRParserException, IOException
	{
		PresentationBak spec = new PresentationBak();
		LRParsingTable table =
			new LR1Generator(spec, Verbosity.None).getParsingTable();
		LRParser parser = new LRParser(table);
		//5 + 3 * 10 = 35
		Integer r = (Integer) parser.parse(new TestScanner(
			v(INT, 5), t(PLUS), v(INT, 3), t(TIMES), v(INT, 10)));
		Assert.assertEquals(new Integer(35), r);
	}
	
	

}

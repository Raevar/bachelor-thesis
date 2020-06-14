package edu.tum.cup2.test;

import edu.tum.cup2.generator.LALR1SCCGenerator;
import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.exceptions.ShiftReduceConflict;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.exceptions.LRParserException;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.SpecCalc3.NonTerminals.*;
import static edu.tum.cup2.test.SpecCalc3.Terminals.*;
import static junit.framework.Assert.fail;

/**
 * Specification of a calculator for arithmetic terms using
 * +, -, *, /, ( and ) and integer terminals.
 * 
 * Since precedences are missing, it is even not LR(1).
 * 
 * @author Andreas Wenger
 */
public class SpecCalc3
	extends CUP2Specification
{
	

	public enum Terminals implements Terminal
	{
		NUMBER, PLUS, MINUS, MULT, DIV, LBR, RBR
	}
	
	public enum NonTerminals implements NonTerminal
	{
		expr
	}
	
	//symbols with values
	public class NUMBER extends SymbolValue<Integer>{};
	public class expr   extends SymbolValue<Integer>{};

	
	public SpecCalc3()
	{
		grammar(
			prod(expr, rhs(NUMBER),
			             new Action() { public int a(Integer n) { return n; }},
			           rhs(expr, PLUS, expr),
					         new Action() { public int a(Integer n1, Integer n2) { return n1 + n2; }},
					       rhs(expr, MINUS, expr),
					         new Action() { public int a(Integer n1, Integer n2) { return n1 - n2; }},
					       rhs(expr, MULT, expr),
					         new Action() { public int a(Integer n1, Integer n2) { return n1 * n2; }},
					       rhs(expr, DIV, expr),
					         new Action() { public int a(Integer n1, Integer n2) { return n1 / n2; }},
					       rhs(LBR, expr, RBR),
					         new Action() { public int a(Integer n) { return n; }}));
	}
	
	
	
	/**
	 * LR(0) generator must fail.
	 */
	@Test public void testLR0()
	{
		try
		{
			new LR0Generator(this).getParsingTable();
			fail("Shift/reduce conflict expected!");
		}
		catch (ShiftReduceConflict ex)
		{
			System.out.println(ex.getMessage());
		}
		catch (GeneratorException ex)
		{
			fail("Wrong exception");
		}
	}
	
	
	/**
	 * LR(1) generator must fail.
	 */
	@Test public void testLR1()
		throws GeneratorException, LRParserException
	{
		try
		{
			new LR1Generator(this).getParsingTable();
			fail("Shift/reduce conflict expected!");
		}
		catch (ShiftReduceConflict ex)
		{
			System.out.println(ex.getMessage());
		}
		catch (GeneratorException ex)
		{
			fail("Wrong exception");
		}
	}
	/**
	 * LR(1) generator must fail.
	 */
	@Test public void testLALR1SCC()
		throws GeneratorException, LRParserException
	{
		try
		{
			new LALR1SCCGenerator(this).getParsingTable();
			fail("Shift/reduce conflict expected!");
		}
		catch (ShiftReduceConflict ex)
		{
			System.out.println(ex.getMessage());
		}
		catch (GeneratorException ex)
		{
			fail("Wrong exception");
		}
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new SpecCalc3());
	}


}


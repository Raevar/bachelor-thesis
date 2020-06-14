package edu.tum.cup2.test.lr1blowup;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.Time;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.lr1blowup.Spec14Prec.NonTerminals.*;
import static edu.tum.cup2.test.lr1blowup.Spec14Prec.Terminals.*;
import static junit.framework.Assert.fail;

/**
 * Grammar to test LR(1)-blowup.
 * 
 * @author Andreas Wenger
 */
public class Spec14Prec
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		TERMINAL, LBR, RBR, OP1, OP2, OP3, OP4, OP5, OP6, OP7, OP8, OP9, OP10, OP11, OP12, OP13, OP14
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		term
	}

	
	public Spec14Prec()
	{
		precedences(left(OP14, OP13, OP12, OP11, OP10, OP9, OP8, OP7, OP6, OP5, OP4, OP3, OP2, OP1));
		grammar(
			prod(term,
					          rhs(TERMINAL),
					          rhs(LBR, term, RBR),
					          rhs(term, OP1, term),
					          rhs(term, OP2, term),
					          rhs(term, OP3, term),
					          rhs(term, OP4, term),
					          rhs(term, OP5, term),
					          rhs(term, OP6, term),
					          rhs(term, OP7, term),
					          rhs(term, OP8, term),
					          rhs(term, OP9, term),
					          rhs(term, OP10, term),
					          rhs(term, OP11, term),
					          rhs(term, OP12, term),
					          rhs(term, OP13, term),
					          rhs(term, OP14, term))	            
		);
	}
	
	
	@Test public void testLR1()
	{
		try
		{
			new LR1Generator(this, Verbosity.Detailled).getParsingTable();
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new Spec14Prec());
	}


}
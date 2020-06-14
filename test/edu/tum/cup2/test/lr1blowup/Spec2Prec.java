package edu.tum.cup2.test.lr1blowup;

import org.junit.Test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.Time;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.lr1blowup.Spec2Prec.NonTerminals.*;
import static edu.tum.cup2.test.lr1blowup.Spec2Prec.Terminals.*;


/**
 * Grammar to test LR(1)-blowup.
 * 
 * @author Andreas Wenger
 */
public class Spec2Prec
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		TERMINAL, LBR, RBR, OP1, OP2
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		term
	}

	
	public Spec2Prec()
	{
		precedences(left(OP2, OP1));
		grammar(
			prod(term,
					          rhs(TERMINAL),
					          rhs(LBR, term, RBR),
					          rhs(term, OP1, term),
					          rhs(term, OP2, term))	            
		);
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new Spec2Prec());
	}


}
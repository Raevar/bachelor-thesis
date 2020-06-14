package edu.tum.cup2.test.lr1blowup;

import org.junit.Test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.Time;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.lr1blowup.Spec14NoPrec.NonTerminals.*;
import static edu.tum.cup2.test.lr1blowup.Spec14NoPrec.Terminals.*;

/**
 * Grammar to test LR(1)-blowup.
 * 
 * @author Andreas Wenger
 */
public class Spec14NoPrec
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
		term, brackets, op1, op2, op3, op4, op5, op6, op7, op8, op9, op10, op11, op12, op13, op14
	}

	
	public Spec14NoPrec()
	{
		grammar(
			prod(term,
                    rhs(op1)),
      prod(op1,
                    rhs(op2),
                    rhs(op1, OP1, op2)),
      prod(op2,
                    rhs(op3),
                    rhs(op2, OP2, op3)),
      prod(op3,
                    rhs(op4),
                    rhs(op3, OP3, op4)),
      prod(op4,
                    rhs(op5),
                    rhs(op4, OP4, op5)),
      prod(op5,
                    rhs(op6),
                    rhs(op5, OP5, op6)),
			prod(op6,
				            rhs(op7),
				            rhs(op6, OP6, op7)),
			prod(op7,
					          rhs(op8),
					          rhs(op7, OP7, op8)),
			prod(op8,
					          rhs(op9),
					          rhs(op8, OP8, op9)),
			prod(op9,
					          rhs(op10),
					          rhs(op9, OP9, op10)),
			prod(op10,
					          rhs(op11),
					          rhs(op10, OP10, op11)),
			prod(op11,
					          rhs(op12),
					          rhs(op11, OP11, op12)),
			prod(op12,
					          rhs(op13),
					          rhs(op12, OP12, op13)),
			prod(op13,
					          rhs(op14),
					          rhs(op13, OP13, op14)),
			prod(op14,
					          rhs(brackets),
					          rhs(op14, OP14, brackets)),				            
			prod(brackets,
					          rhs(TERMINAL),
					          rhs(LBR, term, RBR))	            
		);
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new Spec14NoPrec());
	}


}
package edu.tum.cup2.test.lr1blowup;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.lr1blowup.Spec6NoPrec.NonTerminals.*;
import static edu.tum.cup2.test.lr1blowup.Spec6NoPrec.Terminals.*;

/**
 * Grammar to test LR(1)-blowup.
 * 
 * @author Andreas Wenger
 */
public class Spec6NoPrec
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		TERMINAL, LBR, RBR, OP1, OP2, OP3, OP4, OP5, OP6, OP7
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		term, brackets, op1, op2, op3, op4, op5, op6, op7
	}

	
	public Spec6NoPrec()
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
				            rhs(brackets),
				            rhs(op6, OP6, brackets)),
			prod(brackets,
					          rhs(TERMINAL),
					          rhs(LBR, term, RBR))	            
		);
	}


}
package edu.tum.cup2.spec;

import static edu.tum.cup2.spec.SampleSpec.NonTerminals.*;
import static edu.tum.cup2.spec.SampleSpec.Terminals.*;

import edu.tum.cup2.grammar.*;
import edu.tum.cup2.semantics.*;


/**
 * Sample CUP2 specification: Simple calculator.
 * 
 * @author Andreas Wenger
 */
public class SampleSpec
	extends CUPSpecification
{

	//terminals (tokens returned by the scanner)
	public enum Terminals implements edu.tum.cup2.grammar.Terminal
	{
		SEMI, PLUS, TIMES, LPAREN, RPAREN, NUMBER;
	}
	
	//non-terminals
	public enum NonTerminals implements edu.tum.cup2.grammar.NonTerminal
	{
            e, t, f;
	}

	//symbols with values
	public class NUMBER extends SymbolValue<Integer>{};
	public class expr   extends SymbolValue<Integer>{};
	public class res    extends SymbolValue<Integer>{};
	
	
	public SampleSpec()
	{
		
		//grammar
		grammar(
                    prod(e,       rhs(e, PLUS, t),
                                  rhs(t)          ),  
                    prod(t,       rhs(t, TIMES, f),
                                  rhs(f)          ),
                    prod(f,       rhs(LPAREN, e, RPAREN),
                                  rhs(NUMBER)     )
		);
	}

}

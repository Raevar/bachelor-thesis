package edu.tum.cup2.spec;

import static edu.tum.cup2.spec.SampleSpec.NonTerminals.*;
import static edu.tum.cup2.spec.SampleSpec.Terminals.*;

import edu.tum.cup2.grammar.*;
import edu.tum.cup2.semantics.*;


/**
 * Introductory CUP2 example: Parsing arithmetic expressions
 * 
 * @author Andreas Wenger
 */
public class SampleSpec
	extends CUPSpecification
{

	//terminals (tokens returned by the scanner)
	public enum Terminals implements Terminal
	{
            PLUS, TIMES, LPAREN, RPAREN, NUMBER;
	}
	
	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
            e, t, f;
	}

	//symbols with values
	public class NUMBER extends SymbolValue<Integer>{};
	public class e      extends SymbolValue<Expression>{};
	public class f      extends SymbolValue<Expression>{};
	public class t      extends SymbolValue<Expression>{};
	
	public SampleSpec()
	{
		
		//grammar
		grammar(
                    prod(e,       rhs(e, PLUS, t),
                         new Action(){ public Expression a(Expression e, expression t) 
                                 {return new BinaryExpression(e,Operators.PLUS,t); } }, 
                                  rhs(t),  
                         new Action(){ public Expression a(Expression t) 
                                 {return t; } }   ), 
                    prod(t,       rhs(t, TIMES, f),
                         new Action(){ public Expression a(Expression t, expression f) 
                                 {return new BinaryExpression(t,Operators.TIMES,f); } }, 
                                  rhs(f),  
                         new Action(){ public Expression a(Expression f) 
                                 {return f; } }   ), 
                    prod(f,       rhs(LPAREN, e, RPAREN),
                         new Action(){ public Expression a(Expression e) 
                                 {return e; } }, 
                                  rhs(NUMBER),
                         new Action(){ public Expression a(Integer n) 
                                 {return new IntegerConstExpression(n); } })
		);

	}

}

package edu.tum.cup2.test;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;
import static edu.tum.cup2.test.GrammarAppel_3_20_withSP.Terminals.$;
import static edu.tum.cup2.test.GrammarAppel_3_20_withSP.NonTerminals.L;
import static edu.tum.cup2.test.GrammarAppel_3_20_withSP.NonTerminals.S;
import static edu.tum.cup2.test.GrammarAppel_3_20_withSP.NonTerminals.S_primed;
import static edu.tum.cup2.test.GrammarAppel_3_20_withSP.Terminals.comma;
import static edu.tum.cup2.test.GrammarAppel_3_20_withSP.Terminals.leftbr;
import static edu.tum.cup2.test.GrammarAppel_3_20_withSP.Terminals.rightbr;
import static edu.tum.cup2.test.GrammarAppel_3_20_withSP.Terminals.x;


/**
 * Grammar 3.20 (LR(0)) from Appel's book, page 60,
 * with start production "S' → .S$".
 * 
 * @author Andreas Wenger
 */
public class GrammarAppel_3_20_withSP
	extends CUP2Specification
{

	public enum Terminals
		implements Terminal
	{
		leftbr,
		rightbr,
		x,
		comma,
		$;
	}


	public enum NonTerminals
		implements NonTerminal
	{
		S_primed,
		S,
		L;
	}
	
	
	public GrammarAppel_3_20_withSP()
	{
		grammar(
			prod(S_primed, rhs(S, $)),
			prod(S, rhs(leftbr, L, rightbr)),
			prod(S, rhs(x)),
			prod(L, rhs(S)),
			prod(L, rhs(L, comma, S)));
	}
	

}

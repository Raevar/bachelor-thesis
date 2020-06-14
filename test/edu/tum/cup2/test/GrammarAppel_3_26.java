package edu.tum.cup2.test;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.GrammarAppel_3_26.T.*;
import static edu.tum.cup2.test.GrammarAppel_3_26.N.*;

/**
 * Grammar 3.26 (LR(0)) from Appel's book, page 67.
 * 
 * @author Andreas Wenger
 */
public class GrammarAppel_3_26
{

	public enum T implements Terminal
	{
		$, eq, x, star
	}


	public enum N implements NonTerminal
	{
		Spr, S, V, E
	}


	public Grammar getWith$()
	{
		return new Grammar(T.values(), N.values(), new Production[]{
			new Production(0, Spr, S, $),
			new Production(1, S, V, eq, E),
			new Production(2, S, E),
			new Production(3, E, V),
			new Production(4, V, x),
			new Production(5, V, star, E)});
	}

}

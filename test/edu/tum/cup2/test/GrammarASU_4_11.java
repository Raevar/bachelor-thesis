package edu.tum.cup2.test;

import java.util.LinkedList;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.util.ArrayTools;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.test.GrammarASU_4_11.T.*;
import static edu.tum.cup2.test.GrammarASU_4_11.N.*;

/**
 * Grammar 4.11 from ASU99, page 230 (german edition).
 * Used for testing FIRST and FOLLOW sets.
 * 
 * @author Andreas Wenger
 */
public class GrammarASU_4_11 extends CUP2Specification
{

	public enum T implements Terminal
	{
		plus, mult, lbr, rbr, id
	}


	public enum N implements NonTerminal
	{
		E, T, Epr, Tpr, F
	}


	public Grammar get()
	{
		LinkedList<Terminal> terminals = new ArrayTools<Terminal>().toLinkedList(GrammarASU_4_11.T.values());
		LinkedList<NonTerminal> nonTerminals = new ArrayTools<NonTerminal>().toLinkedList(N.values());
		return new Grammar(terminals, nonTerminals, new ArrayTools<Production>().toLinkedList(
			new Production(1, E, T, Epr),
			new Production(2, Epr, plus, T, Epr),
			new Production(3, Epr, Epsilon),
			new Production(4, T, F, Tpr),
			new Production(5, Tpr, mult, F, Tpr),
			new Production(6, Tpr, Epsilon),
			new Production(7, F, lbr, E, rbr),
			new Production(8, F, id)));
	}

}

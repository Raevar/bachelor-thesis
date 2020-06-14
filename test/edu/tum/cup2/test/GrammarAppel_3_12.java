package edu.tum.cup2.test;

import java.util.LinkedList;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.util.ArrayTools;

//TODO: (MH) javac bug (this weird bug seems to be around since quite some time) 
//prevents to compile statically imported things that are not fully qualified
//see: http://mihail.stoynov.com/blog/Trackback.aspx?guid=e15876c6-1665-4488-935b-36b4260b0e88
//and http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6391197
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.test.GrammarAppel_3_12.MyNonTerminal.*;
import static edu.tum.cup2.test.GrammarAppel_3_12.MyTerminal.*;

/**
 * Grammar 3.12 from Appel's book, page 49.
 * Used for testing FIRST sets.
 * 
 * @author Andreas Wenger
 */
public class GrammarAppel_3_12
{

	public enum MyTerminal implements Terminal
	{
		a, c, d
	}


	public enum MyNonTerminal implements NonTerminal
	{
		X, Y, Z
	}


	public Grammar get()
	{
		LinkedList<Terminal> terminals = new ArrayTools<Terminal>().toLinkedList(MyTerminal.values());
		LinkedList<NonTerminal> nonTerminals = new ArrayTools<NonTerminal>().toLinkedList(MyNonTerminal.values());
		return new Grammar(terminals, nonTerminals, new ArrayTools<Production>().toLinkedList(
			new Production(1, Z, d),
			new Production(2, Z, X, Y, Z),
			new Production(3, Y, Epsilon),
			new Production(4, Y, c),
			new Production(5, X, Y),
			new Production(6, X, a)));
	}

}

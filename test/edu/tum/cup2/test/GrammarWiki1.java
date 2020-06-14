package edu.tum.cup2.test;

import java.util.LinkedList;

import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.actions.Accept;
import edu.tum.cup2.parser.actions.Reduce;
import edu.tum.cup2.parser.actions.Shift;
import edu.tum.cup2.parser.states.LRParserState;
import edu.tum.cup2.parser.tables.LRActionTable;
import edu.tum.cup2.parser.tables.LRGoToTable;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.util.ArrayTools;
import edu.tum.cup2.util.It;
import edu.tum.cup2.semantics.ParserInterface;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.grammar.SpecialTerminals.EndOfInputStream;
import static edu.tum.cup2.test.GrammarWiki1.NonTerminals.B;
import static edu.tum.cup2.test.GrammarWiki1.NonTerminals.E;
import static edu.tum.cup2.test.GrammarWiki1.Terminals._0;
import static edu.tum.cup2.test.GrammarWiki1.Terminals._1;
import static edu.tum.cup2.test.GrammarWiki1.Terminals.plus;
import static edu.tum.cup2.test.GrammarWiki1.Terminals.times;

/**
 * Grammar, taken from
 * http://de.wikipedia.org/wiki/LR-Parser#Beispiel_eines_Parsers
 * A copy is placed in
 * "Documentation/Other/LRParser - Example 1.pdf"
 * 
 * @author Andreas Wenger
 */
public class GrammarWiki1
	extends CUP2Specification
{
	
	public enum Terminals
		implements Terminal
	{
		times, plus, _0, _1;
	}
	
	
	public enum NonTerminals
		implements NonTerminal
	{
		E, B;
	}
	
	
	public GrammarWiki1()
	{
		grammar(getProductionsArray());
	}
	
	
	public static Production[] getProductionsArray()
	{
		Production[] prods = new Production[5]; //the book uses indices from 1 to 5, but we use 0 to 4
		prods[0] = new Production(1, E, E, times, B);
		prods[1] = new Production(2, E, E, plus, B);
		prods[2] = new Production(3, E, B);
		prods[3] = new Production(4, B, _0);
		prods[4] = new Production(5, B, _1);
		return prods;
	}
	
	
	/**
	 * Gets the manually created parsing table for this grammar.
	 */
	public LRParsingTable getParsingTable()
	{
		//create productions
		Production[] prods = getProductionsArray();
		
		//create states
		LinkedList<Terminal> terminals = new ArrayTools<Terminal>().toLinkedList(Terminals.values());
		terminals.add(EndOfInputStream);
		
		LRParsingTable table = new LRParsingTable(
						new Grammar(terminals, 
							new ArrayTools<NonTerminal>().toLinkedList(NonTerminals.values()),
							new ArrayTools<Production>().toLinkedList(getProductionsArray())
						),
						new ParserInterface(),
						9);
		LRParserState[] states = new LRParserState[table.getStatesCount()];
		It<LRParserState> tableStates = table.getStates();
		for (int i = 0; i < states.length; i++)
		{
			states[i] = tableStates.next();
		}
		
		//create and fill action table
		LRActionTable actionTable = table.getActionTable();
		
		actionTable.set(new Shift(states[1], null, 0), states[0], _0);
		actionTable.set(new Shift(states[2], null, 0), states[0], _1);
		
		actionTable.set(new Reduce(prods[3]), states[1], times);
		actionTable.set(new Reduce(prods[3]), states[1], plus);
		actionTable.set(new Reduce(prods[3]), states[1], _0);
		actionTable.set(new Reduce(prods[3]), states[1], _1);
		actionTable.set(new Reduce(prods[3]), states[1], EndOfInputStream);
		
		actionTable.set(new Reduce(prods[4]), states[2], times);
		actionTable.set(new Reduce(prods[4]), states[2], plus);
		actionTable.set(new Reduce(prods[4]), states[2], _0);
		actionTable.set(new Reduce(prods[4]), states[2], _1);
		actionTable.set(new Reduce(prods[4]), states[2], EndOfInputStream);
		
		actionTable.set(new Shift(states[5], null, 0), states[3], times);
		actionTable.set(new Shift(states[6], null, 0), states[3], plus);
		actionTable.set(new Accept(), states[3], EndOfInputStream);
		
		actionTable.set(new Reduce(prods[2]), states[4], times);
		actionTable.set(new Reduce(prods[2]), states[4], plus);
		actionTable.set(new Reduce(prods[2]), states[4], _0);
		actionTable.set(new Reduce(prods[2]), states[4], _1);
		actionTable.set(new Reduce(prods[2]), states[4], EndOfInputStream);
		
		actionTable.set(new Shift(states[1], null, 0), states[5], _0);
		actionTable.set(new Shift(states[2], null, 0), states[5], _1);
		
		actionTable.set(new Shift(states[1], null, 0), states[6], _0);
		actionTable.set(new Shift(states[2], null, 0), states[6], _1);
		
		actionTable.set(new Reduce(prods[0]), states[7], times);
		actionTable.set(new Reduce(prods[0]), states[7], plus);
		actionTable.set(new Reduce(prods[0]), states[7], _0);
		actionTable.set(new Reduce(prods[0]), states[7], _1);
		actionTable.set(new Reduce(prods[0]), states[7], EndOfInputStream);
		
		actionTable.set(new Reduce(prods[1]), states[8], times);
		actionTable.set(new Reduce(prods[1]), states[8], plus);
		actionTable.set(new Reduce(prods[1]), states[8], _0);
		actionTable.set(new Reduce(prods[1]), states[8], _1);
		actionTable.set(new Reduce(prods[1]), states[8], EndOfInputStream);
		
		//create and fill goto table
		LRGoToTable gotoTable = table.getGotoTable();
		gotoTable.set(states[3], states[0], E);
		gotoTable.set(states[4], states[0], B);
		gotoTable.set(states[7], states[5], B);
		gotoTable.set(states[8], states[6], B);
		
		return table;
	}
	

}

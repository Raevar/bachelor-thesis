package edu.tum.cup2.parser.tables;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Assert;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.actions.Accept;
import edu.tum.cup2.parser.actions.ErrorAction;
import edu.tum.cup2.parser.actions.LRAction;
import edu.tum.cup2.parser.actions.Reduce;
import edu.tum.cup2.parser.actions.Shift;
import edu.tum.cup2.parser.states.ErrorState;
import edu.tum.cup2.parser.states.LRParserState;


/**
 * Test class for {@link LRParsingTable}.
 * 
 * @author Andreas Wenger
 */
public class LRParsingTableTestTool
{
	
	private static boolean DEBUG = false;
	
	
	/**
	 * This method tests, if the two given {@link LRParsingTable}s are equal.
	 * They are equal, if they contain exactly the same information, sharing the
	 * same symbols, and equal productions and items. The only thing that may be different
	 * is the order of the states.
	 * 
	 * TODO: verify that it works. use als equals-method in the LRParsingTable class itself?
	 */
	public static void assertEquals(LRParsingTable t1, LRParsingTable t2)
	{
		//they must have the same number of states
		if (t1.getStatesCount() != t2.getStatesCount())
			throw new AssertionError("Number of states is different: " + t1.getStatesCount() + " vs " + t2.getStatesCount());
		
		//action table and goto table must the have same number of non-error cells
		LRActionTable a1 = t1.getActionTable();
		LRActionTable a2 = t2.getActionTable();
		if (a1.getNonErrorCellsCount() != a2.getNonErrorCellsCount())
		{
			throw new AssertionError("Number of non-error cells in action table is different: " +
				a1.getNonErrorCellsCount() + " vs " + a2.getNonErrorCellsCount());
		}
		LRGoToTable g1 = t1.getGotoTable();
		LRGoToTable g2 = t2.getGotoTable();
		if (g1.getNonErrorCellsCount() != g2.getNonErrorCellsCount())
			throw new AssertionError("Number of non-error cells in goto table is different: " +
				g1.getNonErrorCellsCount() + " vs " + g2.getNonErrorCellsCount());
		
		//go through the states (rows) of t1 and read the actions/gotos, beginning with the common start state 0,
		//following the found states in shift/goto actions.
		//create a map for assigning t1 states to t2 states. if such a mapping is already there for a t1 state, it may not
		//be changed anymore, otherwise it is an error.
		HashMap<LRParserState, LRParserState> statesMapping = new HashMap<LRParserState, LRParserState>();
		statesMapping.put(t1.getStartState(), t2.getStartState());
		LinkedList<LRParserState> queue = new LinkedList<LRParserState>();
		queue.add(t1.getStartState());
		int okStatesCount = 0;
		while (!queue.isEmpty()) //rows
		{
			LRParserState state = queue.removeFirst();
			
			//action table
			for (Terminal terminal : a1.getColumns())
			{
				LRAction action1 = a1.get(state, terminal);
				LRAction action2 = a2.get(statesMapping.get(state), terminal);
				if (action1 instanceof Shift)
				{
					//must be the same shift
					LRParserState s1 = ((Shift) action1).getState();
					if (statesMapping.containsKey(s1))
					{
						//state is already known. check, if t2 has the same shift action
						Assert.assertEquals(new Shift(statesMapping.get(s1), null, 0), action2);
						if (DEBUG) System.out.println("  Shift ok");
					}
					else
					{
						//new state. add it to the map and to the queue
						assertTrue(action2 instanceof Shift);
						statesMapping.put(s1, ((Shift) action2).getState());
						queue.add(s1);
						if (DEBUG) System.out.println("  State " + s1.getID() + " queued");
					}
				}
				else if (action1 instanceof Reduce)
				{
					//must be the same reduce
					Production p1 = ((Reduce) action1).getProduction();
					assertTrue(action2 instanceof Reduce);
					Assert.assertEquals(p1, ((Reduce) action2).getProduction());
					if (DEBUG) System.out.println("  Reduce ok");
				}
				else if (action1 instanceof Accept)
				{
					//must also be accept
					assertTrue(action2 instanceof Accept);
					if (DEBUG) System.out.println("  Accept ok");
				}
				else if (action1 instanceof ErrorAction)
				{
					//must also be error
					assertTrue(action2 instanceof ErrorAction);
					if (DEBUG) System.out.println("  ErrorAction ok");
				}
			}
			
			//goto table
			for (NonTerminal nonTerminal : g1.getColumns())
			{
				LRParserState s1 = g1.get(state, nonTerminal);
				LRParserState s2 = g2.get(statesMapping.get(state), nonTerminal);
				if (s1 instanceof ErrorState)
				{
					//must also be error
					assertTrue(s2 instanceof ErrorState);
					if (DEBUG) System.out.println("  Goto Error ok");
				}
				else
				{
					if (statesMapping.containsKey(s1))
					{
						//state is already known. check, if t2 has the same goto
						Assert.assertEquals(statesMapping.get(s1), s2);
						if (DEBUG) System.out.println("  Goto ok");
					}
					else
					{
						//new state. add it to the map and to the queue
						statesMapping.put(s1, s2);
						queue.add(s1);
						if (DEBUG) System.out.println("  State " + s1.getID() + " queued");
					}
				}
			}
			
			if (DEBUG) System.out.println("STATE " + state.getID() + " OK (" +
				(++okStatesCount) + " states are ok already)");
		}
	}
	

}

package edu.tum.cup2.generator;

import static edu.tum.cup2.util.Tuple2.t;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import edu.tum.cup2.generator.items.Item;
import edu.tum.cup2.generator.states.State;
import edu.tum.cup2.util.Tuple2;


/**
 * Test class for {@link Automaton}.
 * 
 * @author Andreas Wenger
 */
public class AutomatonTestTool
{
	/**
	 * Asserts that that the two given {@link Automaton}s behave equally.
	 * If not, the test fails and the first differing state is printed.
	 */
	@SuppressWarnings("unchecked")
	public static <I1 extends Item, S1 extends State<I1>, I2 extends Item, S2 extends State<I2>> void testEquals(
			Automaton<I1, S1> a1, Automaton<I2, S2> a2)
	{
		// we could test for equal number of states first, but we want to
		// find the first state that is different, so we don't do that test here.
		
		// equal start state?
		assertEquals(a1.getStartState(), a2.getStartState());
		
		// create map between states of a1 and a2
		final HashMap<S1, S2> statesMap = new HashMap<S1, S2>();
		statesMap.put(a1.getStartState(), a2.getStartState());
		
		// queue: begin with start state of a1
		final LinkedList<S1> queue = new LinkedList<S1>();
		queue.add(a1.getStartState());
		
		// set of already checked states
		final HashSet<S1> finished = new HashSet<S1>();
		
		// for each state in queue: find equal state in a2. then, look at all edges and
		// find their equivalents. target states are added to the queue and are suspected to be equal
		// (this is is tested as soon as the states are dequeued).
		while (!queue.isEmpty())
		{
			S1 s1 = queue.poll();
			
			// find equal state in a2
			S2 s2 = null;
			for (S2 s : a2.getStates())
			{
				if (s.equals(s1))
				{
					s2 = s;
					break;
				}
			}
			if (s2 == null)
			{
				fail("No equivalent state in for: " + s1);
			}
			finished.add(s1);
			
			// number of outgoing edges must be equal
			final LinkedList<Edge> e1l = a1.getEdgesFrom(s1);
			final LinkedList<Edge> e2l = a2.getEdgesFrom(s2);
			if (e1l.size() != e2l.size())
			{
				fail("Number of outgoing edges is different. State: " + s1);
			}
			
			// follow edges
			for (Edge e1 : e1l)
			{
				// find equivalent edge in e2
				Edge e2 = null;
				for (Edge e : e2l)
				{
					if (e.getSymbol() == e1.getSymbol())
					{
						e2 = e;
						break;
					}
				}
				if (e2 == null)
				{
					fail("No edge with symbol " + e1.getSymbol() + " at state: " + s2);
				}
				
				// target states must be equal. if still unknown, queue.
				final S1 ts1 = (S1) e1.getDest();
				final S2 ts2 = (S2) e2.getDest();
				
				if (ts1 == null)
				{
					if (ts2 != null)
						fail("Edge with symbol " + e1.getSymbol() + " leads to a final state in Automaton 1 only");
					else
						continue;
				}
				
				
				if (!ts1.equals(ts2))
				{
					ts1.equals(ts2);
					fail("Edge with symbol " + e1.getSymbol() + " leads to different states at state: " + ts1
							+ " and state " + ts2);
				} else if ((!queue.contains(ts1)) && (!finished.contains(ts1)))
				{
					queue.add(ts1);
				}
			}
			
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static <I1 extends Item, S1 extends State<I1>, I2 extends Item, S2 extends State<I2>> void testCongruency(
			Automaton<I1, S1> a1, Automaton<I2, S2> a2)
	{
		// we could test for equal number of states first, but we want to
		// find the first state that is different, so we don't do that test here.
		
		// equal start state?
		
		// create map between states of a1 and a2
		HashMap<S1, S2> statesMap = new HashMap<S1, S2>();
		statesMap.put(a1.getStartState(), a2.getStartState());
		
		// queue: begin with start state of a1
		LinkedList<Tuple2<S1, S2>> queue = new LinkedList<Tuple2<S1, S2>>();
		queue.add(t(a1.getStartState(), a2.getStartState()));
		
		// set of already checked states
		HashSet<S1> finished = new HashSet<S1>();
		HashSet<S1> inQueue = new HashSet<S1>();
		inQueue.add(a1.getStartState());
		
		// for each state in queue: find equal state in a2. then, look at all edges and
		// find their equivalents. target states are added to the queue and are suspected to be equal
		// (this is is tested as soon as the states are dequeued).
		while (!queue.isEmpty())
		{
			Tuple2<S1, S2> t = queue.poll();
			S1 s1 = t.get1();
			inQueue.remove(s1);
			
			// find equal state in a2
			S2 s2 = t.get2();
			finished.add(s1);
			
			// number of outgoing edges must be equal
			LinkedList<Edge> e1l = a1.getEdgesFrom(s1);
			LinkedList<Edge> e2l = a2.getEdgesFrom(s2);
			if (e1l.size() != e2l.size())
			{
				fail("Number of outgoing edges is different. State: " + s1);
			}
			
			// follow edges
			for (Edge e1 : e1l)
			{
				// find equivalent edge in e2
				Edge e2 = null;
				for (Edge e : e2l)
				{
					if (e.getSymbol().toString().equals(e1.getSymbol().toString()))
					{
						e2 = e;
						break;
					}
				}
				if (e2 == null)
				{
					fail("No edge with symbol " + e1.getSymbol() + " at state: " + s2);
				}
				
				// target states must be equal. if still unknown, queue.
				S1 ts1 = (S1) e1.getDest();
				S2 ts2 = (S2) e2.getDest();
				
				if (ts1 == null)
				{
					if (ts2 != null)
						fail("Edge with symbol " + e1.getSymbol() + " leads to a final state in Automaton 1 only");
					else
						continue;
				}
				
				if (statesMap.containsKey(ts1))
				{
					if (statesMap.get(ts1) != ts2)
						fail("Edge with symbol " + e1.getSymbol() + " leads to different states at state: " + ts1
								+ " and state " + ts2);
				}
				if ((!inQueue.contains(ts1)) && (!finished.contains(ts1)))
				{
					inQueue.add(ts1);
					queue.add(t(ts1, ts2));
				}
			}
			
		}
		
	}
	
	
}

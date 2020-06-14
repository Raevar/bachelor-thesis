package edu.tum.cup2.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import edu.tum.cup2.generator.terminals.ITerminalSeq;
import edu.tum.cup2.generator.terminals.TerminalSeqf;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.tables.TerminalTreeMap;


/**
 * Tests for the {@link TreeMap} class.
 * 
 * @author Gero
 * 
 */
public class KTreeMapTest
{
	public enum TT implements Terminal
	{
		A,
		B,
		C,
		D,
		E,
		F,
		G,
		H;
	}
	
	private KTreeMap<ITerminalSeq, Terminal, Integer> map;
	private static final ITerminalSeq ABCDEFGH = new TerminalSeqf(TT.values());
	private static final ITerminalSeq ABCD = new TerminalSeqf(TT.A, TT.B, TT.C, TT.D);
	private static final ITerminalSeq ABCDE = new TerminalSeqf(TT.A, TT.B, TT.C, TT.D, TT.E);
	private static final ITerminalSeq ABCDEF = new TerminalSeqf(TT.A, TT.B, TT.C, TT.D, TT.E, TT.F);
	private static final ITerminalSeq ABCDG = new TerminalSeqf(TT.A, TT.B, TT.C, TT.D, TT.G);
	private static final ITerminalSeq ABCDGH = new TerminalSeqf(TT.A, TT.B, TT.C, TT.D, TT.G, TT.H);
	private static final ITerminalSeq ABCDEGH = new TerminalSeqf(TT.A, TT.B, TT.C, TT.D, TT.E, TT.G, TT.H);
	private static final ITerminalSeq A = new TerminalSeqf(TT.A);
	private static final ITerminalSeq AB = new TerminalSeqf(TT.A, TT.B);
	private static final ITerminalSeq ABC = new TerminalSeqf(TT.A, TT.B, TT.C);
	private static final ITerminalSeq _ = new TerminalSeqf();
	
	
	@Before
	public void setUp() throws Exception
	{
		map = new TerminalTreeMap<Integer>(TT.values().length);
	}
	
	
	/**
	 * Test method for {@link edu.tum.cup2.util.KTreeMap#put(java.lang.Iterable, java.lang.Object)}.
	 */
	@Test
	public void testPut()
	{
		// ### One branch
		assertTrue(map.put(ABCDEFGH, 1));
		assertTrue(map.size() == 1);
		assertEquals((long) map.getValue(A), 1);
		test1(map);
		
		// One short branch on the longer branch
		assertTrue(map.put(ABCD, 2));
		assertTrue(map.size() == 2);
		test1(map);
		test2(map);
		
		// Put a branching-branch
		assertTrue(map.put(ABCDGH, 3));
		assertTrue(map.size() == 3);
		test1(map);
		test2(map);
		test3(map);
		
		// Put a second branching-branch
		assertTrue(map.put(ABCDEGH, 1));
		assertTrue(map.size() == 4);
		test1(map);
		test2(map);
		test3(map);
		test4(map);
	}
	
	
	/**
	 * <pre>
	 * root - A:1 - B:1 - C:1 - D:1 - E:1 - F:1 - G:1 - (H:1)
	 * </pre>
	 * 
	 * @param map
	 */
	private static void test1(KTreeMap<ITerminalSeq, Terminal, Integer> map)
	{
		assertTrue(map.containsKeyExact(ABCDEFGH));
		assertTrue(map.containsKey(ABCDEFGH));
		assertEquals((long) map.getExact(ABCDEFGH), 1);
		assertEquals((long) map.get(ABCDE), 1);
		assertTrue(map.containsKey(ABCDE));
		
		assertFalse(map.containsKeyExact(ABCDE));
		assertFalse(map.isEmpty());
	}
	
	
	/**
	 * <pre>
	 * root - A:_ - B:_ - C:_ -(D:2)- E:1 - F:1 - G:1 - (H:1)
	 * </pre>
	 * 
	 * @param map
	 */
	private static void test2(KTreeMap<ITerminalSeq, Terminal, Integer> map)
	{
		// # Exact
		assertTrue(map.containsKeyExact(ABCD));
		assertTrue(map.containsKey(ABCD));
		assertEquals((long) map.getExact(ABCD), 2);
		assertEquals((long) map.get(ABCD), 2);
		assertTrue(map.containsKey(ABCD));
		// # short -> long
		assertFalse(map.containsKeyExact(ABCDE));
		assertFalse(map.containsKeyExact(ABCDEF));
		assertEquals((long) map.get(ABCDE), 1);
		assertEquals((long) map.get(ABCDEF), 1);
		assertEquals((long) map.getValue(ABCDE), 1);
		// # -> short
		assertFalse(map.containsKeyExact(ABC));
		assertFalse(map.containsKeyExact(AB));
		assertFalse(map.containsKeyExact(A));
		assertFalse(map.containsKey(ABC));
		assertFalse(map.containsKey(AB));
		assertFalse(map.containsKey(A));
		// # branch point:
		assertNull(map.getValue(A));
	}
	
	
	/**
	 * <pre>
	 *                                G:3 -(H:3)
	 *                              /
	 * root - A:_ - B:_ - C:_ -(D:2)- E:1 - F:1 - G:1 - (H:1)
	 * </pre>
	 * 
	 * @param map
	 */
	private static void test3(KTreeMap<ITerminalSeq, Terminal, Integer> map)
	{
		// # Exact
		assertTrue(map.containsKeyExact(ABCDGH));
		assertTrue(map.containsKey(ABCDGH));
		assertEquals((long) map.getExact(ABCDGH), 3);
		assertEquals((long) map.get(ABCDGH), 3);
		assertTrue(map.containsKey(ABCDGH));
		// # ABCD -> ABCDGH
		assertEquals((long) map.get(ABCDG), 3);
		assertNull(map.getExact(ABCDG));
		// # short -> long
		assertFalse(map.containsKeyExact(ABCDE));
		assertFalse(map.containsKeyExact(ABCDEF));
		assertEquals((long) map.get(ABCDE), 1);
		assertEquals((long) map.get(ABCDEF), 1);
		// # -> short
		assertFalse(map.containsKeyExact(ABC));
		assertFalse(map.containsKeyExact(AB));
		assertFalse(map.containsKeyExact(A));
		assertFalse(map.containsKey(ABC));
		assertFalse(map.containsKey(AB));
		assertFalse(map.containsKey(A));
	}
	
	
	/**
	 * <pre>
	 *                                G:3 -(H:3)
	 *                              /
	 * root - A:_ - B:_ - C:_ -(D:2)- E:1 - F:1 - G:1 - (H:1) 
	 *                                    \
	 *                                      G:1 -(H:1)
	 * </pre>
	 * 
	 * @param map
	 */
	private static void test4(KTreeMap<ITerminalSeq, Terminal, Integer> map)
	{
		// # Exact
		assertTrue(map.containsKeyExact(ABCDEGH));
		assertTrue(map.containsKey(ABCDEGH));
		assertEquals((long) map.getExact(ABCDEGH), 1);
		assertEquals((long) map.get(ABCDEGH), 1);
		assertTrue(map.containsKey(ABCDEGH));
		// # ABCD -> ABCDEGH
		assertTrue(map.get(ABCDE) != null);
		assertNull(map.getExact(ABCDE));
		assertEquals((long) map.getValue(ABCDE), 1);
		// # short -> long
		assertFalse(map.containsKeyExact(ABCDE));
		assertFalse(map.containsKeyExact(ABCDEF));
		assertEquals((long) map.getValue(ABCDE), 1);
		assertEquals((long) map.get(ABCDEF), 1);
		// # -> short
		assertFalse(map.containsKeyExact(ABC));
		assertFalse(map.containsKeyExact(AB));
		assertFalse(map.containsKeyExact(A));
		assertFalse(map.containsKey(ABC));
		assertFalse(map.containsKey(AB));
		assertFalse(map.containsKey(A));
	}
	
	
	/**
	 * Test method for {@link edu.tum.cup2.util.KTreeMap#remove(java.lang.Iterable)}.
	 * <pre>
	 *                                G:3 -(H:3)
	 *                              /
	 * root - A:_ - B:_ - C:_ -(D:2)- E:1 - F:1 - G:1 - (H:1) 
	 *                                    \
	 *                                      G:1 -(H:1)
	 * </pre>
	 */
	@Test
	public void testRemove()
	{
		// ### Put
		assertTrue(map.put(ABCDEFGH, 1));
		assertTrue(map.put(ABCD, 2));
		assertTrue(map.put(ABCDGH, 3));
		assertTrue(map.put(ABCDEGH, 1));
		
		// ### Remove step by step
		map.remove(ABCDEGH);
		assertTrue(map.size() == 3);
		test1(map);
		test2(map);
		test3(map);

		map.remove(ABCDGH);
		assertTrue(map.size() == 2);
		test1(map);
		test2(map);
		
		map.remove(ABCD);
		assertTrue(map.size() == 1);
		test1(map);
		
		map.remove(ABCDEFGH);
		assertTrue(map.isEmpty());
		assertFalse(map.containsKey(ABCD));
		assertTrue(map.get(ABCD) == null);
	}
	
	
	@Test
	public void testPutSingle()
	{
		assertTrue(map.put(_, 1));
		assertTrue(map.size() == 1);
		assertEquals((long) map.getValue(_), 1);
		assertEquals((long) map.getExact(_), 1);
		assertEquals((long) map.get(_), 1);
		
		map.remove(_);
		assertTrue(map.size() == 0);
	}
	
	
	/**
	 * Test method for {@link edu.tum.cup2.util.KTreeMap#clear()}.
	 */
	@Test
	public void testClear()
	{
		testPut();
		
		map.clear();
		
		assertTrue(map.isEmpty());
		assertFalse(map.containsKey(ABCD));
		assertTrue(map.get(ABCD) == null);
	}
	
}

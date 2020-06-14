package edu.tum.cup2.test;

import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.test.GrammarLectureLLk.N.S;
import static edu.tum.cup2.test.GrammarLectureLLk.T.a;
import static edu.tum.cup2.test.GrammarLectureLLk.T.b;

import java.util.LinkedList;

import edu.tum.cup2.generator.NFA;
import edu.tum.cup2.generator.items.LLkItem;
import edu.tum.cup2.generator.states.LLkState;
import edu.tum.cup2.generator.terminals.ITerminalSeq;
import edu.tum.cup2.generator.terminals.ITerminalSeqSet;
import edu.tum.cup2.generator.terminals.TerminalSeqSetf;
import edu.tum.cup2.generator.terminals.TerminalSeqf;
import edu.tum.cup2.grammar.Grammar;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Production;
import edu.tum.cup2.grammar.SpecialNonTerminals;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.util.ArrayTools;

/**
 * A simple grammar used in the slides of the lecture "Compilerbau" in SS12.
 * 
 * @author Gero
 * 
 */
public class GrammarLectureLLk
{
	public enum T implements edu.tum.cup2.grammar.Terminal
	{
		a, b
	}


	public enum N implements edu.tum.cup2.grammar.NonTerminal
	{
		S
	}


	public Grammar get()
	{
		LinkedList<Terminal> terminals = new ArrayTools<Terminal>().toLinkedList(T.values());
		LinkedList<NonTerminal> nonTerminals = new ArrayTools<NonTerminal>().toLinkedList(N.values());
		return new Grammar(terminals, nonTerminals, new ArrayTools<Production>().toLinkedList(
			new Production(1, S, Epsilon),
			new Production(2, S, a, S, b)
			));
	}
	
	
	public static NFA<LLkItem, LLkState> getLL2Automaton()
	{
		// ### Start
   	LLkState state00 = new LLkState(K2.S_$s$);
		NFA<LLkItem, LLkState> automaton = new NFA<LLkItem, LLkState>(state00);
		
		
		// ### Transition 0
   	LLkState state01 = new LLkState(K2.S_$s$, K2.s_$e);
   	automaton.addEdge(state00, Epsilon, state01);
   	
   	// ### Transition 1
   	LLkState state10 = new LLkState(K2.S_$s$);
   	LLkState state11 = new LLkState(K2.S_$s$, K2.s_$aSb);
   	automaton.addEdge(state10, Epsilon, state11);
   	
   	// ### Transition 2
   	LLkState state20 = new LLkState(K2.s_$aSb);
   	LLkState state21 = new LLkState(K2.s_a$Sb);
   	automaton.addEdge(state20, a, state21);
   	
   	// ### Transition 3
   	LLkState state30 = new LLkState(K2.s_a$Sb);
   	LLkState state31 = new LLkState(K2.s_a$Sb, K2.s_$e);
   	automaton.addEdge(state30, Epsilon, state31);
   	
   	// ### Transition 4
   	LLkState state40 = new LLkState(K2.s_a$Sb);
   	LLkState state41 = new LLkState(K2.s_a$Sb, K2.s_$aSb);
   	automaton.addEdge(state40, Epsilon, state41);
   	
   	// ### Transition 5
   	LLkState state50 = new LLkState(K2.s_a$Sb, K2.s_$e);
   	LLkState state51 = new LLkState(K2.s_aS$b);
   	automaton.addEdge(state50, Epsilon, state51);
   	
   	// ### Transition 6
   	LLkState state60 = new LLkState(K2.s_a$Sb, K2.s_aSb$);
   	LLkState state61 = new LLkState(K2.s_aS$b);
   	automaton.addEdge(state60, Epsilon, state61);
   	
   	// ### Transition 7
   	LLkState state70 = new LLkState(K2.s_aS$b);
   	LLkState state71 = new LLkState(K2.s_aSb$);
   	automaton.addEdge(state70, b, state71);
   	
   	// ### Transition 8
   	LLkState state80 = new LLkState(K2.S_$s$, K2.s_$e);
   	LLkState state81 = new LLkState(K2.S_s$$);
   	automaton.addAcceptingEdge(state80, SpecialTerminals.Epsilon, state81);
   	
   	// ### Transition 9
   	LLkState state90 = new LLkState(K2.S_$s$, K2.s_aSb$);
   	LLkState state91 = new LLkState(K2.S_s$$);
   	automaton.addAcceptingEdge(state90, Epsilon, state91);
		
		return automaton;
	}
	
	
	// Productions of the grammar
	public static final Production S_s$ = new Production(0, SpecialNonTerminals.StartLHS, N.S, SpecialTerminals.EndOfInputStream);
	public static final Production s_e = new Production(1, N.S, Epsilon);
	public static final Production s_aSb = new Production(2, N.S, T.a, N.S, T.b);
	
	// Possible lookahead terminal-sequences
//	public static final ITerminalSeq t_a = new TerminalSeqf(T.a);
	public static final ITerminalSeq t_$ = new TerminalSeqf(SpecialTerminals.EndOfInputStream);
	public static final ITerminalSeq t_b = new TerminalSeqf(T.b);
	public static final ITerminalSeq t_bb = new TerminalSeqf(T.b, T.b);
	public static final ITerminalSeq t_b$ = new TerminalSeqf(T.b, SpecialTerminals.EndOfInputStream);
	public static final ITerminalSeq t_aa = new TerminalSeqf(T.a, T.a);
	public static final ITerminalSeq t_ab = new TerminalSeqf(T.a, T.b);
	
	public static class K2 {
		// Possible looakheads
		public static final ITerminalSeqSet la_aa_ab = new TerminalSeqSetf(t_aa, t_ab);
//		public static final ITerminalSeqSet la_$_b = new TerminalSeqSetf(t_$, t_b);
		public static final ITerminalSeqSet la_bb_b$ = new TerminalSeqSetf(t_bb, t_b$);
		public static final ITerminalSeqSet la_$_bb_b$ = new TerminalSeqSetf(t_$, t_bb, t_b$);
		public static final ITerminalSeqSet la_aa_ab_bb_b$ = new TerminalSeqSetf(t_aa, t_ab, t_bb, t_b$);
//		public static final ITerminalSeqSet la_b = new TerminalSeqSetf(t_b);
		public static final ITerminalSeqSet la_aa_ab_$ = new TerminalSeqSetf(t_aa, t_ab, t_$);
		public static final ITerminalSeqSet la_$ = new TerminalSeqSetf(t_$);
		
		// LLkItems generated from the productions
		public static final LLkItem S_$s$ = new LLkItem(GrammarLectureLLk.S_s$, 0, la_aa_ab_$);
		public static final LLkItem s_$e = new LLkItem(s_e, 0, la_$_bb_b$);
		public static final LLkItem s_$aSb = new LLkItem(s_aSb, 0, la_aa_ab);
		public static final LLkItem s_a$Sb = new LLkItem(s_aSb, 1, la_aa_ab_bb_b$);
		public static final LLkItem s_aS$b = new LLkItem(s_aSb, 2, la_bb_b$);
		public static final LLkItem s_aSb$ = new LLkItem(s_aSb, 3, la_$_bb_b$);
		public static final LLkItem S_s$$ = new LLkItem(GrammarLectureLLk.S_s$, 1, la_$);
	}
}

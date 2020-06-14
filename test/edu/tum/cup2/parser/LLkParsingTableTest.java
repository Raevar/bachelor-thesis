package edu.tum.cup2.parser;

import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static org.junit.Assert.fail;
import edu.tum.cup2.generator.states.LLkState;
import edu.tum.cup2.parser.tables.LLkParsingTable;
import edu.tum.cup2.test.GrammarLectureLLk.K2;
import edu.tum.cup2.test.GrammarLectureLLk.T;


/**
 * Playground for the implementation of a the LL(k) parser
 * 
 * @author Gero
 * 
 */
public class LLkParsingTableTest
{
	public static void fillWithGrammarLectureLL2(LLkParsingTable tbl)
   {
      try
      {
      	// ### Transition 0
      	LLkState state00 = new LLkState(K2.S_$s$);
      	LLkState state01 = new LLkState(K2.S_$s$, K2.s_$e);
      	tbl.set(state00, Epsilon, state01);
      	tbl.setStartState(state00);
      	
      	// ### Transition 1
      	LLkState state10 = new LLkState(K2.S_$s$);
      	LLkState state11 = new LLkState(K2.S_$s$, K2.s_$aSb);
      	tbl.set(state10, Epsilon, state11);
      	
      	// ### Transition 2
      	LLkState state20 = new LLkState(K2.s_$aSb);
      	LLkState state21 = new LLkState(K2.s_a$Sb);
      	tbl.set(state20, T.a, state21);
      	
      	// ### Transition 3
      	LLkState state30 = new LLkState(K2.s_a$Sb);
      	LLkState state31 = new LLkState(K2.s_a$Sb, K2.s_$e);
      	tbl.set(state30, Epsilon, state31);
      	
      	// ### Transition 4
      	LLkState state40 = new LLkState(K2.s_a$Sb);
      	LLkState state41 = new LLkState(K2.s_a$Sb, K2.s_$aSb);
      	tbl.set(state40, Epsilon, state41);
      	
      	// ### Transition 5
      	LLkState state50 = new LLkState(K2.s_a$Sb, K2.s_$e);
      	LLkState state51 = new LLkState(K2.s_aS$b);
      	tbl.set(state50, Epsilon, state51);
      	
      	// ### Transition 6
      	LLkState state60 = new LLkState(K2.s_a$Sb, K2.s_aSb$);
      	LLkState state61 = new LLkState(K2.s_aS$b);
      	tbl.set(state60, Epsilon, state61);
      	
      	// ### Transition 7
      	LLkState state70 = new LLkState(K2.s_aS$b);
      	LLkState state71 = new LLkState(K2.s_aSb$);
      	tbl.set(state70, T.b, state71);
      	
      	// ### Transition 8
      	LLkState state80 = new LLkState(K2.S_$s$, K2.s_$e);
      	LLkState state81 = new LLkState(K2.S_s$$);
      	tbl.set(state80, Epsilon, state81);
      	
      	// ### Transition 9
      	LLkState state90 = new LLkState(K2.S_$s$, K2.s_aSb$);
      	LLkState state91 = new LLkState(K2.S_s$$);
      	tbl.set(state90, Epsilon, state91);
      	tbl.setEndState(state91);
      	
      } catch (Exception e)
      {
      	fail(e.getMessage());
      }
   }
}

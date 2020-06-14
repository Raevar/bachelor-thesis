package edu.tum.cup2.grammar;

import org.junit.Test;
import static junit.framework.Assert.fail;
import edu.tum.cup2.test.*;
import edu.tum.cup2.generator.TranslatedCSpec2;
import edu.tum.cup2.spec.CUP2Specification;
import java.util.LinkedList;

/**
 * tests CheckedGrammar
 * 
 * @author Michael Hausmann
 *
 */
public class CheckedGrammarTest {

	@Test public void checkReachability()
	{
		CUP2Specification mySpec = new SpecCalc1_NotReduced(); //Spec with not reachable NonTerminal
		Grammar grammar = mySpec.getGrammar();
		
		//CheckGrammar as decorator
		CheckedGrammar checkedGrammar = new CheckedGrammar(grammar);
		
		//start reachability analysis
		checkedGrammar.checkReachability();
		
		//get result
		LinkedList<NonTerminal> reachableNt_Actual = checkedGrammar.getReachableNonTerminals();
		
		//compute target value
		LinkedList<NonTerminal> reachableNt_Target = checkedGrammar.getNonTerminals();
		reachableNt_Target.remove(SpecCalc1_NotReduced.NonTerminals.notReachableNonTerminal);
		
		//compare target with actual
		if(!isEqualList(reachableNt_Target, reachableNt_Actual))
			fail();
		
		//make sure hasNotReachableNonTerminals() return correct value 
		if(!checkedGrammar.hasNotReachableNonTerminals())
			fail();
	}
	
	@Test public void checkProductivity()
	{
		CUP2Specification mySpec = new SpecCalc1_NotReduced(); //Spec with not reachable NonTerminal
		Grammar grammar = mySpec.getGrammar();
		
		//CheckGrammar as decorator
		CheckedGrammar checkedGrammar = new CheckedGrammar(grammar);
		
		//start reachability analysis
		checkedGrammar.checkProductivity();
		
		//get result
		LinkedList<NonTerminal> productiveNt_Actual = checkedGrammar.getProductiveNonTerminals();
		
		//compute target value
		LinkedList<NonTerminal> productiveNt_Target = checkedGrammar.getNonTerminals();
		productiveNt_Target.remove(SpecCalc1_NotReduced.NonTerminals.notProductiveNonTerminal);
		
		//compare target with actual
		if(!isEqualList(productiveNt_Target, productiveNt_Actual))
			fail();
		
		//make sure hasNotProductiveNonTerminals() return correct value 
		if(!checkedGrammar.hasNotProductiveNonTerminals())
			fail();
	}
	
	@Test public void testReducedGrammar()
	{
		CUP2Specification mySpec = new SpecCalc1_NotReduced(); //Spec with not reachable NonTerminal
		Grammar grammar = mySpec.getGrammar();
		
		//CheckGrammar as decorator
		CheckedGrammar checkedGrammar = new CheckedGrammar(grammar);
		
		IGrammar reducedGrammar = checkedGrammar.getReducedGrammar();
		LinkedList<NonTerminal> reducedNt_Actual = reducedGrammar.getNonTerminals();
		LinkedList<NonTerminal> reducedNt_Target = checkedGrammar.getNonTerminals();
		reducedNt_Target.remove(SpecCalc1_NotReduced.NonTerminals.notProductiveNonTerminal);
		reducedNt_Target.remove(SpecCalc1_NotReduced.NonTerminals.notReachableNonTerminal);
		
		//compare target with actual
		if(!isEqualList(reducedNt_Target, reducedNt_Actual))
			fail();
		
		//make sure the convenience methods hasNotProductiveNonTerminals(), hasNotReachableNonTerminals(),
		//isReduced() return the correct value
		if( !checkedGrammar.hasNotProductiveNonTerminals()
				|| !checkedGrammar.hasNotReachableNonTerminals()
				|| checkedGrammar.isReduced()
				)
			fail();
	}
	
	private <T> boolean isEqualList(LinkedList<T> t, LinkedList<T> a)
	{
		for(T nt : t)
		{
			if(!a.contains(nt)) return false;
		}
		for(T nt : a)
		{
			if(!t.contains(nt)) return false;
		}
		return true;
	}
	
	@Test public void testCSpec()
	{
		CUP2Specification cSpec = new TranslatedCSpec2();
		Grammar grammar = cSpec.getGrammar();
		
		//CheckGrammar as decorator
		CheckedGrammar checkedGrammar = new CheckedGrammar(grammar);
		
		IGrammar reducedGrammar = checkedGrammar.getReducedGrammar();
		LinkedList<NonTerminal> reducedNt_Actual = reducedGrammar.getNonTerminals();
		LinkedList<NonTerminal> reducedNt_Target = checkedGrammar.getNonTerminals();
		reducedNt_Target.remove(TranslatedCSpec2.NonTerminals.case_statement); //case_statement ist not supported by the Cmac Parser
		
		//test if getNotReachableNonTerminals() finds the case_statement NT
		LinkedList<NonTerminal> notReachableNT = checkedGrammar.getNotReachableNonTerminals();
		assert(notReachableNT.size() == 1);
		assert(notReachableNT.contains(TranslatedCSpec2.NonTerminals.case_statement));
		
		//test getNotProductiveNonTerminals() - get not productive NTs (has to be an empty set)
		LinkedList<NonTerminal> notProductiveNT = checkedGrammar.getNotProductiveNonTerminals();
		assert(notProductiveNT.size() == 0);
		
	  //compare target with actual
		if(!isEqualList(reducedNt_Target, reducedNt_Actual))
			fail();
		
		//make sure the convenience methods hasNotProductiveNonTerminals(), hasNotReachableNonTerminals(),
		//isReduced() return the correct value
		if( checkedGrammar.hasNotProductiveNonTerminals()
				|| !checkedGrammar.hasNotReachableNonTerminals()
				|| checkedGrammar.isReduced()
				)
			fail();
		
	}
}

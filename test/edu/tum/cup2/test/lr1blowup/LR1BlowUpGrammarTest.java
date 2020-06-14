package edu.tum.cup2.test.lr1blowup;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.spec.CUP2Specification;


/**
 * Class for testing LR(1)-blowup.
 * 
 * Result: Against expectations (but luckily!) there is no exponential growth
 * of the number of states when a LR(1)-grammar is defined without precedences
 * like done in the following grammars.
 * 
 * @author Andreas Wenger
 */
public class LR1BlowUpGrammarTest
{
	
	private CUP2Specification[] specsNoPrec = new CUP2Specification[]{
		new Spec1NoPrec(), new Spec2NoPrec(), new Spec3NoPrec(), new Spec4NoPrec(),
		new Spec5NoPrec(), new Spec6NoPrec(), new Spec7NoPrec(), new Spec14NoPrec()
	};
	private CUP2Specification[] specsPrec = new CUP2Specification[]{
		new Spec1Prec(), new Spec2Prec(), new Spec3Prec(), new Spec4Prec(),
		new Spec5Prec(), new Spec6Prec(), new Spec7Prec(), new Spec14Prec()
	};
	
	
	@Test public void countStates()
		throws GeneratorException
	{
		for (int i = 0; i < specsNoPrec.length; i++)
		{
			CUP2Specification specNoPrec = specsNoPrec[i];
			CUP2Specification specPrec = specsPrec[i];
			System.out.println("Grammar: " + specNoPrec.getClass().getSimpleName() + " / " + specPrec.getClass().getSimpleName());
			LRParsingTable tableNoPrec = new LR1Generator(specNoPrec, Verbosity.None).getParsingTable();
			LRParsingTable tablePrec = new LR1Generator(specPrec, Verbosity.None).getParsingTable();
			System.out.println("  #LR(1):   " + tableNoPrec.getStatesCount() + " / " + tablePrec.getStatesCount());
			tableNoPrec = new LR1toLALRGenerator(specNoPrec, Verbosity.None).getParsingTable();
			tablePrec = new LR1toLALRGenerator(specPrec, Verbosity.None).getParsingTable();
			System.out.println("  #LALR(1): " + tableNoPrec.getStatesCount() + " / " + tablePrec.getStatesCount());
		}
	}

}

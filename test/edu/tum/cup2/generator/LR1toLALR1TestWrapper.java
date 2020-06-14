package edu.tum.cup2.generator;

import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.generator.items.LR1Item;
import edu.tum.cup2.generator.states.LR1State;
import edu.tum.cup2.spec.CUP2Specification;

public class LR1toLALR1TestWrapper extends LR1toLALRGenerator
{
	public LR1toLALR1TestWrapper(CUP2Specification spec)
			throws GeneratorException {
		super(spec);
	}

	public Automaton<LR1Item, LR1State> getLALRAutomaton()
	{
		return super.getLALRAutomaton();
	}
}

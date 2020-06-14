package edu.tum.cup2.parser.tables;

import java.util.Hashtable;

import edu.tum.cup2.parser.actions.LRAction;

/**
 * Little Decorator for Serialization Test
 * 
 * @author Michael Hausmann
 */
public class LRActionTableTestDecorator
{
	LRActionTable actionTable;
	
	public LRActionTableTestDecorator(LRActionTable parentTable)
	{
		actionTable = parentTable;
	}
	
	public Hashtable<StateSymbolKey, LRAction> getActionTable()
	{
		return actionTable.getTable();
	}
}

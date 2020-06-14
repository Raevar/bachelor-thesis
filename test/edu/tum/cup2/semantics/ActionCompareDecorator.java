package edu.tum.cup2.semantics;
 
/**
 * Little Decorator for the Action class required for testing Serialization. 
 * 
 * @author Michael Hausmann
 *
 */
public class ActionCompareDecorator
{
	private Action a;
	public ActionCompareDecorator(Action a)
	{
		this.a = a;
	}
	
	public boolean equals(ActionCompareDecorator o)
	{
		if(	o == null ) return false;
		if(	!(o instanceof ActionCompareDecorator )) return false;
		if(a == null)
			return o.a == null;
		else if(null == a.getActionSubclassName())
			return null == o.a.getActionSubclassName();
		else if( o.a.getParamsCount() != a.getParamsCount()
			   | o.a.isVoidReturn() != a.isVoidReturn()
			   | !o.a.getActionSubclassName().equals(a.getActionSubclassName())
			   ) return false;
		return true;
	}
}

package ide.dropdown;


public class InsertAction
	extends Action
{

	public InsertAction(String s) {
		super(s);
	}
	
	@Override
	public boolean equals(Object x)
	{
		if (x instanceof Action)
			return text==null?((Action)x).text==null:text.equals(((Action)x).text);
		return false;
	}
	
}

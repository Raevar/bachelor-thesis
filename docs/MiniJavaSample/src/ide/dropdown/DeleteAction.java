package ide.dropdown;

public class DeleteAction
	extends Action
{

	public int	bL;
	public int	bC;
	public int	eL;
	public int	eC;

	public DeleteAction(String s, int bL, int bC, int eL, int eC) {
		super(s);
		this.bL = bL;
		this.bC = bC;
		this.eL = eL;
		this.eC = eC;
	}

}

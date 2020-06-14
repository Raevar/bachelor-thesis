package docugen;

import java.util.LinkedList;
import java.util.List;


/**
 * Used for numbering chapters and sections.
 * 
 * It builds a tree like this:
 * 
 * <pre>
 *        (0)          |  level 0
 *      /  |  \        |
 *    1    2    3      |  level 1
 *        / \   |      |
 *       1  2   1      |  level 2
 * </pre>
 * 
 * @author Andreas Wenger
 */
public class Tree
{
	
	private Tree parent;
	private int number;
	private String title;
	private List<Tree> children = new LinkedList<Tree>();
	
	
	public Tree()
	{
		this(null, 0, null);
	}
	
	
	private Tree(Tree parent, int number, String title)
	{
		this.parent = parent;
		this.number = number;
		this.title = title;
	}
	
	
	public Tree addChild(String title)
	{
		Tree child = new Tree(this, children.size() + 1, title);
		children.add(child);
		return child;
	}
	
	
	public Tree getParent()
	{
		return parent;
	}
	
	
	public int getNumber()
	{
		return number;
	}
	
	
	public String getTitle()
	{
		return title;
	}
	
	
	public int getLevel()
	{
		if (parent == null)
			return 0;
		else
			return parent.getLevel() + 1;
	}
	
	
	public String getLevelsAsString()
	{
		if (parent == null)
			return "";
		else
			return parent.getLevelsAsString() + number + ".";
	}
	
	
	public List<Tree> getChildren()
	{
		return children;
	}
	

}

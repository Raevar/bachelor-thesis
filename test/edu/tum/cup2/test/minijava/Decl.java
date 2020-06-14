package edu.tum.cup2.test.minijava;

import java.util.List;


/**
 * @author Michael Petter
 */
public class Decl
{

	public List<String> varlist;


	public Decl(List<String> l)
	{
		varlist = l;
	}


	public String toString()
	{
		String ret = "int ";
		boolean first = true;
		for (String s : varlist)
		{
			if (first) first=false;
			else ret += ",";
			ret += s;
		}
		return ret + ";\n";
	}


	public void accept(MinijavaVisitor v)
	{
		v.preVisit(this);
		v.postVisit(this);
	}
}

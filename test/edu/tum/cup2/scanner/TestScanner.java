package edu.tum.cup2.scanner;

import static edu.tum.cup2.grammar.SpecialTerminals.EndOfInputStream;

import java.util.ArrayList;

import edu.tum.cup2.grammar.Terminal;


/**
 * Simple {@link Scanner} for testing purposes.
 * 
 * It just returns the symbols with which it was created,
 * optionally with values.
 * 
 * @author Andreas Wenger
 */
public class TestScanner
	implements Scanner
{

	private final ArrayList<ScannerToken<Object>> input;
	private int position = 0;


	public TestScanner(Terminal... symbols)
	{
		this.input = new ArrayList<ScannerToken<Object>>(symbols.length);
		for (int i = 0; i < symbols.length; i++)
		{
			this.input.add(new ScannerToken<Object>(symbols[i]));
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public TestScanner(ScannerToken<?>... tokens)
	{
		this.input = new ArrayList<ScannerToken<Object>>(tokens.length);
		for (int i = 0; i < tokens.length; i++)
		{
			this.input.add((ScannerToken<Object>) tokens[i]);
		}
	}


	public ScannerToken<Object> readNextTerminal()
	{
		if (position < input.size())
			return input.get(position++);
		else
			return new ScannerToken<Object>(EndOfInputStream);
	}
	
	
	public static ScannerToken<Object> t(Terminal t)
	{
		return new ScannerToken<Object>(t);
	}
	
	
	public static ScannerToken<Object> v(Terminal t, Object v)
	{
		return new ScannerToken<Object>(t, v);
	}
	
	
}

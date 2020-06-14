package edu.tum.cup2.scanner;

import edu.tum.cup2.grammar.Terminal;


public class ScannerTokenTestTool
{
	
	public static ScannerToken<Integer> terminal(Terminal terminal, int x)
	{
		return new ScannerToken<Integer>(terminal, x);
	}
  public static ScannerToken<Integer> terminal(Terminal terminal, int x, int line, int column)
  {
    return new ScannerToken<Integer>(terminal, x, line, column);
  }
	
	
	public static <T> ScannerToken<T> terminal(Terminal terminal, T x)
	{
		return new ScannerToken<T>(terminal, x);
	}
  public static <T> ScannerToken<T> terminal(Terminal terminal, T x, int line, int column)
  {
    return new ScannerToken<T>(terminal, x, line, column);
  }
	
	
	public static ScannerToken<? extends Object> terminal(Terminal terminal)
	{
		return new ScannerToken<Integer>(terminal);
	}
  public static ScannerToken<? extends Object> terminal(Terminal terminal, int line, int column)
  {
    return new ScannerToken<Integer>(terminal, line, column);
  }
	

}

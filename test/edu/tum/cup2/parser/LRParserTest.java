package edu.tum.cup2.parser;

import static edu.tum.cup2.test.GrammarWiki1.Terminals._1;
import static edu.tum.cup2.test.GrammarWiki1.Terminals.plus;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.tum.cup2.parser.exceptions.ErrorActionException;
import edu.tum.cup2.parser.exceptions.ErrorStateException;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.test.GrammarWiki1;


public class LRParserTest
{
	
	
	/**
	 * Runs the parser with an example, taken from
	 * http://de.wikipedia.org/wiki/LR-Parser#Beispiel_eines_Parsers
	 * A copy is placed in
	 * "Documentation/Other/LRParser - Example 1.pdf"
	 * 
	 * No asserts are done, this is just a demo on the console.
	 */
	@Test public void runExample1()
	{
		LRParsingTable table = new GrammarWiki1().getParsingTable();
		
		//create parser
		LRParser parser = new LRParser(table);
		
		//call the parser with an input stream
		//TODO: automized test
		try
		{
			parser.parse(new TestScanner(_1, plus, _1));
		}
		catch (ErrorActionException ex)
		{
			ex.printStackTrace();
			fail("Error in action table!");
		}
		catch (ErrorStateException ex)
		{
			ex.printStackTrace();
			fail("Error in goto table!");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail("Other parsing error.");
		}
		
	}
	

}

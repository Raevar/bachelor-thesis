package edu.tum.cup2.generator;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.io.LRParsingTableTest;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.test.GrammarAppel_3_20;
import edu.tum.cup2.test.GrammarWiki1;


/**
 * Test cases for the {@link LR0Generator} class.
 * 
 * @author Andreas Wenger
 */
public class LR0GeneratorTest
{
	
	
	/**
	 * Tests the {@link LR0Generator}, using the {@link GrammarAppel_3_20}.
	 */
	@Test public void computeParsingTableTest_Appel()
		throws Exception
	{
		GrammarAppel_3_20 grammar = new GrammarAppel_3_20();
		//compute the parsing table
		LRParsingTable table = new LR0Generator(grammar).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new File("3_20.html"));
		
		//action table must have 29 cells, goto table must have 4 items
		assertEquals(29, table.getActionTable().getNonErrorCellsCount());
		assertEquals(4, table.getGotoTable().getNonErrorCellsCount());
	}
	
	
	/**
	 * Tests the {@link LR0Generator}, using the {@link GrammarWiki1}
	 * and trying an example input.
	 */
	@Test public void computeParsingTableTest_Wiki()
		throws Exception
	{
		GrammarWiki1 grammar = new GrammarWiki1();
		LR0Generator generator = new LR0Generator(grammar);
		
		LRParsingTable table = generator.getParsingTable();
		
		//check table
		LRParsingTable correctTable = grammar.getParsingTable();
		assertEquals(correctTable, table);
	}

}

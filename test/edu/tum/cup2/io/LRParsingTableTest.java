package edu.tum.cup2.io;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.test.GrammarWiki1;


/**
 * Test class for {@link LRParsingTableDump}.
 * 
 * @author Andreas Wenger
 */
public class LRParsingTableTest
{
	
	
	@Test public void dumpToHTMLTest()
		throws Exception
	{
		GrammarWiki1 grammar = new GrammarWiki1();
		LR0Generator generator = new LR0Generator(grammar);
		
		LRParsingTableDump.dumpToHTML(generator.getParsingTable(), new File("test-dump.html"));
	}

}

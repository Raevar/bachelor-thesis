package edu.tum.cup2.io;

import static junit.framework.Assert.fail;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.LRParserTestWrapper;
import edu.tum.cup2.parser.actions.LRAction;
import edu.tum.cup2.parser.actions.Reduce;
import edu.tum.cup2.parser.tables.LRActionTable;
import edu.tum.cup2.parser.tables.LRActionTableTestDecorator;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.parser.tables.LRParsingTableTestTool;
import edu.tum.cup2.parser.tables.StateSymbolKey;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.ActionCompareDecorator;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.SpecCalc4;
import edu.tum.cup2.test.errorRecovery.SpecMiniJava;

/**
 * Test class for {@link LRParsingTableSerialization}.
 * 
 * @author Michael Hausmann
 */
public class LRParserSerializationTest {
	
	static final String fTestFileName = "LRParsingTableSerializerTest.cup2"; 
	
  /**
   * SD :
   * 	Sorry, but creation of parser-generator with grammar only is not possible anymore!
   * 	Please update your code in such a way, that a complete specification is given.
   * 

	@Test public void saveAndLoadParserTest()
	throws Exception
	{
		//create LRParserSerialization object
		LRParserSerialization serial = new LRParserSerialization(fTestFileName);
		
		//generate parser
		GrammarWiki1 grammar = new GrammarWiki1();
		LR0Generator generator = new LR0Generator(grammar);
		LRParser parser = new LRParser(generator.getParsingTable());
		LRParsingTableDump.dumpToHTML(generator.getParsingTable(), new File("table1.test.cup2"));
		
		//serialize parser
		serial.saveParser(parser);
		
		//de-serialize parser
		LRParser parser2 = serial.loadParser();
			
		//TODO Scanner anwerfen: Scanner input = 
		//parser2.parse(input);
		
		LRParsingTableDump.dumpToHTML(generator.getParsingTable(), new File("table2.test.cup2"));
	}
	
	 *
	 **/
	
	@Test public void SpecCalc4ParserSerializationTest()
	throws Exception
	{
		SpecCalc4 spec = new SpecCalc4();	
		saveAndLoadParserAndCompare(spec, fTestFileName);
	}
	@Test public void MiniJavaParserSerializationTest()
			throws Exception
			{
				SpecMiniJava spec = new SpecMiniJava();
				saveAndLoadParserAndCompare(spec, fTestFileName);
			}
	private void saveAndLoadParserAndCompare(CUP2Specification spec, String strTestFileName)
	throws GeneratorException
	{
		//create LRParserSerialization object
		LRParserSerialization serial = new LRParserSerialization(strTestFileName);
		
		LR1Generator generator = new LR1Generator(spec);
		LRParsingTable table1 = generator.getParsingTable();
		LRParser parser = new LRParser(table1);
		LRParsingTableDump.dumpToHTML(
				table1, 
				new File("SpecCalc4.table1.test.cup2.html"));
		
		//serialize parser
		serial.saveParser(parser);
		
		//de-serialize parser
		LRParser parser2 = serial.loadParser();
		
		//TODO Scanner anwerfen: Scanner input = 
		//parser2.parse(input);
		
		//create a LRParserTestWrapper and test reconstructed parser
		LRParserTestWrapper parser2wrapper = new LRParserTestWrapper(parser2);
		LRParsingTable table2 = parser2wrapper.getParsingTable(); 
		//TODO just for debug
		LRParsingTableDump.dumpToHTML(
				table2, 
				new File("SpecCalc4.table2.test.cup2.html"));
	
		//automatic comparsion of parsing tables
		LRParsingTableTestTool.assertEquals(table1, table2);
		
		//verify whether semantic action are de-serialized properly
		equalSemanticActions(table2.getActionTable(), table1.getActionTable());
	} /* end of test */

	private boolean equalSemanticActions(LRActionTable t1, LRActionTable t2)
	{
		LRActionTableTestDecorator newActionTableWrapper = new LRActionTableTestDecorator(t1);
		Hashtable<StateSymbolKey, LRAction> htActionsNew = newActionTableWrapper.getActionTable();
		LRActionTableTestDecorator oldActionTableWrapper = new LRActionTableTestDecorator(t2);
		Hashtable<StateSymbolKey, LRAction> htActionsOld = oldActionTableWrapper.getActionTable();
		
		//check if number of actions is identical
		if(htActionsOld.size() != htActionsNew.size())
		{
			System.err.println("Number of actions after de-serializing different");
			fail();
			return false;
		}
		
		//check if actions are identical
		for( Enumeration<StateSymbolKey> e = htActionsOld.keys(); e.hasMoreElements();)
		{
			StateSymbolKey ssk = e.nextElement();
			LRAction lrActionOld = htActionsOld.get(ssk);
			if(lrActionOld == null)
			{
				System.err.println("lrActionOld in saveAndLoadParserTest2 is null");
				fail();
				return false;
			}
			LRAction lrActionNew = htActionsNew.get(ssk);
			if(lrActionNew == null)
			{
				System.err.println("lrActionNew in saveAndLoadParserTest2 is null");
				fail();
				return false;
			}
			
			//get Actions from LRActions
			Action actOld = null;
			Action actNew = null;
      if (lrActionOld instanceof Reduce)
        actOld = ((Reduce)lrActionOld).getAction();
      if (lrActionNew instanceof Reduce)
        actNew = ((Reduce)lrActionNew).getAction();
			
			//create CompareDecorators
			ActionCompareDecorator aNew = new ActionCompareDecorator(actNew);
			ActionCompareDecorator aOld = new ActionCompareDecorator(actOld);
			if(!aNew.equals(aOld))
			{
				if(actOld != null)
					System.err.println("failure: semantic actions don't match (class: " + actOld.getClass().getName() + ")");	
				fail();
				return false;
			} /* end if not equals */
		} /* end for */
		return true;
	} /* end of equalSemanticActions */
	
} /* end of class */

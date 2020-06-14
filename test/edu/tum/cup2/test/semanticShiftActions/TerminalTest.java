package edu.tum.cup2.test.semanticShiftActions;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.exceptions.LRParserException;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.semanticShiftActions.TerminalTest.NonTerminals.*;
import static edu.tum.cup2.test.semanticShiftActions.TerminalTest.Terminals.*;


/**
 * @author Stefan Dangl
 */
public class TerminalTest
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
	  a,b,c,d
	}
	
	public enum NonTerminals implements NonTerminal
	{
	  S,
	  W
	}
	
	//symbols with values
	public class a extends SymbolValue<String>{};
	public class b extends SymbolValue<String>{};
	public class c extends SymbolValue<String>{};
  public class d extends SymbolValue<String>{};

  public class S extends SymbolValue<String>{};
  public class W extends SymbolValue<String>{};
	
	public TerminalTest()
	{
	  if (table!=null) return;
	  
	  precedences();
	
	  grammar(
			prod(S,  	rhs(a,b,SpecialTerminals.$a,c),
			  new Action() {
        public String a(String a, String b)
        {
            System.out.println("S->ab.c (1)");
            return a+" "+b;
        }},
        new Action() {
        public String a(String a, String b, Object x, String c)
        {
            System.out.println("S->abc. (r)");
            return a+" "+b+" "+c;
         }}
			),
      prod(S,   rhs(a,b,d),
          new Action() {
          public String a(String a, String b, String d)
          {
              System.out.println("S->abd. (r)");
              return a+" "+b+" "+d;
          }}
        ),
			prod(S,    rhs(a,SpecialTerminals.$a,W,SpecialTerminals.$a,c),
          new Action() {
          public String a(String a)
          {
              System.out.println("S->a.Wc (1)");
              return a;
          }},
	        new Action() {
	        public String a(String a, Object x, String W)
	        {
	            System.out.println("S->aW.c (2)");
	            return a+" "+W;
	        }},
	        new Action() {
	        public String a(String a, Object x, String W, Object y, String c)
	        {
	            System.out.println("S->aWc. (r)");
	            return a+" "+W+" "+c;
	         }}
	    ),
      prod(W,    rhs(a,b,SpecialTerminals.$a,c),
          new Action() {
          public String a(String a, String b)
          {
              System.out.println("W->ab.c (1)");
              return a+" "+b;
          }},
          new Action() {
          public String a(String a, String b, Object x, String c)
          {
              System.out.println("W->abc. (r)");
              return a+" "+b+" "+c;
           }}
       )
			
		);
	  
	  try {
      table = new LR1Generator(this).getParsingTable();
    } catch (GeneratorException e) {
      e.printStackTrace();
    }
    LRParsingTableDump.dumpToHTML(table, new File("TerminalTest.html"));
    
	}
	
	private static LRParsingTable table = null;

	@Test public void testActionAfterTerminal() throws LRParserException, IOException
	{
		System.out.println("testActionAfterTerminal() : ");
		Object result = new LRParser(table).parse(new TestScanner(
      terminal(a,"a"),
      terminal(b,"b"),
      // action
      terminal(c,"c")
      // reduce action
		));
		System.out.println(result);
	}
	
  @Test public void testBadActionAfterTerminal() throws LRParserException, IOException
  {
    System.out.println("testBadActionAfterTerminal() : ");
    Object result = new LRParser(table).parse(new TestScanner(
      terminal(a,"a"),
      terminal(b,"b"),
      terminal(d,"d")
      // reduce action
    ));
    System.out.println(result);
  }
  
  @Test public void testActionBeforeAndAfterNonterminal() throws LRParserException, IOException
  {
    System.out.println("testActionBeforeAndAfterNonterminal() : ");
    Object result = new LRParser(table).parse(new TestScanner(
      terminal(a,"a"),
      // action
      // start of W
        terminal(a,"a"),
        terminal(b,"b"),
        // action
        terminal(c,"c"),
        // reduce action
      // end of W
      // action
      terminal(c,"c")
      // reduce action
    ));
    System.out.println(result);
  }

  
}


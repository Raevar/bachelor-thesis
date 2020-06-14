package edu.tum.cup2.test.semanticShiftActions;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.spec.exceptions.IllegalSpecException;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.semanticShiftActions.SpecificationTest2.NonTerminals.*;
import static edu.tum.cup2.test.semanticShiftActions.SpecificationTest2.Terminals.*;
import static org.junit.Assert.fail;


/**
 * @author Stefan Dangl
 */
public class SpecificationTest2
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
	  a,b,c,d
	}
	
	public enum NonTerminals implements NonTerminal
	{
	  S
	}
	
	// terminals with values
	public class a extends SymbolValue<String>{};
	public class b extends SymbolValue<String>{};
	public class c extends SymbolValue<String>{};
  public class d extends SymbolValue<String>{};

	// non-terminals with values
  public class S extends SymbolValue<String>{};
	
  public void specifyGrammar()
  {
    grammar(
      prod(S,
        rhs(a,SpecialTerminals.$a,b,d),
          new Action() {
          public String a() // METHOD TOO LESS PARAMETERS
          {
              System.out.println("S->a.bc (1)");
              System.out.println("  a = "+a);
              return a+"";
          }},
          new Action() {
          public String a(String a, Object x, String b, String d)
          {
              System.out.println("S->abc. (r)");
              System.out.println("  a   = "+a);
              System.out.println("  b   = "+b);
              //System.out.println("  aux = "+previousResult);
              System.out.println("  d   = "+d);
              return a+" "+b+" "+d;
          }}
      )
      
    );
  }
  
  @Test
  public void test()
    throws Exception
  {
    try{
      this.specifyGrammar();
      fail();
    } catch (edu.tum.cup2.spec.exceptions.IllegalSpecException e)
    { }
  }

  
}


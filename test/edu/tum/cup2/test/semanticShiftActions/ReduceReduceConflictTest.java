package edu.tum.cup2.test.semanticShiftActions;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.semanticShiftActions.ReduceReduceConflictTest.NonTerminals.*;
import static edu.tum.cup2.test.semanticShiftActions.ReduceReduceConflictTest.Terminals.*;


/**
 * @author Stefan Dangl
 */
public class ReduceReduceConflictTest
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
	
	public ReduceReduceConflictTest()
	{
	 precedences();
	
	  grammar(
			prod(S,
        rhs(a,SpecialTerminals.$a,b,c),
          new Action() {
          public String a(String a)
          {
              System.out.println("S->a.bc (1)");
              System.out.println("  a = "+a);
              return a.toUpperCase();
          }},
          new Action() {
          public String a(String a, Object previousResult, String b, String c)
          {
              System.out.println("S->abc. (r)");
              System.out.println("  a               = "+a);
              System.out.println("  previousResult  = "+previousResult);
              System.out.println("  b               = "+b);
              System.out.println("  c               = "+c);
              return previousResult+" "+b+" "+c;
          }}
        ,
        rhs(a,SpecialTerminals.$a,b,d),
          new Action() {
          public String a(String a)
          {
              System.out.println("S->a.bd (1)");
              System.out.println("  a = "+a);
              return a.toUpperCase();
          }},
          new Action() {
          public String a(String a, Object previousResult, String b, String d)
          {
              System.out.println("S->abc. (r)");
              System.out.println("  a               = "+a);
              System.out.println("  previousResult  = "+previousResult);
              System.out.println("  b               = "+b);
              System.out.println("  d               = "+d);
              return previousResult+" "+b+" "+d;
          }}
			)
			
		);
	}

	@Test(expected=edu.tum.cup2.generator.exceptions.ReduceReduceConflict.class)
	public void test()
		throws Exception
	{
		System.out.println("Reduce-Reduce-Conflict Test :");
		try{
		  new LR1Generator(this);
		}
		catch(edu.tum.cup2.generator.exceptions.ReduceReduceConflict e)
		{
		  System.out.println(e);
		  throw e;
		}
	}

  
}


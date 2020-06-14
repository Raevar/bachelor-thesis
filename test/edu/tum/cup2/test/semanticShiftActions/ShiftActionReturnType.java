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

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.semanticShiftActions.ShiftActionReturnType.NonTerminals.*;
import static edu.tum.cup2.test.semanticShiftActions.ShiftActionReturnType.Terminals.*;

/**
 * @author Stefan Dangl
 */
public class ShiftActionReturnType
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
	  a,b,c
	}
	
	public enum NonTerminals implements NonTerminal
	{
	  S
	}
	
	// terminals with values
	public class a extends SymbolValue<String>{};
	public class b extends SymbolValue<String>{};
	public class c extends SymbolValue<String>{};

	// non-terminals with values
  public class S extends SymbolValue<String>{};
	
	public ShiftActionReturnType()
	{
	 precedences();
	
	  grammar(
			prod(S,  	rhs(a,b,SpecialTerminals.$a,SpecialTerminals.$a,SpecialTerminals.$a,c),
			  new Action() {
          public void a(String a, String b)
          {
              System.out.println("S->ab.c (1)");
              System.out.println("  a = "+a);
              System.out.println("  b = "+b);
          }},
        new Action() {
          public String a(String a, String b)
          {
              System.out.println("S->ab.c (2)");
              System.out.println("  a = "+a);
              System.out.println("  b = "+b);
              return (a+" "+b).toUpperCase();
          }},
        new Action() {
          public void a(String a, String b, Object irrelevant)
          {
              System.out.println("S->ab.c (3)");
              System.out.println("  a = "+a);
              System.out.println("  b = "+b);
          }},
        new Action() {
          public String a(String a, String b, String previousResult, String c)
          {
              System.out.println("S->abc. (r)");
              System.out.println("  a                         = "+a);
              System.out.println("  b                         = "+b);
              System.out.println("  result of shift-action 2  = "+previousResult);
              System.out.println("  c                         = "+c);
              return previousResult+" "+c;
          }}
			)
			
		);
	}
	

	@Test public void test()
		throws Exception
	{
		System.out.println("test() : ");
		LRParsingTable table = new LR1Generator(this).getParsingTable();

    LRParsingTableDump.dumpToHTML(table, new File("ShiftActionReturnType.html"));
		Object result = new LRParser(table).parse(new TestScanner(
      terminal(a,"a"),
      terminal(b,"b"),
      terminal(c,"c")
		));
		System.out.println(result);
	}

  
}


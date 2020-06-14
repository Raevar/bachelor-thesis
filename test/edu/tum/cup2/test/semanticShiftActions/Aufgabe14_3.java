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
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.semanticShiftActions.Aufgabe14_3.NonTerminals.*;
import static edu.tum.cup2.test.semanticShiftActions.Aufgabe14_3.Terminals.*;


/**
 * @author Stefan Dangl
 */
public class Aufgabe14_3
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
	  EXPR_END, NUMBER, BI_SIGN, UN_SIGN, TER_SIGN
	}
	
	public enum NonTerminals implements NonTerminal
	{
	  expr, expr_list
	}
	
	//symbols with values
	public class NUMBER extends SymbolValue<String>{};
	public class BI_SIGN extends SymbolValue<String>{};
	public class UN_SIGN extends SymbolValue<String>{};
  public class TER_SIGN extends SymbolValue<String>{};
  public class expr extends SymbolValue<String>{};
  public class expr_list extends SymbolValue<String>{};
	
	public Aufgabe14_3()
	{
	 precedences();
	
	  grammar(
			prod(expr_list,  	rhs(),
        new Action() {
				public String a()
           { return ""; }}
			),
      prod(expr_list,   rhs(expr, EXPR_END, expr_list),
        new Action() {
        public String a(String e, String el)
           { return e+";\n"+el; }}
      ),
      /*
      prod(expr_list,    rhs(SpecialTerminals.Error,EXPR_END),
          new Action() {
            public String a(ErrorInformation x)
            {
              return "erroniusXpreschn;"; 
            }
          }
      ),*/
      prod(expr_list,    rhs(SpecialTerminals.Error,EXPR_END, expr_list),
          new Action() {
            public String a(ErrorInformation x, String el)
            {
              return "erroniusXpreschn;\n"+el; 
            }
          }
      ),
      
      prod(expr,   rhs(BI_SIGN, expr, expr),
        new Action() {
        public String a(String s, String e1, String e2)
           { return "("+e1+" "+s+" "+e2+")"; }}
      ),
      prod(expr,   rhs(UN_SIGN, expr),
        new Action() {
        public String a(String s, String e)
           { return s+e; }}
      ),
      prod(expr,   rhs(NUMBER),
        new Action() {
        public String a(String n)
           { return n; }}
      ),
      
      prod(expr,   rhs(TER_SIGN, expr, expr, SpecialTerminals.$a, expr),
        new Action() {
        public String a(String s, String e1, String e2)
           { System.out.println("TER (1)"); return "("+e1+" "+s+" "+e2; }},
        new Action() {
        public String a(String s, String e1, String e2, Object RESULT1, String e3)
           { System.out.println("TER (2)"); return RESULT1+" "+s+" "+e2+")"; }}
      )
      
      /*
      ,prod(expr,  	rhs(SpecialTerminals.Error),
          new Action() {
						public String a(ErrorInformation x)
						{
             	return "erroniusXpreschn";
						}
					}
			)*/
			
		);
	}
	

  /**
	 *      + + 3 4 -5;
   *      / 15 5;
	 **/
	@Test public void test1()
		throws Exception
	{
		System.out.println("test1() : ");
		LRParsingTable table = new LR1Generator(this).getParsingTable();

    LRParsingTableDump.dumpToHTML(table, new File("Aufgabe14_3.html"));
		Object result = new LRParser(table).parse(new TestScanner(
      terminal(TER_SIGN,"++"),
      terminal(NUMBER,"100"),
      terminal(NUMBER,"8"),
      terminal(NUMBER,"12"),
      terminal(EXPR_END)
		));
		System.out.println(result);
	}

  
}


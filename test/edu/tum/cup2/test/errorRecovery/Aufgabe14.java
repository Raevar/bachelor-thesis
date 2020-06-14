package edu.tum.cup2.test.errorRecovery;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.ParserObserver;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.errorRecovery.Aufgabe14.NonTerminals.*;
import static edu.tum.cup2.test.errorRecovery.Aufgabe14.Terminals.*;

/**
 * @author Stefan Dangl
 */
public class Aufgabe14
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
	  EXPR_END, NUMBER, BI_SIGN, UN_SIGN
	}
	
	public enum NonTerminals implements NonTerminal
	{
	  expr, expr_list
	}
	
	//symbols with values
	public class NUMBER extends SymbolValue<String>{};
	public class BI_SIGN extends SymbolValue<String>{};
	public class UN_SIGN extends SymbolValue<String>{}
  public class expr extends SymbolValue<String>{};
  public class expr_list extends SymbolValue<String>{};
	
	public Aufgabe14()
	{
	 precedences();
	
	  grammar(
			prod(expr_list,  	rhs(expr,EXPR_END),
        new Action() {
				public String a(String e)
           { return e+";"; }}
			),
      prod(expr_list,   rhs(expr, EXPR_END, expr_list),
        new Action() {
        public String a(String e, String el)
           { return e+";\n"+el; }}
      ),
      prod(expr_list,    rhs(SpecialTerminals.Error,EXPR_END),
          new Action() {
            public String a(ErrorInformation x)
            {
              return "erroniusXpreschn;"; 
            }
          }
      ),
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
      )
      
      
      ,prod(expr,  	rhs(SpecialTerminals.Error),
          new Action() {
						public String a(ErrorInformation x)
						{
             	return "erroniusXpreschn";
						}
					}
			)
			
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
		Object result = new LRParser(table).parse(new TestScanner(
      terminal(BI_SIGN,"+"),
      terminal(BI_SIGN,"+"),
      terminal(NUMBER,"3"),
      terminal(NUMBER,"4"),
      terminal(UN_SIGN,"-"),
      terminal(NUMBER,"5"),
      terminal(EXPR_END),
      terminal(BI_SIGN,"/"),
      terminal(NUMBER,"15"),
      terminal(NUMBER,"5"),
      terminal(EXPR_END)
		));
		System.out.println(result);
	}

  /**
   *      + 3 4 ;
   *      + + + 3 ;
   *      + 1 1 ;
   *      + 2 2 ;
   *      + 3 3 ;
   *      4 4 ;
   **/
  @Test public void errorTest()
    throws Exception
  {
    System.out.println("errorTest() : ");
    LRParsingTable table = new LR1Generator(this).getParsingTable();
    LRParser parser = new LRParser(table);
    parser.register(
        new ParserObserver()
        {
          public void syntax_error(ErrorInformation error) {
            if (error.isRecovered())
              System.err.println("A syntax error occured :\n"+error);
            else
              System.err.println("An unrecoverable syntax error occured :\n"+error);
          }
        }
    );
    Object result = parser.parse(new TestScanner(
    
      terminal(BI_SIGN,"+",     0, 0),
      terminal(NUMBER,"3",      0, 1),
      terminal(NUMBER,"4",      0, 2),
      terminal(EXPR_END,        0, 3),
      
      terminal(BI_SIGN,"+",     1, 0),
      terminal(BI_SIGN,"+",     1, 1),
      terminal(BI_SIGN,"+",     1, 2),
      terminal(NUMBER,"3",      1, 3),
      terminal(EXPR_END,        1, 4),
      
      terminal(BI_SIGN,"+",     2, 0),
      terminal(NUMBER,"1",      2, 1),
      terminal(NUMBER,"1",      2, 2),
      terminal(EXPR_END,        2, 3),
      
      terminal(BI_SIGN,"+",     3, 0),
      terminal(NUMBER,"2",      3, 1),
      terminal(NUMBER,"2",      3, 2),
      terminal(EXPR_END,        3, 3),
      
      terminal(BI_SIGN,"+",     4, 0),
      terminal(NUMBER,"3",      4, 1),
      terminal(NUMBER,"3",      4, 2),
      terminal(EXPR_END,        4, 3),
      
      terminal(NUMBER,"4",      5, 1),
      terminal(NUMBER,"4",      5, 2),
      terminal(EXPR_END,        5, 3),
      
      terminal(BI_SIGN,"+",     4, 0),
      terminal(NUMBER,"5",      4, 1),
      terminal(NUMBER,"5",      4, 2),
      terminal(EXPR_END,        4, 3),
      
      terminal(EXPR_END,        6, 0),
      terminal(EXPR_END,        6, 1),

      terminal(BI_SIGN,"+",     7, 0),
      terminal(NUMBER,"6",      7, 1),
      terminal(NUMBER,"6",      7, 2),
      terminal(EXPR_END,        7, 3),

      terminal(BI_SIGN,"+",     8, 0),
      terminal(NUMBER,"7",      8, 1),
      terminal(EXPR_END,        8, 3),
      terminal(NUMBER,"7",      8, 4),
      terminal(EXPR_END,        8, 5),

      terminal(BI_SIGN,"+",     8, 0),
      terminal(NUMBER,"7",      8, 1),
      terminal(NUMBER,"7",      8, 3),
      terminal(NUMBER,"7",      8, 4),
      terminal(EXPR_END,        8, 3)
      
    ));
    System.out.println("\nThe result is:\n"+result);
    Class c = String[].class;
  }
  
}


package edu.tum.cup2.test.errorRecovery.tokenInserter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.exceptions.EndOfInputstreamException;
import edu.tum.cup2.parser.exceptions.LRParserException;
import edu.tum.cup2.parser.exceptions.MissingErrorRecoveryException;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.InsertionScanner;
import edu.tum.cup2.scanner.RandomTokenCreator;
import edu.tum.cup2.scanner.ScannerToken;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.ParserInterface;
import edu.tum.cup2.semantics.ParserObserver;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.spec.Insertable;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.Aufgabe14_3.NonTerminals.*;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.Aufgabe14_3.Terminals.*;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.ASSIGN;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.BEGIN;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.BUNOP;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.COMMA;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.IDENT;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.INTCONST;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.LPAR;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.RPAR;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.SEMICOLON;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.TYPE;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.WHILE;

/**
 * @author Stefan Dangl
 */
public class Aufgabe14_3
	extends CUP2Specification
{

	public enum Terminals implements Terminal
	{
    EXPR_END, NUMBER, BI_SIGN, UN_SIGN;
    public Object insert;
	}

	public enum NonTerminals implements NonTerminal
	{
	  expr, expr_list;
	}

	//terminals with values
	public static class BI_SIGN extends SymbolValue<String>{};
  public static class NUMBER extends SymbolValue<String>{};
	public static class UN_SIGN extends SymbolValue<String>{};

  // non-terminals with values
  public static class expr extends SymbolValue<String>{};
  public class expr_list extends SymbolValue<String>{};

	public Aufgabe14_3()
	{
	 precedences();

	 // symbols to be inserted (with top-down preceference)
	 insert(NUMBER,    new String[]{"number"}, null);         // insert number "null"
	 //insertable(BI_SIGN,   new String[]{"+","-","*","/"}, "o");   // insert binary sign
	 insert(EXPR_END,  new String[]{";"});                    // no value-binding
		 
	 register(new ParserInterface(){
	   @Override
  	 public int getErrorSyncSize() {
  	    return 1;
  	 }
	   public int getMaxTokenInsertTries() {
	     return 4;
	   };
	 });

	 grammar(
	      prod(expr_list,   rhs(),
	        new Action() {
	        public String a()
	           { return ""; }}
	      ),
	      prod(expr_list,   rhs(expr, EXPR_END, expr_list),
	        new Action() {
	        public String a(String e, String el)
	           { return e+";\n"+el; }}
	      ),
	      prod(expr_list,    rhs(SpecialTerminals.Error,EXPR_END, expr_list),
	          new Action() {
	            public String a(ErrorInformation x, String el)
	            { return "erroniusXpreschn;\n"+el;  } }
	      ),
	      prod(expr_list,    rhs(SpecialTerminals.Error),
	          new Action() {
	            public String a(ErrorInformation x)
	            { return "erroniusXpreschn\n";  } }
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
	    );
	  }
	
	  /**
	   * 0     + 3 4 ;
	   * 1     + + + 3 ;
	   * 2     + 1 1 ;
	   * 3     + 2 2 ;
	   * 4     + 3 3 ;
	   * 5     4 4 ;
	   * 6     + 5 5 ;
	   * 7     ; ;
	   * 8     + 6 ;
	   * 9     + 7 7 + 7 + 7 7 ;
	   * 10    + 8 ; + ;
	 * 11    + 11 11 ;
	 * 12    + + ;
	 * 13    + + + ;
	 * 14    + 14 ;
	 * 15    + + + + + ;
	 * 16	 + + + +
	   **/
	@Test
	public void errorTest() throws Exception {
		System.out.println("errorTest() (maxTokenInsertTries=4): ");
		/** create table **/
		LRParsingTable table = new LR1Generator(this).getParsingTable();
		/** dump table **/
		LRParsingTableDump.dumpToHTML(table, new java.io.File(
			"Aufgabe14_3_InsertionScanner.html"));
		/** create parser **/
		LRParser parser = new LRParser(table);
		/** supply with input ... */
		InsertionScanner input = 
			new InsertionScanner(
				table, 
			new TestScanner(
			terminal(BI_SIGN, "+", 0, 0), terminal(NUMBER, "3", 0, 1),
			terminal(NUMBER, "4", 0, 2), terminal(EXPR_END, 0, 3),
			
			terminal(BI_SIGN, "+", 1, 0), terminal(BI_SIGN, "+", 1, 1), terminal(BI_SIGN,
				"+", 1, 2), terminal(NUMBER, "3", 1, 3), terminal(EXPR_END, 1, 4),

			terminal(BI_SIGN, "+", 2, 0), terminal(NUMBER, "1", 2, 1), terminal(NUMBER,
				"1", 2, 2), terminal(EXPR_END, 2, 3),

			terminal(BI_SIGN, "+", 3, 0), terminal(NUMBER, "2", 3, 1), terminal(NUMBER,
				"2", 3, 2), terminal(EXPR_END, 3, 3),

			terminal(BI_SIGN, "+", 4, 0), terminal(NUMBER, "3", 4, 1), terminal(NUMBER,
				"3", 4, 2), terminal(EXPR_END, 4, 3),

			terminal(NUMBER, "4", 5, 0), terminal(NUMBER, "4", 5, 1), terminal(EXPR_END,
				5, 2),

			terminal(BI_SIGN, "+", 6, 0), terminal(NUMBER, "5", 6, 1), terminal(NUMBER,
				"5", 6, 2), terminal(EXPR_END, 6, 3),

			terminal(EXPR_END, 7, 0), terminal(EXPR_END, 7, 1),

			terminal(BI_SIGN, "+", 8, 0), terminal(NUMBER, "6", 8, 1), terminal(EXPR_END,
				8, 2),

			terminal(BI_SIGN, "+", 9, 0), terminal(NUMBER, "7", 9, 1), terminal(NUMBER,
				"7", 9, 2), terminal(BI_SIGN, "+", 9, 3), terminal(NUMBER, "7", 9, 4),
			terminal(BI_SIGN, "+", 9, 5), terminal(NUMBER, "7", 9, 6), terminal(NUMBER,
				"7", 9, 7), terminal(EXPR_END, 9, 8),

			terminal(BI_SIGN, "+", 10, 0), terminal(NUMBER, "8", 10, 1), terminal(
				EXPR_END, 10, 2), terminal(BI_SIGN, "+", 10, 3),
			terminal(EXPR_END, 10, 4),

			terminal(BI_SIGN, "+", 11, 0), terminal(NUMBER, "11", 11, 1), terminal(
				NUMBER, "11", 11, 2), terminal(EXPR_END, 11, 3),

			terminal(BI_SIGN, "+", 12, 0), terminal(BI_SIGN, "+", 12, 1), terminal(
				EXPR_END, 12, 2),

			terminal(BI_SIGN, "+", 13, 0), terminal(BI_SIGN, "+", 13, 1), terminal(
				BI_SIGN, "+", 13, 2), terminal(EXPR_END, 13, 3),

			terminal(BI_SIGN, "+", 14, 0), terminal(NUMBER, "14", 14, 1), terminal(
				EXPR_END, 14, 2),

			terminal(BI_SIGN, "+", 15, 0), terminal(BI_SIGN, "+", 15, 1), terminal(
				BI_SIGN, "+", 15, 2), terminal(BI_SIGN, "+", 15, 3), terminal(BI_SIGN,
				"+", 15, 4), terminal(EXPR_END, 15, 5)
			
			,terminal(BI_SIGN, "+", 16, 0),terminal(BI_SIGN, "+", 16, 1)
			,terminal(BI_SIGN, "+", 16, 2),terminal(BI_SIGN, "+", 16, 3)
/**/
		));
		
		/** test1 **/
		/*
		ScannerToken<Object> t;
		do{
			t = input.readNextTerminal();
			System.err.println(t+" <-");
		}while(t.getSymbol()!=SpecialTerminals.EndOfInputStream);
		*/
		/** parser observer **/
		parser.register(new ParserObserver() {
			public void syntax_error(ErrorInformation error) {
				if (error.isRecovered())
					System.out.println(error);
				else
					System.out.println("!!! UNRECOVERABE :\n    " + error);
			}
		});
		/** test 2 **/
		Object result = parser.parse(input, true, false);
		System.out.println("\nThe result is:\n" + result);
	}


	
	@Test
	public void randomTokenTest() throws GeneratorException, IOException, LRParserException
	{
		LRParsingTable table = new LR1Generator(this).getParsingTable();

		@SuppressWarnings("unchecked")
		List<List<ScannerToken<? extends Object>>> correctList
		/**/ 
		 	= Arrays.asList(
				Arrays.asList(
					terminal(BI_SIGN, "+"), terminal(NUMBER, "3"),
					terminal(NUMBER, "4"), terminal(EXPR_END)
				)
				,
				Arrays.asList(
					terminal(BI_SIGN, "+"), terminal(NUMBER, "27"),
					(ScannerToken<?>)terminal(NUMBER, "8", 0, 2)
				)
				,
				Arrays.asList(
					terminal(BI_SIGN, "+"), terminal(NUMBER, "R"),
					(ScannerToken<?>)terminal(NUMBER, "L"), terminal(BI_SIGN, "+")
				)
				,
				Arrays.asList(
					(ScannerToken<?>)terminal(BI_SIGN, "+"), terminal(NUMBER, "F")
				)
				,
				Arrays.asList(
					terminal(EXPR_END), terminal(EXPR_END)
				)
			)
			/*/
			= null
			/**/;
		InsertionScanner inserter = new InsertionScanner(
			table,
			new TokenPrinter(
				new RandomTokenCreator(
					this,
					correctList
				)
			)
		);
		/*/
		ScannerToken<? extends Object> t;
		do{
			t = inserter.readNextTerminal();
			System.out.print("inserter gives me: ");
			System.out.println(t);
		}while(t.getSymbol()!=SpecialTerminals.EndOfInputStream);
		/*/
		LRParser parser = new LRParser(table);
		parser.register(new ParserObserver() {
			public void syntax_error(ErrorInformation error) {
				if (error.isRecovered())
					System.out.println(error);
				else
					System.out.println("!!! UNRECOVERABE :\n    " + error);
			}
		});
		Object result = "";
		try{
			result = parser.parse(inserter, true);
		}catch (LRParserException e){
			if (!(
				e instanceof MissingErrorRecoveryException ||
				e instanceof EndOfInputstreamException
			))
				e.printStackTrace();
		}
		System.out.println();
		System.out.println(result.toString()); //TEST/**/
		/**/
	}
	
}

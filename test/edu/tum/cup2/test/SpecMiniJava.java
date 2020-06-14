package edu.tum.cup2.test;

import edu.tum.cup2.generator.LALR1SCCGenerator;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import edu.tum.cup2.generator.LR0Generator;
import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.minijava.Cond;
import edu.tum.cup2.test.minijava.Constants;
import edu.tum.cup2.test.minijava.Decl;
import edu.tum.cup2.test.minijava.Expr;
import edu.tum.cup2.test.minijava.Program;
import edu.tum.cup2.test.minijava.Stmt;


//locate static imports after others to be compatible with javac
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.SpecMiniJava.NonTerminals.*;
import static edu.tum.cup2.test.SpecMiniJava.Terminals.*;
import static edu.tum.cup2.test.minijava.Constants.LE;
import static junit.framework.Assert.fail;

/**
 * Specification of the MiniJava grammar, but here
 * without semantic actions.
 * 
 * Based on the old CUP grammar written by Michael Petter.
 * 
 * @author Andreas Wenger
 */
public class SpecMiniJava
	extends CUP2Specification
{

	
	//terminals
	public enum Terminals implements Terminal
	{
		SEMICOLON, COMMA, LPAR, RPAR, BEGIN, END,
		IF, ELSE, WHILE, READ, WRITE, BUNOP, ASSIGN,
		TYPE, BINOP, UNOP, COMP, BBINOP,
		INTCONST, IDENT, BOOLCONST
	}
	

	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		program, decllist, decl, stmtlist, identlist, stmt, expr, cond
	}
	
	
	//symbols with values
	public class TYPE       extends SymbolValue<Integer>{};
	public class BINOP      extends SymbolValue<Integer>{};
	public class UNOP       extends SymbolValue<Integer>{};
	public class COMP       extends SymbolValue<Integer>{};
	public class BBINOP     extends SymbolValue<Integer>{};
	public class INTCONST   extends SymbolValue<Integer>{};
	public class IDENT      extends SymbolValue<String>{};
	public class BOOLCONST  extends SymbolValue<Boolean>{};
	
	public class program    extends SymbolValue<Program>{};
	public class decllist   extends SymbolValue<List<Decl>>{};
	public class decl       extends SymbolValue<Decl>{};
	public class stmtlist   extends SymbolValue<List<Stmt>>{};
	public class identlist  extends SymbolValue<List<String>>{};
	public class stmt       extends SymbolValue<Stmt>{};
	public class expr       extends SymbolValue<Expr>{};
	public class cond       extends SymbolValue<Cond>{};

	
	public SpecMiniJava()
	{
		precedences(
			left(ELSE, UNOP, BINOP, BUNOP, BBINOP));
		grammar(
			prod(program,
			              rhs(decllist, stmtlist),
			                new Action() { public Program a(List<Decl> d, List<Stmt> s)
			                  { return new Program(d, s); }}),
			prod(decllist,
				            rhs(decllist, decl),
				              new Action() { public List<Decl> a(List<Decl> dl, Decl d)
				                { dl.add(d); return dl; }},
				            rhs(),
				              new Action() { public List<Decl> a()
				                { return new LinkedList<Decl>(); }}),
			prod(stmtlist,
				            rhs(stmtlist, stmt),
				              new Action() { public List<Stmt> a(List<Stmt> sl, Stmt s)
				                { sl.add(s); return sl; }},
				            rhs(),
				              new Action() { public List<Stmt> a()
			                  { return new LinkedList<Stmt>(); }}),  
			prod(decl,
				            rhs(TYPE, IDENT, identlist, SEMICOLON),
				              new Action() { public Decl a(Integer t, String i, List<String> il)
		                    { il.add(i); return new Decl(il); }}), 
			prod(identlist,
				            rhs(identlist, COMMA, IDENT),
				              new Action() { public List<String> a(List<String> il, String i)
                        { il.add(i); return il; }}, 
				            rhs(),
				              new Action() { public List<String> a()
                        { return new LinkedList<String>(); }}), 
			prod(stmt,
				            rhs(SEMICOLON),
				              new Action() { public Stmt a()
                        { return Stmt.empty(); }},
				            rhs(BEGIN, stmtlist, END),
				              new Action() { public Stmt a(List<Stmt> sl)
                        { return Stmt.compound(sl); }},
				            rhs(IDENT, ASSIGN, expr, SEMICOLON),
				              new Action() { public Stmt a(String i, Expr e)
                        { return Stmt.assign(i, e); }},
				            rhs(IDENT, ASSIGN, READ, LPAR, RPAR, SEMICOLON),
				              new Action() { public Stmt a(String i)
                        { return Stmt.read(i); }},
				            rhs(WRITE, LPAR, expr, RPAR, SEMICOLON),
				              new Action() { public Stmt a(Expr e)
                        { return Stmt.write(e); }},
				            rhs(IF, LPAR, cond, RPAR, stmt),
				              new Action() { public Stmt a(Cond c, Stmt s)
                        { return Stmt.ifthen(c, s); }},
				            rhs(IF, LPAR, cond, RPAR, stmt, ELSE, stmt),
				              new Action() { public Stmt a(Cond c, Stmt t, Stmt e)
                        { return Stmt.ifthenelse(c, t, e); }},
				            rhs(WHILE, LPAR, cond, RPAR, stmt),
				              new Action() { public Stmt a(Cond c, Stmt s)
                       { return Stmt.whileloop(c, s); }}),
			prod(cond,
				            rhs(BOOLCONST),
				              new Action() { public Cond a(Boolean c)
                        { return Cond.boolconst(c); }},
				            rhs(LPAR, cond, RPAR),
				              new Action() { public Cond a(Cond c)
                        { return Cond.priority(c); }},
				            rhs(expr, COMP, expr),
				              new Action() { public Cond a(Expr e1, Integer op, Expr e2)
                        { return Cond.binop(e1, op, e2); }},
				            rhs(BUNOP, cond),
				              new Action() { public Cond a(Cond c)
                        { return Cond.unop(c); }},
				            rhs(cond, BBINOP, cond),
				              new Action() { public Cond a(Cond c1, Integer op, Cond c2)
                        { return Cond.binop(c1, op, c2); }}),
			prod(expr,
				            rhs(IDENT),
				              new Action() { public Expr a(String i)
                        { return Expr.ident(i); }},
				            rhs(INTCONST),
				              new Action() { public Expr a(Integer c)
                        { return Expr.intconst(c); }},
				            rhs(LPAR, expr, RPAR),
				              new Action() { public Expr a(Expr e)
                        { return Expr.priority(e); }},
				            rhs(BINOP, expr), //yes, BINOP (hack...)
				              new Action() { public Expr a(Integer op, Expr e) //op: not in original grammar, but needed because it has a value
                        { return Expr.unop(e); }},
				            rhs(expr, BINOP, expr),
				              new Action() { public Expr a(Expr e1, Integer op, Expr e2)
                        { return Expr.binop(e1, op, e2); }})
		);		       
	}

	/**
	 * LR(0) without precedences must fail.
	 */
	@Test public void testLR0NoPrecedences()
	{
		try
		{
		  precedences();
			new LR0Generator(this).getParsingTable();
			fail();
		}
		catch (GeneratorException ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * LR(1) without precedences must fail.
	 */
	@Test public void testLR1NoPrecedences()
	{
		try
		{
		  precedences();
			new LR1Generator(this).getParsingTable();
			fail();
		}
		catch (GeneratorException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * LR(1) with left-precedence for IF (x) IF (y) ; ELSE ;
	 * should result in:
	 * 	IF (x)
	 * 		IF (y) ;
	 * 	ELSE 
	 * 		;
	 * instead of
	 * 	IF (x)
	 * 		IF (y) ;
	 * 		ELSE 
	 * 			;
	 * @author Stefan Dangl
	 */
	@Test public void testLR1LeftElsePrecedence()
	{
		try
		{
			precedences(left(BBINOP,BINOP),left(RPAR,ELSE));
			LRParsingTable table = new LR1Generator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("SpecMiniJava_SDanglPrecedences.html")); //TEST
			Program result = (Program)new LRParser(table).parse(new TestScanner(
					terminal(IF),
					terminal(LPAR),
					terminal(BOOLCONST,true),
					terminal(RPAR),
					terminal(IF),
					terminal(LPAR),
					terminal(BOOLCONST,false),
					terminal(RPAR),
					terminal(SEMICOLON),
					terminal(ELSE),
					terminal(SEMICOLON)
			));
			System.out.println(result);
			if (((Stmt.IfThenElse)(result.ls.get(0))).els==null){
				System.out.println("First else part is null\n");
				fail("Precedences behave badly!!!");
			}else{
				System.out.println("First else part is not null\n");
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			fail();
		}
	}
	
	
	/**
	 * LR(1) must work.
	 */
	@Test public void testLR1()
	{
		try
		{
			LRParsingTable table = new LR1Generator(this).getParsingTable();
                        //LRParsingTable table = new LALR1SCCGenerator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("SpecMiniJava.html")); //TEST
			
			//send through test program:
			//
			//  int n, w , sum , i;
			//  n = read ();
			//  w = 0;
			//  sum = 0;
			//  i = 1;
			//  while ( !(sum < n)) {
			//      w = w + 1;
			//      sum = sum + i;
			//      i = i + 2;
			//  }
			//  write (-w );
			Object result = new LRParser(table).parse(new TestScanner(
				terminal(TYPE),          // int
				terminal(IDENT, "n"),    // n
				terminal(COMMA),         // ,
				terminal(IDENT, "w"),    // w
				terminal(COMMA),         // ,
				terminal(IDENT, "sum"),  // sum
				terminal(COMMA),         // ,
				terminal(IDENT, "i"),    // i
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "n"),    // n
				terminal(ASSIGN),        // =
				terminal(READ),          // read
				terminal(LPAR),          // (
				terminal(RPAR),          // )
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "w"),    // w
				terminal(ASSIGN),        // =
				terminal(INTCONST, 0),   // 0
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "sum"),  // sum
				terminal(ASSIGN),        // =
				terminal(INTCONST, 0),   // 0
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "i"),    // i
				terminal(ASSIGN),        // =
				terminal(INTCONST, 1),   // 1
				terminal(SEMICOLON),     // ;
				terminal(WHILE),         // while
				terminal(LPAR),          // (
				terminal(BUNOP),         // !
				terminal(LPAR),          // (
				terminal(IDENT, "sum"),  // sum
				terminal(COMP, LE),      // <
				terminal(IDENT, "n"),    // n
				terminal(RPAR),          // )
				terminal(RPAR),          // )
				terminal(BEGIN),         // {
				terminal(IDENT, "w"),    // w
				terminal(ASSIGN),        // =
				terminal(IDENT, "w"),    // w
				terminal(BINOP, Constants.PLUS), // +
				terminal(INTCONST, 1),   // 1
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "sum"),  // sum
				terminal(ASSIGN),        // =
				terminal(IDENT, "sum"),  // sum
				terminal(BINOP, Constants.PLUS), // +
				terminal(IDENT, "i"),    // i
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "i"),    // i
				terminal(ASSIGN),        // =
				terminal(IDENT, "i"),    // i
				terminal(BINOP, Constants.PLUS), // +
				terminal(INTCONST, 2),   // 2
				terminal(SEMICOLON),     // ;
				terminal(END),           // }
				terminal(WRITE),         // write
				terminal(LPAR),          // (
				terminal(BINOP, Constants.MINUS), // -
				terminal(IDENT, "w"),    // w
				terminal(RPAR),          // )
				terminal(SEMICOLON)      // ;
			));
			System.out.println(result.toString()); //TEST
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	/**
	 * LR(1)-to-LALR must work.
	 */
	@Test public void testLR1toLALR()
	{
		try
		{
			LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("SpecMiniJava-lalr.html")); //TEST
			
			//send through test program:
			//
			//  int n, w , sum , i;
			//  n = read ();
			//  w = 0;
			//  sum = 0;
			//  i = 1;
			//  while ( !(sum < n)) {
			//      w = w + 1;
			//      sum = sum + i;
			//      i = i + 2;
			//  }
			//  write (-w );
			Object result = new LRParser(table).parse(new TestScanner(
				terminal(TYPE),          // int
				terminal(IDENT, "n"),    // n
				terminal(COMMA),         // ,
				terminal(IDENT, "w"),    // w
				terminal(COMMA),         // ,
				terminal(IDENT, "sum"),  // sum
				terminal(COMMA),         // ,
				terminal(IDENT, "i"),    // i
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "n"),    // n
				terminal(ASSIGN),        // =
				terminal(READ),          // read
				terminal(LPAR),          // (
				terminal(RPAR),          // )
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "w"),    // w
				terminal(ASSIGN),        // =
				terminal(INTCONST, 0),   // 0
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "sum"),  // sum
				terminal(ASSIGN),        // =
				terminal(INTCONST, 0),   // 0
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "i"),    // i
				terminal(ASSIGN),        // =
				terminal(INTCONST, 1),   // 1
				terminal(SEMICOLON),     // ;
				terminal(WHILE),         // while
				terminal(LPAR),          // (
				terminal(BUNOP),         // !
				terminal(LPAR),          // (
				terminal(IDENT, "sum"),  // sum
				terminal(COMP, LE),      // <
				terminal(IDENT, "n"),    // n
				terminal(RPAR),          // )
				terminal(RPAR),          // )
				terminal(BEGIN),         // {
				terminal(IDENT, "w"),    // w
				terminal(ASSIGN),        // =
				terminal(IDENT, "w"),    // w
				terminal(BINOP, Constants.PLUS), // +
				terminal(INTCONST, 1),   // 1
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "sum"),  // sum
				terminal(ASSIGN),        // =
				terminal(IDENT, "sum"),  // sum
				terminal(BINOP, Constants.PLUS), // +
				terminal(IDENT, "i"),    // i
				terminal(SEMICOLON),     // ;
				terminal(IDENT, "i"),    // i
				terminal(ASSIGN),        // =
				terminal(IDENT, "i"),    // i
				terminal(BINOP, Constants.PLUS), // +
				terminal(INTCONST, 2),   // 2
				terminal(SEMICOLON),     // ;
				terminal(END),           // }
				terminal(WRITE),         // write
				terminal(LPAR),          // (
				terminal(BINOP, Constants.MINUS), // -
				terminal(IDENT, "w"),    // w
				terminal(RPAR),          // )
				terminal(SEMICOLON)      // ;
			));
			System.out.println(result.toString()); //TEST
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	private String written = "";
	
	
	@Test public void testTime()
	{
		Time.measureTime(new SpecMiniJava());
	}
	

}
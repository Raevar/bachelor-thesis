package edu.tum.cup2.test.errorRecovery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.test.minijava.Cond;
import edu.tum.cup2.test.minijava.Constants;
import edu.tum.cup2.test.minijava.Decl;
import edu.tum.cup2.test.minijava.Expr;
import edu.tum.cup2.test.minijava.MinijavaVisitor;
import edu.tum.cup2.test.minijava.Program;
import edu.tum.cup2.test.minijava.Stmt;
import edu.tum.cup2.test.minijava.Expr.Identifier;
import edu.tum.cup2.test.minijava.Stmt.Assign;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.grammar.SpecialTerminals.Error;
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.errorRecovery.SpecMiniJava.NonTerminals.*;
import static edu.tum.cup2.test.errorRecovery.SpecMiniJava.Terminals.*;
import static edu.tum.cup2.test.minijava.Constants.LE;
import static junit.framework.Assert.fail;

/**
 * Specification of the MiniJava grammar, but here
 * without "useful" semantic actions.
 * 
 * Based on the old CUP grammar written by Michael Petter.
 * 
 * SD : Added some error-recovery
 * 
 * @author Andreas Wenger
 * @author Stefan Dangl
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
		                    { il.add(i); return new Decl(il); }},
				            rhs(TYPE, Error, identlist, SEMICOLON),
				              new Action() { public Decl a(Integer t, ErrorInformation i, List<String> il)
		                    { System.out.println(i); return new Decl(il); }}), 
			prod(identlist,
				            rhs(identlist, COMMA, IDENT),
				              new Action() { public List<String> a(List<String> il, String i)
                        { il.add(i); return il; }}, 
				            rhs(identlist, Error),
				              new Action() { public List<String> a(List<String> il, ErrorInformation i)
                        { System.out.println(i); return il; }}, 
				            rhs(),
				              new Action() { public List<String> a()
                        { return new LinkedList<String>(); }}), 
			prod(stmt,
				            rhs(Error),
				              new Action() { public Stmt a(ErrorInformation x)
                        { System.out.println(x); return Stmt.empty(); }},
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
				            /*rhs(Error),
				              new Action() { public Cond a(ErrorInformation x)
                        { System.out.println(x); return Cond.boolconst(true); }},*/
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
	 * Test-method for error-recovery.
	 */
	@Test public void testErrors()
	{
		try
		{
			LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
			
			//send through test program:
			//
			//  int n p , w ,, sum , i ;
			//  a, b, c ;
			//  n = read );
			//  w = 0;
			//  sum = a;
			//  i = 1;
			//  while ( !(sum < + ) )) {
			//      w = w ++;
			//      sum = sum + i;
			//      i = i 2;
			//  }
			//  write (-w );
			Object result = new LRParser(table).parse(new TestScanner(
				terminal(TYPE),          // int
				terminal(IDENT, "n"),    // n
				terminal(IDENT, "p"),    // p
				terminal(COMMA),         // ,
				terminal(IDENT, "w"),    // w
				terminal(COMMA),         // ,
				terminal(COMMA),         // ,
				terminal(IDENT, "sum"),  // sum
				terminal(COMMA),         // ,
				terminal(IDENT, "i"),    // i
				terminal(SEMICOLON),     // ;
				
				terminal(IDENT, "a"),    // a
				terminal(COMMA),         // ,
				terminal(IDENT, "b"),    // b
				terminal(COMMA),         // ,
				terminal(IDENT, "c"),    // c
				terminal(SEMICOLON),     // ;
				
				terminal(IDENT, "n"),    // n
				terminal(ASSIGN),        // =
				terminal(READ),          // read
				terminal(RPAR),          // )
				terminal(SEMICOLON),     // ;
				
				terminal(IDENT, "w"),    // w
				terminal(ASSIGN),        // =
				terminal(INTCONST, 0),   // 0
				terminal(SEMICOLON),     // ;
				
				terminal(IDENT, "sum"),  // sum
				terminal(ASSIGN),        // =
        terminal(IDENT, "a"),    // w
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
        terminal(RPAR),          // )
				terminal(RPAR),          // )
				terminal(RPAR),          // )
				terminal(BEGIN),         // {
				terminal(IDENT, "w"),    // w
				terminal(ASSIGN),        // =
				terminal(IDENT, "w"),    // w
				terminal(BINOP, Constants.PLUS), // +
				terminal(BINOP, Constants.PLUS), // +
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
				terminal(INTCONST, 2),   // 2
				terminal(SEMICOLON),     // ;
				
				terminal(END),           // }
				terminal(WRITE),         // write
				terminal(LPAR),          // (
				terminal(BINOP, Constants.MINUS), // -
				terminal(IDENT, "w"),    // w
				terminal(RPAR),          // )
				terminal(SEMICOLON)      // ;
			), true);
			System.out.println(result.toString()); //TEST
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			fail();
		}
	}

  
  /**
   * Test-method for error-recovery.
   */
  @Test public void testErrors2()
  {
    try
    {
      LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
      
      //send through test program:
      //
      //  int n p , w ,, sum , i ;
      //  a, b, c ;
      //  n = read );
      //  w = 0;
      //  sum = a;
      //  i = 1;
      //  while ( !(sum < ) )) {
      //      w = w ++;
      //      sum = sum + i;
      //      i = i 2;
      //  }
      //  write (-w );
      Object result = new LRParser(table).parse(new TestScanner(
        terminal(TYPE),          // int
        terminal(IDENT, "n"),    // n
        terminal(IDENT, "p"),    // p
        terminal(COMMA),         // ,
        terminal(IDENT, "w"),    // w
        terminal(COMMA),         // ,
        terminal(COMMA),         // ,
        terminal(IDENT, "sum"),  // sum
        terminal(COMMA),         // ,
        terminal(IDENT, "i"),    // i
        terminal(SEMICOLON),     // ;
        
        terminal(IDENT, "a"),    // a
        terminal(COMMA),         // ,
        terminal(IDENT, "b"),    // b
        terminal(COMMA),         // ,
        terminal(IDENT, "c"),    // c
        terminal(SEMICOLON),     // ;
        
        terminal(IDENT, "n"),    // n
        terminal(ASSIGN),        // =
        terminal(READ),          // read
        terminal(RPAR),          // )
        terminal(SEMICOLON),     // ;
        
        terminal(IDENT, "w"),    // w
        terminal(ASSIGN),        // =
        terminal(INTCONST, 0),   // 0
        terminal(SEMICOLON),     // ;
        
        terminal(IDENT, "sum"),  // sum
        terminal(ASSIGN),        // =
        terminal(IDENT, "a"),    // a
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
        terminal(LPAR),          // (
        terminal(RPAR),          // )
        terminal(RPAR),          // )
        terminal(BEGIN),         // {
        terminal(IDENT, "w"),    // w
        terminal(ASSIGN),        // =
        terminal(IDENT, "w"),    // w
        terminal(BINOP, Constants.PLUS), // +
        terminal(BINOP, Constants.PLUS), // +
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
        terminal(INTCONST, 2),   // 2
        terminal(SEMICOLON),     // ;
        
        terminal(END),           // }
        terminal(WRITE),         // write
        terminal(LPAR),          // (
        terminal(BINOP, Constants.MINUS), // -
        terminal(IDENT, "w"),    // w
        terminal(RPAR),          // )
        terminal(SEMICOLON)      // ;
      ), true);
      System.out.println(result.toString()); //TEST
      
      ((Program)result).accept(
          new MinijavaVisitor()
          {
            private List<String> valid_idents = null;

            @Override
            public boolean preVisit(Program p) {
              valid_idents = new ArrayList<String>();
              return true;
            }
            @Override
            public boolean preVisit(Decl d) {
              valid_idents.addAll(d.varlist);
              return true;
            }
            @Override
            public void visit(Identifier d) {
              //System.out.println("visit("+d+")");
              if (!valid_idents.contains(d.i))
                System.err.println("invalid identifier : "+d.i);
            }
          }
      );
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      fail();
    }
  }

}
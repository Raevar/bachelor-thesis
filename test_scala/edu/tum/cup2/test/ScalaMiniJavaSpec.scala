package edu.tum.cup2.test {

import edu.tum.cup2.semantics.{SymbolValue}
import edu.tum.cup2.spec.util.{RHSSymbols}
import edu.tum.cup2.test.minijava.{Cond, Constants, Decl, Expr, Program, Stmt}

import edu.tum.cup2.generator.{LR1Generator}
import edu.tum.cup2.io.{LRParsingTableDump}
import edu.tum.cup2.parser.{LRParser}
import edu.tum.cup2.scanner.{TestScanner, ScannerTokenTestTool}
import java.io.{File}
import org.junit.Test
import junit.framework.Assert.fail
import java.util.LinkedList

import collection.JavaConversions._

import edu.tum.cup2.spec.scala.{ScalaCUPSpecification, SymbolEnum}
import edu.tum.cup2.spec.{CUP2Specification}


/**
 * Scala-Version of { @link SpecMiniJava }, but only a few tests are implemented.
 *  
 * @author matthias
 *
 */
class ScalaMiniJavaSpec extends CUP2Specification with ScalaCUPSpecification {
	
  object Terminals extends SymbolEnum {
    val SEMICOLON, COMMA, LPAR, RPAR, BEGIN, END,
		IF, ELSE, WHILE, READ, WRITE, BUNOP, ASSIGN,
		TYPE, BINOP, UNOP, COMP, BBINOP,
		INTCONST, IDENT, BOOLCONST = TerminalEnum
  }
  
  object NonTerminals extends SymbolEnum {
    val program, decllist, decl, stmtlist, identlist,
        stmt, expr, cond = NonTerminalEnum
  }
  
  // make the enum-Values available
  import NonTerminals._, Terminals._
  
  
  // tell parent class what (non)terminals exist
  val terminals = Terminals
  val nonTerminals = NonTerminals
  
  //symbols with values
  class BINOP      extends SymbolValue[Int]{}
  class UNOP       extends SymbolValue[Int]{}
  class COMP       extends SymbolValue[Int]{}
  class BBINOP     extends SymbolValue[Int]{}
  class INTCONST   extends SymbolValue[Int]{}
  class IDENT      extends SymbolValue[String]{}
  class BOOLCONST  extends SymbolValue[Boolean]{}
  
  class program    extends SymbolValue[Program]{}
  class decllist   extends SymbolValue[List[Decl]]{}
  class decl       extends SymbolValue[Decl]{}
  class stmtlist   extends SymbolValue[List[Stmt]]{}
  class identlist  extends SymbolValue[List[String]]{}
  class stmt       extends SymbolValue[Stmt]{}
  class expr       extends SymbolValue[Expr]{}
  class cond       extends SymbolValue[Cond]{}
  
  
  //precedences
  precedences(left(ELSE, UNOP, BINOP, BUNOP, BBINOP))
  
  // specify grammar
  grammar(
    program   ->  (
    		        decllist ~ stmtlist ^^ { (d : List[Decl], s : List[Stmt]) => new Program(d, s) }
    		      ),
	decllist  ->  (
			        decllist ~ decl ^^ { (dl : List[Decl], d : Decl) => dl :+ d } |
			        { () => List[Decl]() } // originally LinkedList !
	              ),
    stmtlist  ->  (
    		        stmtlist ~ stmt ^^ { (sl : List[Stmt], s : Stmt) => sl :+ s } |
				    { () => List[Stmt]() }
    		      ),  
    decl      ->  (
    		        // !! have to change TYPE from type Int to String since null-value for
    		        // Int is NOT ALLOWED in Scala
    		        TYPE ~ IDENT ~ identlist ~ SEMICOLON ^^ { (i : String, il : List[String]) => new Decl(il :+ i) } 
    		      ), 
	identlist ->  (
			        identlist ~ COMMA ~ IDENT ^^ { (il : List[String], i : String) =>  il :+ i } | 
				    { () => List[String]() }
			      ), 
	stmt      ->  (
			        SEMICOLON ^^ { () => Stmt.empty() } |
			        BEGIN ~ stmtlist ~ END ^^ { (sl : List[Stmt]) => Stmt.compound(sl) } |
			        IDENT ~ ASSIGN ~ expr ~ SEMICOLON ^^ { (i : String, e : Expr) => Stmt.assign(i, e) } |
			        IDENT ~ ASSIGN ~ READ ~ LPAR ~ RPAR ~ SEMICOLON ^^ { (i : String) => Stmt.read(i) } |
			        WRITE ~ LPAR ~ expr ~ RPAR ~ SEMICOLON ^^ { (e : Expr) => Stmt.write(e) } |
			        IF ~ LPAR ~ cond ~ RPAR ~ stmt ^^ { (c : Cond, s : Stmt) => Stmt.ifthen(c, s) } | 
			        IF ~ LPAR ~ cond ~ RPAR ~ stmt ~ ELSE ~ stmt ^^ { (c : Cond, t : Stmt, e : Stmt) => Stmt.ifthenelse(c, t, e) } |
			        WHILE ~ LPAR ~ cond ~ RPAR ~ stmt ^^ { (c : Cond, s : Stmt) => Stmt.whileloop(c, s) }
			      ),
    cond      ->  (
    		        BOOLCONST ^^ { (c : Boolean) => Cond.boolconst(c) } | 
    		        LPAR ~ cond ~ RPAR ^^ { (c : Cond) => Cond.priority(c) } |
    		        expr ~ COMP ~ expr ^^ { (e1 : Expr, op : Int, e2 : Expr) => Cond.binop(e1, op, e2) } |
    		        BUNOP ~ cond ^^ { (c : Cond) => Cond.unop(c) } |
    		        cond ~ BBINOP ~ cond ^^ { (c1 : Cond, op : Int, c2 : Cond) => Cond.binop(c1, op, c2) }
    		      ),
    expr      ->  (
    		        IDENT ^^ { (i : String) => Expr.ident(i) } |
    		        INTCONST ^^ { (c : Int) => Expr.intconst(c) } |
    		        LPAR ~ expr ~ RPAR ^^ { (e : Expr) => Expr.priority(e) } |
    		        BINOP ~ expr ^^ { (op : Int, e : Expr) => Expr.unop(e) } |
    		        expr ~ BINOP ~ expr ^^ { (e1 : Expr, op : Int, e2 : Expr) => Expr.binop(e1, op, e2) }
    		      )
  )

  /**
   * LR(1) must work.
   */
  @Test
  def testLR1() = {
	import ScannerTokenTestTool._
	import Constants._
	  
	try {
	  var table = new LR1Generator(this).getParsingTable()
	  LRParsingTableDump.dumpToHTML(table, new File("ScalaSpecMiniJava.html")); //TEST
			
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
			var result = new LRParser(table).parse(new TestScanner(
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
		} catch {
			case ex => {
			ex.printStackTrace();
			fail();
		}
	}
  }

}
}
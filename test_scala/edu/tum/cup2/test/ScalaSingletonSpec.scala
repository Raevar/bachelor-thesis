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
 * Scala Spec as Singleton.
 *  
 * @author matthias
 *
 */
object ScalaSingletonSpec extends CUP2Specification with ScalaCUPSpecification {

  object Terminals extends SymbolEnum { val NUMBER = TerminalEnum }
  object NonTerminals extends SymbolEnum {
    val expr = NonTerminalEnum
  }
  // make the enum-Values available
  import NonTerminals._, Terminals._
  
  // tell parent class what (non)terminals exist
  val terminals = Terminals
  val nonTerminals = NonTerminals
  

  class NUMBER extends SymbolValue[Int]
  class expr extends SymbolValue[Int]

  // ... snip ...

  grammar(
    expr -> (NUMBER ^^ { (n: Int) => n })
  )
}

/**
 * Same Spec as the singleton but defined as a class this time.
 * 
 * @author matthias
 */
class ScalaSingletonSpecClass extends CUP2Specification with ScalaCUPSpecification {

  object Terminals extends SymbolEnum { val NUMBER = TerminalEnum }
  object NonTerminals extends SymbolEnum {
    val expr = NonTerminalEnum
  }
  // make the enum-Values available
  import NonTerminals._, Terminals._
  
  // tell parent class what (non)terminals exist
  val terminals = Terminals
  val nonTerminals = NonTerminals
  

  class NUMBER extends SymbolValue[Int]
  class expr extends SymbolValue[Int]

  // ... snip ...

  grammar(
    expr -> (NUMBER ^^ { (n: Int) => n })
  )
}


/**
 * Execute a simple test on both, the singleton and the class, specifications.
 * 
 * @author matthias
 */
class ScalaSingletonSpecTest {
  /**
   * Test this case.
   */
  @Test
  def testSingleton() = {
	  
	import ScannerTokenTestTool._
	import Constants._
	//import ScalaSingletonSpec.Terminals._

	// Spec defined as class
	var spec = ScalaSingletonSpec
	
	try {
	  var table = new LR1Generator(spec).getParsingTable()
	  LRParsingTableDump.dumpToHTML(table, new File("ScalaSingletonSpec.html")); //TEST
			
			var result = new LRParser(table).parse(new TestScanner(
				terminal(spec.Terminals.NUMBER, 42)
			));
			System.out.println(result.toString()); //TEST
		} catch {
			case ex => {
			ex.printStackTrace();
			fail();
		}
	}
  }
  
  
  @Test
  def testSingletonAsClass() = {
	  
	import ScannerTokenTestTool._
	import Constants._
	//import ScalaSingletonSpec.Terminals._

	// Spec defined as singleton
	var spec = new ScalaSingletonSpecClass()
	
	try {
	  var table = new LR1Generator(spec).getParsingTable()
	  LRParsingTableDump.dumpToHTML(table, new File("ScalaSingletonSpec.html")); //TEST
			
			var result = new LRParser(table).parse(new TestScanner(
				terminal(spec.Terminals.NUMBER, 42)
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
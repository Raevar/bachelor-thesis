package edu.tum.cup2.spec {

import edu.tum.cup2.generator.{LR1toLALRGenerator, Verbosity}
import edu.tum.cup2.parser.{LRParser}
import edu.tum.cup2.scanner.{TestScanner}
import edu.tum.cup2.grammar.{Terminal}
import edu.tum.cup2.scanner.ScannerTokenTestTool.terminal

object ScalaSampleSpecTest {
	
  def main(args : Array[String]) : Unit = {

	  println("TEST STARTED")
	  
	  // Test 1
	  try {
	 	  var spec = new ScalaSampleSpec()
	 	  
	 	   var table = new  LR1toLALRGenerator(spec, Verbosity.Sparse).getParsingTable();
			//check the generated parser with a small computation
			var result = new LRParser(table).parse(new TestScanner(
				terminal(spec.Terminals.NUMBER, 30),
				terminal(spec.Terminals.PLUS),
				terminal(spec.Terminals.NUMBER, 4),
				terminal(spec.Terminals.TIMES),
				terminal(spec.Terminals.LPAREN),
				terminal(spec.Terminals.NUMBER, 2),
				terminal(spec.Terminals.PLUS),
				terminal(spec.Terminals.NUMBER, 15),
				terminal(spec.Terminals.RPAREN),
				terminal(spec.Terminals.SEMI)
			))
			//result must be 30 + 4 * (2 + 15) = 98
			println("RESULT "+result)
	  } catch {
	 	  case x => println("EXCEPTION " + x); x.printStackTrace()
	  }
	  
  }
  
}

}
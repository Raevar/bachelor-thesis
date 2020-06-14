// YOU MOST LIKELY WANT TO CHANGE THE PLACE OF THE CLASS TO FIT YOUR PURPOSES
// BEGIN SECTION package_spec
package Example ;
// END SECTION
// BEGIN SECTION import_list
 import java_cup . runtime . * ;
// END SECTION
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.semantics.Action;
import static edu.tum.cup2.grammar.SpecialTerminals.*;
// SPECIFY THE PACKAGE WHERE YOUR CLASS SHOULD RESIDE HERE AS >>MYPACKAGE<<
import static MYPACKAGE.MyCalculator.Terminals.*;
import static MYPACKAGE.MyCalculator.NonTerminals.*;


public class MyCalculator extends CUPSpecification {


  // BEGIN SECTION parser_code_part

	public static void main(String args[]) throws Exception {
		SymbolFactory sf = new DefaultSymbolFactory();
		if (args.length==0) new Parser(new Scanner(System.in,sf),sf).parse();
		else new Parser(new Scanner(new java.io.FileInputStream(args[0]),sf),sf).parse();
	}

  // END SECTION
  // BEGIN SECTION action_code_part
  // END SECTION
  // BEGIN SECTION init_code
  // END SECTION
  // BEGIN SECTION scan_code
  // END SECTION
  public enum Terminals implements Terminal {
    RPAREN,PLUS,TIMES,SEMI,LPAREN,NUMBER;
  }
  public class NUMBER extends SymbolValue<Integer>{};
  public enum NonTerminals implements NonTerminal {
    expr_part,expr,expr_list;
  }
  public class expr_part extends SymbolValue<Integer>{};
  public class expr extends SymbolValue<Integer>{};
  public class expr_list extends SymbolValue<Integer>{};



  public MyCalculator() {


    // VERY VERY IMPORTANT: AT THE MOMENT NO TRANSLATION IS DONE FOR PRECEDENCE-ANNOTATIONS ON PRODUCTIONS.
    // SEARCH IN THE ORIGINAL CUP-FILE FOR %prec TO FIND OUT IF YOU HAVE TO WORRY ABOUT THIS ISSUE.
    precedences(
      left(TIMES),
      left(PLUS)
    );


    grammar(



      //::----------prod
      prod(

        expr_list,

        //::---rhs
        rhs(expr_list, expr_part
        ),

        //::---rhs
        rhs(expr_part
        )

      ),


      //::----------prod
      prod(

        expr_part,

        //::---rhs
        rhs(expr, $a, SEMI
        ),
          new Action() {
            public Integer a(Integer e) {
              Integer RESULT;
              //INSERTED CODE STRING:
                 System.out.println(" = "+e+";"); 
              //END INSERT
              return RESULT;
            }
          }

      ),


      //::----------prod
      prod(

        expr,

        //::---rhs
        rhs(NUMBER
        ),
          new Action() {
            public Integer a(Integer n) {
              Integer RESULT;
              //INSERTED CODE STRING:
                 RESULT=n; 
              //END INSERT
              return RESULT;
            }
          },

        //::---rhs
        rhs(expr, PLUS, expr
        ),
          new Action() {
            public Integer a(Integer l, Object missingLabel1, Integer r) {
              Integer RESULT;
              //INSERTED CODE STRING:
                 RESULT=new Integer(l.intValue() + r.intValue()); 
              //END INSERT
              return RESULT;
            }
          },

        //::---rhs
        rhs(expr, TIMES, expr
        ),
          new Action() {
            public Integer a(Integer l, Object missingLabel1, Integer r) {
              Integer RESULT;
              //INSERTED CODE STRING:
                 RESULT=new Integer(l.intValue() * r.intValue()); 
              //END INSERT
              return RESULT;
            }
          },

        //::---rhs
        rhs(LPAREN, expr, RPAREN
        ),
          new Action() {
            public Integer a(Object missingLabel0, Integer e, Object missingLabel2) {
              Integer RESULT;
              //INSERTED CODE STRING:
                 RESULT=e; 
              //END INSERT
              return RESULT;
            }
          }

      )

    );// END OF GRAMMAR

  }// END OF CONSTRUCTOR

}// END OF CLASS

// YOU MOST LIKELY WANT TO CHANGE THE PLACE OF THE CLASS TO FIT YOUR PURPOSES
// BEGIN SECTION package_spec
package edu.tum.cup2.spec;
// END SECTION
// BEGIN SECTION import_list
// END SECTION
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUPSpecification;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.semantics.Action;
import static edu.tum.cup2.grammar.SpecialTerminals.*;
// SPECIFY THE PACKAGE WHERE YOUR CLASS SHOULD RESIDE HERE AS >>MYPACKAGE<<
import static edu.tum.cup2.spec.MyCalculator.Terminals.*;
import static edu.tum.cup2.spec.MyCalculator.NonTerminals.*;


public class MyCalculator extends CUPSpecification {

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
              Integer RESULT = 0;
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

package edu.tum.cup2.generator;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.spec.CUP2Specification;
import static edu.tum.cup2.generator.TranslatedPHPSpec.NonTerminals.*;
import static edu.tum.cup2.generator.TranslatedPHPSpec.Terminals.*;

public class TranslatedPHPSpec extends CUP2Specification {

	protected final static Integer PUBLIC = new Integer(0);
	protected final static Integer PRIVATE = new Integer(0);
	protected final static Integer PROTECTED = new Integer(0);
	protected final static Integer ABSTRACT = new Integer(0);
	protected final static Integer FINAL = new Integer(0);
	protected final static Integer STATIC = new Integer(0);
	


    /**
     * Report a non fatal error (or warning).  This method takes a message
     * string and an additional object (to be used by specializations implemented in subclasses).
     * The super class prints the message to System.err.
     * @param message an error message.
     * @param info    an extra object reserved for use by specialized subclasses.
     */
    public void report_error(String message, Object info) {
		// System.err.print(message);
  		// if (info instanceof Symbol)
		//	if (((Symbol)info).left != -1)
		//		System.err.println(" at character " + ((Symbol)info).left + " of input");
		//	else System.err.println("");
	    // else 
	   	//	System.err.println("");
    }
	

public enum Terminals implements Terminal {
T_CURLY_OPEN_WITH_DOLAR,T_ABSTRACT,T_CLASS_C,T_CLASS,T_VARIABLE,T_NS_SEPARATOR,T_ELSE,T_UNSET_CAST,T_START_HEREDOC,T_DOLLAR_OPEN_CURLY_BRACES,T_NUM_STRING,T_TRY,T_STRING,T_STRING_VARNAME,T_MUL_EQUAL,T_MOD_EQUAL,T_INSTANCEOF,T_LOGICAL_XOR,T_CONSTANT_ENCAPSED_STRING,T_EXTENDS,T_OPEN_PARENTHESE,T_CONST,T_TIMES,T_KOVA,T_FOR,T_INTERFACE,T_RETURN,T_CURLY_OPEN,T_OR,T_PRINT,T_RGREATER,T_BOOLEAN_OR,T_IS_SMALLER_OR_EQUAL,T_VAR,T_MINUS_EQUAL,T_AS,T_AT,T_UNSET,T_NAMESPACE,T_OR_EQUAL,T_DOLLAR,T_XOR_EQUAL,T_DNUMBER,T_PRIVATE,T_FOREACH,T_INT_CAST,T_BREAK,T_CASE,T_IF,T_DIV_EQUAL,T_TILDA,T_ENDFOR,T_ECHO,T_DEFAULT,T_GOTO,T_ARRAY_CAST,T_OBJECT_CAST,T_NEKUDA,T_SL_EQUAL,T_LNUMBER,T_QUESTION_MARK,T_SEMICOLON,T_ENCAPSED_AND_WHITESPACE,T_HALT_COMPILER,T_VAR_COMMENT,T_ENDIF,T_EMPTY,T_DOUBLE_CAST,T_COMMA,T_ELSEIF,T_REQUIRE,T_IS_IDENTICAL,T_MINUS,T_IMPLEMENTS,T_FUNC_C,T_INCLUDE_ONCE,T_REQUIRE_ONCE,T_CLONE,T_ARRAY,T_PLUS_EQUAL,T_FINAL,T_AND_EQUAL,T_DOUBLE_ARROW,T_WHILE,T_NS_C,T_USE,T_ENDSWITCH,T_FILE,T_INC,T_DIR,T_CLOSE_PARENTHESE,T_DIV,T_IS_NOT_IDENTICAL,T_LIST,T_SL,T_END_HEREDOC,T_GLOBAL,T_DEFINE,T_STATIC,T_SR,T_LOGICAL_AND,T_OBJECT_OPERATOR,T_PLUS,T_DECLARE,T_THROW,T_INCLUDE,T_PROTECTED,T_BOOLEAN_AND,T_ENDWHILE,T_SWITCH,T_PUBLIC,T_ENDFOREACH,T_BACKQUATE,T_ENDDECLARE,T_EQUAL,T_METHOD_C,T_SR_EQUAL,T_ISSET,T_CURLY_CLOSE,T_EVAL,T_LGREATER,T_NEW,T_DEC,T_CONCAT_EQUAL,T_PAAMAYIM_NEKUDOTAYIM,T_LINE,T_LOGICAL_OR,T_IS_EQUAL,T_IS_NOT_EQUAL,T_CATCH,T_QUATE,T_FUNCTION,T_CLOSE_RECT,T_STRING_CAST,T_INLINE_HTML,T_BOOL_CAST,T_DO,T_OPEN_RECT,T_NOT,T_IS_GREATER_OR_EQUAL,T_REFERENCE,T_CONTINUE,T_EXIT,T_NEKUDOTAIM,T_PRECENT
}

public enum NonTerminals implements NonTerminal {
method_or_not,else_single,variable_class_name,optional_class_type,is_reference,variable_modifiers,class_variable_declaration,object_dim_list,new_elseif_list,class_statement_list,foreach_optional_arg,unset_variables,foreach_statement,unticked_statement,for_statement,case_separator,possible_comma,tracked_variable,use_declarations,fully_qualified_class_name,declare_list,inner_statement,top_statement_list,class_entry_type,assignment_list_element,internal_functions_in_yacc,declare_statement,ctor_arguments,variable_name,variable_property,function_call,class_statement,non_empty_member_modifiers,parameter_list,lexical_vars,dynamic_class_name_variable_property,non_empty_array_pair_list,class_name,dynamic_class_name_reference,dim_offset,interface_entry,encaps_var,member_modifier,assignment_list,foreach_variable,global_var,scalar,static_member,exit_expr,implements_list,extends_from,new_else_single,compound_variable,variable,elseif_list,variable_without_objects,static_scalar,class_declaration_statement,function_call_parameter_list,function_declaration_statement,class_constant,namespace_name,constant_declaration,echo_expr_list,simple_indirect_reference,non_empty_additional_catches,use_declaration,base_variable,additional_catches,unticked_function_declaration_statement,method_body,common_scalar,method_modifiers,statement,array_pair_list,non_empty_static_array_pair_list,additional_catch,reference_variable,dynamic_class_name_variable_properties,for_expr,encaps_var_offset,expr_without_variable,variable_properties,w_variable,case_list,while_statement,expr,static_class_constant,parameter,class_name_reference,unticked_class_declaration_statement,base_variable_with_function_calls,isset_variables,non_empty_parameter_list,static_array_pair_list,unset_variable,switch_case_list,thestart,encaps_list,rw_variable,lexical_var_list,interface_list,non_empty_for_expr,static_var_list,use_filename,non_empty_function_call_parameter_list,r_variable,inner_statement_list,backticks_expr,object_property,global_var_list,interface_extends_list,top_statement,string_st
}


public TranslatedPHPSpec() {
	
precedences(
		right( T_STATIC, T_ABSTRACT, T_FINAL, T_PRIVATE, T_PROTECTED, T_PUBLIC),
		left( T_ENDIF),
		left( T_ELSE),
		left( T_ELSEIF),
		left( T_NEW, T_CLONE),
		right( T_OPEN_RECT),
		right( T_TILDA,T_INC,T_DEC,T_INT_CAST,T_DOUBLE_CAST,T_STRING_CAST,T_ARRAY_CAST,T_OBJECT_CAST,T_BOOL_CAST,T_UNSET_CAST,T_AT),
		left( T_INSTANCEOF),
		right( T_NOT),
		left( T_TIMES,T_DIV,T_PRECENT),
		left( T_PLUS,T_MINUS,T_NEKUDA),
		left( T_SL,T_SR),
		left( T_RGREATER,T_IS_SMALLER_OR_EQUAL,T_LGREATER,T_IS_GREATER_OR_EQUAL),
		left( T_IS_EQUAL,T_IS_NOT_EQUAL,T_IS_IDENTICAL,T_IS_NOT_IDENTICAL),
		left( T_REFERENCE),
		left( T_KOVA),
		left( T_OR),
		left( T_BOOLEAN_AND),
		left( T_BOOLEAN_OR),
		left( T_QUESTION_MARK,T_SEMICOLON),
		left( T_EQUAL, T_PLUS_EQUAL,T_MINUS_EQUAL,T_MUL_EQUAL,T_DIV_EQUAL,T_CONCAT_EQUAL,T_MOD_EQUAL,T_AND_EQUAL,T_OR_EQUAL,T_XOR_EQUAL,T_SL_EQUAL,T_SR_EQUAL),
		right( T_PRINT),
		left( T_LOGICAL_AND),
		left( T_LOGICAL_XOR),
		left( T_LOGICAL_OR),
		left( T_COMMA),
		left( T_INCLUDE, T_INCLUDE_ONCE, T_EVAL, T_REQUIRE, T_REQUIRE_ONCE)
);

grammar(

prod(
  thestart,
  rhs(top_statement_list 
/*    new Action() {
      public Program a(List sList) {
//INSERTED CODE STRING:

	PhpAstLexer phpAstLexer = (PhpAstLexer) parser.getScanner();
	List commentList = phpAstLexer.getCommentList();
	RESULT = new Program(sListleft, sListright, parser.ast, sList, commentList);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  namespace_name,
  rhs(T_STRING 
/*    new Action() {
      public List a(String n) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new Identifier(nleft, nright, parser.ast, n));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(namespace_name, T_NS_SEPARATOR, T_STRING 
/*    new Action() {
      public List a(List list, Object missingLabel1, String n) {
//INSERTED CODE STRING:

	list.add(new Identifier(nleft, nright, parser.ast, n));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  top_statement_list,
  rhs(top_statement_list, top_statement 
/*    new Action() {
      public List a(List sList, Statement statement) {
//INSERTED CODE STRING:

	if(statement != null) {
		if (!(statement instanceof NamespaceDeclaration) && sList.size() > 0) {
			Statement lastStatement = (Statement) ((LinkedList) sList).getLast();
			if (lastStatement instanceof NamespaceDeclaration) {
				((NamespaceDeclaration)lastStatement).addStatement(statement);
			} else {
				sList.add(statement);
			}
		} else {
			sList.add(statement);
		}
	}
	RESULT = sList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:
 
	RESULT = new LinkedList(); 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  top_statement,
  rhs(statement 
/*    new Action() {
      public Statement a(Statement statement) {
//INSERTED CODE STRING:
 
	RESULT = statement; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(function_declaration_statement 
/*    new Action() {
      public Statement a(FunctionDeclaration statement) {
//INSERTED CODE STRING:
 
	RESULT = statement; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(class_declaration_statement 
/*    new Action() {
      public Statement a(Statement statement) {
//INSERTED CODE STRING:
 
	RESULT = statement; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_HALT_COMPILER, T_OPEN_PARENTHESE, T_CLOSE_PARENTHESE, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object missingLabel0, Object missingLabel1, Object missingLabel2, Object statement) {
//INSERTED CODE STRING:
 
	// Note: we never generate T_HALT_COMPILER
	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NAMESPACE, namespace_name, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new NamespaceDeclaration(sleft, eright, parser.ast, 
		new NamespaceName(listleft, listright, parser.ast, list, false, false), null, true);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NAMESPACE, namespace_name, T_CURLY_OPEN, top_statement_list, T_CURLY_CLOSE 
/*    new Action() {
      public Statement a(Object s, List list, Object token, List sList, Object e) {
//INSERTED CODE STRING:

	RESULT = new NamespaceDeclaration(sleft, eright, parser.ast,
		new NamespaceName(listleft, listright, parser.ast, list, false, false),  
		new Block(tokenleft, eright, parser.ast, sList), false);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NAMESPACE, T_CURLY_OPEN, top_statement_list, T_CURLY_CLOSE 
/*    new Action() {
      public Statement a(Object s, Object token, List sList, Object e) {
//INSERTED CODE STRING:

	RESULT = new NamespaceDeclaration(sleft, eright, parser.ast, null, 
		new Block(tokenleft, eright, parser.ast, sList), false);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_USE, use_declarations, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new UseStatement(sleft, eright, parser.ast, list);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(constant_declaration, T_SEMICOLON 
/*    new Action() {
      public Statement a(List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new ConstantDeclaration(listleft, eright, parser.ast, list);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  use_declarations,
  rhs(use_declarations, T_COMMA, use_declaration 
/*    new Action() {
      public List a(List list, Object missingLabel1, UseStatementPart useDecl) {
//INSERTED CODE STRING:

	list.add(useDecl);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(use_declaration 
/*    new Action() {
      public List a(UseStatementPart useDecl) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(useDecl);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  use_declaration,
  rhs(namespace_name 
/*    new Action() {
      public UseStatementPart a(List list) {
//INSERTED CODE STRING:

	RESULT = new UseStatementPart(listleft, listright, parser.ast, 
		new NamespaceName(listleft, listright, parser.ast, list, false, false), null);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(namespace_name, T_AS, T_STRING 
/*    new Action() {
      public UseStatementPart a(List list, Object missingLabel1, String aliasName) {
//INSERTED CODE STRING:

	RESULT = new UseStatementPart(listleft, aliasNameright, parser.ast, 
		new NamespaceName(listleft, listright, parser.ast, list, false, false),
		new Identifier(aliasNameleft, aliasNameright, parser.ast, aliasName));

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NS_SEPARATOR, namespace_name 
/*    new Action() {
      public UseStatementPart a(Object missingLabel0, List list) {
//INSERTED CODE STRING:

	RESULT = new UseStatementPart(listleft, listright, parser.ast, 
		new NamespaceName(listleft, listright, parser.ast, list, true, false), null);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NS_SEPARATOR, namespace_name, T_AS, T_STRING 
/*    new Action() {
      public UseStatementPart a(Object missingLabel0, List list, Object missingLabel2, String aliasName) {
//INSERTED CODE STRING:

	RESULT = new UseStatementPart(listleft, aliasNameright, parser.ast,
		new NamespaceName(listleft, listright, parser.ast, list, true, false),
		new Identifier(aliasNameleft, aliasNameright, parser.ast, aliasName));

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  inner_statement_list,
  rhs(inner_statement_list, inner_statement 
/*    new Action() {
      public List a(List sList, Statement statement) {
//INSERTED CODE STRING:

	// Ignore null statements
	if(statement != null) {
		sList.add(statement);
	}		
	RESULT = sList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:
 
	RESULT = new LinkedList(); 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  inner_statement,
  rhs(statement 
/*    new Action() {
      public Statement a(Statement statement) {
//INSERTED CODE STRING:
 
	RESULT = statement; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(function_declaration_statement 
/*    new Action() {
      public Statement a(FunctionDeclaration statement) {
//INSERTED CODE STRING:
 
	RESULT = statement; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(class_declaration_statement 
/*    new Action() {
      public Statement a(Statement statement) {
//INSERTED CODE STRING:
 
	RESULT = statement; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_HALT_COMPILER, T_OPEN_PARENTHESE, T_CLOSE_PARENTHESE, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object halt, Object missingLabel1, Object close, Object missingLabel3) {
//INSERTED CODE STRING:
 
	// Note: we never generate K_HALT_COMPILER
	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  statement,
  rhs(unticked_statement 
/*    new Action() {
      public Statement a(Statement statement) {
//INSERTED CODE STRING:
 
	RESULT = statement; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_STRING, T_NEKUDOTAIM 
/*    new Action() {
      public Statement a(String label, Object e) {
//INSERTED CODE STRING:

	RESULT = new GotoLabel(labelleft, eright, parser.ast, new Identifier(labelleft, labelright, parser.ast, label));

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  unticked_statement,
  rhs(T_CURLY_OPEN, inner_statement_list, T_CURLY_CLOSE 
/*    new Action() {
      public Statement a(Object token, List sList, Object e) {
//INSERTED CODE STRING:

	RESULT = new Block(tokenleft, eright, parser.ast, sList);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_IF, T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE, statement, elseif_list, else_single 
/*    new Action() {
      public Statement a(Object token, Object missingLabel1, Expression cond, Object missingLabel3, Statement iftrue, List [ ] elseif, Statement iffalse) {
//INSERTED CODE STRING:

	Expression innerCondition = null;	
	Statement trueStatement = null;
	Statement falseStatement = iffalse;

	for (int i=0 ; i < elseif[0].size() ; i++) {
		innerCondition = (Expression)elseif[0].get(i);	
	 	trueStatement = (Statement)elseif[1].get(i);
	 	int s = ((Integer)elseif[2].get(i)).intValue();
		falseStatement = new IfStatement(s, iffalseright, parser.ast, innerCondition, trueStatement, falseStatement);
	}
	RESULT = new IfStatement(tokenleft, iffalseright, parser.ast, cond, iftrue, falseStatement);		

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_IF, T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE, T_NEKUDOTAIM, inner_statement_list, new_elseif_list, new_else_single, T_ENDIF, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, Object missingLabel1, Expression cond, Object missingLabel3, Object colon, List ifTrueStatementList, List [ ] elseif, Statement iffalse, Object missingLabel8, Object e) {
//INSERTED CODE STRING:

	Expression innerCondition = null;	
	Statement trueStatement = null;
	Statement falseStatement = iffalse;
		
	for (int i=0 ; i < elseif[0].size() ; i++) {
		innerCondition = (Expression)elseif[0].get(i);	
	 	trueStatement = (Statement)elseif[1].get(i);
	 	int s = ((Integer)elseif[2].get(i)).intValue();
		falseStatement = new IfStatement(s, iffalseright, parser.ast, innerCondition, trueStatement, falseStatement);
	}
	Block block = new Block(colonleft, ifTrueStatementListright, parser.ast, ifTrueStatementList, false);
	RESULT = new IfStatement(tokenleft, eright, parser.ast, cond, block, falseStatement);		

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_WHILE, T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE, while_statement 
/*    new Action() {
      public Statement a(Object token, Object missingLabel1, Expression expr, Object missingLabel3, Statement statement) {
//INSERTED CODE STRING:

	RESULT = new WhileStatement(tokenleft, statementright, parser.ast, expr, statement);			

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DO, statement, T_WHILE, T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, Statement statement, Object missingLabel2, Object missingLabel3, Expression expr, Object missingLabel5, Object e) {
//INSERTED CODE STRING:

	RESULT = new DoStatement(tokenleft, eright, parser.ast, expr, statement);			

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_FOR, T_OPEN_PARENTHESE, for_expr, T_SEMICOLON, for_expr, T_SEMICOLON, for_expr, T_CLOSE_PARENTHESE, for_statement 
/*    new Action() {
      public Statement a(Object token, Object missingLabel1, List initializations, Object missingLabel3, List conds, Object missingLabel5, List increasements, Object missingLabel7, Statement statement) {
//INSERTED CODE STRING:
		
	RESULT = new ForStatement(tokenleft, statementright, parser.ast, initializations, conds, increasements, statement);			

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_SWITCH, T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE, switch_case_list 
/*    new Action() {
      public Statement a(Object token, Object missingLabel1, Expression expr, Object missingLabel3, Block caseBlock) {
//INSERTED CODE STRING:

	RESULT = new SwitchStatement(tokenleft, caseBlockright, parser.ast, expr, caseBlock);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_BREAK, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new BreakStatement(tokenleft, eright, parser.ast); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_BREAK, expr, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, Expression expr, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new BreakStatement(tokenleft, eright, parser.ast, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CONTINUE, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new ContinueStatement(tokenleft, eright, parser.ast); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CONTINUE, expr, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, Expression expr, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new ContinueStatement(tokenleft, eright, parser.ast, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_RETURN, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new ReturnStatement(tokenleft, eright, parser.ast); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_RETURN, expr_without_variable, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, Expression expr, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new ReturnStatement(tokenleft, eright, parser.ast, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_RETURN, variable, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, VariableBase expr, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new ReturnStatement(tokenleft, eright, parser.ast, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_GLOBAL, global_var_list, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new GlobalStatement(sleft, eright, parser.ast, list);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_STATIC, static_var_list, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new StaticStatement(sleft, eright, parser.ast, list);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ECHO, echo_expr_list, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, List exprList, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new EchoStatement(sleft, eright, parser.ast, exprList); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_INLINE_HTML 
/*    new Action() {
      public Statement a(Object html) {
//INSERTED CODE STRING:

	RESULT = new InLineHtml(htmlleft, htmlright, parser.ast);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_SEMICOLON 
/*    new Action() {
      public Statement a(Expression expr, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new ExpressionStatement(exprleft, eright, parser.ast, expr);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_USE, use_filename, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, Expression expr, Object e) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(expr);
	RESULT = new ExpressionStatement(sleft, eright, parser.ast, 
		new FunctionInvocation(sleft, exprright, parser.ast,
			new FunctionName(sleft, sright, parser.ast, 
				new Identifier(sleft, sright, parser.ast, "use")), list));	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_UNSET, T_OPEN_PARENTHESE, unset_variables, T_CLOSE_PARENTHESE, T_SEMICOLON 
/*    new Action() {
      public Statement a(String s, Object missingLabel1, List list, Object closePar, Object e) {
//INSERTED CODE STRING:

	RESULT = new ExpressionStatement(sleft, eright, parser.ast, 
		new FunctionInvocation(sleft, closeParright, parser.ast,
			new FunctionName(sleft, sright, parser.ast,
				new Identifier(sleft, sright, parser.ast, "unset")), list));	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_FOREACH, T_OPEN_PARENTHESE, variable, T_AS, foreach_variable, foreach_optional_arg, T_CLOSE_PARENTHESE, foreach_statement 
/*    new Action() {
      public Statement a(Object token, Object missingLabel1, VariableBase expr, Object missingLabel3, Expression var, Expression arg, Object missingLabel6, Statement statement) {
//INSERTED CODE STRING:

	ForEachStatement s = null;
	if (arg == null) {
		s = new ForEachStatement(tokenleft, statementright, parser.ast, expr, var, statement);
	} else {
		s = new ForEachStatement(tokenleft, statementright, parser.ast, expr, var, arg, statement);
	}
	RESULT = s;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_FOREACH, T_OPEN_PARENTHESE, expr_without_variable, T_AS, variable, foreach_optional_arg, T_CLOSE_PARENTHESE, foreach_statement 
/*    new Action() {
      public Statement a(Object token, Object missingLabel1, Expression expr, Object missingLabel3, VariableBase var, Expression arg, Object missingLabel6, Statement statement) {
//INSERTED CODE STRING:

	ForEachStatement s = null;
	if (arg == null) {
		s = new ForEachStatement(tokenleft, statementright, parser.ast, expr, var, statement);
	} else {
		s = new ForEachStatement(tokenleft, statementright, parser.ast, expr, var, arg, statement);
	}
	RESULT = s;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DECLARE, T_OPEN_PARENTHESE, declare_list, T_CLOSE_PARENTHESE, declare_statement 
/*    new Action() {
      public Statement a(Object s, Object missingLabel1, List [ ] lists, Object missingLabel3, Statement statement) {
//INSERTED CODE STRING:

	RESULT = new DeclareStatement(sleft, statementright, parser.ast, lists[0], lists[1], statement);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token) {
//INSERTED CODE STRING:
 
	RESULT = new EmptyStatement(tokenleft, tokenright, parser.ast); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_TRY, T_CURLY_OPEN, inner_statement_list, T_CURLY_CLOSE, T_CATCH, T_OPEN_PARENTHESE, fully_qualified_class_name, tracked_variable, T_CLOSE_PARENTHESE, T_CURLY_OPEN, inner_statement_list, T_CURLY_CLOSE, additional_catches 
/*    new Action() {
      public Statement a(Object s, Object tryBlockStart, List tryList, Object tryBlockEnd, Object catch_word, Object missingLabel5, NamespaceName className, Variable var, Object missingLabel8, Object catchBlockStart, List catchList, Object catchBlockEnd, List catchesList) {
//INSERTED CODE STRING:

	Block tryBlock = new Block(tryBlockStartleft, tryBlockEndright, parser.ast, tryList);
	Block catchBlock = new Block(catchBlockStartleft, catchBlockEndright, parser.ast, catchList);
	CatchClause catchClause = new CatchClause(catch_wordleft, catchBlockEndright, parser.ast, className, var, catchBlock);
	((LinkedList) catchesList).addFirst(catchClause);
	RESULT = new TryStatement(sleft, catchesListright, parser.ast, tryBlock, catchesList);	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_THROW, expr, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object token, Expression expr, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new ThrowStatement(tokenleft, eright, parser.ast, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_GOTO, T_STRING, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, String label, Object e) {
//INSERTED CODE STRING:

	RESULT = new GotoStatement(sleft, eright, parser.ast, new Identifier(labelleft, labelright, parser.ast, label));

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(SpecialTerminals.Error 
/*    new Action() {
      public Statement a(Object theError) {
//INSERTED CODE STRING:

	ASTError SpecialTerminals.Error = new ASTError(theErrorleft, theErrorright, parser.ast);
	RESULT = SpecialTerminals.Error;	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_VAR_COMMENT, unticked_statement 
/*    new Action() {
      public Statement a(String varComment, Statement statement) {
//INSERTED CODE STRING:

	RESULT = statement;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  additional_catches,
  rhs(non_empty_additional_catches 
/*    new Action() {
      public List a(List list) {
//INSERTED CODE STRING:

	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	List list = new LinkedList();
	RESULT = list;	

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  non_empty_additional_catches,
  rhs(additional_catch 
/*    new Action() {
      public List a(CatchClause catch_statement) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(catch_statement);
	RESULT = list;	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_additional_catches, additional_catch 
/*    new Action() {
      public List a(List list, CatchClause catch_statement) {
//INSERTED CODE STRING:

	list.add(catch_statement);
	RESULT = list;	

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  additional_catch,
  rhs(T_CATCH, T_OPEN_PARENTHESE, fully_qualified_class_name, T_VARIABLE, T_CLOSE_PARENTHESE, T_CURLY_OPEN, inner_statement_list, T_CURLY_CLOSE 
/*    new Action() {
      public CatchClause a(Object catch_word, Object missingLabel1, NamespaceName className, String var, Object missingLabel4, Object catchBlockStart, List catchList, Object catchBlockEnd) {
//INSERTED CODE STRING:

	RESULT = new CatchClause(catch_wordleft, catchBlockEndright, parser.ast, className,
		new Variable(varleft, varright, parser.ast, var),
		new Block(catchBlockStartleft, catchBlockEndright, parser.ast, catchList));

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  unset_variables,
  rhs(unset_variable 
/*    new Action() {
      public List a(VariableBase var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(var);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(unset_variables, T_COMMA, unset_variable 
/*    new Action() {
      public List a(List list, Object missingLabel1, VariableBase var) {
//INSERTED CODE STRING:

	list.add(var);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  unset_variable,
  rhs(variable 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:

	RESULT = var;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  use_filename,
  rhs(T_CONSTANT_ENCAPSED_STRING 
/*    new Action() {
      public Expression a(String scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, scalar, Scalar.TYPE_STRING);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_OPEN_PARENTHESE, T_CONSTANT_ENCAPSED_STRING, T_CLOSE_PARENTHESE 
/*    new Action() {
      public Expression a(Object s, String scalar, Object e) {
//INSERTED CODE STRING:

	RESULT = new Scalar(sleft, eright, parser.ast, scalar, Scalar.TYPE_STRING);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  function_declaration_statement,
  rhs(unticked_function_declaration_statement 
/*    new Action() {
      public FunctionDeclaration a(FunctionDeclaration functionDeclaration) {
//INSERTED CODE STRING:

	RESULT = functionDeclaration;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  class_declaration_statement,
  rhs(unticked_class_declaration_statement 
/*    new Action() {
      public Statement a(Statement classDeclaration) {
//INSERTED CODE STRING:

	RESULT = classDeclaration;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  is_reference,
  rhs(
/*    new Action() {
      public Boolean a() {
//INSERTED CODE STRING:

	RESULT = Boolean.FALSE;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_REFERENCE 
/*    new Action() {
      public Boolean a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = Boolean.TRUE;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  unticked_function_declaration_statement,
  rhs(T_FUNCTION, is_reference, string_st, T_OPEN_PARENTHESE, parameter_list, T_CLOSE_PARENTHESE, T_CURLY_OPEN, inner_statement_list, T_CURLY_CLOSE 
/*    new Action() {
      public FunctionDeclaration a(Object s, Boolean isReference, String fn, Object missingLabel3, List paramList, Object missingLabel5, Object blockStart, List sList, Object blockEnd) {
//INSERTED CODE STRING:

	RESULT = new FunctionDeclaration(sleft, blockEndright, parser.ast,
		new Identifier(fnleft, fnright, parser.ast, fn), paramList,
		new Block(blockStartleft, blockEndright, parser.ast, sList), isReference.booleanValue());

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  unticked_class_declaration_statement,
  rhs(class_entry_type, T_STRING, extends_from, implements_list, T_CURLY_OPEN, class_statement_list, T_CURLY_CLOSE 
/*    new Action() {
      public Statement a(Integer modifier, String className, Expression superClass, List interfaces, Object blockStart, List sList, Object blockEnd) {
//INSERTED CODE STRING:

	RESULT = new ClassDeclaration(modifierleft ,blockEndright, parser.ast, modifier.intValue(),
		new Identifier(classNameleft, classNameright, parser.ast, className), superClass, interfaces,
		new Block(blockStartleft, blockEndright, parser.ast, sList));

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(interface_entry, T_STRING, interface_extends_list, T_CURLY_OPEN, class_statement_list, T_CURLY_CLOSE 
/*    new Action() {
      public Statement a(Integer s, String className, List interfaces, Object blockStart, List sList, Object blockEnd) {
//INSERTED CODE STRING:

	RESULT = new InterfaceDeclaration(sleft ,blockEndright, parser.ast, 
		new Identifier(classNameleft, classNameright, parser.ast, className), interfaces, 
		new Block(blockStartleft, blockEndright, parser.ast, sList));

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  class_entry_type,
  rhs(T_CLASS 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = new Integer(ClassDeclaration.MODIFIER_NONE);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ABSTRACT, T_CLASS 
/*    new Action() {
      public Integer a(Object missingLabel0, Object missingLabel1) {
//INSERTED CODE STRING:

	RESULT = new Integer(ClassDeclaration.MODIFIER_ABSTRACT);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_FINAL, T_CLASS 
/*    new Action() {
      public Integer a(Object missingLabel0, Object missingLabel1) {
//INSERTED CODE STRING:

	RESULT = new Integer(ClassDeclaration.MODIFIER_FINAL);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  extends_from,
  rhs(
/*    new Action() {
      public Expression a() {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_EXTENDS, fully_qualified_class_name 
/*    new Action() {
      public Expression a(Object missingLabel0, NamespaceName className) {
//INSERTED CODE STRING:

	RESULT = className;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  interface_entry,
  rhs(T_INTERFACE
  )
)
,
prod(
  interface_extends_list,
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	List list = new LinkedList();
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_EXTENDS, interface_list 
/*    new Action() {
      public List a(Object missingLabel0, List list) {
//INSERTED CODE STRING:

	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  implements_list,
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	List list = new LinkedList();
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_IMPLEMENTS, interface_list 
/*    new Action() {
      public List a(Object missingLabel0, List list) {
//INSERTED CODE STRING:

	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  interface_list,
  rhs(fully_qualified_class_name 
/*    new Action() {
      public List a(NamespaceName className) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(className);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(interface_list, T_COMMA, fully_qualified_class_name 
/*    new Action() {
      public List a(List list, Object missingLabel1, NamespaceName className) {
//INSERTED CODE STRING:

	list.add(className);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  foreach_optional_arg,
  rhs(
/*    new Action() {
      public Expression a() {
//INSERTED CODE STRING:
 
	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DOUBLE_ARROW, foreach_variable 
/*    new Action() {
      public Expression a(Object missingLabel0, Expression var) {
//INSERTED CODE STRING:
 
	RESULT = var;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  foreach_variable,
  rhs(variable 
/*    new Action() {
      public Expression a(VariableBase var) {
//INSERTED CODE STRING:
 
	RESULT = var;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_REFERENCE, variable 
/*    new Action() {
      public Expression a(Object s, VariableBase var) {
//INSERTED CODE STRING:
 
	RESULT = new Reference (sleft, varright, parser.ast, var);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  for_statement,
  rhs(statement 
/*    new Action() {
      public Statement a(Statement statement) {
//INSERTED CODE STRING:

	RESULT = statement;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NEKUDOTAIM, inner_statement_list, T_ENDFOR, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, List sList, Object missingLabel2, Object missingLabel3) {
//INSERTED CODE STRING:

	RESULT = new Block(sleft, sListright, parser.ast, sList, false);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  foreach_statement,
  rhs(statement 
/*    new Action() {
      public Statement a(Statement statement) {
//INSERTED CODE STRING:

	RESULT = statement;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NEKUDOTAIM, inner_statement_list, T_ENDFOREACH, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, List sList, Object missingLabel2, Object missingLabel3) {
//INSERTED CODE STRING:

	RESULT = new Block(sleft, sListright, parser.ast, sList, false);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  declare_statement,
  rhs(statement 
/*    new Action() {
      public Statement a(Statement statement) {
//INSERTED CODE STRING:

	RESULT = statement;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NEKUDOTAIM, inner_statement_list, T_ENDDECLARE, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object s, List sList, Object missingLabel2, Object missingLabel3) {
//INSERTED CODE STRING:

	RESULT = new Block(sleft, sListright, parser.ast, sList, false);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  declare_list,
  rhs(string_st, T_EQUAL, static_scalar 
/*    new Action() {
      public List [ ] a(String key, Object missingLabel1, Expression value) {
//INSERTED CODE STRING:

	List listKeys = new LinkedList();
	List listValues = new LinkedList();
	
	listKeys.add(new Identifier(keyleft, keyright, parser.ast, key));
	listValues.add(value);

	RESULT = new List[] { listKeys, listValues };

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(declare_list, T_COMMA, string_st, T_EQUAL, static_scalar 
/*    new Action() {
      public List [ ] a(List [ ] lists, Object missingLabel1, String key, Object missingLabel3, Expression value) {
//INSERTED CODE STRING:

	lists[0].add(new Identifier(keyleft, keyright, parser.ast, key));
	lists[1].add(value);
	RESULT = lists;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  switch_case_list,
  rhs(T_CURLY_OPEN, case_list, T_CURLY_CLOSE 
/*    new Action() {
      public Block a(Object s, List caseList, Object e) {
//INSERTED CODE STRING:

	RESULT = new Block(sleft, eright, parser.ast, caseList);	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CURLY_OPEN, T_SEMICOLON, case_list, T_CURLY_CLOSE 
/*    new Action() {
      public Block a(Object s, Object missingLabel1, List caseList, Object e) {
//INSERTED CODE STRING:

	RESULT = new Block(sleft, eright, parser.ast, caseList);	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NEKUDOTAIM, case_list, T_ENDSWITCH, T_SEMICOLON 
/*    new Action() {
      public Block a(Object s, List caseList, Object missingLabel2, Object e) {
//INSERTED CODE STRING:

	RESULT = new Block(sleft, eright, parser.ast, caseList, false);	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NEKUDOTAIM, T_SEMICOLON, case_list, T_ENDSWITCH, T_SEMICOLON 
/*    new Action() {
      public Block a(Object s, Object missingLabel1, List caseList, Object missingLabel3, Object e) {
//INSERTED CODE STRING:

	RESULT = new Block(sleft, eright, parser.ast, caseList, false);	

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  case_list,
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList(); // of SwitchCase

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(case_list, T_CASE, expr, case_separator, inner_statement_list 
/*    new Action() {
      public List a(List caseList, Object token, Expression expr, Object missingLabel3, List statements) {
//INSERTED CODE STRING:

	SwitchCase switchCase = new SwitchCase(tokenleft, statementsright, parser.ast, expr, statements, false);
	if (caseList == null) {
		caseList = new LinkedList(); // of switchCase
	}
	caseList.add(switchCase);
	RESULT = caseList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(case_list, T_DEFAULT, case_separator, inner_statement_list 
/*    new Action() {
      public List a(List caseList, Object token, Object missingLabel2, List statements) {
//INSERTED CODE STRING:

	SwitchCase switchCase = new SwitchCase(tokenleft, statementsright, parser.ast, null, statements, true);
	if (caseList == null) {
		caseList = new LinkedList(); // of SwitchCase
	}
	caseList.add(switchCase);
	RESULT = caseList;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  case_separator,
  rhs(T_NEKUDOTAIM
  ),
  rhs(T_SEMICOLON
  )
)
,
prod(
  while_statement,
  rhs(statement 
/*    new Action() {
      public Statement a(Statement statement) {
//INSERTED CODE STRING:

	RESULT = statement;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NEKUDOTAIM, inner_statement_list, T_ENDWHILE, T_SEMICOLON 
/*    new Action() {
      public Statement a(Object colon, List sList, Object missingLabel2, Object missingLabel3) {
//INSERTED CODE STRING:

	RESULT = new Block(colonleft, sListright, parser.ast, sList, false);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  elseif_list,
  rhs(
/*    new Action() {
      public List [ ] a() {
//INSERTED CODE STRING:

	List listConditions = new LinkedList();
	List listStatements = new LinkedList();
	List listTokens = new LinkedList();
	RESULT = new List[] { listConditions, listStatements, listTokens };

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(elseif_list, T_ELSEIF, T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE, statement 
/*    new Action() {
      public List [ ] a(List [ ] elseifList, Object token, Object missingLabel2, Expression cond, Object missingLabel4, Statement iftrue) {
//INSERTED CODE STRING:
	
	((LinkedList)elseifList[0]).addFirst(cond);
	((LinkedList)elseifList[1]).addFirst(iftrue);
	((LinkedList)elseifList[2]).addFirst(new Integer(tokenleft));
	RESULT = elseifList;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  new_elseif_list,
  rhs(
/*    new Action() {
      public List [ ] a() {
//INSERTED CODE STRING:

	List listConditions = new LinkedList();
	List listStatements = new LinkedList();
	List listTokens = new LinkedList();
	RESULT = new List[] { listConditions, listStatements, listTokens };

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(new_elseif_list, T_ELSEIF, T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE, T_NEKUDOTAIM, inner_statement_list 
/*    new Action() {
      public List [ ] a(List [ ] elseifList, Object token, Object missingLabel2, Expression cond, Object missingLabel4, Object colon, List sList) {
//INSERTED CODE STRING:
	
	Block block = new Block(colonleft, sListright, parser.ast, sList, false);
	((LinkedList)elseifList[0]).addFirst(cond);
	((LinkedList)elseifList[1]).addFirst(block);
	((LinkedList)elseifList[2]).addFirst(new Integer(tokenleft));
	RESULT = elseifList;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  else_single,
  rhs(
/*    new Action() {
      public Statement a() {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ELSE, statement 
/*    new Action() {
      public Statement a(Object missingLabel0, Statement statement) {
//INSERTED CODE STRING:

	RESULT = statement;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  new_else_single,
  rhs(
/*    new Action() {
      public Statement a() {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ELSE, T_NEKUDOTAIM, inner_statement_list 
/*    new Action() {
      public Statement a(Object missingLabel0, Object colon, List list) {
//INSERTED CODE STRING:

	RESULT = new Block(colonleft, listright, parser.ast, list, false);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  parameter_list,
  rhs(non_empty_parameter_list 
/*    new Action() {
      public List a(List list) {
//INSERTED CODE STRING:

	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  non_empty_parameter_list,
  rhs(parameter 
/*    new Action() {
      public List a(FormalParameter parameter) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(parameter);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_parameter_list, T_COMMA, parameter 
/*    new Action() {
      public List a(List list, Object missingLabel1, FormalParameter parameter) {
//INSERTED CODE STRING:

	list.add(parameter);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  parameter,
  rhs(optional_class_type, T_VARIABLE 
/*    new Action() {
      public FormalParameter a(Expression classType, String var) {
//INSERTED CODE STRING:

	int s = classType == null ? varleft : classTypeleft;
	Variable v = new Variable(varleft, varright, parser.ast, var);
	RESULT = new FormalParameter(s, varright, parser.ast, classType, v);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(optional_class_type, T_REFERENCE, T_VARIABLE 
/*    new Action() {
      public FormalParameter a(Expression classType, Object ref, String var) {
//INSERTED CODE STRING:

	int s = classType == null ? refleft : classTypeleft;
	Variable v = new Variable(varleft, varright, parser.ast, var);
	Reference ref_var = new Reference (refleft, varright, parser.ast, v);
	RESULT = new FormalParameter(s, varright, parser.ast, classType, ref_var);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(optional_class_type, T_VARIABLE, T_EQUAL, static_scalar 
/*    new Action() {
      public FormalParameter a(Expression classType, String var, Object missingLabel2, Expression scalar) {
//INSERTED CODE STRING:

	int s = classType == null ? varleft : classTypeleft;
	Variable v = new Variable(varleft, varright, parser.ast, var);
	RESULT = new FormalParameter(s, scalarright, parser.ast, classType, v, scalar);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(optional_class_type, T_REFERENCE, T_VARIABLE, T_EQUAL, static_scalar 
/*    new Action() {
      public FormalParameter a(Expression classType, Object ref, String var, Object missingLabel3, Expression scalar) {
//INSERTED CODE STRING:

	int s = classType == null ? refleft : classTypeleft;
	Variable v = new Variable(varleft, varright, parser.ast, var);
	Reference ref_var = new Reference (refleft, varright, parser.ast, v);
	RESULT = new FormalParameter(s, scalarright, parser.ast, classType, ref_var, scalar);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  optional_class_type,
  rhs(
/*    new Action() {
      public Expression a() {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(fully_qualified_class_name 
/*    new Action() {
      public Expression a(NamespaceName className) {
//INSERTED CODE STRING:

	RESULT = className;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ARRAY 
/*    new Action() {
      public Expression a(Object array) {
//INSERTED CODE STRING:

	RESULT = new Identifier(arrayleft, arrayright, parser.ast, "array");

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  function_call_parameter_list,
  rhs(non_empty_function_call_parameter_list 
/*    new Action() {
      public List a(List paramsList) {
//INSERTED CODE STRING:

	RESULT = paramsList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  non_empty_function_call_parameter_list,
  rhs(expr_without_variable 
/*    new Action() {
      public List a(Expression var) {
//INSERTED CODE STRING:

	List paramsList = new LinkedList();
	paramsList.add(var);	
	RESULT = paramsList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable 
/*    new Action() {
      public List a(VariableBase var) {
//INSERTED CODE STRING:

	List paramsList = new LinkedList();
	paramsList.add(var);	
	RESULT = paramsList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_REFERENCE, w_variable 
/*    new Action() {
      public List a(Object s, VariableBase var) {
//INSERTED CODE STRING:

	List paramsList = new LinkedList();
	Expression var_ref = new Reference(sleft, varright, parser.ast, var);
	paramsList.add(var_ref);	
	RESULT = paramsList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_function_call_parameter_list, T_COMMA, expr_without_variable 
/*    new Action() {
      public List a(List paramsList, Object missingLabel1, Expression var) {
//INSERTED CODE STRING:

	paramsList.add(var);	
	RESULT = paramsList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_function_call_parameter_list, T_COMMA, variable 
/*    new Action() {
      public List a(List paramsList, Object missingLabel1, VariableBase var) {
//INSERTED CODE STRING:

	paramsList.add(var);
	RESULT = paramsList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_function_call_parameter_list, T_COMMA, T_REFERENCE, w_variable 
/*    new Action() {
      public List a(List paramsList, Object missingLabel1, Object s, VariableBase var) {
//INSERTED CODE STRING:

	Expression var_ref = new Reference(sleft, varright, parser.ast, var);
	paramsList.add(var_ref);	
	RESULT = paramsList;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  global_var_list,
  rhs(global_var_list, T_COMMA, global_var 
/*    new Action() {
      public List a(List list, Object missingLabel1, VariableBase var) {
//INSERTED CODE STRING:

	list.add(var);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(global_var 
/*    new Action() {
      public List a(VariableBase var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(var);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  global_var,
  rhs(T_VARIABLE 
/*    new Action() {
      public VariableBase a(String var) {
//INSERTED CODE STRING:

	RESULT = new Variable(varleft, varright, parser.ast, var);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DOLLAR, r_variable 
/*    new Action() {
      public VariableBase a(Object s, VariableBase var) {
//INSERTED CODE STRING:

	RESULT = new ReflectionVariable(sleft, varright, parser.ast, var);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DOLLAR, T_CURLY_OPEN, expr, T_CURLY_CLOSE 
/*    new Action() {
      public VariableBase a(Object s, Object missingLabel1, Expression varName, Object e) {
//INSERTED CODE STRING:

	RESULT = new ReflectionVariable(sleft, eright, parser.ast, varName);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  static_var_list,
  rhs(static_var_list, T_COMMA, T_VARIABLE 
/*    new Action() {
      public List a(List list, Object missingLabel1, String var) {
//INSERTED CODE STRING:

	Variable v = new Variable(varleft, varright, parser.ast, var); 
	list.add(v);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(static_var_list, T_COMMA, T_VARIABLE, T_EQUAL, static_scalar 
/*    new Action() {
      public List a(List list, Object missingLabel1, String var, Object missingLabel3, Expression expr) {
//INSERTED CODE STRING:

	Variable v = new Variable(varleft, varright, parser.ast, var); 
	Assignment assignment = new Assignment(varleft, exprright, parser.ast, v, Assignment.OP_EQUAL, expr); 
	list.add(assignment);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_VARIABLE 
/*    new Action() {
      public List a(String var) {
//INSERTED CODE STRING:

	Variable v = new Variable(varleft, varright, parser.ast, var); 
	List list = new LinkedList();
	list.add(v);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_VARIABLE, T_EQUAL, static_scalar 
/*    new Action() {
      public List a(String var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:

	Variable v = new Variable(varleft, varright, parser.ast, var); 
	Assignment assignment = new Assignment(varleft, exprright, parser.ast, v, Assignment.OP_EQUAL, expr); 
	List list = new LinkedList();
	list.add(assignment);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  class_statement_list,
  rhs(class_statement_list, class_statement 
/*    new Action() {
      public List a(List list, Statement classStatement) {
//INSERTED CODE STRING:

	list.add(classStatement);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  class_statement,
  rhs(variable_modifiers, class_variable_declaration, T_SEMICOLON 
/*    new Action() {
      public Statement a(Integer modifier, List decList, Object e) {
//INSERTED CODE STRING:

	RESULT = new FieldsDeclaration(modifierleft, eright, parser.ast, modifier.intValue(), decList);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(constant_declaration, T_SEMICOLON 
/*    new Action() {
      public Statement a(List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new ConstantDeclaration(listleft, eright, parser.ast, list);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(method_modifiers, T_FUNCTION, is_reference, string_st, T_OPEN_PARENTHESE, parameter_list, T_CLOSE_PARENTHESE, method_body 
/*    new Action() {
      public Statement a(Integer modifier, Object s, Boolean isReference, String fn, Object missingLabel4, List paramList, Object missingLabel6, Block body) {
//INSERTED CODE STRING:

	int methodStart = modifier == null ? sleft : modifierleft;
	modifier = modifier == null ? PhpAstParser.PUBLIC : modifier;
	Identifier functionId = new Identifier(fnleft, fnright, parser.ast, fn);
	FunctionDeclaration functionDeclaration = new FunctionDeclaration(sleft, bodyright, parser.ast, functionId, paramList, body, isReference.booleanValue());
	RESULT = new MethodDeclaration(methodStart, bodyright, parser.ast, modifier.intValue(), functionDeclaration, true);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_VAR_COMMENT, unticked_statement 
/*    new Action() {
      public Statement a(String varComment, Statement statement) {
//INSERTED CODE STRING:

	RESULT = statement;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  method_body,
  rhs(T_SEMICOLON 
/*    new Action() {
      public Block a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CURLY_OPEN, inner_statement_list, T_CURLY_CLOSE 
/*    new Action() {
      public Block a(Object s, List list, Object e) {
//INSERTED CODE STRING:

	Block block = new Block(sleft, eright, parser.ast, list);
	RESULT = block;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  variable_modifiers,
  rhs(non_empty_member_modifiers 
/*    new Action() {
      public Integer a(List list) {
//INSERTED CODE STRING:

	int result = 0;
	for (Iterator it = list.iterator(); it.hasNext() ; ) {
		Integer modifier = (Integer)it.next();
		result |= modifier.intValue();
	}
	RESULT = new Integer(result);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_VAR 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = PhpAstParser.PUBLIC;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  method_modifiers,
  rhs(
/*    new Action() {
      public Integer a() {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_member_modifiers 
/*    new Action() {
      public Integer a(List list) {
//INSERTED CODE STRING:

	int result = 0;
	for (Iterator it = list.iterator(); it.hasNext() ; ) {
		Integer modifier = (Integer)it.next();
		result |= modifier.intValue();
	}
	RESULT = new Integer(result);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  non_empty_member_modifiers,
  rhs(member_modifier 
/*    new Action() {
      public List a(Integer modifier) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(modifier);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_member_modifiers, member_modifier 
/*    new Action() {
      public List a(List list, Integer modifier) {
//INSERTED CODE STRING:

	list.add(modifier);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  member_modifier,
  rhs(T_PUBLIC 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = PhpAstParser.PUBLIC;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_PROTECTED 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = PhpAstParser.PROTECTED;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_PRIVATE 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = PhpAstParser.PRIVATE;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_STATIC 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = PhpAstParser.STATIC;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ABSTRACT 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = PhpAstParser.ABSTRACT;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_FINAL 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = PhpAstParser.FINAL;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  class_variable_declaration,
  rhs(class_variable_declaration, T_COMMA, T_VARIABLE 
/*    new Action() {
      public List a(List list, Object missingLabel1, String var) {
//INSERTED CODE STRING:

	Variable varId = new Variable(varleft, varright, parser.ast, var);
	list.add(new ASTNode[] {varId, null});
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(class_variable_declaration, T_COMMA, T_VARIABLE, T_EQUAL, static_scalar 
/*    new Action() {
      public List a(List list, Object missingLabel1, String var, Object missingLabel3, Expression expr) {
//INSERTED CODE STRING:

	Variable varId = new Variable(varleft, varright, parser.ast, var);
	list.add(new ASTNode[] {varId, expr});
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_VARIABLE 
/*    new Action() {
      public List a(String var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	Variable varId = new Variable(varleft, varright, parser.ast, var);
	list.add(new ASTNode[] {varId, null});
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_VARIABLE, T_EQUAL, static_scalar 
/*    new Action() {
      public List a(String var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	Variable varId = new Variable(varleft, varright, parser.ast, var);
	list.add(new ASTNode[] {varId, expr});
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  constant_declaration,
  rhs(constant_declaration, T_COMMA, T_STRING, T_EQUAL, static_scalar 
/*    new Action() {
      public List a(List list, Object missingLabel1, String constName, Object missingLabel3, Expression expr) {
//INSERTED CODE STRING:

	Identifier constId = new Identifier(constNameleft, constNameright, parser.ast, constName);
	list.add(new ASTNode[] {constId, expr});
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CONST, T_STRING, T_EQUAL, static_scalar 
/*    new Action() {
      public List a(Object missingLabel0, String constName, Object missingLabel2, Expression expr) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	Identifier constId = new Identifier(constNameleft, constNameright, parser.ast, constName);
	list.add(new ASTNode[] {constId, expr});
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  echo_expr_list,
  rhs(echo_expr_list, T_COMMA, expr 
/*    new Action() {
      public List a(List exprList, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:

	exprList.add(expr);
	RESULT = exprList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr 
/*    new Action() {
      public List a(Expression expr) {
//INSERTED CODE STRING:

	List exprList = new LinkedList();
	exprList.add(expr);
	RESULT = exprList;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  for_expr,
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_for_expr 
/*    new Action() {
      public List a(List exprList) {
//INSERTED CODE STRING:

	RESULT = exprList;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  non_empty_for_expr,
  rhs(non_empty_for_expr, T_COMMA, expr 
/*    new Action() {
      public List a(List exprList, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:

	exprList.add(expr);
	RESULT = exprList;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr 
/*    new Action() {
      public List a(Expression expr) {
//INSERTED CODE STRING:

	List exprList = new LinkedList();
	exprList.add(expr);
	RESULT = exprList;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  expr_without_variable,
  rhs(T_LIST, T_OPEN_PARENTHESE, assignment_list, T_CLOSE_PARENTHESE, T_EQUAL, expr 
/*    new Action() {
      public Expression a(Object s, Object missingLabel1, List varList, Object close, Object missingLabel4, Expression expr) {
//INSERTED CODE STRING:

	ListVariable vars = new ListVariable(sleft, closeright, parser.ast, varList);
	RESULT = new Assignment(sleft, exprright, parser.ast, vars, Assignment.OP_EQUAL, expr);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var, Assignment.OP_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_EQUAL, T_REFERENCE, variable 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Object reftoken, VariableBase refvar) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, refvarright, parser.ast, var, Assignment.OP_EQUAL, new Reference(reftokenleft, refvarright, parser.ast, refvar)); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_EQUAL, T_REFERENCE, T_NEW, class_name_reference, ctor_arguments 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Object reftoken, Object s, ClassName className, List ctor) {
//INSERTED CODE STRING:

	RESULT = new Assignment(varleft, ctorright, parser.ast, var, Assignment.OP_EQUAL, 
		new Reference(reftokenleft, ctorright, parser.ast, 
			new ClassInstanceCreation(sleft, ctorright, parser.ast, className, ctor)));

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NEW, class_name_reference, ctor_arguments 
/*    new Action() {
      public Expression a(Object s, ClassName className, List ctor) {
//INSERTED CODE STRING:

	RESULT = new ClassInstanceCreation(sleft, ctorright, parser.ast, className, ctor);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CLONE, expr 
/*    new Action() {
      public Expression a(Object s, Expression expr) {
//INSERTED CODE STRING:

	RESULT = new CloneExpression(sleft, exprright, parser.ast, expr);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_PLUS_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_PLUS_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_MINUS_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_MINUS_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_MUL_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_MUL_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_DIV_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 	
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_DIV_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_CONCAT_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_CONCAT_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_MOD_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_MOD_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_AND_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_AND_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_OR_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_OR_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_XOR_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_XOR_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_SL_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_SL_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable, T_SR_EQUAL, expr 
/*    new Action() {
      public Expression a(VariableBase var, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new Assignment(varleft, exprright, parser.ast, var , Assignment.OP_SR_EQUAL, expr); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(rw_variable, T_INC 
/*    new Action() {
      public Expression a(VariableBase var, Object token) {
//INSERTED CODE STRING:
 
	RESULT = new PostfixExpression(varleft, tokenright, parser.ast, var , PostfixExpression.OP_INC); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_INC, rw_variable 
/*    new Action() {
      public Expression a(Object token, VariableBase var) {
//INSERTED CODE STRING:
 
	RESULT = new PrefixExpression(tokenleft, varright, parser.ast, var , PrefixExpression.OP_INC); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(rw_variable, T_DEC 
/*    new Action() {
      public Expression a(VariableBase var, Object token) {
//INSERTED CODE STRING:
 
	RESULT = new PostfixExpression(varleft, tokenright, parser.ast, var , PostfixExpression.OP_DEC); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DEC, rw_variable 
/*    new Action() {
      public Expression a(Object token, VariableBase var) {
//INSERTED CODE STRING:
 
	RESULT = new PrefixExpression(tokenleft, varright, parser.ast, var , PrefixExpression.OP_DEC); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_BOOLEAN_OR, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_BOOL_OR, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_BOOLEAN_AND, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_BOOL_AND, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_LOGICAL_OR, expr 
/*    new Action() {
      public Expression a(Expression expr1, String missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_STRING_OR, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_LOGICAL_AND, expr 
/*    new Action() {
      public Expression a(Expression expr1, String missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_STRING_AND, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_LOGICAL_XOR, expr 
/*    new Action() {
      public Expression a(Expression expr1, String missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_STRING_XOR, expr2);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_OR, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_OR, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_REFERENCE, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_AND, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_KOVA, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_XOR, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_NEKUDA, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_CONCAT, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_PLUS, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_PLUS, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_MINUS, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_MINUS, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_TIMES, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_MUL, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_DIV, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_DIV, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_PRECENT, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_MOD, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_SL, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_SL, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_SR, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_SR, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_PLUS, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new UnaryOperation(tokenleft, exprright, parser.ast, expr , UnaryOperation.OP_PLUS); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_MINUS, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new UnaryOperation(tokenleft, exprright, parser.ast, expr , UnaryOperation.OP_MINUS); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NOT, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new UnaryOperation(tokenleft, exprright, parser.ast, expr , UnaryOperation.OP_NOT); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_TILDA, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new UnaryOperation(tokenleft, exprright, parser.ast, expr , UnaryOperation.OP_TILDA); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_IS_IDENTICAL, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_IS_IDENTICAL, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_IS_NOT_IDENTICAL, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_IS_NOT_IDENTICAL, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_IS_EQUAL, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_IS_EQUAL, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_IS_NOT_EQUAL, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_IS_NOT_EQUAL, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_RGREATER, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_RGREATER, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_IS_SMALLER_OR_EQUAL, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_IS_SMALLER_OR_EQUAL, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_LGREATER, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_LGREATER, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_IS_GREATER_OR_EQUAL, expr 
/*    new Action() {
      public Expression a(Expression expr1, Object missingLabel1, Expression expr2) {
//INSERTED CODE STRING:
 
	RESULT = new InfixExpression(expr1left, expr2right, parser.ast, expr1 , InfixExpression.OP_IS_GREATER_OR_EQUAL, expr2); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_INSTANCEOF, class_name_reference 
/*    new Action() {
      public Expression a(Expression expr, Object missingLabel1, ClassName className) {
//INSERTED CODE STRING:
 
	RESULT = new InstanceOfExpression(exprleft, classNameright, parser.ast, expr , className); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE 
/*    new Action() {
      public Expression a(Object s, Expression expr, Object e) {
//INSERTED CODE STRING:

	RESULT = new ParenthesisExpression(sleft, eright, parser.ast, expr);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_QUESTION_MARK, expr, T_NEKUDOTAIM, expr 
/*    new Action() {
      public Expression a(Expression cond, Object missingLabel1, Expression ifTrue, Object missingLabel3, Expression ifFalse) {
//INSERTED CODE STRING:
 
	RESULT = new ConditionalExpression(condleft, ifFalseright, parser.ast, cond , ifTrue, ifFalse); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_QUESTION_MARK, T_NEKUDOTAIM, expr 
/*    new Action() {
      public Expression a(Expression cond, Object missingLabel1, Object missingLabel2, Expression ifFalse) {
//INSERTED CODE STRING:
 
	RESULT = new ConditionalExpression(condleft, ifFalseright, parser.ast, cond , null, ifFalse); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(internal_functions_in_yacc 
/*    new Action() {
      public Expression a(Expression expr) {
//INSERTED CODE STRING:

	RESULT = expr;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_INT_CAST, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new CastExpression(tokenleft, exprright, parser.ast, expr , CastExpression.TYPE_INT); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DOUBLE_CAST, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new CastExpression(tokenleft, exprright, parser.ast, expr , CastExpression.TYPE_REAL); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_STRING_CAST, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new CastExpression(tokenleft, exprright, parser.ast, expr , CastExpression.TYPE_STRING); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ARRAY_CAST, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new CastExpression(tokenleft, exprright, parser.ast, expr , CastExpression.TYPE_ARRAY); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_OBJECT_CAST, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new CastExpression(tokenleft, exprright, parser.ast, expr , CastExpression.TYPE_OBJECT); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_BOOL_CAST, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new CastExpression(tokenleft, exprright, parser.ast, expr , CastExpression.TYPE_BOOL); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_UNSET_CAST, expr 
/*    new Action() {
      public Expression a(Object token, Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = new CastExpression(tokenleft, exprright, parser.ast, expr , CastExpression.TYPE_UNSET); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_EXIT, exit_expr 
/*    new Action() {
      public Expression a(String s, Expression expr) {
//INSERTED CODE STRING:

	List expList = new LinkedList();
	if (expr != null) {
		expList.add(expr);
	}
	RESULT = new FunctionInvocation(sleft, exprright, parser.ast,
		new FunctionName(sleft, sright, parser.ast, 
			new Identifier(sleft, sright, parser.ast, s)), expList);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_AT, expr 
/*    new Action() {
      public Expression a(Object s, Expression expr) {
//INSERTED CODE STRING:

	RESULT = new IgnoreError(sleft, exprright, parser.ast, expr);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(scalar 
/*    new Action() {
      public Expression a(Expression scalar) {
//INSERTED CODE STRING:
 
	RESULT = scalar; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ARRAY, T_OPEN_PARENTHESE, array_pair_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public Expression a(Object s, Object missingLabel1, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new ArrayCreation(sleft, eright, parser.ast, list);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_BACKQUATE, backticks_expr, T_BACKQUATE 
/*    new Action() {
      public Expression a(Object s, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new BackTickExpression(sleft, eright, parser.ast, list);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_PRINT, expr 
/*    new Action() {
      public Expression a(String s, Expression expr) {
//INSERTED CODE STRING:

	List expList = new LinkedList();
	if (expr != null) {
		expList.add(expr);
	}
	RESULT = new FunctionInvocation(sleft, exprright, parser.ast,
		new FunctionName(sleft, sright, parser.ast, 
			new Identifier(sleft, sright, parser.ast, "print")), expList);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_FUNCTION, is_reference, T_OPEN_PARENTHESE, parameter_list, T_CLOSE_PARENTHESE, lexical_vars, T_CURLY_OPEN, inner_statement_list, T_CURLY_CLOSE 
/*    new Action() {
      public Expression a(Object s, Boolean isReference, Object missingLabel2, List paramList, Object missingLabel4, List varsList, Object blockStart, List list, Object blockEnd) {
//INSERTED CODE STRING:

	RESULT = new LambdaFunctionDeclaration(sleft, blockEndright, parser.ast, paramList, varsList, 
		new Block(blockStartleft, blockEndright, parser.ast, list), isReference.booleanValue());

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  lexical_vars,
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_USE, T_OPEN_PARENTHESE, lexical_var_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public List a(Object s, Object missingLabel1, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  lexical_var_list,
  rhs(lexical_var_list, T_COMMA, T_VARIABLE 
/*    new Action() {
      public List a(List list, Object missingLabel1, String var) {
//INSERTED CODE STRING:

	Variable v = new Variable(varleft, varright, parser.ast, var);
	list.add(v);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(lexical_var_list, T_COMMA, T_REFERENCE, T_VARIABLE 
/*    new Action() {
      public List a(List list, Object missingLabel1, Object ref, String var) {
//INSERTED CODE STRING:

	list.add(new Reference (refleft, varright, parser.ast, new Variable(varleft, varright, parser.ast, var)));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_VARIABLE 
/*    new Action() {
      public List a(String var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new Variable(varleft, varright, parser.ast, var));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_REFERENCE, T_VARIABLE 
/*    new Action() {
      public List a(Object ref, String var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new Reference (refleft, varright, parser.ast, new Variable(varleft, varright, parser.ast, var)));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  function_call,
  rhs(namespace_name, T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public VariableBase a(List list, Object missingLabel1, List parameters, Object e) {
//INSERTED CODE STRING:

	RESULT = new FunctionInvocation(listleft, eright, parser.ast, 
		new FunctionName(listleft, listright, parser.ast, 
			new NamespaceName(listleft, listright, parser.ast, list, false, false)), parameters);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NAMESPACE, T_NS_SEPARATOR, namespace_name, T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public VariableBase a(Object s, Object missingLabel1, List list, Object missingLabel3, List parameters, Object e) {
//INSERTED CODE STRING:

	RESULT = new FunctionInvocation(sleft, eright, parser.ast, 
		new FunctionName(sleft, listright, parser.ast, 
			new NamespaceName(sleft, listright, parser.ast, list, false, true)), parameters);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NS_SEPARATOR, namespace_name, T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public VariableBase a(Object s, List list, Object missingLabel2, List parameters, Object e) {
//INSERTED CODE STRING:

	RESULT = new FunctionInvocation(sleft, eright, parser.ast, 
		new FunctionName(sleft, listright, parser.ast, 
			new NamespaceName(sleft, listright, parser.ast, list, true, false)), parameters);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(class_name, T_PAAMAYIM_NEKUDOTAYIM, T_STRING, T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public VariableBase a(Expression className, Object missingLabel1, String fn, Object missingLabel3, List parameters, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new StaticMethodInvocation(classNameleft, eright, parser.ast, className, 
		new FunctionInvocation(fnleft, eright, parser.ast,
			new FunctionName(fnleft, fnright, parser.ast, 
				new Identifier(fnleft, fnright, parser.ast, fn)), parameters)); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(class_name, T_PAAMAYIM_NEKUDOTAYIM, variable_without_objects, T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public VariableBase a(Expression className, Object missingLabel1, Variable reflectionName, Object missingLabel3, List parameters, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new StaticMethodInvocation(classNameleft, eright, parser.ast, className, 
		new FunctionInvocation(reflectionNameleft, eright, parser.ast, 
			new FunctionName(reflectionNameleft, reflectionNameright, parser.ast, reflectionName), parameters)); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable_class_name, T_PAAMAYIM_NEKUDOTAYIM, T_STRING, T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public VariableBase a(Variable className, Object missingLabel1, String fn, Object missingLabel3, List parameters, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new StaticMethodInvocation(classNameleft, eright, parser.ast, className, 
		new FunctionInvocation(fnleft, eright, parser.ast, 
			new FunctionName(fnleft, fnright, parser.ast, 
				new Identifier(fnleft, fnright, parser.ast, fn)), parameters)); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable_class_name, T_PAAMAYIM_NEKUDOTAYIM, variable_without_objects, T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public VariableBase a(Variable className, Object missingLabel1, Variable reflectionName, Object missingLabel3, List parameters, Object e) {
//INSERTED CODE STRING:
 
	RESULT = new StaticMethodInvocation(classNameleft, eright, parser.ast, className, 
		new FunctionInvocation(reflectionNameleft, eright, parser.ast, 
			new FunctionName(reflectionNameleft, reflectionNameright, parser.ast, reflectionName), parameters)); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable_without_objects, T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public VariableBase a(Variable reflectionName, Object missingLabel1, List parameters, Object e) {
//INSERTED CODE STRING:

	RESULT = new FunctionInvocation(reflectionNameleft, eright, parser.ast, 
		new FunctionName(reflectionNameleft, reflectionNameright, parser.ast, reflectionName), parameters); 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  class_name,
  rhs(T_STATIC 
/*    new Action() {
      public Expression a(Object s) {
//INSERTED CODE STRING:

	RESULT = new Identifier(sleft, sright, parser.ast, "static");

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(namespace_name 
/*    new Action() {
      public Expression a(List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(listleft, listright, parser.ast, list, false, false);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NAMESPACE, T_NS_SEPARATOR, namespace_name 
/*    new Action() {
      public Expression a(Object missingLabel0, Object missingLabel1, List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(listleft, listright, parser.ast, list, false, true);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NS_SEPARATOR, namespace_name 
/*    new Action() {
      public Expression a(Object missingLabel0, List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(listleft, listright, parser.ast, list, true, false);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  fully_qualified_class_name,
  rhs(namespace_name 
/*    new Action() {
      public NamespaceName a(List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(listleft, listright, parser.ast, list, false, false);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NAMESPACE, T_NS_SEPARATOR, namespace_name 
/*    new Action() {
      public NamespaceName a(Object s, Object missingLabel1, List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(sleft, listright, parser.ast, list, false, true);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NS_SEPARATOR, namespace_name 
/*    new Action() {
      public NamespaceName a(Object s, List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(sleft, listright, parser.ast, list, true, false);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  class_name_reference,
  rhs(class_name 
/*    new Action() {
      public ClassName a(Expression className) {
//INSERTED CODE STRING:

	RESULT = new ClassName(classNameleft, classNameright, parser.ast, className);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(dynamic_class_name_reference 
/*    new Action() {
      public ClassName a(ClassName className) {
//INSERTED CODE STRING:

	RESULT = className;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  dynamic_class_name_reference,
  rhs(base_variable, T_OBJECT_OPERATOR, object_property, dynamic_class_name_variable_properties 
/*    new Action() {
      public ClassName a(VariableBase var, Object missingLabel1, VariableBase firstVarProperty, List propertyList) {
//INSERTED CODE STRING:

	// then get the aggregated list of properties (->...->...->...)
	LinkedList list = (LinkedList) propertyList;
	list.addFirst(firstVarProperty);
	
	// now create the dispatch(es) nodes 
	Dispatch dispatch = null;
	VariableBase dispatcher = var;
	Iterator listIt = list.iterator();
	while (listIt.hasNext()) {
		VariableBase property = (VariableBase)listIt.next();
		dispatch = parser.createDispatch(dispatcher, property);
		dispatcher = dispatch;
	}	
	
	// create class name from the dispatch
	RESULT = new ClassName(varleft, propertyListright, parser.ast, dispatch);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(base_variable 
/*    new Action() {
      public ClassName a(VariableBase var) {
//INSERTED CODE STRING:

	 RESULT = new ClassName(varleft, varright, parser.ast, var);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  dynamic_class_name_variable_properties,
  rhs(dynamic_class_name_variable_properties, dynamic_class_name_variable_property 
/*    new Action() {
      public List a(List variables, VariableBase var) {
//INSERTED CODE STRING:

	variables.add(var);
	RESULT = variables;	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  dynamic_class_name_variable_property,
  rhs(T_OBJECT_OPERATOR, object_property 
/*    new Action() {
      public VariableBase a(Object missingLabel0, VariableBase var) {
//INSERTED CODE STRING:

	RESULT = var;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  exit_expr,
  rhs(
/*    new Action() {
      public Expression a() {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_OPEN_PARENTHESE, T_CLOSE_PARENTHESE 
/*    new Action() {
      public Expression a(Object missingLabel0, Object missingLabel1) {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE 
/*    new Action() {
      public Expression a(Object missingLabel0, Expression expr, Object missingLabel2) {
//INSERTED CODE STRING:

	RESULT = expr;	

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  backticks_expr,
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ENCAPSED_AND_WHITESPACE 
/*    new Action() {
      public List a(String string) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	Scalar scalar = new Scalar(stringleft, stringright, parser.ast, string, string == null ? Scalar.TYPE_UNKNOWN : Scalar.TYPE_STRING);
	list.add(scalar);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(encaps_list 
/*    new Action() {
      public List a(List list) {
//INSERTED CODE STRING:

	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  ctor_arguments,
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public List a(Object missingLabel0, List paramsList, Object missingLabel2) {
//INSERTED CODE STRING:

	RESULT = paramsList;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  common_scalar,
  rhs(T_LNUMBER 
/*    new Action() {
      public Expression a(String scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, scalar, Scalar.TYPE_INT);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DNUMBER 
/*    new Action() {
      public Expression a(String scalar) {
//INSERTED CODE STRING:
 
	RESULT = new Scalar(scalarleft, scalarright, parser.ast, scalar, Scalar.TYPE_REAL);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CONSTANT_ENCAPSED_STRING 
/*    new Action() {
      public Expression a(String scalar) {
//INSERTED CODE STRING:
 
	RESULT = new Scalar(scalarleft, scalarright, parser.ast, scalar, Scalar.TYPE_STRING);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_LINE 
/*    new Action() {
      public Expression a(Object scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, "__LINE__", Scalar.TYPE_SYSTEM);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_FILE 
/*    new Action() {
      public Expression a(String scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, "__FILE__", Scalar.TYPE_SYSTEM);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DIR 
/*    new Action() {
      public Expression a(Object scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, "__DIR__", Scalar.TYPE_SYSTEM);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CLASS_C 
/*    new Action() {
      public Expression a(Object scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, "__CLASS__", Scalar.TYPE_SYSTEM);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_METHOD_C 
/*    new Action() {
      public Expression a(Object scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, "__METHOD__", Scalar.TYPE_SYSTEM);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_FUNC_C 
/*    new Action() {
      public Expression a(Object scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, "__FUNCTION__", Scalar.TYPE_SYSTEM);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NS_C 
/*    new Action() {
      public Expression a(Object scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, "__NAMESPACE__", Scalar.TYPE_SYSTEM);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_START_HEREDOC, T_ENCAPSED_AND_WHITESPACE, T_END_HEREDOC 
/*    new Action() {
      public Expression a(String label, String string, Object e) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new Scalar(stringleft, stringright, parser.ast, string, string == null ? Scalar.TYPE_UNKNOWN : Scalar.TYPE_STRING));
	RESULT = new Quote(labelleft, eright, parser.ast, list, label.charAt(0) == '\'' ? Quote.QT_NOWDOC : Quote.QT_HEREDOC);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_START_HEREDOC, T_END_HEREDOC 
/*    new Action() {
      public Expression a(String label, Object e) {
//INSERTED CODE STRING:

	RESULT = new Quote(labelleft, eright, parser.ast, new LinkedList(), label.charAt(0) == '\'' ? Quote.QT_NOWDOC : Quote.QT_HEREDOC);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  static_scalar,
  rhs(common_scalar 
/*    new Action() {
      public Expression a(Expression scalar) {
//INSERTED CODE STRING:

	RESULT = scalar;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(namespace_name 
/*    new Action() {
      public Expression a(List list) {
//INSERTED CODE STRING:

	if (list.size() == 1) {
		RESULT = new Scalar(listleft, listright, parser.ast, ((Identifier)list.get(0)).getName(), Scalar.TYPE_STRING);
	} else {
		RESULT = new NamespaceName(listleft, listright, parser.ast, list, false, false);
	}

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NAMESPACE, T_NS_SEPARATOR, namespace_name 
/*    new Action() {
      public Expression a(Object s, Object missingLabel1, List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(sleft, listright, parser.ast, list, false, true);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NS_SEPARATOR, namespace_name 
/*    new Action() {
      public Expression a(Object s, List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(sleft, listright, parser.ast, list, true, false);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_PLUS, static_scalar 
/*    new Action() {
      public Expression a(Object s, Expression expr) {
//INSERTED CODE STRING:

	RESULT = new UnaryOperation(sleft, exprright, parser.ast, expr, UnaryOperation.OP_PLUS);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_MINUS, static_scalar 
/*    new Action() {
      public Expression a(Object s, Expression expr) {
//INSERTED CODE STRING:

	RESULT = new UnaryOperation(sleft, exprright, parser.ast, expr, UnaryOperation.OP_MINUS);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ARRAY, T_OPEN_PARENTHESE, static_array_pair_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public Expression a(Object s, Object missingLabel1, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new ArrayCreation(sleft, eright, parser.ast, list);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(static_class_constant 
/*    new Action() {
      public Expression a(StaticConstantAccess classConstant) {
//INSERTED CODE STRING:

	RESULT = classConstant;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  static_class_constant,
  rhs(class_name, T_PAAMAYIM_NEKUDOTAYIM, T_STRING 
/*    new Action() {
      public StaticConstantAccess a(Expression className, Object missingLabel1, String varName) {
//INSERTED CODE STRING:

	RESULT = new StaticConstantAccess(classNameleft, varNameright, parser.ast, className, 
		new Identifier(varNameleft, varNameright, parser.ast, varName)); 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  scalar,
  rhs(T_STRING_VARNAME 
/*    new Action() {
      public Expression a(String scalar) {
//INSERTED CODE STRING:

	RESULT = new Scalar(scalarleft, scalarright, parser.ast, scalar, Scalar.TYPE_STRING);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(class_constant 
/*    new Action() {
      public Expression a(StaticConstantAccess classConstant) {
//INSERTED CODE STRING:

	RESULT = classConstant;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(namespace_name 
/*    new Action() {
      public Expression a(List list) {
//INSERTED CODE STRING:

	if (list.size() == 1) {
		RESULT = new Scalar(listleft, listright, parser.ast, ((Identifier)list.get(0)).getName(), Scalar.TYPE_STRING);
	} else {
		RESULT = new NamespaceName(listleft, listright, parser.ast, list, false, false);
	}

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NAMESPACE, T_NS_SEPARATOR, namespace_name 
/*    new Action() {
      public Expression a(Object s, Object missingLabel1, List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(sleft, listright, parser.ast, list, false, true);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NS_SEPARATOR, namespace_name 
/*    new Action() {
      public Expression a(Object s, List list) {
//INSERTED CODE STRING:

	RESULT = new NamespaceName(sleft, listright, parser.ast, list, true, false);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(common_scalar 
/*    new Action() {
      public Expression a(Expression scalar) {
//INSERTED CODE STRING:

	RESULT = scalar;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_QUATE, encaps_list, T_QUATE 
/*    new Action() {
      public Expression a(Object s, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new Quote(sleft, eright, parser.ast, list, Quote.QT_QUOTE);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_START_HEREDOC, encaps_list, T_END_HEREDOC 
/*    new Action() {
      public Expression a(String s, List list, Object e) {
//INSERTED CODE STRING:

	RESULT = new Quote(sleft, eright, parser.ast, list, Quote.QT_HEREDOC);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  static_array_pair_list,
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_static_array_pair_list, possible_comma 
/*    new Action() {
      public List a(List list, Object missingLabel1) {
//INSERTED CODE STRING:

	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  possible_comma,
  rhs(),
  rhs(T_COMMA
  )
)
,
prod(
  non_empty_static_array_pair_list,
  rhs(non_empty_static_array_pair_list, T_COMMA, static_scalar, T_DOUBLE_ARROW, static_scalar 
/*    new Action() {
      public List a(List list, Object missingLabel1, Expression key, Object missingLabel3, Expression value) {
//INSERTED CODE STRING:

	list.add(new ArrayElement(keyleft, valueright, parser.ast, key, value));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_static_array_pair_list, T_COMMA, static_scalar 
/*    new Action() {
      public List a(List list, Object missingLabel1, Expression value) {
//INSERTED CODE STRING:

	list.add(new ArrayElement(valueleft, valueright, parser.ast, value));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(static_scalar, T_DOUBLE_ARROW, static_scalar 
/*    new Action() {
      public List a(Expression key, Object missingLabel1, Expression value) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new ArrayElement(keyleft, valueright, parser.ast, key, value));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(static_scalar 
/*    new Action() {
      public List a(Expression value) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new ArrayElement(valueleft, valueright, parser.ast, value));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  expr,
  rhs(r_variable 
/*    new Action() {
      public Expression a(VariableBase var) {
//INSERTED CODE STRING:
 RESULT = var; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr_without_variable 
/*    new Action() {
      public Expression a(Expression ewv) {
//INSERTED CODE STRING:
 RESULT = ewv; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  r_variable,
  rhs(variable 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:
 RESULT = var; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  w_variable,
  rhs(variable 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:
 RESULT = var; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  rw_variable,
  rhs(variable 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:
 RESULT = var; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  variable,
  rhs(base_variable_with_function_calls, T_OBJECT_OPERATOR, object_property, method_or_not, variable_properties 
/*    new Action() {
      public VariableBase a(VariableBase var, Object missingLabel1, VariableBase memberProperty, List paramsList, List propertyList) {
//INSERTED CODE STRING:

	// get the last property 
	VariableBase firstVarProperty = null;
	if (paramsList == null) {
		firstVarProperty = memberProperty;
	} else {
		FunctionName fn = new FunctionName(memberPropertyleft, memberPropertyright, parser.ast, memberProperty);
		firstVarProperty = new FunctionInvocation(memberPropertyleft, paramsListright, parser.ast, fn, paramsList);
	}

	// then get the aggregated list of properties (->...->...->...)
	LinkedList list = (LinkedList) propertyList;
	list.addFirst(firstVarProperty);
	
	// now create the dispatch(es) nodes 
	Dispatch dispatch = null;
	VariableBase dispatcher = var;
	Iterator listIt = list.iterator();
	while (listIt.hasNext()) {
		VariableBase property = (VariableBase)listIt.next();
		dispatch = parser.createDispatch(dispatcher, property);
		dispatcher = dispatch;
	}	
	RESULT = dispatch;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(base_variable_with_function_calls 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:
 
	RESULT = var; 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  variable_properties,
  rhs(variable_properties, variable_property 
/*    new Action() {
      public List a(List variables, VariableBase variableProperty) {
//INSERTED CODE STRING:

	variables.add(variableProperty);
	RESULT = variables;	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  variable_property,
  rhs(T_OBJECT_OPERATOR, object_property, method_or_not 
/*    new Action() {
      public VariableBase a(Object missingLabel0, VariableBase memberProperty, List paramsList) {
//INSERTED CODE STRING:

	if (paramsList == null) {
		RESULT = memberProperty;
	} else {
		RESULT = new FunctionInvocation(memberPropertyleft, paramsListright, parser.ast, 
			new FunctionName(memberPropertyleft, memberPropertyright, parser.ast, memberProperty), paramsList);
	}

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  method_or_not,
  rhs(T_OPEN_PARENTHESE, function_call_parameter_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public List a(Object missingLabel0, List paramsList, Object missingLabel2) {
//INSERTED CODE STRING:

	RESULT = paramsList;	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  variable_without_objects,
  rhs(reference_variable 
/*    new Action() {
      public Variable a(Variable var) {
//INSERTED CODE STRING:
 
	RESULT = var; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(simple_indirect_reference, reference_variable 
/*    new Action() {
      public Variable a(Integer ref_count, Variable var) {
//INSERTED CODE STRING:

	// the ref_count counts the number of reflection (DOLLAR sign) so now we should 
	// accomulate the dolars into reflection variables
	Variable finalVar = var;
	for (int i=0; i<ref_count.intValue(); i++) {
		finalVar = new ReflectionVariable(ref_countright - i - 1, varright, parser.ast, finalVar);				
	}
	RESULT = finalVar;	

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  static_member,
  rhs(class_name, T_PAAMAYIM_NEKUDOTAYIM, variable_without_objects 
/*    new Action() {
      public VariableBase a(Expression className, Object missingLabel1, Variable var) {
//INSERTED CODE STRING:

	RESULT = new StaticFieldAccess(classNameleft, varright, parser.ast, className, var);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  variable_class_name,
  rhs(reference_variable 
/*    new Action() {
      public Variable a(Variable var) {
//INSERTED CODE STRING:

	RESULT = var;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  base_variable_with_function_calls,
  rhs(base_variable 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:
 RESULT = var; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(function_call 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:
 RESULT = var; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  base_variable,
  rhs(reference_variable 
/*    new Action() {
      public VariableBase a(Variable var) {
//INSERTED CODE STRING:
 
	RESULT = var; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(simple_indirect_reference, reference_variable 
/*    new Action() {
      public VariableBase a(Integer ref_count, Variable var) {
//INSERTED CODE STRING:

	// the ref_count counts the number of reflection (DOLLAR sign) so now we should 
	// accomulate the dolars into reflection variables
	VariableBase finalVar = var;
	for (int i=0; i<ref_count.intValue(); i++) {
		finalVar = new ReflectionVariable(ref_countright - i - 1, varright, parser.ast, finalVar);				
	}
	RESULT = finalVar;	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(static_member 
/*    new Action() {
      public VariableBase a(VariableBase s) {
//INSERTED CODE STRING:

	RESULT = s;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  reference_variable,
  rhs(reference_variable, T_OPEN_RECT, dim_offset, T_CLOSE_RECT 
/*    new Action() {
      public Variable a(Variable varName, Object missingLabel1, Expression index, Object e) {
//INSERTED CODE STRING:

	RESULT = new ArrayAccess(varNameleft, eright, parser.ast, varName, index, ArrayAccess.VARIABLE_ARRAY);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(reference_variable, T_CURLY_OPEN, expr, T_CURLY_CLOSE 
/*    new Action() {
      public Variable a(Variable varName, Object missingLabel1, Expression index, Object e) {
//INSERTED CODE STRING:

	RESULT = new ArrayAccess(varNameleft, eright, parser.ast, varName, index, ArrayAccess.VARIABLE_HASHTABLE);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(compound_variable 
/*    new Action() {
      public Variable a(Variable comp_var) {
//INSERTED CODE STRING:
 RESULT = comp_var; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  compound_variable,
  rhs(tracked_variable 
/*    new Action() {
      public Variable a(Variable var) {
//INSERTED CODE STRING:
 RESULT = var; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DOLLAR, T_CURLY_OPEN, expr, T_CURLY_CLOSE 
/*    new Action() {
      public Variable a(Object s, Object missingLabel1, Expression expr, Object e) {
//INSERTED CODE STRING:

	ReflectionVariable var = new ReflectionVariable(sleft, eright, parser.ast, expr);
	RESULT = var;	 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  dim_offset,
  rhs(
/*    new Action() {
      public Expression a() {
//INSERTED CODE STRING:
 
	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr 
/*    new Action() {
      public Expression a(Expression expr) {
//INSERTED CODE STRING:
 
	RESULT = expr; 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  object_property,
  rhs(object_dim_list 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:
 
	RESULT = var; 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable_without_objects 
/*    new Action() {
      public VariableBase a(Variable var) {
//INSERTED CODE STRING:

	RESULT = var;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  object_dim_list,
  rhs(object_dim_list, T_OPEN_RECT, dim_offset, T_CLOSE_RECT 
/*    new Action() {
      public VariableBase a(VariableBase var, Object missingLabel1, Expression index, Object e) {
//INSERTED CODE STRING:

	RESULT = new ArrayAccess(varleft, eright, parser.ast, var, index, ArrayAccess.VARIABLE_ARRAY);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(object_dim_list, T_CURLY_OPEN, expr, T_CURLY_CLOSE 
/*    new Action() {
      public VariableBase a(VariableBase var, Object missingLabel1, Expression index, Object e) {
//INSERTED CODE STRING:

	RESULT = new ArrayAccess(varleft, eright, parser.ast, var, index, ArrayAccess.VARIABLE_HASHTABLE);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable_name 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:
 RESULT = var; 
//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  variable_name,
  rhs(string_st 
/*    new Action() {
      public VariableBase a(String varName) {
//INSERTED CODE STRING:

	RESULT = new Variable(varNameleft, varNameright, parser.ast, varName); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CURLY_OPEN, expr, T_CURLY_CLOSE 
/*    new Action() {
      public VariableBase a(Object s, Expression expr, Object e) {
//INSERTED CODE STRING:

	RESULT = new ReflectionVariable(sleft, eright, parser.ast, expr); 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  simple_indirect_reference,
  rhs(T_DOLLAR 
/*    new Action() {
      public Integer a(Object missingLabel0) {
//INSERTED CODE STRING:

	RESULT = new Integer(1);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(simple_indirect_reference, T_DOLLAR 
/*    new Action() {
      public Integer a(Integer ref, Object missingLabel1) {
//INSERTED CODE STRING:

	RESULT = new Integer(1 + ref.intValue());

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  assignment_list,
  rhs(assignment_list, T_COMMA, assignment_list_element 
/*    new Action() {
      public List a(List listElements, Object missingLabel1, VariableBase element) {
//INSERTED CODE STRING:

	if (element != null) {
		listElements.add(element);
	} else {
		listElements.add(new Variable(elementleft, elementright, parser.ast, ""));
	}	
	RESULT = listElements;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(assignment_list_element 
/*    new Action() {
      public List a(VariableBase var) {
//INSERTED CODE STRING:

	List listElements = new LinkedList();
	if (var != null) {
		listElements.add(var);	
	} else {
		listElements.add(new Variable(varleft, varright, parser.ast, ""));
	}
	RESULT = listElements;
	;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  assignment_list_element,
  rhs(variable 
/*    new Action() {
      public VariableBase a(VariableBase var) {
//INSERTED CODE STRING:

	RESULT = var;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_LIST, T_OPEN_PARENTHESE, assignment_list, T_CLOSE_PARENTHESE 
/*    new Action() {
      public VariableBase a(Object s, Object missingLabel1, List varList, Object e) {
//INSERTED CODE STRING:

	RESULT = new ListVariable(sleft, eright, parser.ast, varList);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(
/*    new Action() {
      public VariableBase a() {
//INSERTED CODE STRING:

	RESULT = null;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  array_pair_list,
  rhs(
/*    new Action() {
      public List a() {
//INSERTED CODE STRING:

	RESULT = new LinkedList();

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_array_pair_list, possible_comma 
/*    new Action() {
      public List a(List list, Object missingLabel1) {
//INSERTED CODE STRING:

	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  non_empty_array_pair_list,
  rhs(non_empty_array_pair_list, T_COMMA, expr, T_DOUBLE_ARROW, expr 
/*    new Action() {
      public List a(List list, Object missingLabel1, Expression key, Object missingLabel3, Expression value) {
//INSERTED CODE STRING:

	list.add(new ArrayElement(keyleft, valueright, parser.ast, key, value));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_array_pair_list, T_COMMA, expr 
/*    new Action() {
      public List a(List list, Object missingLabel1, Expression expr) {
//INSERTED CODE STRING:

	list.add(new ArrayElement(exprleft, exprright, parser.ast, expr));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_DOUBLE_ARROW, expr 
/*    new Action() {
      public List a(Expression key, Object missingLabel1, Expression value) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new ArrayElement(keyleft, valueright, parser.ast, key, value));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr 
/*    new Action() {
      public List a(Expression expr) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new ArrayElement(exprleft, exprright, parser.ast, expr));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_array_pair_list, T_COMMA, expr, T_DOUBLE_ARROW, T_REFERENCE, w_variable 
/*    new Action() {
      public List a(List list, Object missingLabel1, Expression key, Object missingLabel3, Object s, VariableBase var) {
//INSERTED CODE STRING:

	list.add(new ArrayElement(keyleft, varright, parser.ast, key, new Reference(sleft, varright, parser.ast, var)));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(non_empty_array_pair_list, T_COMMA, T_REFERENCE, w_variable 
/*    new Action() {
      public List a(List list, Object missingLabel1, Object s, VariableBase var) {
//INSERTED CODE STRING:

	list.add(new ArrayElement(sleft, varright, parser.ast, new Reference(sleft, varright, parser.ast, var)));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(expr, T_DOUBLE_ARROW, T_REFERENCE, w_variable 
/*    new Action() {
      public List a(Expression key, Object missingLabel1, Object s, VariableBase var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new ArrayElement(keyleft, varright, parser.ast, key, new Reference(sleft, varright, parser.ast, var)));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_REFERENCE, w_variable 
/*    new Action() {
      public List a(Object s, VariableBase var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new ArrayElement(sleft, varright, parser.ast, new Reference(sleft, varright, parser.ast, var)));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  encaps_list,
  rhs(encaps_list, encaps_var 
/*    new Action() {
      public List a(List list, VariableBase var) {
//INSERTED CODE STRING:

	list.add(var);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(encaps_list, T_ENCAPSED_AND_WHITESPACE 
/*    new Action() {
      public List a(List list, String string) {
//INSERTED CODE STRING:

	list.add(new Scalar(stringleft, stringright, parser.ast, string, string == null ? Scalar.TYPE_UNKNOWN : Scalar.TYPE_STRING));
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(encaps_var 
/*    new Action() {
      public List a(VariableBase var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(var);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_ENCAPSED_AND_WHITESPACE, encaps_var 
/*    new Action() {
      public List a(String string, VariableBase var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(new Scalar(stringleft, stringright, parser.ast, string, string == null ? Scalar.TYPE_UNKNOWN : Scalar.TYPE_STRING));
	list.add(var);
	RESULT = list;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  encaps_var,
  rhs(tracked_variable 
/*    new Action() {
      public VariableBase a(Variable var) {
//INSERTED CODE STRING:

	RESULT = var;

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(tracked_variable, T_OPEN_RECT, encaps_var_offset, T_CLOSE_RECT 
/*    new Action() {
      public VariableBase a(Variable varName, Object missingLabel1, Expression index, Object e) {
//INSERTED CODE STRING:

	RESULT = new ArrayAccess(varNameleft, eright, parser.ast, varName, index, ArrayAccess.VARIABLE_ARRAY);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(tracked_variable, T_OBJECT_OPERATOR, string_st 
/*    new Action() {
      public VariableBase a(Variable var, Object missingLabel1, String string) {
//INSERTED CODE STRING:

	RESULT = parser.createDispatch(var, new Variable(stringleft, stringright, parser.ast, string));

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DOLLAR_OPEN_CURLY_BRACES, expr, T_CURLY_CLOSE 
/*    new Action() {
      public VariableBase a(Object s, Expression expr, Object e) {
//INSERTED CODE STRING:

	RESULT = new ReflectionVariable(sleft, eright, parser.ast, expr);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DOLLAR_OPEN_CURLY_BRACES, T_STRING_VARNAME, T_OPEN_RECT, expr, T_CLOSE_RECT, T_CURLY_CLOSE 
/*    new Action() {
      public VariableBase a(Object s, String varName, Object missingLabel2, Expression index, Object missingLabel4, Object e) {
//INSERTED CODE STRING:

	RESULT = new ArrayAccess(sleft, eright, parser.ast,
		new Variable(varNameleft, varNameright, parser.ast, varName), index, ArrayAccess.VARIABLE_ARRAY);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_CURLY_OPEN_WITH_DOLAR, variable, T_CURLY_CLOSE 
/*    new Action() {
      public VariableBase a(Object s, VariableBase var, Object e) {
//INSERTED CODE STRING:

	RESULT = new ReflectionVariable(sleft, eright, parser.ast, var);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  encaps_var_offset,
  rhs(string_st 
/*    new Action() {
      public Expression a(String string) {
//INSERTED CODE STRING:

	RESULT = new Identifier(stringleft, stringright, parser.ast, string);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_NUM_STRING 
/*    new Action() {
      public Expression a(String num) {
//INSERTED CODE STRING:

	RESULT = new Scalar(numleft,numright, parser.ast, num, Scalar.TYPE_REAL);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(tracked_variable 
/*    new Action() {
      public Expression a(Variable var) {
//INSERTED CODE STRING:

	RESULT = var;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  internal_functions_in_yacc,
  rhs(T_ISSET, T_OPEN_PARENTHESE, isset_variables, T_CLOSE_PARENTHESE 
/*    new Action() {
      public Expression a(String s, Object missingLabel1, List varList, Object e) {
//INSERTED CODE STRING:

	RESULT = new FunctionInvocation(sleft, eright, parser.ast, 
		new FunctionName(sleft, sright, parser.ast, 
			new Identifier(sleft, sright, parser.ast, "isset")), varList);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_EMPTY, T_OPEN_PARENTHESE, variable, T_CLOSE_PARENTHESE 
/*    new Action() {
      public Expression a(String s, Object missingLabel1, VariableBase var, Object e) {
//INSERTED CODE STRING:

	LinkedList varList = new LinkedList();
	varList.add(var);
	RESULT = new FunctionInvocation(sleft, eright, parser.ast, 
		new FunctionName(sleft, sright, parser.ast, new Identifier(sleft, sright, parser.ast, "empty")), varList);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_INCLUDE, expr 
/*    new Action() {
      public Expression a(String include, Expression expr) {
//INSERTED CODE STRING:

	RESULT = new Include(includeleft, exprright, parser.ast, expr, Include.IT_INCLUDE);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_INCLUDE_ONCE, expr 
/*    new Action() {
      public Expression a(String include, Expression expr) {
//INSERTED CODE STRING:

	RESULT = new Include(includeleft, exprright, parser.ast, expr, Include.IT_INCLUDE_ONCE);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_EVAL, T_OPEN_PARENTHESE, expr, T_CLOSE_PARENTHESE 
/*    new Action() {
      public Expression a(String s, Object missingLabel1, Expression expr, Object e) {
//INSERTED CODE STRING:

	LinkedList exprList = new LinkedList();
	exprList.add(expr);
	RESULT = new FunctionInvocation(sleft, eright, parser.ast, 
		new FunctionName(sleft, sright, parser.ast, new Identifier(sleft, sright, parser.ast, "eval")), exprList);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_REQUIRE, expr 
/*    new Action() {
      public Expression a(String include, Expression expr) {
//INSERTED CODE STRING:

	RESULT = new Include(includeleft, exprright, parser.ast, expr, Include.IT_REQUIRE);

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_REQUIRE_ONCE, expr 
/*    new Action() {
      public Expression a(String include, Expression expr) {
//INSERTED CODE STRING:

	RESULT = new Include(includeleft, exprright, parser.ast, expr, Include.IT_REQUIRE_ONCE);

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  isset_variables,
  rhs(variable 
/*    new Action() {
      public List a(VariableBase var) {
//INSERTED CODE STRING:

	List list = new LinkedList();
	list.add(var);
	RESULT = list;	

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(isset_variables, T_COMMA, variable 
/*    new Action() {
      public List a(List varList, Object missingLabel1, VariableBase var) {
//INSERTED CODE STRING:

	varList.add(var);
	RESULT = varList;

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  class_constant,
  rhs(class_name, T_PAAMAYIM_NEKUDOTAYIM, T_STRING 
/*    new Action() {
      public StaticConstantAccess a(Expression className, Object missingLabel1, String varName) {
//INSERTED CODE STRING:

	RESULT = new StaticConstantAccess(classNameleft, varNameright, parser.ast, className, 
		new Identifier(varNameleft, varNameright, parser.ast, varName)); 

//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(variable_class_name, T_PAAMAYIM_NEKUDOTAYIM, T_STRING 
/*    new Action() {
      public StaticConstantAccess a(Variable className, Object missingLabel1, String varName) {
//INSERTED CODE STRING:

	RESULT = new StaticConstantAccess(classNameleft, varNameright, parser.ast, className, 
		new Identifier(varNameleft, varNameright, parser.ast, varName)); 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  tracked_variable,
  rhs(T_VARIABLE 
/*    new Action() {
      public Variable a(String varName) {
//INSERTED CODE STRING:

	RESULT = new Variable(varNameleft, varNameright, parser.ast, varName); 

//END INSERT
      return RESULT; }
    };*/
  )
)
,
prod(
  string_st,
  rhs(T_STRING 
/*    new Action() {
      public String a(String value) {
//INSERTED CODE STRING:
 RESULT = value; 
//END INSERT
      return RESULT; }
    };*/
  ),
  rhs(T_DEFINE 
/*    new Action() {
      public String a(String value) {
//INSERTED CODE STRING:
 RESULT = value; 
//END INSERT
      return RESULT; }
    };*/
  )
)
);
}
}

package edu.tum.cup2.test;

import java.io.File;

import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.test.TrashSpecJava14.NonTerminals.*;
import static edu.tum.cup2.test.TrashSpecJava14.Terminals.*;

/**
 * Specification of the Java 1.4 grammar.
 * Based on the Java 1.4 grammar from the old CUP.
 * 
 * @author Andreas Wenger
 */
public class TrashSpecJava14
	extends CUP2Specification
{


	//terminals
	public enum Terminals implements Terminal
	{
		BOOLEAN, INT,
		LBRACK, RBRACK,
		IDENTIFIER,
		SEMICOLON, COMMA, LBRACE, RBRACE, EQ, LPAREN, RPAREN,
		NEW,
		NOT,
		EQEQ,
		AND,
		XOR,
		OR,
		ANDAND,
		OROR,
		INTEGER_LITERAL,
		BOOLEAN_LITERAL
	}


	//non-terminals
	public enum NonTerminals implements NonTerminal
	{
		goal,
		literal,
		type, primitive_type, 
		array_type,
		name,
		variable_initializer,
		array_initializer,
		variable_initializers,
		primary, primary_no_new_array,
		array_creation_init, array_creation_uninit,
		dim_exprs, dim_expr, dims_opt, dims,
		array_access,
		postfix_expression,
		unary_expression_not_plus_minus,
		cast_expression, equality_expression,
		and_expression, exclusive_or_expression, inclusive_or_expression,
		conditional_and_expression,
		expression
	}
	
	
	//symbols with values
	public class IDENTIFIER               extends SymbolValue<String>{};
	public class INTEGER_LITERAL          extends SymbolValue<Number>{};
	public class BOOLEAN_LITERAL          extends SymbolValue<Boolean>{};
	
	public TrashSpecJava14()
	{
		grammar(
			prod(goal,
									rhs(type, variable_initializer, SEMICOLON)),
			prod(variable_initializer,
					        rhs(expression),
					        rhs(array_initializer)),
			prod(literal,
				          rhs(INTEGER_LITERAL),
				          rhs(BOOLEAN_LITERAL)),
			prod(type,
				          rhs(primitive_type),
				          rhs(array_type)),
			prod(primitive_type,
				          rhs(INT),
				          rhs(BOOLEAN)),
			prod(array_type,
				          rhs(primitive_type, dims),
				          rhs(name, dims)),
			prod(name,
				          rhs(IDENTIFIER)),
			prod(array_initializer,
				          rhs(LBRACE, variable_initializers, RBRACE),
				          rhs(LBRACE, RBRACE)),
			prod(variable_initializers,
				          rhs(variable_initializer), //150
				          rhs(variable_initializers, COMMA, variable_initializer)),
		  prod(primary,
		  	          rhs(primary_no_new_array),
		  	          rhs(array_creation_init),
		  	          rhs(array_creation_uninit)),
		  prod(primary_no_new_array,
		  	          rhs(literal),
		  	          rhs(LPAREN, expression, RPAREN),
		  	          rhs(array_access)),
		  prod(array_creation_uninit,
		  	          rhs(NEW, primitive_type, dim_exprs, dims_opt)),
		  prod(array_creation_init,
		  	          rhs(NEW, primitive_type, dims, array_initializer)),
		  prod(dim_exprs,
		  	          rhs(dim_expr),
		  	          rhs(dim_exprs, dim_expr)),
		  prod(dim_expr,
		  	          rhs(LBRACK, expression, RBRACK)),
		  prod(dims_opt,
		  	          rhs(dims),
		  	          rhs()),
		  prod(dims,
		  	          rhs(LBRACK, RBRACK),
		  	          rhs(dims, LBRACK, RBRACK)),
		  prod(array_access,
		  	          rhs(name, LBRACK, expression, RBRACK),
		  	          rhs(primary_no_new_array, LBRACK, expression, RBRACK)),
		  prod(postfix_expression,
		  	          rhs(primary),
		  	          rhs(name)),
		  prod(unary_expression_not_plus_minus,
		  	          rhs(postfix_expression),
		  	          rhs(NOT, unary_expression_not_plus_minus),
		  	          rhs(cast_expression)),
		  prod(cast_expression,
		  	          rhs(LPAREN, primitive_type, dims_opt, RPAREN, unary_expression_not_plus_minus),
		  	          rhs(LPAREN, expression, RPAREN, unary_expression_not_plus_minus)),
		  prod(equality_expression,
		  	          rhs(unary_expression_not_plus_minus),
		  	          rhs(equality_expression, EQEQ, unary_expression_not_plus_minus)),
		  prod(and_expression,
		  	          rhs(equality_expression),
		  	          rhs(and_expression, AND, equality_expression)),
		  prod(exclusive_or_expression,
		  	          rhs(and_expression),
		  	          rhs(exclusive_or_expression, XOR, and_expression)),
		  prod(inclusive_or_expression,
		  	          rhs(exclusive_or_expression),
		  	          rhs(inclusive_or_expression, OR, exclusive_or_expression)),
		  prod(conditional_and_expression,
		  	          rhs(inclusive_or_expression),
		  	          rhs(conditional_and_expression, ANDAND, inclusive_or_expression)),
		  prod(expression,
		  	          rhs(conditional_and_expression),
		  	          rhs(expression, OROR, conditional_and_expression))
		);		       
	}

	
	
	public static void main(String[] args)
	{
		try
		{
			TrashSpecJava14 spec = new TrashSpecJava14();
			LRParsingTable table = new LR1toLALRGenerator(spec, Verbosity.Detailled).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("TrashSpecJava14-lalr.html")); //TEST
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
		}
	}

}
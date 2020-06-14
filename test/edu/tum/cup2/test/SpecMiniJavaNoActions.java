package edu.tum.cup2.test;

import java.io.File;

import org.junit.Test;

import edu.tum.cup2.generator.LR1Generator;
import edu.tum.cup2.generator.Verbosity;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.spec.CUP2Specification;

//locate static imports after others to be compatible with javac
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.test.SpecMiniJavaNoActions.NonTerminals.*;
import static edu.tum.cup2.test.SpecMiniJavaNoActions.Terminals.*;
import static junit.framework.Assert.fail;

/**
 * Specification of the MiniJava grammar, but here
 * without semantic actions.
 * 
 * Based on the old CUP grammar written by Michael Petter.
 * 
 * @author Andreas Wenger
 */
public class SpecMiniJavaNoActions
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

	
	public SpecMiniJavaNoActions()
	{
		precedences(
			left(ELSE, UNOP, BINOP, BUNOP, BBINOP));
		grammar(
			prod(program,
			              rhs(decllist, stmtlist)),
			prod(decllist,
				            rhs(decllist, decl),
				            rhs()),
			prod(stmtlist,
				            rhs(stmtlist, stmt),
				            rhs()),
			prod(decl,
				            rhs(TYPE, IDENT, identlist, SEMICOLON)),
			prod(identlist,
				            rhs(identlist, COMMA, IDENT),
				            rhs()),
			prod(stmt,
				            rhs(SEMICOLON),
				            rhs(BEGIN, stmtlist, END),
				            rhs(IDENT, ASSIGN, expr, SEMICOLON),
				            rhs(IDENT, ASSIGN, READ, LPAR, RPAR, SEMICOLON),
				            rhs(WRITE, LPAR, expr, RPAR, SEMICOLON),
				            rhs(IF, LPAR, cond, RPAR, stmt),
				            rhs(IF, LPAR, cond, RPAR, stmt, ELSE, stmt),
				            rhs(WHILE, LPAR, cond, RPAR, stmt)),
			prod(cond,
				            rhs(BOOLCONST),
				            rhs(LPAR, cond, RPAR),
				            rhs(expr, COMP, expr),
				            rhs(BUNOP, cond),
				            rhs(cond, BBINOP, cond)),
			prod(expr,
				            rhs(IDENT),
				            rhs(INTCONST),
				            rhs(LPAR, expr, RPAR),
				            rhs(BINOP, expr), //yes, BINOP (hack...)
				            rhs(expr, BINOP, expr))
		);		       
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
		}
	}
	
	
	/**
	 * LR(1) must work.
	 */
	@Test public void testLR1()
	{
		try
		{
			LRParsingTable table = new LR1Generator(this, Verbosity.Sparse).getParsingTable();
			LRParsingTableDump.dumpToHTML(table, new File("SpecMiniJavaNoActions.html")); //TEST
		}
		catch (GeneratorException ex)
		{
			ex.printStackTrace();
			fail();
		}
	}
	
	
	@Test public void testTime()
	{
		Time.measureTime(new SpecMiniJavaNoActions());
	}

}
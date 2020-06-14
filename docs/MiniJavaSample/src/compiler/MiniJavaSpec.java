package compiler;

import edu.tum.cup2.spec.CUP2Specification;
import ide.EditorWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import minijava.Cond;
import minijava.Constants;
import minijava.Decl;
import minijava.Expr;
import minijava.Program;
import minijava.Stmt;

import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.ParserInterface;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.semantics.Action;

import static compiler.MiniJavaSpec.NonTerminals.*;
import static compiler.MiniJavaSpec.Terminals.*;
import static edu.tum.cup2.grammar.SpecialTerminals.Error;


/**
 * MiniJava specification.
 * 
 * @author Stefan Dangl
 * @author Andreas Wenger
 * @author Michael Petter
 */
public class MiniJavaSpec
	extends CUP2Specification
	implements Serializable 
{
  
  //terminals
	public enum Terminals
		implements Terminal
	{
		UNOP,
		LPAR,
		BINOP,
		WHILE,
		INTCONST,
		COMMA,
		READ,
		BEGIN,
		ELSE,
		ASSIGN,
		IDENT,
		BOOLCONST,
		RPAR,
		SEMICOLON,
		COMP,
		END,
		BUNOP,
		BBINOP,
		WRITE,
		TYPE,
		IF;
		public edu.tum.cup2.spec.Insertable	insert	= null;
	}

	public class UNOP
		extends SymbolValue<Integer>
	{
	};

	public class BINOP
		extends SymbolValue<Integer>
	{
	};

	public class INTCONST
		extends SymbolValue<Integer>
	{
	};

	public class IDENT
		extends SymbolValue<String>
	{
	};

	public class BOOLCONST
		extends SymbolValue<Boolean>
	{
	};

	public class COMP
		extends SymbolValue<Integer>
	{
	};

	public class BBINOP
		extends SymbolValue<Integer>
	{
	};

	public class TYPE
		extends SymbolValue<Integer>
	{
	};


	//non-terminals
	public enum NonTerminals
		implements NonTerminal
	{
		stmtlist,
		decllist,
		program,
		stmt,
		expr,
		identlist,
		cond,
		decl;
	}

	public class stmtlist
		extends SymbolValue<List<Stmt>>
	{
	};

	public class decllist
		extends SymbolValue<List<Decl>>
	{
	};

	public class program
		extends SymbolValue<Program>
	{
	};

	public class stmt
		extends SymbolValue<Stmt>
	{
	};

	public class expr
		extends SymbolValue<Expr>
	{
	};

	public class identlist
		extends SymbolValue<List<String>>
	{
	};

	public class cond
		extends SymbolValue<Cond>
	{
	};

	public class decl
		extends SymbolValue<Decl>
	{
	};

	private static final long			serialVersionUID	= -1182588496852167213L;
	private final boolean				DEBUG				= true;
	final boolean						INFORM_EDITOR		= true;					// hack
	public ArrayList<ErrorInformation>	syntaxErrors;

	public MiniJavaSpec() {
		if (DEBUG) System.out.println("MiniJavaSpec created");
		
		precedences(left(ELSE, UNOP, BINOP, BUNOP, BBINOP));

		/** insertable tokens **/
		insert(TYPE, new String[] { "int" }, 0);
		insert(IF, new String[] { "if", "while" });
		insert(BINOP,2, new String[] { "+", "-", "*", "/" }, Constants.PLUS);
		insert(BBINOP,2, new String[] { "&&", "||" }, Constants.AND);
		insert(COMP, new String[] { "<", ">", "==", "<=", ">=", "!=" }, Constants.EQ);
		insert(COMMA, new String[] { "," });
		insert(SEMICOLON, 1, new String[] { ";" }); // < binop ?
		insert(ASSIGN, new String[] { "=" });
		insert(IF, new String[] { "if", "while" });
		insert(RPAR, new String[] { ")" });
		insert(BEGIN,1, new String[] { "{" });
		insert(END, new String[] { "}" });
		insert(LPAR, 1, new String[] { "(" });
		
		/** Parser Interface **/
		register(new ParserInterface(){
			@Override
			public void init(LRParser p, Object... initArgs) {
				System.out.println("init");
				if (DEBUG) syntaxErrors = new ArrayList<ErrorInformation>(); }
			@Override
			public void error(ErrorInformation errInf) {
				if (DEBUG) syntaxErrors.add(errInf); }
			public int getErrorSyncSize() {
				return 2;			}
			@Override
			public void exit(LRParser p) {
				if (DEBUG) for (ErrorInformation e : syntaxErrors)
					System.out.println(e);
			}
		});
		
		/** Grammar **/
		
		grammar(
			prod(program,     rhs(decllist, stmtlist),
			                  	new Action() {
			                  		public Program a(List<Decl> d, List<Stmt> s) {
			                  			return new Program(d,s); }}),
			prod(decllist,    rhs(decl, decllist), 
			                  	new Action() {
			                  		public List<Decl> a(Decl d, List<Decl> dl) {
			                  			dl.add(d); return dl; }},
			                  rhs(),
			                  	new Action() {
			                  		public List<Decl> a() {
			                  			return new LinkedList<Decl>(); }},
			                  rhs(Error, decllist),
			                  	new Action() {
									public List<Decl> a(ErrorInformation info, List<Decl> dl) {
			                  			if (INFORM_EDITOR) EditorWindow.addErrorText("Decleration erroneous");
			                  			return dl; }}),
			prod(stmtlist,    rhs(stmtlist, stmt), 
			                  	new Action() {
			                  		public List<Stmt> a(List<Stmt> sl, Stmt s) {
			                  			 sl.add(s); return sl; }},
			                  rhs(),
			                  	new Action() {
			                  		public List<Stmt> a() {
			                  			return new LinkedList<Stmt>(); }}),
			prod(decl,        rhs(TYPE, IDENT, identlist, SEMICOLON), 
			                  	new Action() {
			                  		public Decl a(Integer type, String i, List<String> il) {
			                  			il.add(0,i); return new Decl(il); }},
			                  rhs(TYPE, Error, identlist, SEMICOLON),
			                  	new Action() {
			                  		public Decl a(Integer type, ErrorInformation info, List<String> il) {
			                  			if (INFORM_EDITOR) EditorWindow.addErrorText("Identifier erroneous");
			                  			return new Decl(il); }}),
			prod(identlist,   rhs(identlist, COMMA, IDENT),
			                  	new Action() {
			                  		public List<String> a(List<String> il, String i) {
			                  			il.add(i); return il; }},
			                  rhs(),
			                  	new Action() {
			                  		public List<String> a() {
			                  			return new LinkedList<String>(); }},
			                  rhs(Error),
			                  	new Action() {
			                  		public List<String> a(ErrorInformation info) {
										if (INFORM_EDITOR ) EditorWindow.addErrorText("Identifier erroneous");
			                  			return new ArrayList<String>(); }}),
			prod(stmt,        rhs(SEMICOLON),
			                  	new Action() {
			                  		public Stmt a() {
			                  			return Stmt.empty(); }},
			                  rhs(BEGIN, stmtlist, END),
			                  	new Action() {
			                  		public Stmt a(List<Stmt> sl) {
			                  			return Stmt.compound(sl); }},
			                  rhs(IDENT, ASSIGN, expr, SEMICOLON),
			                  	new Action() {
			                  		public Stmt a(String i, Expr e) {
			                  			return Stmt.assign(i,e); }},
			                  rhs(IDENT, ASSIGN, READ, LPAR, RPAR, SEMICOLON),
			                  	new Action() {
			                  		public Stmt a(String i) {
			                  			return Stmt.read(i); }},
			                  rhs(WRITE, LPAR, expr, RPAR, SEMICOLON),
			                  	new Action() {
			                  		public Stmt a(Expr e) {
			                  			return Stmt.write(e); }},
			                  rhs(IF, LPAR, cond, RPAR, stmt), 
			                  	new Action() {
			                  		public Stmt a(Cond c, Stmt s) {
			                  			return Stmt.ifthen(c,s); }},
			                  rhs(IF, LPAR, cond, RPAR, stmt, ELSE, stmt),
			                  	new Action() {
			                  		public Stmt a(Cond c, Stmt t, Stmt e) {
			                  			return Stmt.ifthenelse(c,t,e); }},
			                  rhs(WHILE, LPAR, cond, RPAR, stmt), 
			                  	new Action() {
			                  		public Stmt a(Cond c, Stmt s) {
			                  			return Stmt.whileloop(c,s); }},
			                  rhs(Error),
			                  	new Action() {
			                  		public Stmt a(ErrorInformation info) {
			                  			if (INFORM_EDITOR) EditorWindow.addErrorText("Statement erroneous"); // hack
			                  			return Stmt.empty(); }}),
			prod(cond,        rhs(BOOLCONST), 
			                  	new Action() {
			                  		public Cond a(Boolean c) {
			                  			return Cond.boolconst(c); }},
			                  rhs(LPAR, cond, RPAR), 
			                  	new Action() {
			                  		public Cond a(Cond c) {
			                  			return Cond.priority(c); }},
			                  rhs(expr, COMP, expr), 
			                  	new Action() {
			                  		public Cond a(Expr e1, Integer op, Expr e2) {
			                  			return Cond.binop(e1,op,e2); }},
			                  rhs(BUNOP, cond), 
			                  	new Action() {
			                  		public Cond a(Cond c) {
			                  			return Cond.unop(c); }},
			                  rhs(cond, BBINOP, cond), 
			                  	new Action() {
			                  		public Cond a(Cond c1, Integer op, Cond c2) {
			                  			return Cond.binop(c1,op,c2); }}
/*			                  ,rhs(Error),
			                  	new Action() {
			                  		public Cond a(ErrorInformation info) {
			                  			if (INFORM_EDITOR) EditorWindow.nextErrorText = "Condition erroneous"; // hack
			                  			return null; }}*/
			),
			prod(expr,        rhs(IDENT), 
			                  	new Action() {
			                  		public Expr a(String i) {
			                  			return Expr.ident(i); }},
			                  rhs(INTCONST), 
			                  	new Action() {
			                  		public Expr a(Integer c) {
			                  			return Expr.intconst(c); }},
			                  rhs(LPAR, expr, RPAR), 
			                  	new Action() {
			                  		public Expr a(Expr e) {
			                  			return Expr.priority(e); }},
			                  rhs(BINOP, expr), 
			                  	new Action() {
			                  		public Expr a(Integer unused, Expr e) {
			                  			return Expr.unop(e); }},
			                  rhs(expr, BINOP, expr), 
			                  	new Action() {
			                  		public Expr a(Expr e1, Integer op, Expr e2) {
			                  			return Expr.binop(e1,op,e2); }}
/*			                  ,rhs(Error),
			                  	new Action() {
			                  		public Expr a(ErrorInformation info) {
			                  			if (INFORM_EDITOR) EditorWindow.addErrorText("Expression erroneous"); // hack
			                  			return null; }}*/
			)
		);
	}
	
}

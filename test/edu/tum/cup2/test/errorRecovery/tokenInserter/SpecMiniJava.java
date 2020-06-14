package edu.tum.cup2.test.errorRecovery.tokenInserter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import edu.tum.cup2.generator.LR1toLALRGenerator;
import edu.tum.cup2.generator.exceptions.GeneratorException;
import edu.tum.cup2.grammar.NonTerminal;
import edu.tum.cup2.grammar.SpecialTerminals;
import edu.tum.cup2.grammar.Terminal;
import edu.tum.cup2.io.LRParsingTableDump;
import edu.tum.cup2.parser.LRParser;
import edu.tum.cup2.parser.exceptions.EndOfInputstreamException;
import edu.tum.cup2.parser.exceptions.LRParserException;
import edu.tum.cup2.parser.exceptions.MissingErrorRecoveryException;
import edu.tum.cup2.parser.tables.LRParsingTable;
import edu.tum.cup2.scanner.InsertedScannerToken;
import edu.tum.cup2.scanner.InsertionScanner;
import edu.tum.cup2.scanner.RandomTokenCreator;
import edu.tum.cup2.scanner.Scanner;
import edu.tum.cup2.scanner.ScannerToken;
import edu.tum.cup2.scanner.TestScanner;
import edu.tum.cup2.semantics.Action;
import edu.tum.cup2.semantics.ErrorInformation;
import edu.tum.cup2.semantics.ParserInterface;
import edu.tum.cup2.semantics.SymbolValue;
import edu.tum.cup2.spec.CUP2Specification;
import edu.tum.cup2.spec.Insertable;
import edu.tum.cup2.test.minijava.Cond;
import edu.tum.cup2.test.minijava.Constants;
import edu.tum.cup2.test.minijava.Decl;
import edu.tum.cup2.test.minijava.Expr;
import edu.tum.cup2.test.minijava.MinijavaVisitor;
import edu.tum.cup2.test.minijava.Program;
import edu.tum.cup2.test.minijava.Stmt;
import edu.tum.cup2.test.minijava.Expr.Identifier;

// locate static imports after others to be compatible with javac
import static edu.tum.cup2.grammar.SpecialTerminals.Epsilon;
import static edu.tum.cup2.grammar.SpecialTerminals.Error;
import static edu.tum.cup2.scanner.ScannerTokenTestTool.terminal;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.NonTerminals.*;
import static edu.tum.cup2.test.errorRecovery.tokenInserter.SpecMiniJava.Terminals.*;
import static edu.tum.cup2.test.minijava.Constants.LE;
import static junit.framework.Assert.fail;


/*
 * Specification of the MiniJava grammar, but here without "useful" semantic
 * actions.
 * 
 * Based on the old CUP grammar written by Michael Petter.
 * 
 * SD : Added some error-recovery
 * 
 * @author Andreas Wenger
 * 
 * @author Stefan Dangl
 */
public class SpecMiniJava
	extends CUP2Specification
{


	//terminals
	public enum Terminals
		implements Terminal
	{
		SEMICOLON,
		COMMA,
		LPAR,
		RPAR,
		BEGIN,
		END,
		IF,
		ELSE,
		WHILE,
		READ,
		WRITE,
		BUNOP,
		ASSIGN,
		TYPE,
		BINOP,
		UNOP,
		COMP,
		BBINOP,
		INTCONST,
		IDENT,
		BOOLCONST;
		public Insertable	insert	= null;
	}


	//non-terminals
	public enum NonTerminals
		implements NonTerminal
	{
		program,
		decllist,
		decl,
		stmtlist,
		identlist,
		stmt,
		expr,
		cond
	}


	//symbols with values
	public class TYPE
		extends SymbolValue<Integer>
	{
	};

	public class BINOP
		extends SymbolValue<Integer>
	{
	};

	public class UNOP
		extends SymbolValue<Integer>
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

	public class program
		extends SymbolValue<Program>
	{
	};

	public class decllist
		extends SymbolValue<List<Decl>>
	{
	};

	public class decl
		extends SymbolValue<Decl>
	{
	};

	public class stmtlist
		extends SymbolValue<List<Stmt>>
	{
	};

	public class identlist
		extends SymbolValue<List<String>>
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

	public class cond
		extends SymbolValue<Cond>
	{
	}

	protected Queue<ErrorInformation>	errorInfs = new LinkedList<ErrorInformation>();


	public SpecMiniJava() {

		precedences(left(ELSE, UNOP, BINOP, BUNOP, BBINOP));

		/** inserted tokens **/ 
			insert(TYPE, new String[] { "int" }, 0);
			insert(IF, new String[] { "if","while" });
			// BINOP must not be used frequently -> times=2
			insert(COMP, new String[] { "<",">","==","<=",">=","!=" }, Constants.Unknown);
			insert(COMMA, new String[] { "," });
			insert(SEMICOLON,1, new String[] { ";" }); // < binop
			insert(BINOP, new String[] { "+","-","*","/" }, Constants.Unknown);
			insert(ASSIGN, new String[] {"="});
			// Semicolon must not be inserted one after another -
			insert(RPAR, new String[] { ")" });
			insert(LPAR,1, new String[] { "(" });
			// UNOP should not be inserted because it can not repair anything
			//insertable(1,UNOP, new String[] { "!","-" }, Constants.Unknown);
			// ( but because of the "hack", BINOP may be inserted too often )
		/** end inserted tokens**/

		register(new ParserInterface(){
			@Override
			public void error(ErrorInformation error) {
				errorInfs.add(error);
			}
			@Override
			public int getMaxTokenInsertTries() {
				return 10;
			}
			@Override
			public void exit(LRParser p) {
				for (ErrorInformation x:errorInfs)
					System.out.println(x);
			}
		});
		
		grammar(prod(program, rhs(decllist, stmtlist), new Action() {
			public Program a(List<Decl> d, List<Stmt> s) {
				return new Program(d, s);
			}
		}), prod(decllist, rhs(decllist, decl), new Action() {
			public List<Decl> a(List<Decl> dl, Decl d) {
				dl.add(d);
				return dl;
			}
		}, rhs(), new Action() {
			public List<Decl> a() {
				return new LinkedList<Decl>();
			}
		}),

		prod(stmtlist, rhs(stmtlist, stmt), new Action() {
			public List<Stmt> a(List<Stmt> sl, Stmt s) {
				sl.add(s);
				return sl;
			}
		}, rhs(), new Action() {
			public List<Stmt> a() {
				return new LinkedList<Stmt>();
			}
		}),
		prod(decl, rhs(TYPE, IDENT, identlist, SEMICOLON), new Action() {
			public Decl a(Integer t, String i, List<String> il) {
				il.add(i);
				return new Decl(il);
			}
		/**/}, rhs(TYPE, Error, identlist, SEMICOLON), new Action() {
			public Decl a(Integer t, ErrorInformation i, List<String> il) {
				//errorInfs.add(i);//System.out.println(i);
				return new Decl(il);
			}/**/
		}), prod(identlist, rhs(identlist, COMMA, IDENT), new Action() {
			public List<String> a(List<String> il, String i) {
				il.add(i);
				return il;
			}
		/**/}, rhs(identlist, Error), new Action() {
			public List<String> a(List<String> il, ErrorInformation i) {
				//errorInfs.add(i);//System.out.println(i);
				return il;
			}/**/
		}, rhs(), new Action() {
			public List<String> a() {
				return new LinkedList<String>();
			}
		}), prod(stmt,
			/**/rhs(Error), new Action() {
			public Stmt a(ErrorInformation x) {
				//errorInfs.add(x);//System.out.println(x);
				return Stmt.empty();
			}},/**/
			rhs(SEMICOLON), new Action() {
			public Stmt a() {
				return Stmt.empty();
			}
		}, rhs(BEGIN, stmtlist, END), new Action() {
			public Stmt a(List<Stmt> sl) {
				return Stmt.compound(sl);
			}
		}, rhs(IDENT, ASSIGN, expr, SEMICOLON), new Action() {
			public Stmt a(String i, Expr e) {
				return Stmt.assign(i, e);
			}
		}, rhs(IDENT, ASSIGN, READ, LPAR, RPAR, SEMICOLON), new Action() {
			public Stmt a(String i) {
				return Stmt.read(i);
			}
		}, rhs(WRITE, LPAR, expr, RPAR, SEMICOLON), new Action() {
			public Stmt a(Expr e) {
				return Stmt.write(e);
			}
		}, rhs(IF, LPAR, cond, RPAR, stmt), new Action() {
			public Stmt a(Cond c, Stmt s) {
				return Stmt.ifthen(c, s);
			}
		}, rhs(IF, LPAR, cond, RPAR, stmt, ELSE, stmt), new Action() {
			public Stmt a(Cond c, Stmt t, Stmt e) {
				return Stmt.ifthenelse(c, t, e);
			}
		}, rhs(WHILE, LPAR, cond, RPAR, stmt), new Action() {
			public Stmt a(Cond c, Stmt s) {
				return Stmt.whileloop(c, s);
			}
		}),
		prod(cond, rhs(BOOLCONST), new Action() {
			public Cond a(Boolean c) {
				return Cond.boolconst(c);
			}
		},
		rhs(LPAR, cond, RPAR), new Action() {
			public Cond a(Cond c) {
				return Cond.priority(c);
			}
		}, rhs(expr, COMP, expr), new Action() {
			public Cond a(Expr e1, Integer op, Expr e2) {
				return Cond.binop(e1, op, e2);
			}
		}, rhs(BUNOP, cond), new Action() {
			public Cond a(Cond c) {
				return Cond.unop(c);
			}
		}, rhs(cond, BBINOP, cond), new Action() {
			public Cond a(Cond c1, Integer op, Cond c2) {
				return Cond.binop(c1, op, c2);
			}
		}),
		
		prod(expr, rhs(IDENT), new Action() {
			public Expr a(String i) {
				return Expr.ident(i);
			}
		}, rhs(INTCONST), new Action() {
			public Expr a(Integer c) {
				return Expr.intconst(c);
			}
		}, rhs(LPAR, expr, RPAR), new Action() {
			public Expr a(Expr e) {
				return Expr.priority(e);
			}/*
		}, rhs(BINOP, expr), new Action() {
			public Expr a(Integer op, Expr e) { //op: not in original grammar, but needed because it has a value utianr tdrnituaen itdaen ditaen tdiaen dutiane idtaern 
				return Expr.unop(e);
			}*/
		}, rhs(expr, BINOP, expr), new Action() {
			public Expr a(Expr e1, Integer op, Expr e2) {
				return Expr.binop(e1, op, e2);
			}
		}, rhs(UNOP, expr), new Action() {
			public Expr a(Integer op, Expr e2) {
				return Expr.unop(e2);
			}
		}));
	}

	//send through test program:
	//
	//  int n p , w ,, sum , i ;
	//  a, b, c
	//  int d ;
	//  n = read
	//  w = 0;
	//  y < 3 && x<7 ) sum a;
	//  i = 1;
	//  while ( !(sum 2 2 )  {
	//		w = w +5
	//      sum = sum + ;
	//      i i 2;
	//  }
	//  write (-w );

	private Object runInsertionParse(Scanner s) throws GeneratorException, LRParserException, IOException
	{
		LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
		LRParsingTableDump.dumpToHTML(table, new java.io.File(
		"errRec_tokenInserter_SpecMiniJava.html"));
		Scanner input = new InsertionScanner(table,s );
		return (new LRParser(table)).parse(input, true, false);
	}

	@Test(expected=edu.tum.cup2.parser.exceptions.EndOfInputstreamException.class)
	public void testErrors001() throws GeneratorException, LRParserException, IOException {
		Object result = runInsertionParse(
			new TestScanner(
				terminal(TYPE), // int
				terminal(IDENT, "c7"), // c7
				terminal(COMMA), // ,
				terminal(SEMICOLON), // ;
				terminal(IDENT, "i"), // i
				terminal(IDENT, "i"), // i
				terminal(TYPE), // int
				terminal(IDENT, "c7"), // c7
				terminal(SEMICOLON), // ;
				terminal(IDENT, "a0"), // a0
				terminal(BUNOP), // !
				terminal(ASSIGN), // =
				terminal(BEGIN), // {
				terminal(IDENT, "i"), // i
				terminal(SEMICOLON), // ;
				terminal(IDENT, "sum"), // sum
				terminal(IDENT, "sum"), // sum
				terminal(COMMA), // ,
				terminal(IDENT, "i"), // i
				terminal(SEMICOLON), // ;
				terminal(LPAR), // !
				terminal(IDENT, "b1"), // i
				terminal(INTCONST, 2), // 2
				terminal(WHILE) // while
			)
		);
		fail();
	}
	

	@Test(expected=edu.tum.cup2.parser.exceptions.EndOfInputstreamException.class)
	public void testErrors002() throws GeneratorException, LRParserException, IOException {
		Object result = runInsertionParse(
			new TestScanner(
				terminal(TYPE), // int
				terminal(IDENT, "a0"),
				terminal(IDENT, "sum"),
				terminal(SEMICOLON), // ;
				terminal(SEMICOLON), // ;
				terminal(SEMICOLON), // ;
				
				terminal(IDENT, "c7"),
				terminal(TYPE), // int
				terminal(ASSIGN), // =
				terminal(LPAR), // =
				terminal(TYPE), // int
				terminal(SEMICOLON), // ;
				terminal(TYPE), // int
				terminal(IDENT, "b1"),
				terminal(SEMICOLON), // ;
				
				terminal(WHILE),
				terminal(BUNOP),
				terminal(LPAR),
				terminal(COMMA),
				terminal(TYPE),
				terminal(LPAR),
				terminal(WHILE),
				terminal(SEMICOLON), // ;
				terminal(IDENT, "sum"),
				terminal(TYPE),
				terminal(TYPE),
				terminal(SEMICOLON), // ;
				
				terminal(LPAR),
				terminal(IDENT, "i"),
				terminal(SEMICOLON), // ;
				terminal(SEMICOLON), // ;
				terminal(BEGIN),
				terminal(IDENT, "i"),
				terminal(SEMICOLON), // ;

				terminal(WHILE),
				terminal(TYPE),
				terminal(IDENT, "a0"),
				terminal(IDENT, "c7"),
				terminal(SEMICOLON), // ;
				terminal(TYPE),
				terminal(TYPE)
			)
		);
		fail();
	}
	
	/**
	 * Test-method for error-recovery.
	 */
	@Test
	public void testErrors() {
		try {
			/*/
			//test 1
			ScannerToken<Object> t;
			do{
				t = input.readNextTerminal();
				System.err.println(t+" <-");
			}while(t.getSymbol()!=SpecialTerminals.EndOfInputStream);
			/*/
			//test 2
			Object result = runInsertionParse(inputScanner);
			System.out.println();
			String r = result.toString().intern();
			System.out.println(r); //TEST/**/
			if (r!=
				"int p,w,sum,i,n;\n" +
				"int b,c,a;\n" +
				"int d;\n" +
				"n=read();\n" +
				"w=0;\n" +
				"if(y<3&&x<7)sum=a;\n" +
				"\n" +
				"i=1;\n" +
				"while (!(sum ? 2 o 2)){\n" +
				"w=w+5;\n" +
				"i=i o 2;\n" +
				"}\n" +
				"\n" +
				"write(-w);\n"
			) fail();	
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testScan4All() throws GeneratorException, IOException
	{
		LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();
		InsertionScanner inserter = new InsertionScanner(table,inputScanner);
		for (InsertedScannerToken<?> tkn : inserter.scan4allInsertions())
			System.out.println(tkn.getErrorInformation());
	}
	
	@Test
	public void randomTokenTest() throws GeneratorException, IOException, LRParserException
	{
		LRParsingTable table = new LR1toLALRGenerator(this).getParsingTable();

		@SuppressWarnings("unchecked")
		List<List<ScannerToken<? extends Object>>> correctList
		/**/ 
		 	= Arrays.asList(
				Arrays.asList(
					terminal(TYPE), // int
					terminal(IDENT, "a0"), // n
					terminal(COMMA), // ,
					terminal(IDENT, "b1"), // w
					terminal(SEMICOLON) // ;
				),
				Arrays.asList(
					terminal(TYPE), // int
					terminal(IDENT, "c7"), // sum
					terminal(COMMA), // ,
					terminal(IDENT, "i"), // i
					terminal(SEMICOLON) // ;
				),
				Arrays.asList(
					terminal(IDENT, "i"), // i
					terminal(ASSIGN), // =
					terminal(IDENT, "i"), // i
					terminal(INTCONST, 2), // 2
					terminal(SEMICOLON) // ;
				),
				Arrays.asList(
					terminal(WHILE), // while
					terminal(LPAR), // (
					terminal(BUNOP), // !
					terminal(LPAR), // (
					terminal(IDENT, "sum"), // sum
					//terminal(COMP, LE), // <
					terminal(INTCONST, 2), // 2
					//terminal(BINOP, 1), // +
					terminal(INTCONST, 2), // 2
					terminal(RPAR), // )
					terminal(BEGIN) // {
				)
			)
			/*/
			= null
			/**/;
		InsertionScanner inserter = new InsertionScanner(
			table,
			new TokenPrinter(
				new RandomTokenCreator(
					this,
					correctList
				)
			)
		);
		/*/
		ScannerToken<? extends Object> t;
		do{
			t = inserter.readNextTerminal();
			System.out.print("inserter gives me: ");
			System.out.println(t);
		}while(t.getSymbol()!=SpecialTerminals.EndOfInputStream);
		/*/
		Object result = "";
		try{
			result = new LRParser(table).parse(inserter, true);
		}catch (LRParserException e){
			if (e instanceof MissingErrorRecoveryException)
				System.out.println("Missing error recovery :-(");
			else if (e instanceof EndOfInputstreamException)
				System.out.println("... End of input stream");
			else
				e.printStackTrace();
		}
		System.out.println();
		System.out.println(result.toString()); //TEST/**/
		/**/
	}


	/**
	 * Test-method for error-recovery.
	 *//*
	@Test
	public void testErrors2() {
		try {
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
			Object result = new LRParser(table).parse(new InsertionScanner(table,new TestScanner(terminal(TYPE), // int
				terminal(IDENT, "n"), // n
				terminal(IDENT, "p"), // p
				terminal(COMMA), // ,
				terminal(IDENT, "w"), // w
				terminal(COMMA), // ,
				terminal(COMMA), // ,
				terminal(IDENT, "sum"), // sum
				terminal(COMMA), // ,
				terminal(IDENT, "i"), // i
				terminal(SEMICOLON), // ;

				terminal(IDENT, "a"), // a
				terminal(COMMA), // ,
				terminal(IDENT, "b"), // b
				terminal(COMMA), // ,
				terminal(IDENT, "c"), // c
				terminal(SEMICOLON), // ;

				terminal(IDENT, "n"), // n
				terminal(ASSIGN), // =
				terminal(READ), // read
				terminal(RPAR), // )
				terminal(SEMICOLON), // ;

				terminal(IDENT, "w"), // w
				terminal(ASSIGN), // =
				terminal(INTCONST, 0), // 0
				terminal(SEMICOLON), // ;

				terminal(IDENT, "sum"), // sum
				terminal(ASSIGN), // =
				terminal(IDENT, "a"), // a
				terminal(SEMICOLON), // ;
				terminal(IDENT, "i"), // i
				terminal(ASSIGN), // =
				terminal(INTCONST, 1), // 1
				terminal(SEMICOLON), // ;

				terminal(WHILE), // while
				terminal(LPAR), // (
				terminal(BUNOP), // !
				terminal(LPAR), // (
				terminal(IDENT, "sum"), // sum
				terminal(COMP, LE), // <
				terminal(LPAR), // (
				terminal(RPAR), // )
				terminal(RPAR), // )
				terminal(BEGIN), // {
				terminal(IDENT, "w"), // w
				terminal(ASSIGN), // =
				terminal(IDENT, "w"), // w
				terminal(BINOP, Constants.PLUS), // +
				terminal(BINOP, Constants.PLUS), // +
				terminal(SEMICOLON), // ;
				terminal(IDENT, "sum"), // sum
				terminal(ASSIGN), // =
				terminal(IDENT, "sum"), // sum
				terminal(BINOP, Constants.PLUS), // +
				terminal(IDENT, "i"), // i
				terminal(SEMICOLON), // ;

				terminal(IDENT, "i"), // i
				terminal(ASSIGN), // =
				terminal(IDENT, "i"), // i
				terminal(INTCONST, 2), // 2
				terminal(SEMICOLON), // ;

				terminal(END), // }
				terminal(WRITE), // write
				terminal(LPAR), // (
				terminal(BINOP, Constants.MINUS), // -
				terminal(IDENT, "w"), // w
				terminal(RPAR), // )
				terminal(SEMICOLON) // ;
				)), true, false);
			System.out.println(result.toString()); //TEST

			((Program) result).accept(new MinijavaVisitor() {
				private List<String>	valid_idents	= null;

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
						System.err.println("invalid identifier : " + d.i);
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}*/

	private Scanner	inputScanner = new TestScanner(
		terminal(TYPE), // int
		terminal(IDENT, "n"), // n
		terminal(IDENT, "p"), // p
		terminal(COMMA), // ,
		terminal(IDENT, "w"), // w
		terminal(COMMA), // ,
		terminal(COMMA), // ,
		terminal(IDENT, "sum"), // sum
		terminal(COMMA), // ,
		terminal(IDENT, "i"), // i
		terminal(SEMICOLON), // ;

		terminal(IDENT, "a"), // a
		terminal(COMMA), // ,
		terminal(IDENT, "b"), // b
		terminal(COMMA), // ,
		terminal(IDENT, "c"), // c
		
		terminal(TYPE), // int
		terminal(IDENT, "d"), // d
		terminal(SEMICOLON), // ;

		terminal(IDENT, "n"), // n
		terminal(ASSIGN), // =
		terminal(READ), // read
		//terminal(RPAR), // )
		//terminal(SEMICOLON), // ;

		terminal(IDENT, "w"), // w
		terminal(ASSIGN), // =
		terminal(INTCONST, 0), // 0
		terminal(SEMICOLON), // ;
		
		//terminal(IF), // if
		terminal(IDENT, "y"), // y
		terminal(COMP, LE), // <
		terminal(INTCONST, 3), // 3
		terminal(BBINOP, Constants.AND), //&&
		terminal(IDENT, "x"), // x
		terminal(COMP, LE), // <
		terminal(INTCONST, 7), // 7
		terminal(RPAR), // )

		terminal(IDENT, "sum"), // sum
		//terminal(ASSIGN), // =
		terminal(IDENT, "a"), // w
		terminal(SEMICOLON), // ;
		terminal(IDENT, "i"), // i
		terminal(ASSIGN), // =
		terminal(INTCONST, 1), // 1
		terminal(SEMICOLON), // ;

		terminal(WHILE), // while
		terminal(LPAR), // (
		terminal(BUNOP), // !
		terminal(LPAR), // (
		terminal(IDENT, "sum"), // sum
		//terminal(COMP, LE), // <
		terminal(INTCONST, 2), // 2
		//terminal(BINOP, 1), // +
		terminal(INTCONST, 2), // 2
		terminal(RPAR), // )
		terminal(BEGIN), // {
	
		terminal(IDENT, "w"), // w
		terminal(ASSIGN), // =
		terminal(IDENT, "w"), // w
		terminal(BINOP, Constants.PLUS), // +
		terminal(INTCONST, 5), // 5

		terminal(IDENT, "sum"), // sum
		terminal(ASSIGN), // =
		terminal(IDENT, "sum"), // sum
		terminal(BINOP, Constants.PLUS), // +
		//terminal(IDENT, "i"), // i
		terminal(SEMICOLON), // ;

		terminal(IDENT, "i"), // i
		//terminal(ASSIGN), // =
		terminal(IDENT, "i"), // i
		terminal(INTCONST, 2), // 2
		terminal(SEMICOLON), // ;

		terminal(END), // }
		terminal(WRITE), // write
		terminal(LPAR), // (
		terminal(UNOP, Constants.MINUS), // -
		terminal(IDENT, "w"), // w
		terminal(RPAR), // )
		terminal(SEMICOLON) // ;
	);
	
	@Before
	public void init(){
		System.out.println();
		System.out.println("\t\t\t\t===== next test =====");
		System.out.println();
	}
	
}

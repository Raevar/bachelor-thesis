package ide.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import compiler.MiniJavaSpec;

import edu.tum.cup2.grammar.Terminal;

/**
 *
 *	A mapping from terminals to strings
 *  for the IDE to display expected
 *  input.
 *
 ***/

public final class MiniJavaSpec$Terminal2Strings
{
	private final static HashMap<Terminal, Collection<String>> x 
		= new HashMap<Terminal, Collection<String>>();

	public final static Collection<String> get(Terminal t) {
		return x.get(t);
	}

	static{
		x.put(MiniJavaSpec.Terminals.ASSIGN, Arrays.asList("="));
		x.put(MiniJavaSpec.Terminals.BBINOP, Arrays.asList("&&","||"));
		x.put(MiniJavaSpec.Terminals.BEGIN, Arrays.asList("{"));
		x.put(MiniJavaSpec.Terminals.BINOP, Arrays.asList("+", "-", "*", "/"));
		x.put(MiniJavaSpec.Terminals.BOOLCONST, Arrays.asList("true","false"));
		x.put(MiniJavaSpec.Terminals.BUNOP, Arrays.asList( "!" ));
		x.put(MiniJavaSpec.Terminals.COMMA, Arrays.asList(","));
		x.put(MiniJavaSpec.Terminals.COMP, Arrays.asList( "<", ">", "==", "<=", ">=", "!=" ));
		x.put(MiniJavaSpec.Terminals.ELSE, Arrays.asList("else"));
		x.put(MiniJavaSpec.Terminals.END, Arrays.asList("}"));
		x.put(MiniJavaSpec.Terminals.IF, Arrays.asList("if"));
		x.put(MiniJavaSpec.Terminals.IDENT, Arrays.asList("identifier"));
		x.put(MiniJavaSpec.Terminals.INTCONST, Arrays.asList("0"));
		x.put(MiniJavaSpec.Terminals.LPAR, Arrays.asList("("));
		x.put(MiniJavaSpec.Terminals.READ, Arrays.asList("read"));
		x.put(MiniJavaSpec.Terminals.RPAR, Arrays.asList(")"));
		x.put(MiniJavaSpec.Terminals.SEMICOLON, Arrays.asList(";"));
		x.put(MiniJavaSpec.Terminals.TYPE, Arrays.asList("int"));
		x.put(MiniJavaSpec.Terminals.UNOP, new ArrayList<String>());
		x.put(MiniJavaSpec.Terminals.WHILE, Arrays.asList("while"));
		x.put(MiniJavaSpec.Terminals.WRITE, Arrays.asList("write"));
	}
	
	
	
}

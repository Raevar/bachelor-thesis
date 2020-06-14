package minijava.util;

public class Ident
{
	private static String globalIdent = "";
	public static String get(){ return globalIdent; }
	public static void inc() {globalIdent+="  ";}
	public static void dec() {if (globalIdent.length()>0)globalIdent = globalIdent.substring(2);}
	private Ident() {}
}

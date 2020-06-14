package my.project;

import static my.project.MySpec.NonTerminals.*;
import static my.project.MySpec.Terminals.*;

/**
 * MyScanner provides a scanner for the {@link MySpec} grammar.
 * 
 */
%%
%class MyScanner
%cup2
%%
"+" { return token(PLUS); }
"*" { return token(TIMES); }
";" { return token(SEMI); }
"(" { return token(LPAREN); }
")" { return token(RPAREN); }
[0-9]+ { return token(NUMBER, new Integer(yytext())); }
[ \t\r\n\f] { /* ignore white space. */ }
. { System.err.println("Illegal character: "+yytext()); }
